package com.codefellas.pricer.fx;

import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.payoffs.FxEuropeanOptionPayoff;
import com.codefellas.payoffs.FxPayoff;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 27/11/15.
 */
public class FxEuropeanOptionPricer extends FxMcPricer {

    public FxEuropeanOptionPricer(MarketDataContainer marketData, FxEuropeanOptionPayoff payoff, int nPaths, CurveDefinition discountCurve) {
        super(marketData, payoff, nPaths, discountCurve);
    }

    @Override
    public double getPresentValue() throws Exception {
        FxEuropeanOptionPayoff fxEuropeanOptionPayoff = (FxEuropeanOptionPayoff) fxMcEngine.getPayoff();
        ZonedDateTime expiryDate = fxEuropeanOptionPayoff.getExpiryDate();
        float[] initialstate = new float[1];
        initialstate[0]=0;
        return fxMcEngine.price(initialstate,getnPaths())[0]*marketData.getRateCurve(discountCurve).getDiscountFactor(expiryDate);
    }
}
