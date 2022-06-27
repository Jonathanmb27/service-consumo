package com.nttdata.model.dao.util;

public class ConvertToTypeCount {
    public static TransactionType typeTransaction(int type){
        switch (type){
            case 1: return TransactionType.DEPOSITO;
            case 2: return TransactionType.RETIRTO;
            case 3: return TransactionType.PAGO_TC;
            case 4: return TransactionType.CARGO_CONSUMO_TC;
            default: return TransactionType.NONE;
        }
    }

    public static AccountType accountType(int type){
        switch (type){
            case 1: return AccountType.AHORRO;
            case 2: return AccountType.CUENTA_CORRIENTE;
            case 3: return AccountType.PLAZO_FIJO;
            case 4: return AccountType.PERSONAL;
            case 5: return AccountType.EMPRESARIAL;
            case 6: return AccountType.TARJETA_CREDITO;
            case 7: return AccountType.VIP;
            case 8: return AccountType.PYME;
            default: return AccountType.NONE;
        }

    }
    public static ProductType productType(int type){
        switch (type){
            case 1: return ProductType.ACTIVO;
            case 2: return ProductType.PASIVO;
            default: return ProductType.NONE;
        }
    }
}
