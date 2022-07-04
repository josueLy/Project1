package project1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.client.PersonnelDto;
import project1.dto.client.Product_TypeDto;
import project1.model.Personnel;
import project1.model.Product_Type;
import project1.service.interfaces.IProduct_TypeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/productotype")
public class Product_TypeController {

    @Autowired
    private IProduct_TypeService product_typeService;


    @GetMapping("/list")
    public Flux<Product_Type> list(){
        return product_typeService.findAll();
    }
    @PostMapping("/create")
    public Mono<Product_Type> create (@RequestBody Product_TypeDto product_type){
        return product_typeService.save(product_type);
    }
    @PutMapping("/update")
    public Mono<Product_Type> update(@RequestBody Product_Type product_type){
        return product_typeService.update(product_type);
    }
}
