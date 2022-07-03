package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.model.Business;
import project1.model.Client;
import project1.repository.IBusinessRepository;
import project1.service.interfaces.IBussinesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BussinesServiceImpl implements IBussinesService {
/*
    @Autowired
    private IBusinessRepository businessRepository;
    @Override
    public Flux<Business> findAll() {
        return businessRepository.findAll();
    }

    @Override
    public Mono<Business> save(Business business) {
        //Business clientObject = new Business(business.getIdBusiness(),business.getClient(),business.getRuc());
        return businessRepository.save(business);
    }

    @Override
    public Mono<Business> update(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Mono<Void> delete(Business business) {
        return businessRepository.delete(business);
    }

 */
}
