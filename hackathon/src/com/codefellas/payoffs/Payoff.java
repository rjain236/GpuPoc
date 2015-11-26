package com.codefellas.payoffs;

import com.codefellas.common.commonobjects.CurrencyAmount;
import org.threeten.bp.ZonedDateTime;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class Payoff {
    ZonedDateTime tradeDate;
    ZonedDateTime settleDate;
    CurrencyAmount notional;

    public abstract double calculatePayoff(float[] assetValues, Map<String,Double> pathDependentVariables);

    public abstract TreeSet<ZonedDateTime> getRequiredDate();
}
