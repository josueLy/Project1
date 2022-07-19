package com.bootcamp.productservice.dto.bankAccount.data;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.bankAccount.interfaces.ISavingBankAccountDto;
import com.bootcamp.productservice.dto.client.PersonnelDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Personnel;
import com.bootcamp.productservice.model.Product_Type;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class PersonnelBankAccountDto  extends ClientBankAccountDto implements ISavingBankAccountDto {



    @Override
    public Mono<Bank_Account> save(Bank_Account bank_account,BankAccountDto bankAccountDto) {
        this.bank_account=bank_account;
        this.bankAccountDto= bankAccountDto;

        //get Personnel by Id
        Mono<Personnel> personnelMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/personnel/showById/" + bankAccountDto.getPersonnelId()
                        )
                        .retrieve()
                        .bodyToMono(Personnel.class);

        //Save in the account in the personnel table or document
        return  personnelMono.flatMap(personnel -> savePersonnelAccount(personnel));


    }

    private Mono<Bank_Account> savePersonnelAccount(Personnel personnel) {

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

        // call create's method of Client Service
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
}
