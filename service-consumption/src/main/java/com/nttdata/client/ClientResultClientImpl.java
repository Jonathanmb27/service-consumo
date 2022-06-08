package com.nttdata.client;

import com.nttdata.client.dto.ClientResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientResultClientImpl implements ClientResultClient{



    private  RestTemplate restTemplate=new RestTemplate();
    private final String clientHost;

    public ClientResultClientImpl(/*final WebClient webClient,*/
                           @Value("${clientHost}") final String clientHost){
      //  this.webClient=webClient;
        this.clientHost=clientHost;

    }
    @Override
    public ClientResult retrievePersonResult(String id) {
      /*return WebClient.create()
                .get()
                .uri(clientHost+"/persons/"+id)
                .retrieve()
                .bodyToMono(ClientResult.class);*/

      // return null;//webClient.get().uri(clientHost+"/persons/"+id).retrieve().bodyToFlux(ClientResult.class).blockFirst();
        return restTemplate.getForObject(clientHost+"/persons/"+id,ClientResult.class);
    }
}
