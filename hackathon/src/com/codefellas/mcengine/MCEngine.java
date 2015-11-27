
package com.codefellas.mcengine;

import com.codefellas.common.daycountcalculator.Actual365Calculator;
import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.payoffs.Payoff;
import org.threeten.bp.ZonedDateTime;

import java.util.*;

/**
 * Created by rjain236 on 25/11/15.
 */
public abstract class MCEngine<T extends Payoff> {

    ZonedDateTime referenceDate;
    RandomGenerator randomGenerator;
    List<Double> timeGrid;
    TreeSet<ZonedDateTime> datesGrid;
    T payoff;
    int nDimensions;

    public abstract float[][] simulate(final int nPaths)  throws Exception ;

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

    public MCEngine(ZonedDateTime referenceDate,int nDimensions, RandomGenerator randomGenerator, T payoff) {
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
            timeGrid.add(new Actual365Calculator().getDayCountFactor(referenceDate, next));
            next = next.plusDays(1l);
        }
        this.timeGrid = timeGrid;
    }

    public double price(int nPaths) throws Exception {
        float[][] assetValues = simulate(nPaths);
        Map<String, Double> pathDependentVariables = new HashMap<>();
        double averagePayoff =0;
        for (int i = 0; i < nPaths; i++) {
            averagePayoff += payoff.calculatePayoff(assetValues[i], pathDependentVariables);
        }
        return averagePayoff/nPaths;
    }

    public T getPayoff() {
        return payoff;
    }
}
