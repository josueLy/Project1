package com.service.clientservice.service.impl;


import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Client;
import com.service.clientservice.repository.IClientRepository;
import com.service.clientservice.service.interfaces.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private IClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl  clientService;

    private Flux<Client> clientFlux;

    private Client client;

    //@DisplayName("datos del cliente");
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Client esperado = new Client("75509576","Rolando","9376452345","rjsb1597@");

        client = new Client();
        client.setDni("75509576");
        client.setName("Rolando");
        client.setPhoneNumber("920338624");
        client.setEmailAddress("rjsb@gmail.com");

        List<Client> clientList = new ArrayList<>();
        clientList.add(client);

        clientFlux = Mono.just(clientList)
                .flatMapMany(Flux::fromIterable)
                .log();
    }

    @Test
    void findAll(){

        when(clientRepository.findAll()).thenReturn(clientFlux);
        assertNotNull(clientService.findAll());
    }

}