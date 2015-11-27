package com.codefellas.common.commonobjects;

import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

import java.util.*;

/**
 * Created by rjain236 on 25/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class CurrencyAmount extends Addable<CurrencyAmount>{

    Map<com.codefellas.common.businessobject.Currency,Double> currencyValueMap;

    public CurrencyAmount(Map<com.codefellas.common.businessobject.Currency, Double> currencyValueMap) {
        this.currencyValueMap = currencyValueMap;
    }

    public CurrencyAmount(com.codefellas.common.businessobject.Currency currency, Double amt) {
        currencyValueMap = new HashMap<>();
        currencyValueMap.put(currency,amt);
    }

    public Double getValue(com.codefellas.common.businessobject.Currency currency){
        return currencyValueMap.get(currency);
    }

    public Collection<com.codefellas.common.businessobject.Currency> getCurrencies(){
        return currencyValueMap.keySet();
    }

    @Override
    public CurrencyAmount add(CurrencyAmount o) {
        Collection<com.codefellas.common.businessobject.Currency> ccys = o.getCurrencies();
        Map<com.codefellas.common.businessobject.Currency,Double> newList = new HashMap<>(currencyValueMap);
        for(com.codefellas.common.businessobject.Currency currency:ccys){
            Double value = newList.get(currency);
            Double newValue = o.getValue(currency);
            if(value!=null){
                newValue = value+newValue;
            }
            newList.put(currency,newValue);
        }
        return new CurrencyAmount(newList);
    }
}
