package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.client.BusinessDto;
import project1.model.Business;
import project1.repository.IBusinessRepository;
import project1.service.interfaces.IBussinesService;
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
    public Mono<Business> save(BusinessDto business) {
        Business businessObject = new Business();

        businessObject.setDni(business.getDni());
        businessObject.setName(business.getName());
        businessObject.setPhoneNumber(business.getPhoneNumber());
        businessObject.setEmailAddress(business.getEmailAddress());
        businessObject.setRuc(business.getRuc());

        return businessRepository.save(businessObject);
    }

    @Override
    public Mono<Business> update(BusinessDto business) {
        Mono<Business>  monoBusiness = businessRepository.findById(business.getBusinessId());

        monoBusiness=  monoBusiness.map(result->{

          result.setDni(business.getDni());
          result.setName(business.getName());
          result.setPhoneNumber(business.getPhoneNumber());
          result.setEmailAddress(business.getEmailAddress());
          result.setRuc(business.getRuc());

           return  result;
        }).flatMap(result-> businessRepository.save(result));

        return  monoBusiness;
    }
}
