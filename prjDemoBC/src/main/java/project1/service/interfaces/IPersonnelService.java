package project1.service.interfaces;

import project1.dto.client.PersonnelDto;
import project1.model.Client;
import project1.model.Personnel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonnelService {
    Flux<Personnel> findAll();

    Mono<Personnel> save(PersonnelDto personnel);

    Mono<Personnel> update(PersonnelDto personnel);

    Flux<Personnel> ShowByDni(String dni);

    //public Mono<Void> delete(Personnel personnel);
    Mono<Personnel> Delete(String idPersonal);
}
