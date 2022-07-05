package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.client.PersonnelDto;
import project1.model.Client;
import project1.service.interfaces.IClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    //list all Client
    @GetMapping("/list")
    public Flux<Client> list(){
        return clientService.findAll();
    }
    //create new Client
    @PostMapping("/create")
    public Mono<Client> create(@RequestBody PersonnelDto client){
        return clientService.save(client);
    }
    //modify a Client
    @PutMapping("/update")
    public Mono<Client> update(@RequestBody Client client){
        return clientService.update(client);
    }
    //delete a Client
    @DeleteMapping("/delete")
    public Mono<Void> delete(@RequestBody Client client){
        return clientService.delete(client);
    }

}
