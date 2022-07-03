package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.model.Business;
import project1.service.interfaces.IBussinesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/businessC")
public class BusinessController {

    @Autowired
    private IBussinesService bussinesService;

    @GetMapping("/listB")
    public Flux<Business> list(){
        return bussinesService.findAll();
    }
    @PostMapping("/createB")
    public Mono<Business> create(@RequestBody Business business){
        return  bussinesService.save(business);
    }
    @PutMapping("/updateB")
    public Mono<Business> update(@RequestBody Business business){
        return bussinesService.update(business);
    }
    @DeleteMapping("/deleteB")
    public Mono<Void> delete(@RequestBody Business business){
        return bussinesService.delete(business);
    }
}
