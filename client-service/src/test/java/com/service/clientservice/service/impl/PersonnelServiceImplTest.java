package com.service.clientservice.service.impl;

import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Personnel;
import com.service.clientservice.repository.IPersonnelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class PersonnelServiceImplTest {
    @Mock
    private IPersonnelRepository personnelRepository;

    @InjectMocks
    private PersonnelServiceImpl personnelService;

    private Personnel personnel;
    private PersonnelDto personnelDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        /*Mono<Personnel> personnelMono = personnelRepository.findById(personnelDto.getIdPersonal());

        personnelMono = personnelMono.map(client_personnel -> {
            Personnel personnel = client_personnel;

            personnel.setName("rolando");
            personnel.setDni("94353425");
            personnel.setPhoneNumber("454252");
            personnel.setEmailAddress("rjsb@");
            personnel.setPassaport("43526096890");
            //personnel.setAccounts("45834205");

            return personnel;
        });

       */
        personnel = new Personnel();
        personnel.setDni("75509576");
        personnel.setName("Rolando");
        personnel.setEmailAddress("rjsb@gmail");
        personnel.setPhoneNumber("928306906");
        personnel.setPassaport("084560208986");



    }

    @Test
    void findAll() {
        when(personnelRepository.findAll()).thenReturn(Mono.just(personnel));
        assertNotNull(personnelService.findAll());
    }
}