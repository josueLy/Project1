package project1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Client;
@Repository
public interface IClientRepository  extends ReactiveCrudRepository<Client, String> {


}
