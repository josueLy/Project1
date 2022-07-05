package project1.service.interfaces;

import org.springframework.web.bind.annotation.*;
import project1.dto.bankAccount.BankAccountDto;
import project1.dto.credit.CreditDto;
import project1.model.Bank_Account;
import project1.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {

    Flux<Credit> list();

    Mono<Credit> show(String creditId);

    Mono<Credit> create(CreditDto creditDto);

    Mono<Credit> update(CreditDto bankAccountDto);

    Mono<Void> delete(String id);

}
