package com.codefellas.payoffs;

import com.codefellas.common.commonobjects.CurrencyAmount;
import org.threeten.bp.ZonedDateTime;

import java.util.Map;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class Payoff {
    ZonedDateTime tradeDate;
    ZonedDateTime settleDate;
    CurrencyAmount notional;

    public abstract double calculatePayoff(double[] assetValues, Map<String,Double> pathDependentVariables);
}
