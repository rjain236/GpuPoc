package com.codefellas.common.math.random;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class MersenneTwister extends RandomGenerator {

    @Override
    double[][][] getIndependentRandomVariables(int nDimensions, int nPaths, int nTimeSteps){
        return new double[nDimensions][nPaths][nTimeSteps];
    }
}
