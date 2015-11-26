package com.codefellas.mcengine;

import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.marketdata.fx.FxAsset;
import com.codefellas.marketdata.volsurface.ATMVolSurface;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class FXBlackMCEngine extends MCEngine {

    FxAsset fxasset;
    ATMVolSurface atmVolSurface;

    public FXBlackMCEngine(int nDimensions, RandomGenerator randomGenerator, List<Double> timeGrid, FxAsset fxasset, ATMVolSurface atmVolSurface) {
        super(nDimensions, randomGenerator, timeGrid);
        this.fxasset = fxasset;
        this.atmVolSurface = atmVolSurface;
    }


    @Override
    public double[][][] simulate() {
        return new double[0][][];
    }
}
