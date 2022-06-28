package com.nttdata.controller;

import com.nttdata.model.dao.Account;
import com.nttdata.model.request.ConsumptionRequest;
import com.nttdata.model.response.ConsumptionResponse;
import com.nttdata.service.AccountService;
import com.nttdata.util.ConsumptionControllerDataTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebFluxTest
//@ExtendWith(SpringExtension.class)
public class ConsumptionControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    AccountService accountService;

    ConsumptionRequest consumptionRequest;
    ConsumptionResponse consumptionResponse;
    Account account;

    @BeforeEach
    void setUp(){
         consumptionRequest= ConsumptionControllerDataTestUtils.getMockConsumptionRequest();
         consumptionResponse=ConsumptionControllerDataTestUtils.getMockConsumptionResponse();
         account=ConsumptionControllerDataTestUtils.getMockAccount();

    }

    @Test
    void createdConsumption(){
        Mockito.when(accountService.createConsumptionRequest(consumptionRequest))
                .thenReturn(Mono.just(new ConsumptionResponse()));
        webTestClient.post()
                .uri("/consumptions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(consumptionRequest),ConsumptionRequest.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void findByClientIdTest(){
        Mockito.when(accountService.findByClientId(account.getId()))
                .thenReturn(Optional.of(new Account()));

        webTestClient.get().uri(uriBuilder -> uriBuilder.path("/consumptions/{id}").build(account.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}
