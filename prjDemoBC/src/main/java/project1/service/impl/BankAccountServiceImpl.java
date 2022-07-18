package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.bankAccount.BankAccountDto;
import project1.model.Bank_Account;
import project1.repository.IBankAccountRepository;
import project1.repository.IPersonnelRepository;
import project1.service.interfaces.IBankAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Autowired
    private IPersonnelRepository personnelRepository;


    @Override
    public Flux<Bank_Account> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Mono<Bank_Account> show(String id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public Mono<Bank_Account> save(BankAccountDto bankAccountDto) {
        Bank_Account bank_acount = new Bank_Account();

        bank_acount.setAvailableBalance(bankAccountDto.getAvailableBalance());
        bank_acount.setNumberAccount(bankAccountDto.getNumberAccount());

        return bankAccountRepository.save(bank_acount);

    }

    @Override
    public Mono<Bank_Account> update(BankAccountDto bankAccountDto) {
        //get the mono of bank account by Id
        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(bankAccountDto.getBankAccountId());

        bankAcountMono = bankAcountMono.map(/*get the bank_Account Object from mono*/bank_account -> {
            //Set the bank account and assign the mono of bank_Account (bankAcountMono)
            bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
            bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());

            return bank_account;
        }).flatMap(result ->
                //save the new bank_account
                bankAccountRepository.save(result));

        return bankAcountMono;
    }

    @Override
    public Mono<Void> delete(String id) {

       return bankAccountRepository.deleteById(id);
    }
}
