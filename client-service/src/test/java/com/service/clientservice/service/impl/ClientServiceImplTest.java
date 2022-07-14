package com.service.clientservice.service.impl;

import com.service.clientservice.model.Client;
import com.service.clientservice.repository.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.cglib.core.EmitUtils;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {
/*
    @Mock
    private IClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setDni("75509576");
        client.setName("Rolando");
        client.setPhoneNumber("920348645");
        client.setEmailAddress("rjsb@gmail.com");
    }

    @Test
    void findAll() {


    }

    @Test
    void testFindAll() {
        Mono<Client> clientMono = Mono.just(new Client("75509575","Rolando","920339874","rjsb@gmail.com"));
        when(clientRepository.findAll(Client.class)).thenReturn(Arrays.asList(clientMono));
        assertNotNull(clientService.findAll());
    }

    @Test
    void save() {
      //  when(clientRepository.save(any(Client.class))).thenReturn(Mono<Client>);

    }

 */
}