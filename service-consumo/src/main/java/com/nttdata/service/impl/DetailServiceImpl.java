package com.nttdata.service.impl;

import com.nttdata.model.dao.Detail;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.repository.DetailRepository;
import com.nttdata.service.DetailService;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl extends AbstractServiceImpl<Detail> implements DetailService {
    private final DetailRepository detailRepository;
    public DetailServiceImpl(DetailRepository detailRepository){
        this.detailRepository=detailRepository;
    }
    @Override
    AbstractRepository<Detail> getRepo() {
        return detailRepository;
    }
}
