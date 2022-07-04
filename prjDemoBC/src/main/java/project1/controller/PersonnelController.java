package project1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.client.PersonnelDto;
import project1.model.Client;
import project1.model.Personnel;
import project1.service.interfaces.IPersonnelService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/personel")
public class PersonnelController {

    @Autowired
    private IPersonnelService personnelService;

    @GetMapping("/list")
    public Flux<Personnel> list(){
        return personnelService.findAll();
    }
    @PostMapping("/create")
    public Mono<Personnel> create (@RequestBody PersonnelDto personnel){
        return personnelService.save(personnel);
    }
    @PutMapping("/update")
    public Mono<Personnel> update(@RequestBody Personnel personnel){

        return personnelService.update(personnel);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> Delete(@PathVariable("id") String id){
        return personnelService.Delete(id);
    }
}
