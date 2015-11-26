package com.codefellas.pricer;

import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.mcengine.MCEngine;
import com.codefellas.payoffs.Payoff;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class MCPricer<T extends MCEngine> implements Pricer{

    protected MarketDataContainer marketData;
    protected T mcEngine;
    protected int nPaths;
    protected CurveDefinition discountCurve;

    public MCPricer(MarketDataContainer marketData, int nPaths, CurveDefinition discountCurve) {
        this.marketData = marketData;
        this.nPaths = nPaths;
    }

    public CurveDefinition getDiscountCurve() {
        return discountCurve;
    }

    public MarketDataContainer getMarketData() {
        return marketData;
    }

    public T getMcEngine() {
        return mcEngine;
    }

    public int getnPaths() {
        return nPaths;
    }
}
