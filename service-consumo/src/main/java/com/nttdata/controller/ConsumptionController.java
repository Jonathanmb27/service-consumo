package com.nttdata.controller;

import com.nttdata.model.dao.Account;
import com.nttdata.model.request.ConsumptionRequest;
import com.nttdata.model.request.YanquiRequest;
import com.nttdata.model.response.ConsumptionResponse;
import com.nttdata.model.response.YanquiResponse;
import com.nttdata.service.AccountService;
import com.nttdata.service.YanquiAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequestMapping("/consumptions")
@RestController
public class ConsumptionController {
    private final Logger LOGGER= LoggerFactory.getLogger("ConsumptionController");
    private final AccountService accountService;
    private final YanquiAccountService yanquiAccountService;

    public ConsumptionController(AccountService accountService,YanquiAccountService yanquiAccountService){
        this.accountService=accountService;
        this.yanquiAccountService=yanquiAccountService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ConsumptionResponse> create(@RequestBody ConsumptionRequest consumptionRequest){
       return accountService.createConsumptionRequest( consumptionRequest);
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account> findByClientId(@PathVariable String id){
        Optional<Account>account=accountService.findByClientId(id);
        if(account.isPresent()) return Mono.just(account.get());
        else throw new RuntimeException("No se encontro el dato");

    }

    //*
    // llamado de yanqui
    // */
    @PostMapping("/yanqui")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<YanquiResponse> depoistYanqui(@RequestBody YanquiRequest yanquiRequest){
       return yanquiAccountService.depositYanqui(yanquiRequest);
    }

}
