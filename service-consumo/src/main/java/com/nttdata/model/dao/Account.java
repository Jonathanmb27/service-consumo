package com.nttdata.model.dao;

import com.nttdata.model.dao.util.AccountType;
import com.nttdata.model.dao.util.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@Document
public class Account  extends AbstractDocument{

    private String userId;
    private ProductType prodcutType;
    private AccountType accountType;
    private Long cardNumber;
    @DBRef
    private List<Detail> details;
    public Account(){
        details=new ArrayList<>();
    }
}
