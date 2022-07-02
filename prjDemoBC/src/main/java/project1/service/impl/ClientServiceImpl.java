package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;
import project1.dto.client.PersonnelDto;
import project1.model.Client;
import project1.repository.IClientRepository;
import project1.service.interfaces.IClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public Flux<Client> findAll() {
        return  clientRepository.findAll();
    }

    @Override
    public Mono<Client> save(PersonnelDto client) {
        Client clientObject = new Client(client.getDni(),client.getName(),client.getPhoneNumber(),client.getEmailAddress());
        return clientRepository.save(clientObject);
    }

    @Override
    public Mono<Client> update(Client client) {
        return null; // clientRepository.save(client);
    }

    @Override
    public Flux<Client> findClienteByDni(Integer idClient) {
        return  null; //clientRepository.findAll().filter(x ->x.getIdClient().equals(idClient));
    }

    @Override
    public Mono<Void> delete(Client client) {
        return null; // clientRepository.delete(client);
    }

}
