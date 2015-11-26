package com.codefellas.pricer.fx;

import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.payoffs.FXEuropeanOptionPayoff;
import com.codefellas.payoffs.FXPayoff;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 27/11/15.
 */
public class FXEuropeanOptionPricer extends FxMCPricer {

    public FXEuropeanOptionPricer(MarketDataContainer marketData, FXPayoff payoff, int nPaths, CurveDefinition discountCurve) {
        super(marketData, payoff, nPaths, discountCurve);
    }

    @Override
    public double getPresentValue() throws Exception {
        FXEuropeanOptionPayoff fxEuropeanOptionPayoff = (FXEuropeanOptionPayoff) fxMcEngine.getPayoff();
        ZonedDateTime expiryDate = fxEuropeanOptionPayoff.getExpiryDate();
        return fxMcEngine.price(getnPaths())*marketData.getRateCurve(discountCurve).getDiscountFactor(expiryDate);
    }
}
