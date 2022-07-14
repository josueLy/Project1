package com.service.clientservice.service.impl;

import com.service.clientservice.dto.client.BusinessDto;
import com.service.clientservice.model.Business;
import com.service.clientservice.repository.IBusinessRepository;
import com.service.clientservice.service.interfaces.IBussinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BussinesServiceImpl implements IBussinesService {

    @Autowired
    private IBusinessRepository businessRepository;


    @Override
    public Flux<Business> findAll() {
        return businessRepository.findAll();
    }

    @Override
    public Mono<Business> show(String id) {

        return  businessRepository.findById(id);
    }

    @Override
    public Mono<Business> save(BusinessDto bussiness) {
        Business businessObject = new Business();

        businessObject.setDni(bussiness.getDni());
        businessObject.setName(bussiness.getName());
        businessObject.setPhoneNumber(bussiness.getPhoneNumber());
        businessObject.setEmailAddress(bussiness.getEmailAddress());
        businessObject.setRuc(bussiness.getRuc());

        return businessRepository.save(businessObject);
    }

    @Override
    public Mono<Business> update(BusinessDto business) {
        Mono<Business> monoBusiness = businessRepository.findById(business.getBusinessId());

        monoBusiness=  monoBusiness.map(result->{

            result.setDni(business.getDni());
            result.setName(business.getName());
            result.setPhoneNumber(business.getPhoneNumber());
            result.setEmailAddress(business.getEmailAddress());
            result.setRuc(business.getRuc());

            return result;
        }).flatMap(result -> businessRepository.save(result));

        return monoBusiness;
    }

    @Override
    public Mono<Void> delete(String id) {
        return  businessRepository.deleteById(id);
    }
}
