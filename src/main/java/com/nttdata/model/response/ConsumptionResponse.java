package com.nttdata.model.response;

import com.nttdata.model.dao.util.AccountType;
import com.nttdata.model.dao.util.ProductType;
import com.nttdata.model.dao.util.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionResponse {
    private String userId;
    private Long cardNumber;
    private ProductType productType;
    private AccountType accountType;
    private TransactionType transactionType;
    private String consumptionDetails;
    private BigDecimal total;
    private LocalDateTime createdAt;
}
