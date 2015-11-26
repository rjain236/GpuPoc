package com.codefellas.common.math.random;

/**
 * Created by Lenovo on 27/11/2015.
 */
public abstract class RandomGenerator {

    abstract public double[][][] getIndependentRandomVariables(int nDimensions, int nPaths, int nTimeSteps);
}
