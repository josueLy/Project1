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

        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(personnel.getBank_account_id());

        Mono<Personnel> personnelMono= Mono.just(new Personnel());

        Bank_Account bank_account = new Bank_Account();

       personnelMono = bankAccountMono.map(result ->{
          bank_account.setAccountId(String.valueOf(result));
        }).flatMap(result->{
            return personnelRepository.save(bank_account);
        });
        return bank_account;
       /* Personnel personnelObj = new Personnel();

        personnelObj.setDni(personnel.getDni());
        personnelObj.setName(personnel.getName());
        personnelObj.setPhoneNumber(personnel.getPhoneNumber());
        personnelObj.setEmailAddress(personnel.getEmailAddress());
        personnelObj.setPassaport(personnel.getPassport());
       // personnelObj.setBank_acount(personnel.getBank_account_id());

        return personnelRepository.save(personnelObj);

        */
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
