package com.service.clientservice.service.impl;

import com.google.gson.Gson;
import com.service.clientservice.dto.client.PersonnelDto;
import com.service.clientservice.model.Personnel;
import com.service.clientservice.repository.IPersonnelRepository;
import com.service.clientservice.service.interfaces.IPersonnelService;
import com.service.clientservice.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonnelServiceImpl implements IPersonnelService {

    @Autowired
    private IPersonnelRepository personnelRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RedisService redisService;

    @Override
    public Flux<Personnel> findAll() {
        return personnelRepository.findAll();
    }


    @KafkaListener(topics = "bootcamp-proyecto4", groupId = "group_id")
    @Override
    public Mono<Personnel> save(PersonnelDto personnelDto) {

        Personnel personnel = new Personnel();
        personnel.setIdPersonal(personnelDto.getIdPersonal());
        personnel.setDni(personnelDto.getDni());
        personnel.setName(personnelDto.getName());
        personnel.setEmailAddress(personnelDto.getEmailAddress());
        personnel.setPhoneNumber(personnelDto.getPhoneNumber());
        personnel.setPassaport(personnelDto.getPassport());
        personnel.setAccounts(personnelDto.getAccounts());

        //String personnelJson= new Gson().toJson(personnel);
        redisService.savePersonnel(personnel.getDni(),personnel);

        return personnelRepository.save(personnel);

    }


    @Override
    public Mono<Personnel> update(PersonnelDto personnelDto) {

        Mono<Personnel> personnelMono = personnelRepository.findById(personnelDto.getIdPersonal());

        personnelMono = personnelMono.map(client_personnel -> {
            Personnel personnel = client_personnel;

            personnel.setName(personnelDto.getName());
            personnel.setDni(personnelDto.getDni());
            personnel.setPhoneNumber(personnelDto.getPhoneNumber());
            personnel.setEmailAddress(personnelDto.getEmailAddress());
            personnel.setPassaport(personnelDto.getPassport());
            personnel.setAccounts(personnelDto.getAccounts());

            return personnel;
        });

        personnelMono = personnelMono.flatMap(result -> {
            return personnelRepository.save(result);
        });
        return personnelMono;
    }

    @Override
    public Mono<Personnel> ShowByDni(String dni) {

        return personnelRepository.ShowByDni(dni);
    }

    @Cacheable(value = "itemCache")
    @Override
    public Mono<Personnel> showById(String id) {
        return personnelRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String idPersonal) {
        return personnelRepository.deleteById(idPersonal);
    }


}
