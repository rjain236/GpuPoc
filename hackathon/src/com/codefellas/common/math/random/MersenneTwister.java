package com.codefellas.common.math.random;

import com.codefellas.analytics.MersenneTwisterRandomGenerator;

import java.io.IOException;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class MersenneTwister extends RandomGenerator {

    @Override
    public float[][][] getIndependentRandomVariables(int nDimensions, int nPaths, int nTimeSteps) throws IOException {
        int numberRandVar = nDimensions*nPaths*nTimeSteps;
        MersenneTwisterRandomGenerator mersenneTwisterRandomGenerator = new MersenneTwisterRandomGenerator();
        float[] out = mersenneTwisterRandomGenerator.getRandomValue(numberRandVar);
        float[][][] result = new float[nDimensions][nPaths][nTimeSteps];
        int count =0;
        for(int x=0;x<nDimensions;x++){
            for(int y=0;y<nPaths;y++){
                for(int z=0;z<nTimeSteps;z++){
                    result[x][y][z] = out[count];
                    count++;
                }
            }
        }
        return result;
    }
}
