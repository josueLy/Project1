package com.bootcamp.productservice.dto.bankAccount.data;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.bankAccount.interfaces.ISavingBankAccountDto;
import com.bootcamp.productservice.dto.client.PersonnelDto;
import com.bootcamp.productservice.dto.payment.PaymentDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Personnel;
import com.bootcamp.productservice.model.Product_Type;
import com.bootcamp.productservice.model.Quota;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
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
        return  personnelMono.flatMap(this::savePersonnelAccount);

    }

    private Mono<Bank_Account> savePersonnelAccount(Personnel personnel) {

        if (validatePersonnelVipAccount(bank_account.getProduct_type(), personnel)) {

            // validate if the personnel client has a Due
            Mono<Boolean> doesnthaveDue = getQuotasAndValidateDues(personnel);

            // add account and save the personnel client
            return doesnthaveDue.flatMap(nothavedue->this.saveIt(personnel,bank_account,nothavedue));
        } else if (!bank_account.getProduct_type().getDescription().equals(Util.VIP_PRODUCT)) {
            // validate if the personnel client has a Due
            Mono<Boolean> doesnthaveDue = getQuotasAndValidateDues(personnel);

            return   doesnthaveDue.flatMap(nothavedue->this.saveIt(personnel,bank_account,nothavedue));
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

    private Mono<Boolean> getQuotasAndValidateDues(Personnel personnel)
    {
        Calendar calendar = Calendar.getInstance();

        // First Day of Month
        int first_day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int current_month = calendar.get(Calendar.MONTH)+1;
        int current_year = calendar.get(Calendar.YEAR);

        String first_date =first_day+"/"+current_month+"/"+current_year;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date starDate = null;
        try {
            starDate = simpleDateFormat.parse(first_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Last day of Month
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date endDate= calendar.getTime();
        PaymentDto paymentDto = new PaymentDto(personnel.getIdPersonal(),null,starDate,endDate);

        //get List of Quotas
        Flux<Quota> quotaFlux =
                webClientBuilder.build()
                        .post()
                        .uri("http://localhost:8087/payment/listQuotas")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(Mono.just(paymentDto), PaymentDto.class)
                        .retrieve()
                        .bodyToFlux(Quota.class);

        return  quotaFlux
                .collectList()
                .flatMap(this::validateDue);
    }

    private Mono<Boolean> validateDue(List<Quota> quotas)
    {
         boolean notHasDue= false;
         if(quotas.size()>0) {
             for (Quota quota : quotas) {
                 if (quota.getExpirationDate().before(new Date())) {
                     notHasDue = true;
                     break;
                 }
             }
         }else
         {
             return  Mono.just(true);
         }

        return Mono.just(notHasDue);
    }

    private Mono<Bank_Account> saveIt(Personnel personnel,Bank_Account bank_account,boolean doesnthaveDue)
    {
        if(doesnthaveDue)
        {
            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            return  bankAccountMono.flatMap(account->this.savePersonnel(personnel,bank_account));

        }else {
            return Mono.error(new GeneralException(Util.CANNOT_CREATE_ACCOUNT));
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

    @Override
    public Mono<Bank_Account> update(Bank_Account bank_account, BankAccountDto bankAccountDto) {
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

        //Update the personnel Account and validate the VIP product
        return  personnelMono.flatMap(this::updatePersonnelAccount);
    }


    private Mono<Bank_Account> updatePersonnelAccount(Personnel personnel) {

        if (validatePersonnelVipAccountToUpdate(bank_account.getProduct_type(), personnel)) {

            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            // add account and save the personnel client
            return  bankAccountMono.flatMap(account -> savePersonnel(personnel, account));
        } else if (!bank_account.getProduct_type().getDescription().equals(Util.VIP_PRODUCT)) {
            Mono<Bank_Account> bankAccountMono = bankAccountRepository.save(bank_account);

            return   bankAccountMono.flatMap(account -> updatePersonnel(personnel, account));
        } else {
            return Mono.error(new GeneralException(Util.CLIENT_DONT_HAVE_CREDIT_ACCOUNT));
        }

    }

    private boolean validatePersonnelVipAccountToUpdate(Product_Type product_type, Personnel personnel) {
        boolean isCreditProduct = false;
        if (product_type.getDescription().equals(Util.VIP_PRODUCT) && personnel.getAccounts() != null && personnel.getAccounts().size()>2) {
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

    private Mono<Bank_Account> updatePersonnel(Personnel personnel, Bank_Account bank_account) {

        if (personnel.getAccounts() != null) {
            for(int i =0 ; i<personnel.getAccounts().size();i++)
            {
                if(bank_account.getAccountId().equals(personnel.getAccounts().get(i).getAccountId()))
                {
                    personnel.getAccounts().remove(i);
                    break;
                }
            }
            personnel.getAccounts().add(bank_account);
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
