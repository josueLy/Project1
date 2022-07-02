package project1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project1.dto.client.PersonnelDto;
import project1.model.Client;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @PostMapping("/create")
    public Mono<Client> create(@RequestBody PersonnelDto client){
        return  null;//clientService.save(client);
    }
}
