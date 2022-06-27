package com.nttdata.service.impl;

import com.nttdata.model.dao.Account;
import com.nttdata.model.dao.Detail;
import com.nttdata.model.dao.util.ConvertToTypeCount;
import com.nttdata.model.dao.util.convert.ConvertToDto;
import com.nttdata.model.request.ConsumptionRequest;
import com.nttdata.model.response.ConsumptionResponse;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.repository.AccountRepository;
import com.nttdata.service.AccountService;
import com.nttdata.service.DetailService;
import com.nttdata.webclient.ProductClientResult;
import com.nttdata.webclient.dao.Request;
import com.nttdata.webclient.dao.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
@Service
public class AccountServiceImpl extends AbstractServiceImpl<Account> implements AccountService {
    private final Logger LOGGER= LoggerFactory.getLogger("AccountServiceImpl");
    private final AccountRepository accountRepository;
    private final DetailService detailService;
    private  final ProductClientResult productClientResult;
    public AccountServiceImpl(AccountRepository accountRepository,DetailService detailService,
                              ProductClientResult productClientResult){
        this.accountRepository=accountRepository;
        this.detailService=detailService;
        this.productClientResult=productClientResult;
    }
    @Override
    AbstractRepository<Account> getRepo() {
        return accountRepository;
    }

    private Optional<ConsumptionResponse> connectToProduct(ConsumptionRequest request){
        /**
         * Buscamos la tarjeta para generar la transaccion
         * */
        Optional<Account> accountOptional=accountRepository.findAll()
                .stream()
                .filter(s->s.getCardNumber().equals(request.getCardNumber())).findFirst();
        if (accountOptional.isPresent()){
            Account account=accountOptional.get();
            // actulizamos el consumo
            Detail detail=new Detail();
            detail.setTransactionType(ConvertToTypeCount.typeTransaction(request.getTransactionType()));
            detail.setConsumptionDetails(request.getConsumptionDetails());
            detail.setTotal(request.getTotal());
            detail.setModifiedAt(LocalDateTime.now());
            account.getDetails().add(detailService.create(detail));
            accountRepository.save(account);

        }else{
            Detail detail=new Detail();
            detail.setTransactionType(ConvertToTypeCount.typeTransaction(request.getTransactionType()));
            detail.setConsumptionDetails(request.getConsumptionDetails());
            detail.setTotal(request.getTotal());
            detail.setModifiedAt(LocalDateTime.now());

            Account account=new Account();
            account.setUserId(request.getUserId());
            account.setProdcutType(ConvertToTypeCount.productType(request.getProductType()));
            account.setAccountType(ConvertToTypeCount.accountType(request.getAccountType()));
            account.setCardNumber(request.getCardNumber());
            account.setDetails(Arrays.asList(detailService.create(detail)));
            accountRepository.save(account);

        }
        return Optional.of(ConvertToDto.consumptionRequestToResponse(request));
    }


    @Override
    public Mono<ConsumptionResponse> createConsumptionRequest(ConsumptionRequest consumptionRequest) {
        Request request=new Request(consumptionRequest.getCardNumber(),
                consumptionRequest.getTotal(),consumptionRequest.getTransactionType());

        return    productClientResult.retrievePersonResult().put()
                .uri("/products")
                .body(Mono.just(request), Request.class)
                .retrieve()
                .bodyToMono(Response.class)
                .flatMap(s->{
                    if(s.isResponse()==true)
                       return Mono.just(connectToProduct(consumptionRequest).get());
                    else
                        return Mono.just(new ConsumptionResponse());
                });


    }

    @Cacheable(value = "accountCache", key = "#id")
    @Override
    public Optional<Account> findByClientId(String id) {
        return accountRepository.findAll()
                .stream()
                .filter(s->s.getUserId().equals(id)).findFirst();

    }


}
