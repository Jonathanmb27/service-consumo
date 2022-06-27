package com.nttdata.webclient.dao;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class YanquiDeposiExist {
    private Long phoneNumber;
    private boolean exists;
    private BigDecimal amount;
}
