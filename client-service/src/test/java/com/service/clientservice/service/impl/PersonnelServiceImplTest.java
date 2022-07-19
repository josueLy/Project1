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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class PersonnelServiceImplTest {
    @Mock
    private IPersonnelRepository personnelRepository;

    @InjectMocks
    private PersonnelServiceImpl personnelService;

    private Personnel personnel;
    private PersonnelDto personnelDto;
    private Flux<Personnel> personnelFlux;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        personnel = new Personnel();
        personnel.setName("rolando");
        personnel.setDni("94353425");
        personnel.setPhoneNumber("454252");
        personnel.setEmailAddress("rjsb@");
        personnel.setPassaport("43526096890");

        Personnel first_personnel = new Personnel();
        first_personnel.setDni("75509576");
        first_personnel.setName("Rolando");
        first_personnel.setEmailAddress("rjsb@gmail");
        first_personnel.setPhoneNumber("928306906");
        first_personnel.setPassaport("084560208986");

        List<Personnel> personnelList = new ArrayList<>();
        personnelList.add(personnel);
        personnelList.add(first_personnel);

        personnelFlux = Mono.just(personnelList)
                .flatMapMany(Flux::fromIterable)
                .log();

    }

    @Test
    void findAll() {
        when(personnelRepository.findAll()).thenReturn(personnelFlux);
        assertNotNull(personnelService.findAll());
    }
}