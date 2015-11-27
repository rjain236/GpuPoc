package com.codefellas.marketdata.curve;

import com.codefellas.common.daycountcalculator.DayCountCalculator;
import com.codefellas.common.math.interpolation.FlatExtrapolation;
import com.codefellas.marketdata.MarketDataElement;
import com.codefellas.marketdata.MarketDataType;
import com.codefellas.common.math.interpolation.InterpolationDataBundle;
import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import junit.framework.Assert;
import com.codefellas.common.math.interpolation.Tuple;
import org.apache.log4j.Logger;
import org.threeten.bp.ZonedDateTime;

import java.util.*;

/**
 * Created by rjain236 on 25/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class GenericRateCurve extends MarketDataElement implements RateCurve {

    protected TreeMap<ZonedDateTime,Double> values;
    protected InterpolationDataBundle interpolationDataBundle;
    protected CurveDefinition curveDefinition;

    public static final Logger logger = Logger.getLogger(GenericRateCurve.class);

    public GenericRateCurve(ZonedDateTime referenceDate, Collection<Double> y, Collection<ZonedDateTime> x,CurveDefinition curveDefinition) {
        super(referenceDate, MarketDataType.RateCurve);
        Assert.assertTrue("Need equal length pillars and values",y.size()==x.size());
        int index = 0 ;
        TreeSet<Tuple> interpolationSet = new TreeSet<>();
        values = new TreeMap<>();
        this.curveDefinition = curveDefinition;
        List<ZonedDateTime> times = new ArrayList<>(x);
        for(Double pointy:y){
            values.put(times.get(index),pointy);
            interpolationSet.add(new Tuple(getDayCountCalculator().getDayCountFactor(referenceDate,times.get(index)),pointy));
            index++;
        }
        interpolationDataBundle = new InterpolationDataBundle(interpolationSet);
    }


    @Override
    public double getDiscountFactor(ZonedDateTime date) throws Exception {
        Double dcf = curveDefinition.getDayCountCalculator().getDayCountFactor(getReferenceDate(),date);
        Double rate = 0d;
        try {
            rate = curveDefinition.getInterpolationType().getInterpolatedValue(dcf, interpolationDataBundle);
        }catch (Exception ex){
            rate = new FlatExtrapolation().getExtrapolatedValue(dcf,interpolationDataBundle,null);
        }
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
