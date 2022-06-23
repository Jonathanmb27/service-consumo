package com.nttdata.service;

import com.nttdata.model.dao.Account;
import com.nttdata.model.request.ConsumptionRequest;
import com.nttdata.model.response.ConsumptionResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AccountService extends AbstractService<Account>{


    Mono<ConsumptionResponse> createConsumptionRequest(ConsumptionRequest request);
    Optional<Account> findByClientId(String id);


}
