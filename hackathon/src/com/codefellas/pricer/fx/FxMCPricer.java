package com.codefellas.pricer.fx;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.common.math.random.MersenneTwister;
import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.mcengine.FXBlackMCEngine;
import com.codefellas.mcengine.FXMCEngine;
import com.codefellas.payoffs.FXPayoff;
import com.codefellas.payoffs.Payoff;
import com.codefellas.pricer.MCPricer;

/**
 * Created by rjain236 on 27/11/15.
 */
public abstract class FxMCPricer extends MCPricer<FXMCEngine> {

    protected FXMCEngine fxMcEngine;

    public FxMCPricer(MarketDataContainer marketData, FXPayoff payoff, int nPaths, CurveDefinition discountCurve) {
        super(marketData, nPaths,discountCurve);
        this.fxMcEngine = new FXBlackMCEngine(1,new MersenneTwister(),payoff,marketData);
    }

}
