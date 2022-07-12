package com.service.clientservice.controller;


import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Personnel;
import com.service.clientservice.service.interfaces.IPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/personnel")
public class PersonnelController {

    @Autowired
    private IPersonnelService personnelService;

    //Method for listing Personnel clients
    @GetMapping("/list")
    public Flux<Personnel> list(){
        return personnelService.findAll();
    }
    // create new Personnel
    @PostMapping("/create")
    public Mono<Personnel> create (@RequestBody PersonnelDto personnel){
        return personnelService.save(personnel);
    }
    //modify a Personnel
    @PutMapping("/update")
    public Mono<Personnel> update(@RequestBody PersonnelDto personnel){

        return personnelService.update(personnel);
    }
    //show the Personnel by dni
    @GetMapping("/show/{dni}")
    public Flux<Personnel> Show(@PathVariable String dni){
        return personnelService.ShowByDni(dni);
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
