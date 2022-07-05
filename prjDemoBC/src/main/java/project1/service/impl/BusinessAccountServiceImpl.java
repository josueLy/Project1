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

        businessAccountMono = businessAccountMono.flatMap(result -> {
            return businessAccountRepository.save(result);
        });

        return businessAccountMono;

    }

    @Override
    public Mono<Business_Account> update(BusinessAccountDto businessDto) {

        Mono<Business> businessMono = businessRepository.findById(businessDto.getBusinessId());

        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(businessDto.getAccountId());

        Mono<Business_Account> businessAccountMono = businessAccountRepository.findById(businessDto.getIdBusinessAccount());


        businessAccountMono = Mono.zip(businessMono,bankAccountMono,businessAccountMono).map(data ->{
            Business_Account businessAccountObj = data.getT3();

            businessAccountObj.setBusiness(data.getT1());
            businessAccountObj.setAccount(data.getT2());
            //businessAccountObj.setIdBusinessAccount(data.getT3());

            return  businessAccountObj;

        });
        businessAccountMono = businessAccountMono.flatMap(result -> {
            return businessAccountRepository.save(result);
        });

        return businessAccountMono;
    }

    @Override
    public Mono<Business_Account> showById(String idBusinessAccount) {
        return businessAccountRepository.findById(idBusinessAccount);
    }

}
