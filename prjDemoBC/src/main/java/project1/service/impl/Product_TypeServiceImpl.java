package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.client.Product_TypeDto;
import project1.model.Product_Type;
import project1.repository.IProduct_TypeRepository;
import project1.service.interfaces.IProduct_TypeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class Product_TypeServiceImpl implements IProduct_TypeService {

    @Autowired
    private IProduct_TypeRepository product_typeRepository;

    @Override
    public Flux<Product_Type> findAll() {
        return product_typeRepository.findAll();
    }

    @Override
    public Mono<Product_Type> save(Product_TypeDto product_type) {
        Product_Type product_typeObj = new Product_Type();

        product_typeObj.setSaving(product_type.getSaving());
        product_typeObj.setCurrentAccount(product_type.getCurrentAccount());
        product_typeObj.setFixedTerm(product_type.getFixedTerm());
        product_typeObj.setPersonnel(product_type.getPersonnel());
        product_typeObj.setBussiness(product_type.getBussiness());
        product_typeObj.setCreditCard(product_type.getCreditCard());

        return product_typeRepository.save(product_typeObj);

    }


    @Override
    public Mono<Product_Type> update(Product_Type product_type) {

        Mono<Product_Type> product_typeMono = product_typeRepository.findById(product_type.getTypeId());

        product_typeMono = product_typeMono.map(result ->{
            result.setSaving(product_type.getSaving());
            result.setCurrentAccount(product_type.getCurrentAccount());
            result.setFixedTerm(product_type.getFixedTerm());
            result.setPersonnel(product_type.getPersonnel());
            result.setBussiness(product_type.getBussiness());
            result.setCreditCard(product_type.getCreditCard());

            return result;
        }).flatMap(result -> product_typeRepository.save(result));


        return product_typeMono;
    }
}
