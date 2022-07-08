package com.service.clientservice.service.interfaces;

import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Personnel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonnelService {
    Flux<Personnel> findAll();

    Mono<Personnel> save(PersonnelDto personnel);

    Mono<Personnel> update(PersonnelDto personnel);

    Flux<Personnel> ShowByDni(String dni);

    //public Mono<Void> delete(Personnel personnel);
    Mono<Void> delete(String idPersonal);
}
