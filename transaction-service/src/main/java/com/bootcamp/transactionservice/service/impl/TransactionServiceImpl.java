package com.bootcamp.transactionservice.service.impl;

import com.bootcamp.transactionservice.dto.transaction.TransactionDto;

import com.bootcamp.transactionservice.Util.Util;
import com.bootcamp.transactionservice.dto.transaction.TransactionDto;
import com.bootcamp.transactionservice.model.Bank_Account;
import com.bootcamp.transactionservice.model.Business;

import com.bootcamp.transactionservice.model.Personnel;
import com.bootcamp.transactionservice.model.Transaction;
import com.bootcamp.transactionservice.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
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

    private static int current_number_transactions;


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

<<<<<<< HEAD
        if (transacction.getNumeroMT() > nmt ) {
            double comision = 0.10;
            Transaction transaction1 = new Transaction(transacction.getTransactionId(),
            transacction.getAmount()*comision);
            //Transaction transaction1 = new Transaction (transacction.getTransactionId(), transacction.getAmount());


       */
            return null;

    }
//    @Override
  /* public Mono<Transaction> save(TransactionDto transaction) {

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

   */

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

    @Override
    public Mono<Transaction> save(TransactionDto transactionDto) {
        Mono<Transaction> transactionMono = null;

        //consuming product service
        Mono<Bank_Account> bankAccountMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8086/BankAccount/show/" + transactionDto.getAccountId())
                .retrieve()
                .bodyToMono(Bank_Account.class);

        transactionMono = bankAccountMono.map(bank_account -> {
            Transaction transaction = new Transaction();
            transaction.setAccount(bank_account);
            transaction.setType(transactionDto.getType());
            transaction.setAccount_destiny(transactionDto.getAccount_destiny());
            return transaction;
        });

        transactionMono = transactionMono.flatMap(transaction -> {
            return this.getClient(transaction, transactionDto);
        });

        //Setting the Amount
        transactionMono = Mono.zip(bankAccountMono, transactionMono)
                .flatMap(data -> {
                    return this.manageAmount(data.getT1(), data.getT2(), transactionDto);
                });


        return transactionMono.flatMap(transactionRepository::save);
    }


    private Mono<Transaction> manageAmount(Bank_Account bank_account, Transaction transaction, TransactionDto transactionDto) {
        transaction.setAccount(bank_account);

        if ((transactionDto.getType().equals(Util.DRAW_ON_TRANSACTION)
                || transactionDto.getType().equals(Util.DEPOSIT_TRANSACTION))
                && bank_account.getMax_number_transactions() > current_number_transactions) {

            transaction.setAmount(transactionDto.getAmount());

        } else if ((transactionDto.getType().equals(Util.DRAW_ON_TRANSACTION)
                || transactionDto.getType().equals(Util.DEPOSIT_TRANSACTION))
                && bank_account.getMax_number_transactions() <= current_number_transactions) {

            double aumount = bank_account.getComission() + transactionDto.getAmount();
            // adding current_number_transactions variable
            transaction.setAmount(aumount);
        }

        return Mono.just(transaction);
    }

    @Override
    public Mono<Transaction> update(TransactionDto transactionDto) {
        Mono<Transaction> transactionMono = transactionRepository.findById(transactionDto.getTransactionId());
        //consuming product service
        Mono<Bank_Account> bankAccountMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8086/BankAccount/show/" + transactionDto.getAccountId())
                .retrieve()
                .bodyToMono(Bank_Account.class);

        transactionMono = bankAccountMono.map(bank_account -> {
            Transaction transaction = new Transaction();
            transaction.setAccount(bank_account);
            transaction.setType(transactionDto.getType());
            transaction.setAccount_destiny(transactionDto.getAccount_destiny());
            transaction.setAmount(transactionDto.getAmount());
            return transaction;
        });

        transactionMono = transactionMono.flatMap(transaction -> {
            return this.getClient(transaction, transactionDto);
        });

        return transactionMono.flatMap(transactionRepository::save);
    }

    private Mono<Transaction> getClient(Transaction transaction, TransactionDto transactionDto) {
        Mono<Transaction> transactionMono = null;
        if (transactionDto.getBusinessId() != null && !transactionDto.getBusinessId().equals("")) {
            // Get the business client by id
            Mono<Business> businessMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/business/show/" + transactionDto.getBusinessId()
                            ).retrieve()
                            .bodyToMono(Business.class);
            transactionMono = businessMono.map(business -> {
                Transaction transactionObject = transaction;
                transactionObject.setBusiness(business);
                return transactionObject;
            });
        }

        if (transactionDto.getPersonnelId() != null && !transactionDto.getPersonnelId().equals("")) {
            //get Personnel by Id
            Mono<Personnel> personnelMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/personnel/showById/" + transactionDto.getPersonnelId()
                            )
                            .retrieve()
                            .bodyToMono(Personnel.class);

            transactionMono = personnelMono.map(personnel -> {
                Transaction transactionObject = transaction;
                transactionObject.setPersonnel(personnel);
                return transactionObject;
            });
        }
        return transactionMono;
    }

    @Override
    public Mono<Void> delete(String id) {

        return transactionRepository.deleteById(id);

    }


}
