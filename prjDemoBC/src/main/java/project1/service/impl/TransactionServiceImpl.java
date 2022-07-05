package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.transaction.TransactionDto;
import project1.model.*;
import project1.repository.IBankAccountRepository;
import project1.repository.IBusinessRepository;
import project1.repository.IPersonnelRepository;
import project1.repository.ITransactionRepository;
import project1.service.interfaces.ITransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

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

        // Get the account of transacction by Id
        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(transaction.getAccountId());

        //Condition if is Personnel Client or a BusinessClient
        if (transaction.getPersonnelId() != null && !transaction.getPersonnelId().equals("")) {
            //get Personnel by Id
            personnelMono = personnelRepository.findById(transaction.getPersonnelId());

        } else {   //Get the Business Client by Id
            businessMono = businessRepository.findById(transaction.getBusinessId());
        }

        //Set The transacction============================================================================
        final Transaction transactionObject = new Transaction();

        // Create a mono of transaction to set the transaction and save it
        Mono<Transaction> transactionMono = null;

        if (personnelMono != null) {
            // If a Client is Personnel you must set the Personnel Client
            transactionMono=Mono.zip(personnelMono,bankAcountMono).map(data->{

                //Set the personnel Client
                transactionObject.setPersonnel(data.getT1());

                //Set the Account
                transactionObject.setAccount(data.getT2());
                //Set the rest of the attributes of transacction
                transactionObject.setType(transaction.getType());
                transactionObject.setAmount(transaction.getAmount());
                transactionObject.setDate(new Date());

                //assign the transaction object to a mono of transaction
                return transactionObject;


            });
        } else {
            //If a Client is Business you must set the Business Client
            transactionMono = Mono.zip(businessMono, bankAcountMono).map(data -> {

                //Set the Business Client
                transactionObject.setBusiness(data.getT1());

                //Set the bank account
                transactionObject.setAccount(data.getT2());

                //Set the rest of the attributes of transacction
                transactionObject.setType(transaction.getType());
                transactionObject.setAmount(transaction.getAmount());
                transactionObject.setDate(new Date());

                //assign the transaction object to a mono of transaction
                return transactionObject;
            });
        }

        //save the transaction
        transactionMono = transactionMono.flatMap(result -> {
            return transactionRepository.save(result);
        });

        //End of setting transaction ======================================================================

        return transactionMono;
    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {
        return null;
    }
}
