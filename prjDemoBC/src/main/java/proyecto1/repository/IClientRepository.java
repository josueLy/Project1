package proyecto1.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import proyecto1.model.Client;
@Repository
public interface IClientRepository  extends ReactiveCrudRepository<Client, Integer> {

}
