package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.model.Client;
import project1.repository.IClientRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientRepository repo;

    @GetMapping("/listar")
    public Flux<Client> listar(){
        return repo.listar();
    }

    @GetMapping("/{id}")
    public Mono<Client> listarPorId(@PathVariable("id") Integer id){
        return  repo.listarPorId(id);
    }

    @PostMapping("/registrar")
    public Mono<Client> registrar(@RequestBody Client client){
        return repo.registrar(client);
    }

    @PutMapping("/modificar")
    public Mono<Client> modificar(@RequestBody Client client){
        return repo.modificar(client);
    }

    @DeleteMapping("/delete")
    public Mono<Void> eliminar(@PathVariable("id") Integer id){
        return repo.eliminar(id);
    }
}
