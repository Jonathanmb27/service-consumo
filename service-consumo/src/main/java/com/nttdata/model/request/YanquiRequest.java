package com.nttdata.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YanquiRequest {

    private Long dni;
    private Long phoneNumberOwner;
    private Long phoneNumberClient;
    private BigDecimal amount;
    /**
     * 1 deposito
     * 2 retiro
     * */
    private int operationType;

}
