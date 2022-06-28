package com.nttdata.util;

import com.nttdata.model.dao.Account;
import com.nttdata.model.dao.util.AccountType;
import com.nttdata.model.dao.util.ProductType;
import com.nttdata.model.dao.util.TransactionType;
import com.nttdata.model.request.ConsumptionRequest;
import com.nttdata.model.response.ConsumptionResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConsumptionControllerDataTestUtils {


    public static ConsumptionRequest getMockConsumptionRequest(){
        ConsumptionRequest consumptionRequest=new ConsumptionRequest();
        consumptionRequest.setConsumptionDetails("Compra de productos lacteos");
        consumptionRequest.setCardNumber(1255469854l);
        consumptionRequest.setAccountType(1);
        consumptionRequest.setTotal(new BigDecimal(2563.3));
        consumptionRequest.setCreatedAt(LocalDateTime.now());
        consumptionRequest.setTransactionType(1);
        consumptionRequest.setProductType(1);
        consumptionRequest.setUserId("455asdf577544asd");
        consumptionRequest.setTransactionType(1);
        return consumptionRequest;
    }
    public static ConsumptionResponse getMockConsumptionResponse(){
        ConsumptionResponse consumptionResponse=new ConsumptionResponse();
        consumptionResponse.setConsumptionDetails("Operation successfully");
        consumptionResponse.setTotal(new BigDecimal(12542));
        consumptionResponse.setUserId("455asdf577544asd");
        consumptionResponse.setCreatedAt(LocalDateTime.now());
        consumptionResponse.setProductType(ProductType.PASIVO);
        consumptionResponse.setCardNumber(1255469854l);
        consumptionResponse.setAccountType(AccountType.AHORRO);
        consumptionResponse.setTransactionType(TransactionType.CARGO_CONSUMO_TC);
        return consumptionResponse;
    }
    public static Account getMockAccount(){
        Account account=new Account();
        account.setAccountType(AccountType.AHORRO);
        account.setProdcutType(ProductType.PASIVO);
        account.setUserId("454547854545");
        account.setState(1);
        account.setModifiedAt(LocalDateTime.now());
        return account;
    }
}
