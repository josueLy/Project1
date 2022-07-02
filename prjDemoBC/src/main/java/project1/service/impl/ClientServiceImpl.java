package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Flux<Client> findClienteByDni(Integer idClient) {
        return clientRepository.findAll().filter(x ->x.getIdClient().equals(idClient));
    }

    @Override
    public Mono<Void> delete(Client client) {
        return clientRepository.delete(client);
    }

    @Override
    public Flux<Client> buscarPorDni(Integer idClient) {
        return null ;//clientService.buscarPorDni(idClient);
    }
}
