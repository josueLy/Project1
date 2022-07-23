package com.service.clientservice.controller;


import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Personnel;
import com.service.clientservice.service.interfaces.IPersonnelService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/personnel")
public class PersonnelController {

    @Autowired
    private IPersonnelService personnelService;

    //Method for listing Personnel clients
    @CircuitBreaker(name="PersonelCB", fallbackMethod = "fallbackPersonnel")
    @GetMapping("/list")
    public Flux<Personnel> list(){
        return personnelService.findAll();
    }
    private Flux<Personnel> fallbackPersonnel(Exception ex){
        return personnelService.findAll();
    }
    // create new Personnel
    @CircuitBreaker(name="PersonelCB", fallbackMethod = "fallbackSavePersonnel")
    @PostMapping("/create")
    public Mono<Personnel> create (@RequestBody PersonnelDto personnel){
        return personnelService.save(personnel);
    }
    private Mono<Personnel> fallbackSavePersonnel( PersonnelDto personnel,Exception ex){
        if (personnel.getIdPersonal() == personnel.getIdPersonal()) {
            System.out.println("personnel ya existe");
        }
        else {
            System.out.println("inside backup personnel"+ personnelService.save(personnel));
        }
        return null;
    }
    //modify a Personnel
    @PutMapping("/update")
    public Mono<Personnel> update(@RequestBody PersonnelDto personnel){

        return personnelService.update(personnel);
    }
    //show the Personnel by dni
    @CircuitBreaker(name="PersonelCB", fallbackMethod = "fallbackShowPersonnel")
    @GetMapping("/show/{dni}")
    public Mono<Personnel> Show(@PathVariable String dni){
        return personnelService.ShowByDni(dni);
    }
    public Mono<Personnel> fallbackShowPersonnel (@PathVariable String dni , IllegalArgumentException ex ) {
        return personnelService.ShowByDni("dni: "+dni+"personnel");
    }

    // show the Personnel by Id
    @GetMapping("/showById/{id}")
    public Mono<Personnel> showById(@PathVariable String id){

        return personnelService.showById(id);
    }
    //delete a Personnel
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return personnelService.delete(id);
    }




}
