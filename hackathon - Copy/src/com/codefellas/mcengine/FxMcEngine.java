package com.codefellas.mcengine;

import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.payoffs.FxPayoff;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 27/11/15.
 */
public abstract class FxMcEngine extends McEngine<FxPayoff> {

    public FxMcEngine(ZonedDateTime referenceDate, int nDimensions, RandomGenerator randomGenerator, FxPayoff payoff) {
        super(referenceDate, nDimensions, randomGenerator, payoff);
    }
}
