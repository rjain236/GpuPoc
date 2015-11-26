package com.codefellas.mcengine;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.marketdata.fx.FxAsset;
import com.codefellas.marketdata.volsurface.ATMVolSurface;
import com.codefellas.payoffs.FXPayoff;
import com.codefellas.payoffs.Payoff;
import junit.framework.Assert;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class FXBlackMCEngine extends FXMCEngine {

    MarketDataContainer marketDataContainer;

    public FXBlackMCEngine(int nDimensions, RandomGenerator randomGenerator
            ,FXPayoff payoff, MarketDataContainer marketDataContainer) {
        super(marketDataContainer.getReferenceDate(), nDimensions, randomGenerator, payoff);
        this.marketDataContainer = marketDataContainer;
    }


    @Override
    public float[][] simulate(final int nPaths) throws Exception {
        Assert.assertTrue("Only single assert simulation implemented", nDimensions ==1);
        final float[][][] randomNumbers = randomGenerator.getIndependentRandomVariables(1,nPaths,timeGrid.size());
        final float[][] assetvalues = new float[nPaths][timeGrid.size()];
        final int timeSteps = timeGrid.size();
        final float fxRateToday = (float) marketDataContainer.getForwardFxRate(getReferenceDate(),payoff.getCurrencyPair()).doubleValue();
        final float[] delT = new float[timeGrid.size()-1];
        final float[] fwdVol = new float[timeGrid.size()-1];
        final float[] drift = new float[timeGrid.size()-1];
        List<ZonedDateTime> dateGrid = new ArrayList<>(getDatesGrid());
        double prevFxRate = fxRateToday;
        for (int j = 1; j < timeGrid.size(); j++) {
            delT[j] = (float)(timeGrid.get(j)-timeGrid.get(j-1));
            fwdVol[j] = (float) marketDataContainer.getForwardVolatility(dateGrid.get(j - 1), dateGrid.get(j), payoff.getCurrencyPair());
            double nextFwd = marketDataContainer.getForwardFxRate(dateGrid.get(j),payoff.getCurrencyPair());
            drift[j] = (float) Math.log(nextFwd/prevFxRate);
            prevFxRate = nextFwd;
        }

        Kernel kernel = new Kernel(){
            @Override public void run() {
            int gid = getGlobalId();
            for(int i = 0;i<timeSteps;i++){
            if(i==0) {
                assetvalues[gid][i] = fxRateToday;
                continue;
            }
            assetvalues[gid][i] = assetvalues[gid][i-1]*exp((drift[i] - fwdVol[i] * fwdVol[i] / 2) * delT[i] + fwdVol[i] * sqrt(delT[i]) * randomNumbers[0][gid][i]);
            }
            }
        };
        kernel.execute(Range.create(nPaths));
        return assetvalues;
    }

}
