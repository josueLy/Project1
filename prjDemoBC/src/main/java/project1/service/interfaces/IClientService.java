package project1.service.interfaces;

import project1.dto.client.ClientDto;
import project1.dto.client.PersonnelDto;
import project1.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {

    Flux<Client> findAll();

    Mono<Client> save(PersonnelDto client);

    Mono<Client> update(Client client);

    Flux<Client> findClienteByDni(Integer idClient);

    public Mono<Void> delete(Client client);


}
