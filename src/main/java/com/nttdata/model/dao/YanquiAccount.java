package com.nttdata.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class YanquiAccount extends AbstractDocument{


    private Long dni;
    private Long phoneNumber;
    @DBRef
    private List<Detail> details=new ArrayList<>();
}
