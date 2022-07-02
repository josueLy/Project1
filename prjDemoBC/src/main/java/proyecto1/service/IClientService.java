package proyecto1.service;



import proyecto1.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IClientService {
    Flux<Client> findAll();

    Mono<Client> save(Client client);

    Mono<Client> update(Client client);

    Flux<Client> findClienteByDni(Integer idClient);

    public Mono<Void> delete(Client client);

    Flux<Client> buscarPorDni(Integer idClient);
}
