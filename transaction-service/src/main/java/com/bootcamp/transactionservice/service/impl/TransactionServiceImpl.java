package com.bootcamp.transactionservice.service.impl;

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
            return transaction;
        });

        transactionMono = transactionMono.flatMap(transaction -> {
           return this.getClient(transaction,transactionDto);
        });

        //Setting the Amount
        transactionMono = Mono.zip(bankAccountMono, transactionMono)
                .flatMap(data -> {
                    System.out.println(data.getT2().toString());
                    return this.manageAmount(data.getT1(), data.getT2(), transactionDto);
                });

        transactionMono = transactionMono.flatMap(transactionRepository::save);

        return transactionMono;
    }

    private Mono<Transaction> getClient(Transaction transaction, TransactionDto transactionDto) {
        Mono<Transaction> transactionMono =null;
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
                return  transactionObject;
            });
        }

        if(transactionDto.getPersonnelId()!= null && !transactionDto.getPersonnelId().equals(""))
        {
            //get Personnel by Id
            Mono<Personnel> personnelMono =
                    webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8085/personnel/showById/" + transactionDto.getPersonnelId()
                            )
                            .retrieve()
                            .bodyToMono(Personnel.class);

            transactionMono= personnelMono.map(personnel -> {
               Transaction transactionObject = transaction;
               transactionObject.setPersonnel(personnel);
               return  transactionObject;
            });
        }
        return  transactionMono;
    }

    private Mono<Transaction> getBusiness(Tuple2<Transaction,Business> data) {
        if (data.getT2() != null) {
            data.getT1().setBusiness(data.getT2());
        }
        return Mono.just(data.getT1());
    }

    private Mono<Transaction> getPersonnel(Transaction transaction, Personnel personnel) {
        if (personnel != null) {
            transaction.setPersonnel(personnel);
        }

        return Mono.just(transaction);
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
//}

    @Override
    public Mono<Void> delete(String id) {

        return transactionRepository.deleteById(id);

    }


}
