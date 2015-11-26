
package com.codefellas.mcengine;

import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.payoffs.Payoff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class MCEngine {

    RandomGenerator randomGenerator;
    List<Double> timeGrid;
    int nDimensions;

    public abstract double[][][] simulate();

    public MCEngine(int nDimensions, RandomGenerator randomGenerator, List<Double> timeGrid) {
        this.nDimensions = nDimensions;
        this.randomGenerator = randomGenerator;
        this.timeGrid = timeGrid;
    }

    public double price(int nPaths, Payoff tradePayoff) throws IOException {
        double[][][] assetValues = randomGenerator.getIndependentRandomVariables(nDimensions,nPaths,timeGrid.size());
        Map<String, Double> pathDependentVariables = new HashMap<>();
        List<Double> payoffs = new ArrayList<Double>();
        double averagePayoff =0;
        for (int i = 0; i < nPaths; i++) {
            averagePayoff = tradePayoff.calculatePayoff(assetValues[0][i], pathDependentVariables);
        }
        return averagePayoff/nPaths;
    }
}
