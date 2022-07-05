package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.client.PersonnelDto;

import project1.model.Bank_Account;
import project1.model.Client;

import project1.model.Personnel;
import project1.repository.IBankAccountRepository;
import project1.repository.IPersonnelRepository;
import project1.service.interfaces.IPersonnelService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonnelServiceImpl implements IPersonnelService {

    @Autowired
    private IPersonnelRepository personnelRepository;

    @Autowired
    private IBankAccountRepository bankAccountRepository;



    @Override
    public Flux<Personnel> findAll() {
        return personnelRepository.findAll();
    }

    @Override
    public Mono<Personnel> save(PersonnelDto personnel) {
        Mono<Personnel> personnelMono = null;//personnelRepository.findById(personnel.ge);


        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(personnel.getAccount());

        final Personnel personnelobj = new Personnel();

        personnelMono = Mono.zip(personnelMono, bankAccountMono).map(data -> {
            personnelobj.setDni(data.getT1().getDni());
            personnelobj.setName(data.getT1().getName());
            personnelobj.setPhoneNumber(data.getT1().getPhoneNumber());
            personnelobj.setEmailAddress(data.getT1().getEmailAddress());
            personnelobj.setPassaport(data.getT1().getPassaport());
            personnelobj.setAccount(data.getT2());

            return personnelobj;

        });

        personnelMono = personnelMono.flatMap(result -> {
            return personnelRepository.save(result);

        });
        return  personnelMono;
    }


    @Override
    public Mono<Personnel> update(Personnel personnel) {

        Mono<Personnel> personnelMono = personnelRepository.findById(personnel.getIdPersonal());

        personnelMono = personnelMono.map(result ->{
           result.setDni(personnel.getDni());
           result.setName(personnel.getName());
           result.setPhoneNumber(personnel.getPhoneNumber());
           result.setEmailAddress(personnel.getEmailAddress());
           result.setPassaport(personnel.getPassaport());
           return result;
        }).flatMap(result -> personnelRepository.save(result));

        return personnelMono;
    }

    @Override
    public Mono<Void> Delete(String id) {
        return Mono.empty();
    }


}
