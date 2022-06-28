package com.nttdata.model.dao.util;



public enum AccountType {
    /**p
     * 1: Ahorro
     * 2: Cuenta corriente
     * 3: Plazo fijo
     *
     * TC
     * 4: Personal
     * 5: Empresarial
     * 6: Tarjeta de Crédito personal o empresarial
     * */

    /**
     * Funcionalidad agregada:
     * Personal -> VIP
     * Empresaliar -> PYME
     *
     * 7: VIP
     * 8: PYME
     * */
    AHORRO, CUENTA_CORRIENTE,PLAZO_FIJO,VIP, PERSONAL,EMPRESARIAL,TARJETA_CREDITO,PYME,NONE
}
