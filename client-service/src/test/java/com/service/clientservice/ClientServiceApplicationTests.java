package com.service.clientservice;

import com.service.clientservice.controller.ClientController;

import com.service.clientservice.controller.PersonnelController;
import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Client;
import com.service.clientservice.model.Personnel;
import com.service.clientservice.repository.IClientRepository;
import com.service.clientservice.repository.IPersonnelRepository;
import com.service.clientservice.service.impl.ClientServiceImpl;
import com.service.clientservice.service.impl.PersonnelServiceImpl;
import com.service.clientservice.service.interfaces.IClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@WebFluxTest(ClientController.class)
//@SpringBootTest
class ClientServiceApplicationTests {

    @Autowired
    Client client;
    @Autowired
    PersonnelDto personnelDto;

    IPersonnelRepository personnelRepositoryMock = Mockito.mock(IPersonnelRepository.class);

    PersonnelController personnelController = new PersonnelController();

    @BeforeEach
    void setUp(){
    }
    @Test
    void Details(){
        System.out.println("dentro de la prueba");
    }


}

