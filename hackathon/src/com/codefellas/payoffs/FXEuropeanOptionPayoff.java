package com.codefellas.payoffs;

import com.codefellas.common.businessobject.Currency;
import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.common.commonobjects.CurrencyAmount;
import org.threeten.bp.ZonedDateTime;

import java.util.Map;
import java.util.TreeSet;

/**
 * Created by rjain236 on 25/11/15.
 */
public class FXEuropeanOptionPayoff extends FXPayoff {

    protected final double strike;
    protected final ZonedDateTime expiryDate;

    public FXEuropeanOptionPayoff(double notional, Currency notionalCcy, ZonedDateTime settleDate, CurrencyPair
            currencyPair, double strike, ZonedDateTime  expiryDate){
        this.strike = strike;
        this.expiryDate = expiryDate;
        this.currencyPair = currencyPair;
        this.notional = new CurrencyAmount(notionalCcy,notional);
        this.settleDate = settleDate;
    }

    @Override
    public double calculatePayoff(float[] assetValues, Map<String, Double> pathDependentVariables) {
        return Math.max(assetValues[assetValues.length-1] - strike,0);
    }

    @Override
    public TreeSet<ZonedDateTime> getRequiredDate() {
        TreeSet<ZonedDateTime> zonedDateTimes = new TreeSet<>();
        zonedDateTimes.add(expiryDate);
        return zonedDateTimes;
    }

    public double getStrike() {
        return strike;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }
}
