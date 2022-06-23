package com.nttdata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ConsumptionRequest {
    private String userId;
    private Long cardNumber;
    private int productType;
    private int accountType;
    private int transactionType;
    private String consumptionDetails;
    private BigDecimal total;
    private LocalDateTime createdAt;

    public ConsumptionRequest(){
        createdAt=LocalDateTime.now();
    }



}
