package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import project1.dto.client.TransactionDto;
import project1.model.*;
import project1.repository.IBankAccountRepository;
import project1.repository.IBusinessRepository;
import project1.repository.IPersonnelRepository;
import project1.repository.ITransactionRepository;
import project1.service.interfaces.ITransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IPersonnelRepository personnelRepository;

    @Autowired
    private IBusinessRepository businessRepository;

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> save(TransactionDto transaction) {

        Mono<Personnel> personnelMono = null;
        Mono<Business> businessMono = null;

        //Condition if is Personnel Client or a BusinessClient
        if (transaction.getPersonnelId() != null) {
            //get Personnel by Id
            personnelMono = personnelRepository.findById(transaction.getPersonnelId());

        } else {   //Get the Business Client by Id
            businessMono = businessRepository.findById(transaction.getBusinessId());
        }

        //Set The transacction============================================================================
        Transaction transactionObject = new Transaction();

        if (personnelMono != null) {
            // If a Client is Personnel you must set the Personnel Client
            personnelMono.map(personnel -> {
                transactionObject.setPersonnel(personnel);
                return transactionObject;
            });
        } else {
            //If a Client is Business you must set the Business Client
            businessMono.map(business -> {
                transactionObject.setBusiness(business);
                return transactionObject;
            });
        }

        // Get the account of transacction by Id
        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(transaction.getAccountId());

        //Set Bank account
        bankAcountMono.map(bank_account -> {
            transactionObject.setAccount(bank_account);

            //Set the rest of the attributes of transacction
            transactionObject.setType(transaction.getType());
            transactionObject.setAmount(transaction.getAmount());
            transactionObject.setDate(transaction.getDate());

            return transactionObject;

        });

        //End of setting transaction ======================================================================


        return transactionRepository.save(transactionObject);
    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {
        return null;
    }
}
