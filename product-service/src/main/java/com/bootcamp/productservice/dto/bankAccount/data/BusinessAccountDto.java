package com.bootcamp.productservice.dto.bankAccount.data;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.bankAccount.interfaces.ISavingBankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Business;
import com.bootcamp.productservice.model.Business_Account;
import com.bootcamp.productservice.repository.IBusinessAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class BusinessAccountDto extends ClientBankAccountDto implements ISavingBankAccountDto  {

    @Autowired
    private IBusinessAccountRepository businessAccountRepository;

    @Override
    public Mono<Bank_Account> save(Bank_Account bank_account, BankAccountDto bankAccountDto) {
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
    public Mono<Bank_Account> update(Bank_Account bank_account, BankAccountDto bankAccountDto) {

        // Get the business client by id
        Mono<Business> businessMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/business/show/" + bankAccountDto.getBusinessId()
                        ).retrieve()
                        .bodyToMono(Business.class);

        // Save in the business account table
        Mono<Bank_Account> bankAccountMono= businessMono
                .flatMap(business -> updateBusinessBankAccount(bank_account, business));

        return  Mono.zip(bankAccountMono,businessMono)
                .flatMap(data->{
                    Business_Account business_account= new Business_Account();
                    business_account.setAccount(data.getT1());
                    business_account.setBusiness(data.getT2());
                    return businessAccountRepository.save(business_account);
                })
                .map(Business_Account::getAccount);

    }

    private Mono<Bank_Account> updateBusinessBankAccount(Bank_Account bank_account, Business business) {

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



}
