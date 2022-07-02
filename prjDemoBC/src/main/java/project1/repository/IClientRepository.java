package project1.repository;

import project1.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientRepository {

    Mono<Client> registrar(Client client);
    Mono<Client> modificar(Client client);
    Flux<Client> listar();
    Mono<Client> listarPorId(Integer id);
    Mono<Void> eliminar(Integer id);

}
