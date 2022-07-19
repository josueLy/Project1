package com.bootcamp.productservice.dto.bankAccount.data;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.bankAccount.interfaces.ISavingBankAccountDto;
import com.bootcamp.productservice.dto.client.PersonnelDto;
import com.bootcamp.productservice.model.*;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import com.bootcamp.productservice.repository.IBusinessAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class BusinessAccountDto extends ClientBankAccountDto implements ISavingBankAccountDto  {

    boolean isVIP=false;

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
        Mono<Bank_Account> bankAccountMono= businessMono.flatMap(business -> saveBusinessBankAccount(bank_account, business));

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

        if (bank_account.getProduct_type().getDescription().equals(Util.VIP_PRODUCT)) {
            // get Accounts
            Flux<Business_Account> businessAccountFlux = businessAccountRepository.findAll();

            return businessAccountFlux.collectList()
                    .flatMap(business_accounts -> validateVIPAndSaveBusinessAccount(bank_account,business_accounts));

        } else{

            return bankAccountRepository.save(bank_account);

        }
    }

    private Mono<Bank_Account> validateVIPAndSaveBusinessAccount(Bank_Account bank_account,List<Business_Account> business_accounts) {

        if(business_accounts.size()>0) {
            for (Business_Account business_account : business_accounts) {

                if (business_account.getAccount().getProduct_type().getDescription().equals(Util.CREDIT_PRODUCT)) {
                    isVIP = true;
                    break;
                }
            }
        }

        if (isVIP) {

            return bankAccountRepository.save(bank_account);
        } else {
            return Mono.error(new GeneralException(Util.CLIENT_DONT_HAVE_CREDIT_ACCOUNT));
        }
    }
}
