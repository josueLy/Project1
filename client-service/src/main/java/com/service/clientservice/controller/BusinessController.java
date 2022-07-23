package com.service.clientservice.controller;

import com.service.clientservice.dto.client.BusinessDto;
import com.service.clientservice.model.Business;
import com.service.clientservice.service.interfaces.IBussinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private IBussinesService bussinesService;

    //list all Business
    @GetMapping("/list")
    public Flux<Business> list(){
        return bussinesService.findAll();
    }

    //show the Business by Id
    @GetMapping("/show/{id}")
    public Mono<Business> show(@PathVariable("id") String businessId){
        return bussinesService.show(businessId);
    }

    @GetMapping("/show/{dni}")
    public Mono<Business> showDni(@PathVariable("dni") String dni){
        return bussinesService.show(dni);
    }

    // create new Business
    @PostMapping("/create")
    public Mono<Business> create(@RequestBody BusinessDto business){
        return  bussinesService.save(business);
    }

    //modify a Business
    @PutMapping("/update")
    public Mono<Business> update(@RequestBody BusinessDto business){
        return bussinesService.update(business);
    }

    //delete a Business
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id)
    {
        return bussinesService.delete(id);
    }
}
