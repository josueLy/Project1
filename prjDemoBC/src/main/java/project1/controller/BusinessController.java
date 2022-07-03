package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.client.BusinessDto;
import project1.model.Business;
import project1.service.interfaces.IBussinesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private IBussinesService bussinesService;

    @GetMapping("/list")
    public Flux<Business> list(){
        return bussinesService.findAll();
    }
    @PostMapping("/create")
    public Mono<Business> create(@RequestBody BusinessDto business){
        return  bussinesService.save(business);
    }
    @PutMapping("/update")
    public Mono<Business> update(@RequestBody BusinessDto business){
        return bussinesService.update(business);
    }
    @DeleteMapping("/delete")
    public Mono<Void> delete(@RequestBody Business business){
        return bussinesService.delete(business);
    }
}
