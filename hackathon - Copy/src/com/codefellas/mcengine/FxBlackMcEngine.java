package com.codefellas.mcengine;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.payoffs.FxPayoff;
import junit.framework.Assert;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class FxBlackMcEngine extends FxMcEngine {

    MarketDataContainer marketDataContainer;

    public FxBlackMcEngine(int nDimensions, RandomGenerator randomGenerator
            , FxPayoff payoff, MarketDataContainer marketDataContainer) {
        super(marketDataContainer.getReferenceDate(), nDimensions, randomGenerator, payoff);
        this.marketDataContainer = marketDataContainer;
    }


    @Override
    public float[][][] simulate(final int nPaths, float[] initialStates) throws Exception {
        final float fxRateToday = (float) marketDataContainer.getForwardFxRate(getReferenceDate(),payoff.getCurrencyPair()).doubleValue();
        if(initialStates.length ==1 && initialStates[0]==0 ) {
            //Assert.assertTrue("Only single assert simulation implemented", nDimensions ==1);
            initialStates[0] = fxRateToday;
        }else
            Assert.assertTrue("OIndependent dimensions and initial states must be of same size", nDimensions
                    ==initialStates.length);
        final float[] finalInitialStates = initialStates;
        final float[][][] randomNumbers = randomGenerator.getIndependentRandomVariables(nDimensions,nPaths,timeGrid.size());
        final float[][][] assetvalues = new float[nDimensions][nPaths][timeGrid.size()];
        final int timeSteps = timeGrid.size();
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
            for(int j = 0;j<nDimensions;j++) {
                for (int i = 0; i < timeSteps; i++) {
                    if (i == 0) {
                        assetvalues[j][gid][i]=finalInitialStates[j];
                        continue;
                    }
                    assetvalues[j][gid][i] = assetvalues[j][gid][i - 1] * exp((drift[i] - fwdVol[i] * fwdVol[i] / 2) * delT[i] + fwdVol[i] * sqrt(delT[i]) * randomNumbers[nDimensions][gid][i]);
                }
            }
            }
        };
        kernel.execute(Range.create(nPaths));
        return assetvalues;
    }

}
