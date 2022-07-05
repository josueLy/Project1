package project1.service.interfaces;

import project1.dto.businessAccount.BusinessAccountDto;
import project1.dto.client.BusinessDto;
import project1.model.Business;
import project1.model.Business_Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBusinessAccountService {

    Flux<Business_Account> findAll();

    Mono<Business_Account> save(BusinessAccountDto bussinessDto);

    Mono<Business_Account> update(BusinessAccountDto business);

    Mono<Business_Account> showById(String idBusinessAccount);

    Mono<Void> delete(String id);
}
