package com.codefellas.payoffs;

import com.codefellas.common.businessobject.CurrencyPair;
import org.threeten.bp.ZonedDateTime;

import java.util.TreeSet;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class FxPayoff extends Payoff{

    CurrencyPair currencyPair;

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }
}
