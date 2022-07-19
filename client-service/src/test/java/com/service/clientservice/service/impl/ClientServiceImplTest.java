package com.service.clientservice.service.impl;


import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Client;
import com.service.clientservice.repository.IClientRepository;
import com.service.clientservice.service.interfaces.IClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private IClientRepository clientRepository;

    @Mock
    private IClientService  clientService;

    //@DisplayName("datos del cliente");

    @Test
    public void pruebaUsuario(){

        Client esperado = new Client("75509576","Rolando","9376452345","rjsb1597@");
       // Mockito.when(clientService.save()).thenReturn(esperado);


    }

}