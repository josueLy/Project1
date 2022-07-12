package com.bootcamp.transactionservice.service.impl;

import com.bootcamp.transactionservice.dto.transaction.TransactionDto;
import com.bootcamp.transactionservice.model.Personnel;
import com.bootcamp.transactionservice.model.Transaction;
import com.bootcamp.transactionservice.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.Date;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

//    @Autowired
//    private IPersonnelRepository personnelRepository;
//
//    @Autowired
//    private IBusinessRepository businessRepository;
//
//    @Autowired
//    private IBankAccountRepository bankAccountRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> show(String id) {
        return transactionRepository.findById(id);
    }

    public TransactionDto transactionMax( TransactionDto transacction){
      /* // Mono<Transaction> transactionMono = transactionRepository.findById(transacction.getTransactionId());
        int nmt = 1 ;

        if (transacction.getNumeroMT() > nmt ) {
            double comision = 0.10;
            Transaction transaction1 = new Transaction(transacction.getTransactionId(),
            transacction.getAmount()*comision);
            //Transaction transaction1 = new Transaction (transacction.getTransactionId(), transacction.getAmount());


       */
            return null;

    }
//    @Override
   public Mono<Transaction> save(TransactionDto transaction) {

       Mono<Transaction> transactionMono = webClientBuilder.build()
               .get()
               .uri("http://localhost:8085/transaction/show/" + transaction.getPersonnelId() + transaction.getBusinessId())
               .retrieve()
               .bodyToMono(Personnel.class)
               .map(transac -> {
                   Transaction transaction1 = new Transaction();
                   transaction1.setPersonnel(transac);
                   //transaction1.setBusiness(transac);
                   transaction1.setAmount(transaction.getAmount());
                   transaction1.setType(transaction.getType());
                   transaction1.setAmount(transaction.getAmount());
                   return transaction1;
               });
       transactionMono = transactionMono.flatMap(entity -> {
           return transactionRepository.save(entity);
       });
       return transactionMono;
   }

//
//        Mono<Personnel> personnelMono = null;
//        Mono<Business> businessMono = null;
//
//        // Get the account of transacction by Id
//        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(transaction.getAccountId());
//
//        //Condition if is Personnel Client or a BusinessClient
//        if (transaction.getPersonnelId() != null && !transaction.getPersonnelId().equals("")) {
//            //get Personnel by Id
//            personnelMono = personnelRepository.findById(transaction.getPersonnelId());
//
//        } else {   //Get the Business Client by Id
//            businessMono = businessRepository.findById(transaction.getBusinessId());
//        }
//
//        //Set The transacction============================================================================
//        final Transaction transactionObject = new Transaction();
//
//        // Create a mono of transaction to set the transaction and save it
//        Mono<Transaction> transactionMono = null;
//
//        if (personnelMono != null) {
//            // If a Client is Personnel you must set the Personnel Client
//            transactionMono = Mono.zip(personnelMono, bankAcountMono).map(data -> {
//
//                //Set the personnel Client
//                transactionObject.setPersonnel(data.getT1());
//
//                //Set the Account
//                transactionObject.setAccount(data.getT2());
//                //Set the rest of the attributes of transacction
//                transactionObject.setType(transaction.getType());
//                transactionObject.setAmount(transaction.getAmount());
//                transactionObject.setDate(new Date());
//
//                //assign the transaction object to a mono of transaction
//                return transactionObject;
//
//
//            });
//        } else {
//            //If a Client is Business you must set the Business Client
//            transactionMono = Mono.zip(businessMono, bankAcountMono).map(data -> {
//
//                //Set the Business Client
//                transactionObject.setBusiness(data.getT1());
//
//                //Set the bank account
//                transactionObject.setAccount(data.getT2());
//
//                //Set the rest of the attributes of transacction
//                transactionObject.setType(transaction.getType());
//                transactionObject.setAmount(transaction.getAmount());
//                transactionObject.setDate(new Date());
//
//                //assign the transaction object to a mono of transaction
//                return transactionObject;
//            });
//        }
//
//        //save the transaction
//        transactionMono = transactionMono.flatMap(result -> {
//            return transactionRepository.save(result);
//        });
//
//        //End of setting transaction ======================================================================
//
//        return transactionMono;
  //}

//    @Override
//    public Mono<Transaction> update(TransactionDto transaction) {
//        Mono<Transaction> transactionMono = transactionRepository.findById(transaction.getTransactionId());
//        Mono<Bank_Account> bankAccountMono = bankAccountRepository.findById(transaction.getAccountId());
//
//        if (transaction.getPersonnelId() != null && !transaction.getPersonnelId().equals("")) {
//            Mono<Personnel> personnelMono = personnelRepository.findById(transaction.getPersonnelId());
//
//            transactionMono = Mono.zip(transactionMono, personnelMono, bankAccountMono).map(data -> {
//
//                return setOldTransaction(data, transaction);
//            });
//            transactionMono = transactionMono.flatMap(result -> {
//
//                return transactionRepository.save(result);
//            });
//
//            return transactionMono;
//        } else {
//            Mono<Business> businessMono = businessRepository.findById(transaction.getBusinessId());
//
//            transactionMono = Mono.zip(transactionMono, businessMono, bankAccountMono).map(data -> {
//
//                return setOldTransaction(data, transaction);
//
//            });
//
//            transactionMono = transactionMono.flatMap(result -> {
//                return transactionRepository.save(result);
//            });
//            return transactionMono;
//        }
//    }
//
//    private Transaction setOldTransaction(Tuple3 data, TransactionDto transactionDto) {
//        Transaction transaction = (Transaction) data.getT1();//T1 = Transaction Object
//
//        transaction.setPersonnel((Personnel) data.getT2());// T2 = Personnel Object
//        transaction.setAccount((Bank_Account) data.getT3());// T3 = Bank_Accoun Object
//        transaction.setType(transactionDto.getType());
//        transaction.setAmount(transactionDto.getAmount());
//        transaction.setDate(new Date());
//
//        return transaction;
//    }

    @Override
    public Mono<Void> delete(String id) {

        return transactionRepository.deleteById(id);

    }
}
