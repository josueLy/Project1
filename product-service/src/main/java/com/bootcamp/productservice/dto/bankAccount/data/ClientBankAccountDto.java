package com.bootcamp.productservice.dto.bankAccount.data;

import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class ClientBankAccountDto {

    public Bank_Account bank_account;
    public BankAccountDto bankAccountDto;
    @Autowired
    public IBankAccountRepository bankAccountRepository;

    @Autowired
    public WebClient.Builder webClientBuilder;

}
