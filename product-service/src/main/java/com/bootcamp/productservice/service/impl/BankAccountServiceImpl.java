package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.BusinessAcount.AccountDto;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.bankAccount.data.BusinessAccountDto;
import com.bootcamp.productservice.dto.bankAccount.data.PersonnelBankAccountDto;
import com.bootcamp.productservice.dto.client.PersonnelDto;
import com.bootcamp.productservice.dto.monedero.MonederoDto;
import com.bootcamp.productservice.dto.transaction.TransactionDto;
import com.bootcamp.productservice.model.*;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import com.bootcamp.productservice.repository.IBusinessAccountRepository;
import com.bootcamp.productservice.repository.IProduct_TypeRepository;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
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

    @Autowired
    private PersonnelBankAccountDto personnelBankAccountDto;

    @Autowired
    private BusinessAccountDto businessAccountDto;

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

            Mono<List<Bank_Account>> bankAccountsMonoList= personnelMono.flatMap(this::getMonoListOfPersonnelAccounts);

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
                    .flatMap(this::getMonoListOfBusinessAccount);

            //Mono<List<Bank_Account>> to Flux<BankAccount>
            return bankAccountsMonoList
                    .flatMapMany(Flux::fromIterable)
                    .log();

        }else {
            return Flux.error(new GeneralException("Datos enviados vacios"));
        }
    }



    private Mono<List<Bank_Account>> getMonoListOfPersonnelAccounts(Personnel personnel)
    {
        // List to Mono   Mono<List<Bank_Account>>
        return Mono.just(personnel.getAccounts());
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
            bank_account.setCard_number(bankAccountDto.getCard_number());
            bank_account.setMax_number_transactions(bankAccountDto.getMax_number_transactions());

            return bank_account;
        });

        bankAccountMono=bankAccountMono.flatMap(bank_account -> this.IsPrincipalAccount(bank_account,bankAccountDto));

        return bankAccountMono
                .flatMap(bank_account -> saveClientAndBankAccount(bank_account, bankAccountDto));

    }

    private Mono<Bank_Account> IsPrincipalAccount(Bank_Account bank_account,BankAccountDto bankAccountDto)
    {
        if(bankAccountDto.getIsPrincipal_account()>0)
        {
            bank_account.setPrincipal_account(true);
        }else
        {
            bank_account.setPrincipal_account(false);
        }

        return  Mono.just(bank_account);

    }

    private Mono<Bank_Account> saveClientAndBankAccount(Bank_Account bank_account, BankAccountDto bankAccountDto) {
        if (bankAccountDto.getPersonnelId() != null && !bankAccountDto.getPersonnelId().equals(""))
        {
            return personnelBankAccountDto.save(bank_account, bankAccountDto);

        } else if (bankAccountDto.getBusinessId() != null && !bankAccountDto.getBusinessId().equals("")) {

            return businessAccountDto.save(bank_account, bankAccountDto);

        } else
        {
            return Mono.error(new GeneralException(Util.EMPTY_ID));
        }

    }
  //3.
  @Override
  public Flux<Product_Type> listProducts(BankAccountDto bankAccountDto) {

      return  getProducts(bankAccountDto);
  }
  private Flux<Product_Type> getProducts(BankAccountDto bankAccountDto)
  {
      if(bankAccountDto.getPersonnelId()!=null && !bankAccountDto.getPersonnelId().equals(""))
      {
          Flux<Bank_Account> bankAccountFlux= getPersonnelProducts(bankAccountDto);

          return  bankAccountFlux
                  .collectList()
                  .flatMapMany(bankAccounts -> getProducts((BankAccountDto) bankAccounts));
      }
      else if(bankAccountDto.getBusinessId()!=null && !bankAccountDto.getBusinessId().equals(""))
      {
          Flux<Bank_Account> bankAccountFlux= getBusinnesProducts(bankAccountDto);

          return  bankAccountFlux
                  .collectList()
                  .flatMapMany(bankAccounts -> getProducts((BankAccountDto) bankAccounts));
      }else
      {
          return Flux.error(new GeneralException(Util.EMPTY_ID));
      }

  }
  /*
  private Flux<Product_Type> getProducts(List<Bank_Account> bankAccounts){
        List<Product_Type> productTypes=new ArrayList<>();
        for(Bank_Account bank_account: bankAccounts){
            productTypes.addAll(bank_account.getProduct_type())
        }
  }

   */

    private Flux<Bank_Account> getPersonnelProducts(BankAccountDto bankAccountDto){
        Mono<Personnel> personnelMono  =
                 webClientBuilder.build()
                         .get()
                         .uri("http://localhost:8085/personnel/showById/"+bankAccountDto.getPersonnelId())
                         .retrieve()
                         .bodyToMono(Personnel.class);
        return personnelMono.flatMapMany(personnel -> bankAccountRepository.findAllPersonnelByCreationDateBetween(personnel,bankAccountDto.getStartDate(),bankAccountDto.getEndDate()));
    }
    private Flux<Bank_Account> getBusinnesProducts(BankAccountDto bankAccountDto){
        Mono<Business> businessMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/personnel/showById/"+bankAccountDto.getBusinessId())
                        .retrieve()
                        .bodyToMono(Business.class);
        return businessMono.flatMapMany(business -> bankAccountRepository.findAllBusinessByCreationDateBetween(business,bankAccountDto.getStartDate(),bankAccountDto.getEndDate()));
    }

    @Override
    public Mono<Bank_Account> update(BankAccountDto bankAccountDto) {

        //get the mono of bank account by Id
        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(bankAccountDto.getBankAccountId());
        Mono<Product_Type> productTypeMono = product_typeRepository.findById(bankAccountDto.getProductTypeId());

        return Mono.zip(bankAccountMono, productTypeMono)
                .map(/*get the bank_Account Object from mono*/data -> {
                    //Set the bank account and assign the mono of bank_Account (bankAcountMono)
                    Bank_Account bank_account = data.getT1();

                    bank_account.setProduct_type(data.getT2());
                    bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
                    bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());
                    bank_account.setComission(bankAccountDto.getComission());
                    bank_account.setCard_number(bankAccountDto.getCard_number());
                    bank_account.setMax_number_transactions(bankAccountDto.getMax_number_transactions());

                    return bank_account;
                })
                .flatMap(bank_account -> updateClientAndBankAccount(bank_account, bankAccountDto));

    }

    private Mono<Bank_Account> updateClientAndBankAccount(Bank_Account bank_account, BankAccountDto bankAccountDto) {
        if (bankAccountDto.getPersonnelId() != null && !bankAccountDto.getPersonnelId().equals(""))
        {
            return personnelBankAccountDto.update(bank_account, bankAccountDto);

        } else if (bankAccountDto.getBusinessId() != null && !bankAccountDto.getBusinessId().equals("")) {

            return businessAccountDto.update(bank_account, bankAccountDto);

        } else
        {
            return Mono.error(new GeneralException(Util.EMPTY_ID));
        }
    }

    @Override
    public Mono<Void> delete(String id) {

        return bankAccountRepository.deleteById(id);
    }

    @KafkaListener(topics = "recieve-send-topic", groupId = "group_id")
    private void updateAmountOfPrincipalAccount(String message)
    {
        MonederoDto monederoDto = new Gson().fromJson(message, MonederoDto.class);
        String dni = monederoDto.getDni();
        Mono<Personnel> personnelMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/personnel/show/" + monederoDto.getDni())
                        .retrieve()
                        .bodyToMono(Personnel.class);


        Mono<Business> businessMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/business/showDni/" + monederoDto.getDni())
                        .retrieve()
                        .bodyToMono(Business.class);


           personnelMono.subscribe(personnel -> updatePersonnelAccount(personnel, monederoDto));

           businessMono.subscribe(business -> updateBusinessAccount(business, monederoDto));


    }

    private void updatePersonnelAccount(Personnel personnel,MonederoDto monederoDto)
    {
        if(personnel!=null && personnel.getAccounts()!=null && personnel.getAccounts().size()>0) {
            List<Bank_Account> bank_accounts = personnel.getAccounts();

            personnel.setAccounts(setAmount(bank_accounts, monederoDto,personnel.getIdPersonal()));


            PersonnelDto personnelDto = new PersonnelDto(personnel.getIdPersonal(),
                    personnel.getDni(), personnel.getName(), personnel.getPhoneNumber(),
                    personnel.getEmailAddress(), personnel.getPassaport(), personnel.getAccounts());


            webClientBuilder.build()
                    .put()
                    .uri("http://localhost:8085/personnel/update")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(personnelDto), PersonnelDto.class)
                    .retrieve()
                    .bodyToMono(Personnel.class)
                    .subscribe();
        }
    }

    private List<Bank_Account> setAmount(List<Bank_Account> bank_accounts, MonederoDto monederoDto,String personnelId)
    {
        Bank_Account account = null;
        for (int i = 0; i < bank_accounts.size(); i++) {
            account = bank_accounts.get(i);
            if (account.isPrincipal_account() && monederoDto.getPaymentType().equals(Util.RECIEVE_PAYMENT)) {
                double currentAvaliableBalance = account.getAvailableBalance() + monederoDto.getAmount();
                account.setAvailableBalance(account.getAvailableBalance() + monederoDto.getAmount());

                bank_accounts.set(i, account);
                break;
            } else if (account.isPrincipal_account() && monederoDto.getPaymentType().equals(Util.SEND_PAYMENT)) {
                double currentAvaliableBalance = account.getAvailableBalance() - monederoDto.getAmount();
                account.setAvailableBalance(currentAvaliableBalance);

                bank_accounts.set(i, account);
                break;
            }

        }
        bankAccountRepository.save(account).subscribe();
        saveTransactionOfMonederoMovil(account,personnelId,null,monederoDto);
        return  bank_accounts;

    }

    private void updateBusinessAccount(Business business,MonederoDto monederoDto)
    {
        if(business!=null) {
            Flux<Business_Account> accountFlux = businessAccountRepository.findAllByBusiness(business);

            accountFlux
                    .collectList()
                    .subscribe(business_accounts -> setAmountOfBusinessAcount(business_accounts, monederoDto));
        }
    }

    private void setAmountOfBusinessAcount(List<Business_Account> business_accounts, MonederoDto monederoDto)
    {
        if(business_accounts!=null && business_accounts.size()>0) {
            Business_Account business_account = null;
            Bank_Account bank_account = null;
            for (int i = 0; i < business_accounts.size(); i++) {
                business_account = business_accounts.get(i);

                if (business_account.getAccount().isPrincipal_account() && monederoDto.getPaymentType().equals(Util.RECIEVE_PAYMENT)) {
                    bank_account = business_account.getAccount();
                    double currentAvaliableBalance = bank_account.getAvailableBalance() + monederoDto.getAmount();
                    bank_account.setAvailableBalance(currentAvaliableBalance);

                } else if (business_account.getAccount().isPrincipal_account() && monederoDto.getPaymentType().equals(Util.SEND_PAYMENT)) {
                    bank_account = business_account.getAccount();
                    double currentAvaliableBalance = bank_account.getAvailableBalance() - monederoDto.getAmount();
                    bank_account.setAvailableBalance(currentAvaliableBalance);

                }

            }

            bankAccountRepository.save(bank_account).subscribe();
            business_account.setAccount(bank_account);
            businessAccountRepository.save(business_account).subscribe();
            saveTransactionOfMonederoMovil(bank_account,null,business_account.getBusiness().getBusinessId(),monederoDto);
        }

    }

    private void saveTransactionOfMonederoMovil(Bank_Account bank_account,String personnelId,String businessId,MonederoDto monederoDto)
    {
        TransactionDto transactionDto =null;

            transactionDto = new TransactionDto(null,personnelId,bank_account.getAccountId(),
                    businessId,bank_account.getNumberAccount(),Util.DEPOSIT_TRANSACTION,monederoDto.getAmount(),0);


        webClientBuilder.build()
                .post()
                .uri("http://localhost:8087/transaction/save")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(transactionDto), TransactionDto.class)
                .retrieve()
                .bodyToMono(TransactionDto.class)
                .subscribe(System.out::println);


    }





}
