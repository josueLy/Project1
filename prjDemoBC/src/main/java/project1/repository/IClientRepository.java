package project1.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Client;
import reactor.core.publisher.Flux;

@Repository
public interface IClientRepository  extends ReactiveCrudRepository<Client, String> {

    @Query("{'dni': ?0}")
    Flux<Client> buscarPorDni(String dni);
}
