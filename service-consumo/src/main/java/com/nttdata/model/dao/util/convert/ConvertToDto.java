package com.nttdata.model.dao.util.convert;

import com.nttdata.model.dao.util.ConvertToTypeCount;
import com.nttdata.model.request.ConsumptionRequest;
import com.nttdata.model.response.ConsumptionResponse;

import java.time.LocalDateTime;

public class ConvertToDto {

    public static ConsumptionResponse consumptionRequestToResponse(ConsumptionRequest request){
        ConsumptionResponse response=new ConsumptionResponse();
        response.setConsumptionDetails(response.getConsumptionDetails());
        response.setAccountType(ConvertToTypeCount.accountType(request.getAccountType()));
        response.setCardNumber(request.getCardNumber());
        response.setCreatedAt(LocalDateTime.now());
        response.setTotal(request.getTotal());
        response.setProductType(ConvertToTypeCount.productType(request.getProductType()));
        response.setTransactionType(ConvertToTypeCount.typeTransaction(request.getTransactionType()));
        response.setUserId(request.getUserId());
        return response;

    }
}
