package com.service.clientservice.service.interfaces;

import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {

//    Flux<Client> findAll();

    Mono<Client> save(PersonnelDto client);

    Mono<Client> update(Client client);


    public Mono<Void> delete(Client client);


}
