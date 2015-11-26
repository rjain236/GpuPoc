
package com.codefellas.mcengine;

import com.codefellas.common.DayCountCalculator;
import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.payoffs.Payoff;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class MCEngine {

    ZonedDateTime referenceDate;
    RandomGenerator randomGenerator;
    List<Double> timeGrid;
    List<ZonedDateTime> datesGrid;
    int nDimensions;

    public abstract double[][][] simulate(final int nPaths)  throws Exception ;

    public ZonedDateTime getReferenceDate() {
        return referenceDate;
    }

    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    public List<Double> getTimeGrid() {
        return timeGrid;
    }

    public int getnDimensions() {
        return nDimensions;
    }

    public List<ZonedDateTime> getDatesGrid() {
        return datesGrid;
    }

    public MCEngine(ZonedDateTime referenceDate,int nDimensions, RandomGenerator randomGenerator, List<ZonedDateTime>
            simulationDates) {
        this.referenceDate = referenceDate;
        this.nDimensions = nDimensions;
        this.randomGenerator = randomGenerator;
        List<Double> timeGrid = new ArrayList<>();
        for (int i = 0; i < simulationDates.size(); i++) {
            timeGrid.add(DayCountCalculator.Actual365.getDayCountFactor(referenceDate, simulationDates.get(i)));
        }
        this.datesGrid = simulationDates;
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
