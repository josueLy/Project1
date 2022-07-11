package com.bootcamp.productservice.controller;

import com.bootcamp.productservice.dto.credit.CreditDto;
import com.bootcamp.productservice.model.Credit;
import com.bootcamp.productservice.service.interfaces.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private ICreditService creditService;

    //list all Credits
    @GetMapping("/list")
    public Flux<Credit> list(){
        return creditService.list();
    }

    //show the Credit by Id
    @GetMapping("/show/{id}")
    public Mono<Credit> show(@PathVariable("id") String creditId)
    {
        return  creditService.show(creditId);
    }

//     create new Credit
    @PostMapping("/create")
    public Mono<Credit> create(@RequestBody CreditDto creditDto){
        return  creditService.create(creditDto);
    }

    //modify a Credit
//    @PutMapping("/update")
//    public Mono<Credit> update(@RequestBody CreditDto creditDto){
//        return creditService.update(creditDto);
//    }

    //delete a Credit
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id)
    {
        return creditService.delete(id);
    }
}
