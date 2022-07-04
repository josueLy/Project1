package project1.service.impl;

import org.springframework.stereotype.Service;
import project1.dto.businessAccount.BusinessAccountDto;
import project1.model.Business_Account;
import project1.service.interfaces.IBusinessAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessAccountServiceImpl implements IBusinessAccountService {
    @Override
    public Flux<Business_Account> findAll() {
        return null;
    }

    @Override
    public Mono<Business_Account> save(BusinessAccountDto bussinessDto) {
        return null;
    }

    @Override
    public Mono<Business_Account> update(BusinessAccountDto business) {
        return null;
    }
}
