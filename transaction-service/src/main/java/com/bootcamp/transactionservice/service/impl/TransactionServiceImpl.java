package com.bootcamp.transactionservice.service.impl;

import com.bootcamp.transactionservice.service.interfaces.Util.Util;
import com.bootcamp.transactionservice.dto.transaction.TransactionDto;
import com.bootcamp.transactionservice.model.*;
import com.bootcamp.transactionservice.repository.IPaymentRepository;
import com.bootcamp.transactionservice.repository.ITransactionRepository;
import com.bootcamp.transactionservice.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IPaymentRepository paymentRepository;

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
    @KafkaListener(topics = "bootcamp-proyecto4", groupId = "group_id")
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

        Mono.zip(bankAccountMono, transactionMono)
                .flatMap(data -> savePayment(data.getT1(), data.getT2(), transactionDto))
                .subscribe();

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

    private Mono<Payment> savePayment(Bank_Account bank_account,Transaction transaction,TransactionDto transactionDto) {
        if(bank_account.getProduct_type().getDescription().equals(Util.CREDIT_PRODUCT))
        {
            Payment payment = new Payment();
            payment.setBankAccount(bank_account);

            double price = transaction.getAmount()/transactionDto.getQuota_number();

            Calendar calendar = Calendar.getInstance();
            int last_day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int current_month = calendar.get(Calendar.MONTH)+1;
            int current_year = calendar.get(Calendar.YEAR);

            String date = last_day+"/"+current_month+"/"+current_year;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date expirationDate = null;
            try {
                expirationDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<Quota> quotas = new ArrayList<>();
            for(int quotaCounter =1 ; quotaCounter<=transactionDto.getQuota_number();quotaCounter++)
            {
                Quota quota = new Quota();
                quota.setQuotaId(UUID.randomUUID().toString());
                quota.setPrice(price);
                quota.setExpirationDate(new Date());
                //quota.setExpirationDate(expirationDate);
                quota.setStatus(Util.IN_DUE);
                quotas.add(quota);

                //Add a Month
                calendar.setTime(expirationDate);
                calendar.add(Calendar.MONTH,1);
                expirationDate = calendar.getTime();
            }

            payment.setQuotas(quotas);
            if(transaction.getPersonnel()!=null)
            {
                payment.setPersonnel(transaction.getPersonnel());
            }else
            {
                payment.setBusiness(transaction.getBusiness());
            }

            payment.setPaymentDate(new Date());

           return paymentRepository.save(payment);
        }else
        {
            return null;
        }
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
            transaction.setDate(new Date());
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
