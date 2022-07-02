package proyecto1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import proyecto1.model.Client;
import proyecto1.service.IClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/listar")
    public Flux<Client> listar(){
        return clientService.findAll();
    }

    @PostMapping("/register")
    public Mono<Client> registrar(@RequestBody Client client){
        return clientService.save(client);
    }

    @PutMapping("/update")
    public Mono<Client> actualizar(@RequestBody Client client){
        return clientService.update(client);
    }

    @DeleteMapping("/delete")
    public Mono<Void> eliminar(@RequestBody Client client){
        return clientService.delete(client);
    }

}
