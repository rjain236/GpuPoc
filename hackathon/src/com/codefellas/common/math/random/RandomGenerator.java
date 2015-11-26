package com.codefellas.common.math.random;

import java.io.IOException;

/**
 * Created by Lenovo on 27/11/2015.
 */
public abstract class RandomGenerator {

    abstract public float[][][] getIndependentRandomVariables(int nDimensions, int nPaths, int nTimeSteps) throws IOException;
}
