package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.client.ClientDto;
import project1.dto.client.PersonnelDto;
import project1.model.Client;
import project1.model.Personnel;
import project1.repository.IPersonnelRepository;
import project1.service.interfaces.IPersonnelService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonnelServiceImpl implements IPersonnelService {

    @Autowired
    private IPersonnelRepository personnelRepository;


    @Override
    public Flux<Personnel> findAll() {
        return personnelRepository.findAll();
    }

    @Override
    public Mono<Personnel> save(PersonnelDto personnel) {
        Personnel personnelObj = new Personnel();

        personnelObj.setDni(personnel.getDni());
        personnelObj.setName(personnel.getName());
        personnelObj.setPhoneNumber(personnel.getPhoneNumber());
        personnelObj.setEmailAddress(personnel.getEmailAddress());
        personnelObj.setPassaport(personnel.getPassaort());

        return personnelRepository.save(personnelObj);
    }


    @Override
    public Mono<Personnel> update(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    @Override
    public Mono<Void> Delete(String id) {
        return Mono.empty();
    }


}
