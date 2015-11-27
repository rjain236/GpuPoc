
package com.codefellas.mcengine;

import com.amd.aparapi.internal.annotation.DocMe;
import com.codefellas.common.DayCountCalculator;
import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.payoffs.Payoff;
import org.threeten.bp.ZonedDateTime;

import java.util.*;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class McEngine<T extends Payoff> {

    ZonedDateTime referenceDate;
    RandomGenerator randomGenerator;
    List<Double> timeGrid;
    TreeSet<ZonedDateTime> datesGrid;
    T payoff;
    int nDimensions;

    public abstract float[][][] simulate(final int nPaths, float[] initialStates) throws Exception ;

    public ZonedDateTime getReferenceDate() {
        return referenceDate;
    }

    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    public int getnDimensions() {
        return nDimensions;
    }

    public TreeSet<ZonedDateTime> getDatesGrid() {
        return datesGrid;
    }

    public McEngine(ZonedDateTime referenceDate, int nDimensions, RandomGenerator randomGenerator, T payoff) {
        this.referenceDate = referenceDate;
        this.nDimensions = nDimensions;
        this.randomGenerator = randomGenerator;
        this.payoff = payoff;
        TreeSet<ZonedDateTime> zonedDateTimeTreeSet = payoff.getRequiredDate();
        ZonedDateTime lastDate = zonedDateTimeTreeSet.last();
        ZonedDateTime next = referenceDate;
        List<Double> timeGrid = new ArrayList<>();
        this.datesGrid = new TreeSet<>();
        while (!next.isAfter(lastDate)){
            datesGrid.add(next);
            timeGrid.add(DayCountCalculator.Actual365.getDayCountFactor(referenceDate, next));
            next = next.plusDays(1l);
        }
        this.timeGrid = timeGrid;
    }

    public double[] price(float[] initialAssetStates,int nPaths) throws Exception {
        float[][][] assetValues = simulate(nPaths,initialAssetStates);
        Map<String, Double> pathDependentVariables = new HashMap<>();
        double[] averagePayoff = new double[initialAssetStates.length];
        for (int j = 0; j < initialAssetStates.length; j++) {
            for (int i = 0; i < nPaths; i++) {
                averagePayoff[j] += payoff.calculatePayoff(assetValues[j][i], pathDependentVariables);
            }
            averagePayoff[j] /=nPaths;
        }
        return averagePayoff;
    }

    public T getPayoff() {
        return payoff;
    }
}
