package com.service.clientservice.service.impl;

import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Client;
import com.service.clientservice.repository.IClientRepository;
import com.service.clientservice.service.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Mono<Client> save(PersonnelDto client) {
        Client clientObject = new Client(client.getDni(), client.getName(), client.getPhoneNumber(), client.getEmailAddress());
        return clientRepository.save(clientObject);
    }

    @Override
    public Mono<Client> update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Mono<Void> delete(Client client) {
        return clientRepository.delete(client);
        }


}
