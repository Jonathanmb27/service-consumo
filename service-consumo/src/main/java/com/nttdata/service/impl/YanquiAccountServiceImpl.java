package com.nttdata.service.impl;

import com.nttdata.events.Deposit;
import com.nttdata.events.EventDispatcher;
import com.nttdata.model.dao.Detail;
import com.nttdata.model.dao.YanquiAccount;
import com.nttdata.model.dao.util.TransactionType;
import com.nttdata.model.request.YanquiRequest;
import com.nttdata.model.response.YanquiResponse;
import com.nttdata.repository.AbstractRepository;
import com.nttdata.repository.YanquiAccountRepository;
import com.nttdata.service.DetailService;
import com.nttdata.service.YanquiAccountService;
import com.nttdata.webclient.ProductClientResult;
import com.nttdata.webclient.dao.YanquiDeposiExist;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Service
public class YanquiAccountServiceImpl extends AbstractServiceImpl<YanquiAccount> implements YanquiAccountService {

    private final YanquiAccountRepository yanquiAccountRepository;
    private final ProductClientResult productClientResult;
    private final DetailService detailService;
    private final EventDispatcher eventDispatcher;

    public YanquiAccountServiceImpl(YanquiAccountRepository yanquiAccountRepository,
                                    EventDispatcher eventDispatcher,
                                    ProductClientResult productClientResult,
                                    DetailService detailService){
        this.yanquiAccountRepository=yanquiAccountRepository;
        this.eventDispatcher=eventDispatcher;
        this.productClientResult=productClientResult;
        this.detailService=detailService;
    }
    @Override
    AbstractRepository<YanquiAccount> getRepo() {
        return yanquiAccountRepository;
    }

    @Override
    public Mono<YanquiResponse> depositYanqui(YanquiRequest yanquiRequest) {
        /**
         * Verificamos la exixtencia del monedero movil,
         * luego verificamos el tipo de transaccion a realizar
         * y paso seguido lanzamos un evento para que producto
         * debite el monto de la tansaccion
         * */

      return   productClientResult.retrievePersonResult().get()
                .uri("/products/yanqui/{param}",yanquiRequest.getPhoneNumberOwner())
                .retrieve()
                .bodyToMono(YanquiDeposiExist.class).flatMap(s->{
                    if(s.isExists()){
                        return Mono.just(generateOperation(yanquiRequest,s.getAmount()));
                    }else return Mono.just(new YanquiResponse("Error transaction"));
              });

    }
    private YanquiResponse generateOperation(YanquiRequest yanquiRequest, BigDecimal amount){
        /**
         * 1 deposito
         * 2 retiro
         *
         * Aqui ya se verifico que existe el monedero movil
         * ahora verificamos si ya tiene transaccines
         * realizadas para poder generar los consumos
         * */
         if(yanquiRequest.getOperationType()==1){
             generateTransaction(yanquiRequest);
             //  send  event
             Deposit deposit=new Deposit();
             deposit.setOperationType(1);
             deposit.setPhoneNumber(yanquiRequest.getPhoneNumberOwner());
             deposit.setAmount(yanquiRequest.getAmount());
             eventDispatcher.publish(deposit);
             return new YanquiResponse("Successfully");
         }else{
             if((amount.compareTo(yanquiRequest.getAmount())>=0)){
                 generateTransaction(yanquiRequest);
                 // send event
                 Deposit deposit=new Deposit();
                 deposit.setOperationType(2);
                 deposit.setPhoneNumber(yanquiRequest.getPhoneNumberOwner());
                 deposit.setAmount(yanquiRequest.getAmount());
                 eventDispatcher.publish(deposit);
                 return new YanquiResponse("Successfully");
             }else{
                 return new YanquiResponse("Error operation, insufficient balance");
             }
         }


    }

    @Cacheable(value = "yanquiAccountCache", key = "#numberPhone")
    private Optional<YanquiAccount> findByYanquiAccount(Long numberPhone){
      return   yanquiAccountRepository.findAll()
                .stream().filter(s->s.getPhoneNumber().equals(numberPhone)).findFirst();
    }
    private void generateTransaction(YanquiRequest yanquiRequest){
        Optional<YanquiAccount> yanquiAccountOptional=findByYanquiAccount(yanquiRequest.getPhoneNumberOwner());
        if(yanquiAccountOptional.isPresent()){
            YanquiAccount yanquiAccountFound=yanquiAccountOptional.get();

            Detail detail=new Detail();
            detail.setTotal(yanquiRequest.getAmount());
            detail.setPhoneNumber(yanquiRequest.getPhoneNumberClient());
            if(yanquiRequest.getOperationType()==1){
                detail.setTransactionType(TransactionType.DEPOSITO);
            }
            if(yanquiRequest.getOperationType()==2){
                detail.setTransactionType(TransactionType.RETIRTO);
            }
            Detail detail1=detailService.create(detail);
            yanquiAccountFound.getDetails().add(detail1);
            yanquiAccountRepository.save(yanquiAccountFound);

        }else{
            Detail detail=new Detail();
            detail.setTotal(yanquiRequest.getAmount());
            detail.setPhoneNumber(yanquiRequest.getPhoneNumberClient());
            if(yanquiRequest.getOperationType()==1){
                detail.setTransactionType(TransactionType.DEPOSITO);
            }
            if(yanquiRequest.getOperationType()==2){
                detail.setTransactionType(TransactionType.RETIRTO);
            }
            Detail detail1=detailService.create(detail);

            YanquiAccount yanquiAccount=new YanquiAccount();
            yanquiAccount.setDni(yanquiRequest.getDni());
            yanquiAccount.setPhoneNumber(yanquiRequest.getPhoneNumberOwner());
            yanquiAccount.setDetails(Arrays.asList(detail1));
            yanquiAccountRepository.save(yanquiAccount);
        }
    }
}
