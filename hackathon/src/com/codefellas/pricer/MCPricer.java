package com.codefellas.pricer;

import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.mcengine.MCEngine;
import com.codefellas.payoffs.Payoff;

/**
 * Created by rjain236 on 25/11/15.
 */
public class MCPricer implements Pricer{
    MarketDataContainer marketData;
    Payoff payoff;
    MCEngine mcEngine;

    public MCPricer(MarketDataContainer marketData, Payoff payoff, MCEngine mcEngine) {
        this.marketData = marketData;
        this.payoff = payoff;
        this.mcEngine = mcEngine;
    }

    @Override
    public double getPresentValue() {
        return 0;
    }
}
