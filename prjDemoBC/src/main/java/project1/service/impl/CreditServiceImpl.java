package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.bankAccount.BankAccountDto;
import project1.dto.credit.CreditDto;
import project1.model.Bank_Account;
import project1.model.Business;
import project1.model.Credit;
import project1.model.Transaction;
import project1.repository.IBusinessRepository;
import project1.repository.ICreditRepository;
import project1.service.interfaces.ICreditService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CreditServiceImpl implements ICreditService {

    @Autowired
    private ICreditRepository creditRepository;

    @Autowired
    private IBusinessRepository businessRepository;

    @Override
    public Flux<Credit> list() {
        return creditRepository.findAll();
    }

    @Override
    public Mono<Credit> show(String creditId) {
        return creditRepository.findById(creditId);
    }

    @Override
    public Mono<Credit> create(CreditDto creditDto) {
        Mono<Business> businessMono = businessRepository.findById(creditDto.getBusinessId());

        Mono<Credit> creditMono = businessMono.map(business -> {

            Credit credit = new Credit();

            credit.setBusiness(business);
            credit.setInterestRate(creditDto.getInterestRate());

            return credit;
        });

        creditMono= creditMono.flatMap(entity -> {
            return creditRepository.save(entity);
        });


        return creditMono;
    }

    @Override
    public Mono<Credit> update(CreditDto creditDto) {

        Mono<Credit> creditMono= creditRepository.findById(creditDto.getCreditId());
        Mono<Business> businessMono = businessRepository.findById(creditDto.getBusinessId());


        creditMono= Mono.zip(creditMono,businessMono).map(data->{
           Credit credit = data.getT1();

           credit.setBusiness(data.getT2());
           credit.setInterestRate(creditDto.getInterestRate());

           return  credit;
        });

        creditMono= creditMono.flatMap(result->{
           return  creditRepository.save(result);
        });

        return  creditMono;
    }

    @Override
    public Mono<Void> delete(String id) {
        Mono<Credit> creditMono = creditRepository.findById(id);

        creditMono.flatMap(result -> creditRepository.delete(result));

        return Mono.just(null);
    }
}
