package com.codefellas.marketdata.curve;

import com.codefellas.marketdata.MarketDataElement;
import com.codefellas.marketdata.MarketDataType;
import com.codefellas.common.DayCountCalculator;
import com.codefellas.common.math.interpolation.InterpolationDataBundle;
import junit.framework.Assert;
import com.codefellas.common.math.interpolation.Tuple;
import org.apache.log4j.Logger;
import org.threeten.bp.ZonedDateTime;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by rjain236 on 25/11/15.
 */
public class GenericRateCurve extends MarketDataElement implements RateCurve {

    protected TreeMap<ZonedDateTime,Double> values;
    protected InterpolationDataBundle interpolationDataBundle;
    protected CurveDefinition curveDefinition;

    public static final Logger logger = Logger.getLogger(GenericRateCurve.class);

    public GenericRateCurve(ZonedDateTime referenceDate, MarketDataType marketDataType, List<Double> y, List<ZonedDateTime> x) {
        super(referenceDate, marketDataType);
        Assert.assertTrue("Need equal length pillars and values",y.size()==x.size());
        int index = 0 ;
        TreeSet<Tuple> interpolationSet = new TreeSet<>();
        values = new TreeMap<>();
        for(Double pointy:y){
            values.put(x.get(index),pointy);
            interpolationSet.add(new Tuple(getDayCountCalculator().getDayCountFactor(referenceDate,x.get(index)),y.get(index)));
            index++;
        }
        interpolationDataBundle = new InterpolationDataBundle(interpolationSet);
    }


    @Override
    public double getDiscountFactor(ZonedDateTime date) throws Exception {
        Double dcf = curveDefinition.getDayCountCalculator().getDayCountFactor(getReferenceDate(),date);
        Double rate = curveDefinition.getInterpolationType().getInterpolatedValue(dcf,interpolationDataBundle);
        return curveDefinition.getCompoundingType().getDf(dcf,rate);
    }

    @Override
    public DayCountCalculator getDayCountCalculator() {
        return curveDefinition.getDayCountCalculator();
    }

    @Override
    public double getRate(ZonedDateTime date) throws Exception {
        Double dcf = getDayCountCalculator().getDayCountFactor(getReferenceDate(),date);
        return curveDefinition.getCompoundingType().getRate(dcf,getDiscountFactor(date));
    }

    @Override
    public CurveDefinition getCurveDefinition() {
        return curveDefinition;
    }
}
