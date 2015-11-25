package common.commonobjects;

import common.businessobject.Currency;

import java.util.*;

/**
 * Created by rjain236 on 25/11/15.
 */
public class CurrencyAmount extends Addable<CurrencyAmount>{

    Map<Currency,Double> currencyValueMap;

    public CurrencyAmount(Map<Currency, Double> currencyValueMap) {
        this.currencyValueMap = currencyValueMap;
    }

    public CurrencyAmount(Currency currency, Double amt) {
        currencyValueMap = new HashMap<>();
        currencyValueMap.put(currency,amt);
    }

    public Double getValue(Currency currency){
        return currencyValueMap.get(currency);
    }

    public Collection<Currency> getCurrencies(){
        return currencyValueMap.keySet();
    }

    @Override
    public CurrencyAmount add(CurrencyAmount o) {
        Collection<Currency> ccys = o.getCurrencies();
        Map<Currency,Double> newList = new HashMap<>(currencyValueMap);
        for(Currency currency:ccys){
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
