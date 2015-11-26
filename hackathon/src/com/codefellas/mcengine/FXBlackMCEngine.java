package com.codefellas.mcengine;

import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.marketdata.fx.FxAsset;
import com.codefellas.marketdata.volsurface.ATMVolSurface;
import junit.framework.Assert;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class FXBlackMCEngine extends MCEngine {

    FxAsset fxasset;
    ATMVolSurface atmVolSurface;

    public FXBlackMCEngine(ZonedDateTime referenceDate,int nDimensions, RandomGenerator randomGenerator, List<ZonedDateTime>
            simulationDates, FxAsset fxasset, ATMVolSurface atmVolSurface) {
        super(referenceDate, nDimensions, randomGenerator, simulationDates);
        this.fxasset = fxasset;
        this.atmVolSurface = atmVolSurface;
    }


    @Override
    public double[][][] simulate(final int nPaths) throws Exception {
        Assert.assertTrue("Only single assert simulation implemented", nDimensions ==1);
        double[][][] randomNumbers = randomGenerator.getIndependentRandomVariables(nDimensions,nPaths,timeGrid.size());
        double[][][] assetvalues = new double[nDimensions][nPaths][timeGrid.size()];
        double fxRateToday = fxasset.getFXForward(getReferenceDate());
        for (int i = 0; i < nPaths; i++) {
            assetvalues[0][i][0] = fxRateToday;
            for (int j = 1; j < timeGrid.size(); j++) {
                final double delT = timeGrid.get(j)-timeGrid.get(j-1);
                final double fwdVol = atmVolSurface.getForwardVolatility(getDatesGrid().get(j-1),getDatesGrid().get(j));
                final double drift = fxasset.getForeignCurve().getRate(getDatesGrid().get(j)) - fxasset
                        .getDomesticCurve().getRate(getDatesGrid().get(j));
                assetvalues[0][i][j] = assetvalues[0][i][j-1]*Math.exp((drift-fwdVol*fwdVol/2.)
                        *delT+ fwdVol*Math.sqrt(delT)*randomNumbers[0][i][j] );
            }
        }
        return assetvalues;
    }
}
