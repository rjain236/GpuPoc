package com.codefellas.mcengine;

import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.payoffs.FXPayoff;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 27/11/15.
 */
public abstract class FXMCEngine extends MCEngine<FXPayoff>{

    public FXMCEngine(ZonedDateTime referenceDate, int nDimensions, RandomGenerator randomGenerator, FXPayoff payoff) {
        super(referenceDate, nDimensions, randomGenerator, payoff);
    }
}
