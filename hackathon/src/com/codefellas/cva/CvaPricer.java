package com.codefellas.cva;

import com.codefellas.common.math.random.MersenneTwister;
import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.mcengine.FxBlackMcEngine;
import com.codefellas.mcengine.FxMcEngine;
import com.codefellas.mcengine.McEngine;
import com.codefellas.payoffs.FxEuropeanOptionPayoff;
import com.codefellas.payoffs.FxPayoff;
import com.codefellas.payoffs.Payoff;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class CvaPricer {
    protected int nCvaSimPaths;
    protected FxBlackMcEngine mcEngine;
    protected List<ZonedDateTime> simulatedDates;
    protected int nPricingPaths;
    protected double[][] exposures;

    public CvaPricer(MarketDataContainer marketData,FxEuropeanOptionPayoff payoff ) throws Exception {
        //get simulated market data scenarios
        mcEngine = new FxBlackMcEngine(1,new MersenneTwister(),payoff,marketData);
        float[] initialstate = new float[1];
        initialstate[0] =0;
        float[][][] simulatedMarketAsset = mcEngine.simulate(nCvaSimPaths,initialstate);
        float[][][] transposedsimulatedMarketAsset = new float[1][simulatedDates.size()][nCvaSimPaths];
        //transpose y&z [nDimensions][nPaths][timeGrid.size()];
        for (int i = 0; i < nCvaSimPaths; i++) {
            for (int j = 0; j < simulatedDates.size(); j++) {
                transposedsimulatedMarketAsset[j][i] = simulatedMarketAsset[i][j];
            }
        }

        //at each time step calculate
        simulatedDates = new ArrayList<ZonedDateTime>(mcEngine.getDatesGrid());
        double[][] exposures= new double[simulatedDates.size()][nCvaSimPaths];
        for (int i = 0; i < simulatedDates.size(); i++) {
            marketData.setReferenceDate(simulatedDates.get(i));
            mcEngine = new FxBlackMcEngine(nCvaSimPaths,new MersenneTwister(),payoff,marketData);
            exposures[i] = mcEngine.price(transposedsimulatedMarketAsset[0][i],nPricingPaths);
        }
    }


    public double[] getExposuresForTimeSlice(ZonedDateTime dateTime) {
        int index = simulatedDates.indexOf(dateTime);
        return exposures[index];
    }

    public double getPotentialFutureExposure(ZonedDateTime dateTime, double confidenceInterval) {
        return getExposuresForTimeSlice(dateTime)[(int)Math.floor(getExposuresForTimeSlice(dateTime)
                .length*confidenceInterval)];
    }

    public double getExpectedExposure(ZonedDateTime dateTime) {
        double[] exposuresForTimeSlice = getExposuresForTimeSlice(dateTime);
        double average=0;
        for (int i = 0; i < exposuresForTimeSlice.length; i++) {
            average += exposuresForTimeSlice[i];
        }
        return average;
    }
}
