package com.nttdata.model.dao;

import com.nttdata.model.dao.util.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Detail extends AbstractDocument{

    private TransactionType transactionType;
    private String consumptionDetails;
    private BigDecimal total;
    private Long phoneNumber;
}
