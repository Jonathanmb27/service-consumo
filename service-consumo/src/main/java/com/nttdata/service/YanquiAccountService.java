package com.nttdata.service;

import com.nttdata.model.dao.YanquiAccount;
import com.nttdata.model.request.YanquiRequest;
import com.nttdata.model.response.YanquiResponse;
import reactor.core.publisher.Mono;

public interface YanquiAccountService extends AbstractService<YanquiAccount>{

    Mono<YanquiResponse> depositYanqui(YanquiRequest yanquiRequest);
}
