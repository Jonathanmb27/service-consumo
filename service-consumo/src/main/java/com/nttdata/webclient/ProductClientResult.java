package com.nttdata.webclient;

import com.nttdata.webclient.dao.Request;
import com.nttdata.webclient.dao.Response;
import org.springframework.web.reactive.function.client.WebClient;

public interface ProductClientResult {
     WebClient retrievePersonResult();
}
