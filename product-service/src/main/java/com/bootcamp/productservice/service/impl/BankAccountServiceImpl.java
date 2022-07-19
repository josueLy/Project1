package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.BusinessAcount.AccountDto;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.client.PersonnelDto;
import com.bootcamp.productservice.model.*;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import com.bootcamp.productservice.repository.IBusinessAccountRepository;
import com.bootcamp.productservice.repository.IProduct_TypeRepository;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Autowired
    private IProduct_TypeRepository product_typeRepository;

    @Autowired
    private IBusinessAccountRepository businessAccountRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;



    @Override
    public Flux<Bank_Account> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Mono<Bank_Account> show(String id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public Flux<Bank_Account> showAccountsByClient(AccountDto accountDto) {

        if(accountDto.getPersonnelId()!=null && !accountDto.getPersonnelId().equals(""))// Get the products of Personnel Client
        {
            //get Personnel by Id
            Mono<Personnel> personnelMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/personnel/showById/" + accountDto.getPersonnelId()
                            )
                            .retrieve()
                            .bodyToMono(Personnel.class);

            Mono<List<Bank_Account>> bankAccountsMonoList= personnelMono.flatMap(personnel -> getMonoListOfPersonnelAccounts(personnel));

            return bankAccountsMonoList
                    .flatMapMany(Flux::fromIterable)
                    .log();
        }
        else if(accountDto.getBusinessId()!=null && !accountDto.getBusinessId().equals(""))// Get the products of business client
        {

            // Get the business client by id
            Mono<Business> businessMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/business/show/" + accountDto.getBusinessId()
                            ).retrieve()
                            .bodyToMono(Business.class);

            Flux<Business_Account> businessAccountFlux=businessMono
                    .flatMapMany(business -> businessAccountRepository.findAllByBusiness(business));

            Mono<List<Bank_Account>> bankAccountsMonoList= businessAccountFlux.collectList()
                    .flatMap(business_accounts -> getMonoListOfBusinessAccount(business_accounts));

            return bankAccountsMonoList
                    .flatMapMany(Flux::fromIterable)
                    .log();

        }else {
            return Flux.error(new GeneralException("Datos enviados vacios"));
        }
    }

    private Mono<List<Bank_Account>> getMonoListOfPersonnelAccounts(Personnel personnel)
    {
        List<Bank_Account> bank_accounts= new ArrayList<>();

        for(Bank_Account bank_account : personnel.getAccounts())
        {
            bank_accounts.add(bank_account);
        }

        return Mono.just(bank_accounts);
    }

    private  Mono<List<Bank_Account>> getMonoListOfBusinessAccount(List<Business_Account> business_accounts)
    {
        List<Bank_Account> bank_accounts= new ArrayList<>();

        for(Business_Account business_account : business_accounts)
        {
            bank_accounts.add(business_account.getAccount());
        }

        return  Mono.just(bank_accounts);
    }

    @Override
    public Mono<Bank_Account> save(BankAccountDto bankAccountDto) {


        Mono<Product_Type> productTypeMono = product_typeRepository.findById(bankAccountDto.getProductTypeId());


        Mono<Bank_Account> bankAccountMono = productTypeMono.map(product_type -> {

            Bank_Account bank_account = new Bank_Account();
            bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());
            bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
            bank_account.setComission(bankAccountDto.getComission());
            bank_account.setProduct_type(product_type);

            return bank_account;
        });


        return bankAccountMono.flatMap(bank_account -> saveClientAndBankAccount(bank_account, bankAccountDto));
    }

    private Mono<Bank_Account> saveClientAndBankAccount(Bank_Account bank_account, BankAccountDto bankAccountDto) {
        if (bankAccountDto.getPersonnelId() != null && !bankAccountDto.getPersonnelId().equals("")) {
            //get Personnel by Id
            Mono<Personnel> personnelMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/personnel/showById/" + bankAccountDto.getPersonnelId()
                            )
                            .retrieve()
                            .bodyToMono(Personnel.class);

            //Save in the account in the personnel table or document
            return  personnelMono.flatMap(personnel -> savePersonnelAccount(bank_account, personnel));

        } else if (bankAccountDto.getBusinessId() != null && !bankAccountDto.getBusinessId().equals("")) {
            // Get the business client by id
            Mono<Business> businessMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/business/show/" + bankAccountDto.getBusinessId()
                            ).retrieve()
                            .bodyToMono(Business.class);

            // Save in the business account table
            Mono<Bank_Account> bankAccountMono= businessMono
                    .flatMap(business -> saveBusinessBankAccount(bank_account, business));

            return  Mono.zip(bankAccountMono,businessMono)
                    .flatMap(data->{
                        Business_Account business_account= new Business_Account();
                        business_account.setAccount(data.getT1());
                        business_account.setBusiness(data.getT2());
                        return businessAccountRepository.save(business_account);
                    })
                    .map(Business_Account::getAccount);
        } else {
            return Mono.error(new GeneralException(Util.EMPTY_ID));
        }

    }

    private Mono<Bank_Account> savePersonnelAccount(Bank_Account bank_account, Personnel personnel) {

        if (validatePersonnelVipAccount(bank_account.getProduct_type(), personnel)) {

            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            // add account and save the personnel client
            return  bankAccountMono.flatMap(account -> savePersonnel(personnel, account));
        } else if (!bank_account.getProduct_type().getDescription().equals(Util.VIP_PRODUCT)) {
            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            return   bankAccountMono.flatMap(account -> savePersonnel(personnel, account));
        } else {
            return Mono.error(new GeneralException(Util.CLIENT_DONT_HAVE_CREDIT_ACCOUNT));
        }

    }

    private boolean validatePersonnelVipAccount(Product_Type product_type, Personnel personnel) {
        boolean isCreditProduct = false;
        if (product_type.getDescription().equals(Util.VIP_PRODUCT) && personnel.getAccounts() != null) {
            // get Accounts
            for (Bank_Account bankAccount : personnel.getAccounts()) {
                if (bankAccount.getProduct_type().getDescription().equals(Util.CREDIT_PRODUCT)) {
                    isCreditProduct = true;
                    break;
                }
            }
            return isCreditProduct;

        } else {
            return false;
        }

    }

    private Mono<Bank_Account> savePersonnel(Personnel personnel, Bank_Account bank_account) {

        if (personnel.getAccounts() != null) {
            personnel.getAccounts().add(bank_account);
        } else {
            List<Bank_Account> accounts = new ArrayList<>();
            accounts.add(bank_account);
            personnel.setAccounts(accounts);
        }

        PersonnelDto personnelDto = new PersonnelDto(personnel.getIdPersonal(),
                personnel.getDni(),personnel.getName(),personnel.getPhoneNumber(),
                personnel.getEmailAddress(),personnel.getPassaport(),personnel.getAccounts());

        // call create method of Client Service
         webClientBuilder.build()
                .put()
                .uri("http://localhost:8085/personnel/update")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(personnelDto), PersonnelDto.class)
                .retrieve()
                .bodyToMono(Personnel.class)
                .subscribe(System.out::println);

        return  Mono.just(bank_account);
    }

    private Mono<Bank_Account> saveBusinessBankAccount(Bank_Account bank_account, Business business) {

        if (bank_account.getProduct_type().getDescription().equals(Util.PYME_PRODUCT)) {
            // get Accounts
            Flux<Business_Account> businessAccountFlux = businessAccountRepository.findAllByBusiness(business);

            return businessAccountFlux.collectList()
                    .flatMap(business_accounts -> validatePYMEAndSaveBusinessAccount(bank_account,business_accounts));

        } else
        {
            return bankAccountRepository.save(bank_account);
        }


    }

    private Mono<Bank_Account> validatePYMEAndSaveBusinessAccount(Bank_Account bank_account,List<Business_Account> business_accounts) {
        boolean  isPYME=false;
        if(business_accounts.size()>0) {
            for (Business_Account business_account : business_accounts) {

                if (business_account.getAccount().getProduct_type().getDescription().equals(Util.CREDIT_PRODUCT)) {
                    isPYME = true;
                    break;
                }
            }
        }

        if (isPYME) {

            return bankAccountRepository.save(bank_account);
        } else {
            return Mono.error(new GeneralException(Util.CLIENT_DONT_HAVE_CREDIT_ACCOUNT));
        }
    }

    @Override
    public Mono<Bank_Account> update(BankAccountDto bankAccountDto) {

        //get the mono of bank account by Id
        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(bankAccountDto.getBankAccountId());
        Mono<Product_Type> productTypeMono = product_typeRepository.findById(bankAccountDto.getProductTypeId());


        bankAcountMono = Mono.zip(bankAcountMono, productTypeMono).map(/*get the bank_Account Object from mono*/data -> {
            //Set the bank account and assign the mono of bank_Account (bankAcountMono)
            Bank_Account bank_account = data.getT1();

            bank_account.setProduct_type(data.getT2());
            bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
            bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());
            bank_account.setComission(bankAccountDto.getComission());
            return bank_account;
        }).flatMap(
                //save the new bank_account
                bankAccountRepository::save);

        return bankAcountMono;
    }


    @Override
    public Mono<Void> delete(String id) {

        return bankAccountRepository.deleteById(id);
    }


}
