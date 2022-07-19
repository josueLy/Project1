package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.product_type.Product_TypeDto;
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

        product_typeObj.setDescription(product_type.getDescription());

        return product_typeRepository.save(product_typeObj);

    }


    @Override
    public Mono<Product_Type> update(Product_Type product_type) {

        Mono<Product_Type> product_typeMono = product_typeRepository.findById(product_type.getTypeId());

        product_typeMono = product_typeMono.map(result ->{
            result.setDescription(product_type.getDescription());

            return result;
        }).flatMap(result -> product_typeRepository.save(result));


        return product_typeMono;
    }

    @Override
    public Mono<Product_Type> ShowById(String typeId) {
        return product_typeRepository.findById(typeId);
    }

    @Override
    public Mono<Void> delete(String id) {
        return product_typeRepository.deleteById(id);
    }
}
