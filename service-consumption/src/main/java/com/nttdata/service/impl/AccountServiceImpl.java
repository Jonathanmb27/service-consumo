package com.nttdata.service.impl;

import com.nttdata.client.ClientResultClient;
import com.nttdata.client.dto.ClientResult;
import com.nttdata.dao.Account;
import com.nttdata.repository.AccountRepository;
import com.nttdata.service.AccountSerevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountSerevice {
    private final Logger LOGGER= LoggerFactory.getLogger("AccountServiceImpl");
    private  AccountRepository accountRepository;
    private ClientResultClient client;
    public AccountServiceImpl(AccountRepository accountRepository, ClientResultClient client){
        this.accountRepository=accountRepository;
        this.client=client;
    }


    /**
     * @param account
     * -para crear una cuneta se debera verificar
     * si el usuario ingresado existe en el
     * microservicio de clientes.
     *
     * - se debera verificar si es:
     *       1: consumo
     *       2: depositos
     *       3: retiros
     * */
    @Override
    public void create(Account account) {
        //verificamos la existencia de el cleinte
       ClientResult clientResult=client.retrievePersonResult(account.getClientId());

        if(clientResult.getClientId().equals(account.getClientId())){
            LOGGER.info("# Objeto obtenido con exito");
            switch (account.getDetail().getTransactionType()){
                case 1:
                    /**
                     * llamar al microservicio de productos
                     * para verificar la existencia del mismo y
                     * hacer la operacion de restar(consumo)
                     * */

                    break;
                case 2:
                    // debemos sumar en el saldo de la tarjeta
                    break;
                case 3:
                    // debemos restar en el saldo de la tarjeta
                    break;
            }
            account.setClientId(clientResult.getClientId());
            account.setClientName(clientResult.getName());
            account.setClientIdentification(clientResult.getIdentification());
            accountRepository.save(account);
        }
        else LOGGER.error("# se produjo un error al conectarse con microservicio producto");
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     * @param id
     * retorna todos los consumos
     * de un cliente especifico,
     * el id del parametro es el id
     * del objeto persona
     * */
    @Override
    public List<Account> findById(String id) {
        return accountRepository.findAll()
                .stream()
                .filter(s->s.getClientId().equals(id))
                .collect(Collectors.toList());

    }
}
