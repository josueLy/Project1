package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

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

    private boolean isVIP= false;

    @Override
    public Flux<Bank_Account> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Mono<Bank_Account> show(String id) {
        return bankAccountRepository.findById(id);
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


        return bankAccountMono.flatMap(bank_account -> {
            return saveClientAndBankAccount(bank_account, bankAccountDto);
        });
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
           Mono<Bank_Account> bankAccountMono= personnelMono.flatMap(personnel -> {
                return savePersonnelAccount(bank_account, personnel);
            });



            return bankAccountMono;

        } else if (bankAccountDto.getBusinessId() != null && !bankAccountDto.getBusinessId().equals("")) {
            // Get the business client by id
            Mono<Business> businessMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/business/show/" + bankAccountDto.getBusinessId()
                            ).retrieve()
                            .bodyToMono(Business.class);
            // Save in the business account table
            Mono<Bank_Account> bankAccountMono= businessMono.flatMap(business -> {
                return saveBusinessAccount(bank_account, business);
            });

            return bankAccountMono;
        } else {
            return Mono.error(new GeneralException(Util.EMPTY_ID));
        }

    }

    private Mono<Bank_Account> savePersonnelAccount(Bank_Account bank_account, Personnel personnel) {

        if (validatePersonnelVipAccount(bank_account.getProduct_type(), personnel)) {

            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            // add account and save the personnel client
            return  bankAccountMono.flatMap(account -> {
                return savePersonnel(personnel, account);
            });
        } else if (!bank_account.getProduct_type().getDescription().equals(Util.VIP_PRODUCT)) {
            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            return   bankAccountMono.flatMap(account -> {
                return savePersonnel(personnel, account);
            });
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

    private Mono<Bank_Account> saveBusinessAccount(Bank_Account bank_account, Business business) {
        validateBusinessVipAccount(bank_account.getProduct_type(), business);
        if (isVIP) {

            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            Business_Account business_account = new Business_Account();
            business_account.setAccount(bank_account);
            business_account.setBusiness(business);

            //Call create Method of Client Service

            return bankAccountMono;
        } else if (!bank_account.getProduct_type().getDescription().equals(Util.VIP_PRODUCT)) {
            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            Business_Account business_account = new Business_Account();
            business_account.setAccount(bank_account);
            business_account.setBusiness(business);

            //Call create Method of Client Service

            return bankAccountMono;
        } else {
            return Mono.error(new GeneralException(Util.CLIENT_DONT_HAVE_CREDIT_ACCOUNT));
        }

    }

    private Mono<Boolean> validateBusinessVipAccount(Product_Type product_type, Business business) {

        if (product_type.getDescription().equals(Util.VIP_PRODUCT)) {
            // get Accounts
            Flux<Business_Account> businessAccountFlux = businessAccountRepository.findByBusinessBusinessId(business.getBusinessId());



            return businessAccountFlux.collectList()
                    .flatMap(business_accounts -> {
                        return validateIfBusinessHaveACreditAccount(business_accounts);
                    });


        } else {
            return Mono.just(false);
        }

    }

    private Mono<Boolean> validateIfBusinessHaveACreditAccount(List<Business_Account> business_accounts)
    {

            if(business_accounts!= null && business_accounts.size()>0)
            {
                for (Business_Account business_account :business_accounts)
                {
                     if(business_account.getAccount().getProduct_type().equals(Util.CREDIT_PRODUCT))
                     {
                         isVIP= true;
                     }
                }
            }
            return Mono.just(isVIP);

    }


    //    private void saveBusinessAccount(Bank_Account bank_account, BankAccountDto bankAccountDto)
//    {
//
//
//        Mono<Business_Account> businessAccountMono= businessMono.flatMap(business -> {
//            Business_Account  business_account= new Business_Account();
//            business_account.setBusiness(business);
//            business_account.setAccount(bank_account);
//            return businessAccountRepository.save(business_account);
//        });
//    }
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
