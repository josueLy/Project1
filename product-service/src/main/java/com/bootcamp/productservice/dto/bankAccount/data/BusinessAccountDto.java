package com.bootcamp.productservice.dto.bankAccount.data;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.bankAccount.interfaces.ISavingBankAccountDto;
import com.bootcamp.productservice.dto.payment.PaymentDto;
import com.bootcamp.productservice.model.*;
import com.bootcamp.productservice.repository.IBusinessAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            Mono<Boolean> haveDue = getQuotasAndValidateDues(business);
            return haveDue.flatMap(have_due->saveIt(bank_account,have_due));
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
            // validate if the personnel client has a Due
            Mono<Boolean> haveDue = getQuotasAndValidateDues(business_accounts.get(0).getBusiness());
            return haveDue.flatMap(have_due->saveIt(bank_account,have_due));
        } else {
            return Mono.error(new GeneralException(Util.CLIENT_DONT_HAVE_CREDIT_ACCOUNT));
        }
    }

    private Mono<Boolean> getQuotasAndValidateDues(Business business)
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
        int last_day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String last_date = last_day+"/"+current_month+"/"+current_year;

        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(last_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PaymentDto paymentDto = new PaymentDto(null,business.getBusinessId(),starDate,endDate);

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
        boolean HasDue= true;
        if(quotas.size()>0) {
            for (Quota quota : quotas) {
                if (!quota.getExpirationDate().before(new Date())) {
                    HasDue = false;
                    break;
                }
            }
        }else
        {
            return  Mono.just(true);
        }

        return Mono.just(HasDue);
    }


    private Mono<Bank_Account> saveIt(Bank_Account bank_account, boolean haveDue)
    {
        if(!haveDue)
           return bankAccountRepository.save(bank_account);
        else
            return  Mono.error(new GeneralException(Util.CANNOT_CREATE_ACCOUNT));

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
