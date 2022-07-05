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

        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(personnel.getAccount());

        //Setting the bank account in personnel object
        Mono<Personnel> personnelMono = bankAccountMono.map(bank_account -> {
            Personnel personnelobj = new Personnel();

            personnelobj.setDni(personnel.getDni());
            personnelobj.setName(personnel.getName());
            personnelobj.setPhoneNumber(personnel.getPhoneNumber());
            personnelobj.setEmailAddress(personnel.getEmailAddress());
            personnelobj.setPassaport(personnel.getPassport());
            personnelobj.setAccount(bank_account);

            return personnelobj;
        });

        personnelMono = personnelMono.flatMap(result -> {
            return personnelRepository.save(result);

        });
        return personnelMono;
    }


    @Override
    public Mono<Personnel> update(Personnel personnelDto) {

        Mono<Personnel> personnelMono = personnelRepository.findById(personnelDto.getIdPersonal());

        //Find Bank_Account by Id--->

        //Use Mono.zip (bank_account, personnelMono).MAP(data-{})
        personnelMono = personnelMono.map(personnel -> {
            personnel.setDni(personnel.getDni());
            personnel.setName(personnelDto.getName());
            personnel.setPhoneNumber(personnelDto.getPhoneNumber());
            personnel.setEmailAddress(personnelDto.getEmailAddress());
            personnel.setPassaport(personnelDto.getPassaport());

            return personnel;
        }).flatMap(result -> personnelRepository.save(result));

        return personnelMono;
    }

    @Override
    public Mono<Void> Delete(String id) {
        return Mono.empty();
    }


}
