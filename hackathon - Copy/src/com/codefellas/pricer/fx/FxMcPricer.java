package com.codefellas.pricer.fx;

import com.codefellas.common.math.random.MersenneTwister;
import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.mcengine.FxBlackMcEngine;
import com.codefellas.mcengine.FxMcEngine;
import com.codefellas.payoffs.FxPayoff;
import com.codefellas.pricer.McPricer;

/**
 * Created by rjain236 on 27/11/15.
 */
public abstract class FxMcPricer extends McPricer<FxMcEngine> {

    protected FxMcEngine fxMcEngine;

    public FxMcPricer(MarketDataContainer marketData, FxPayoff payoff, int nPaths, CurveDefinition discountCurve) {
        super(marketData, nPaths,discountCurve);
        this.fxMcEngine = new FxBlackMcEngine(1,new MersenneTwister(),payoff,marketData);
    }

}
