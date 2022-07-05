package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.businessAccount.BusinessAccountDto;
import project1.model.Bank_Account;
import project1.model.Business;
import project1.model.Business_Account;
import project1.repository.IBankAccountRepository;
import project1.repository.IBusinessAccountRepository;
import project1.repository.IBusinessRepository;
import project1.service.interfaces.IBusinessAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessAccountServiceImpl implements IBusinessAccountService {

    @Autowired
    private IBusinessAccountRepository businessAccountRepository;

    @Autowired
    private IBusinessRepository businessRepository;

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Override
    public Flux<Business_Account> findAll() {
        return businessAccountRepository.findAll();
    }

    @Override
    public Mono<Business_Account> save(BusinessAccountDto bussinessDto) {


        Mono<Business> businessMono = businessRepository.findById(bussinessDto.getBusinessId());
        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(bussinessDto.getAccountId());

        Mono<Business_Account> businessAccountMono = null;

        businessAccountMono = Mono.zip(businessMono,bankAccountMono).map(data->{
            Business_Account businessAccountObj = new Business_Account();

            businessAccountObj.setBusiness(data.getT1());
            businessAccountObj.setAccount(data.getT2());

            return businessAccountObj;
        });

        businessAccountMono = bankAccountMono.flatMap(result -> {
            return businessAccountRepository.save(result);
        });


        /*businessAccountObj=Mono.zip(businessMono,bankAccountMono).map(data ->{

            businessAccountObj.setBusiness(data.getT1());

            businessAccountObj.setAccount(data.getT2());

            return

        });



        /*

        Mono<Business> businessMono = businessRepository.findById(bussinessDto.getBusinessId());

        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(bussinessDto.getAccountId());

        Mono<Business_Account> businessAccountMono = Mono.just(new Business_Account());

        Business_Account businessAccount = new Business_Account();

        businessAccountMono = businessMono.map(result -> {


            businessAccount.setBusiness(result);

            return businessAccount;
        });

        businessAccountMono = bankAccountMono.map(result -> {

            businessAccount.setAccount(result);

            return businessAccount;
        }).flatMap(result -> {

            return businessAccountRepository.save(businessAccount);
        });

        return businessAccountMono;

         */


    }

    @Override
    public Mono<Business_Account> update(BusinessAccountDto business) {

        Mono<Business> businessMono = businessRepository.findById(business.getBusinessId());
        //Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(business.getBusinessId(business.getAccountId()));


        /*
        Mono<Business_Account> businessAccountMono= Mono.just(new Business_Account());

        Business_Account businessAccount = new Business_Account();

        businessAccountMono=businessMono.map(result->{


            businessAccount.setBusiness(result);

            return businessAccount;
        });

        businessAccountMono=bankAccountMono.map(result->{

            businessAccount.setAccount(result);

            return  businessAccount;
        }).flatMap(result->{

            return  businessAccountRepository.save(businessAccount);
        });

        return  businessAccountMono;

    }

         */
        return null;
    }

}
