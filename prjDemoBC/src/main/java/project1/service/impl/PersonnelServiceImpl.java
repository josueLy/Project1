package project1.service.impl;

import org.reactivestreams.Publisher;
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
    public Mono<Personnel> update(PersonnelDto personnel) {

        Mono<Personnel> personnelMono = personnelRepository.findById(personnel.getIdPersonal());


        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(personnel.getAccount());
        //Find Bank_Account by Id--->

        //Use Mono.zip (bank_account, personnelMono).MAP(data-{})

        personnelMono = Mono.zip(personnelMono,bankAccountMono).map(data ->{

            Personnel personnelObj  =new Personnel();
            personnelObj.setDni(personnel.getDni());
            personnelObj.setName(personnel.getName());
            personnelObj.setPhoneNumber(personnel.getPhoneNumber());
            personnelObj.setEmailAddress(personnel.getEmailAddress());
            personnelObj.setPassaport(personnel.getPassport());
            personnelObj.setAccount(data.getT2());

            return personnelObj;

        });
        personnelMono = personnelMono.flatMap(result -> {
            return personnelRepository.save(result);

        });
        return personnelMono;
        /*
        personnelMono = personnelMono.map(personnel -> {
            personnel.setDni(personnel.getDni());
            personnel.setName(personnel.getName());
            personnel.setPhoneNumber(personnel.getPhoneNumber());
            personnel.setEmailAddress(personnel.getEmailAddress());
            personnel.setPassaport(personnel.getPassaport());

            return personnel;
        }).flatMap(result -> personnelRepository.save(result));

         */
    }

    @Override
    public Flux<Personnel> ShowByDni(String dni) {

        return personnelRepository.ShowByDni(dni);//filter(x -> x.getDni().equals(dni));
    }

    @Override
    public Mono<Void> delete(String idPersonal) {
        return personnelRepository.deleteById(idPersonal);
    }



   /*
    Mono<Business>  businessMono = businessRepository.findById(id);

        businessMono.flatMap(result->businessRepository.delete(result));

        return Mono.just(null);
    @Override
    public Flux<Personnel> ShowByDni(String dni) {
        return personnelRepository.findAll().filter(x -> x.getDni().equals(dni));
    }

    */



}
