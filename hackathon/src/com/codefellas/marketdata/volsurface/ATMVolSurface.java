package com.codefellas.marketdata.volsurface;

import com.codefellas.common.math.interpolation.InterpolationDataBundle;
import com.codefellas.common.math.interpolation.Tuple;
import com.codefellas.marketdata.MarketDataElement;
import com.codefellas.marketdata.MarketDataType;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.threeten.bp.ZonedDateTime;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class ATMVolSurface extends MarketDataElement implements VolSurface {
    protected TreeMap<ZonedDateTime,Double> values;
    protected InterpolationDataBundle extrapolationDataBundle;
    protected InterpolationDataBundle interpolationDataBundle;
    protected VolSurfaceDefinition volSurfaceDefinition;

    public static final Logger logger = Logger.getLogger(ATMVolSurface.class);

    public ATMVolSurface(ZonedDateTime referenceDate, MarketDataType marketDataType, List<ZonedDateTime> dates,
                         List<Double> atmvols) {
        super(referenceDate, marketDataType);
        Assert.assertTrue("Need equal length pillars and values", dates.size() == atmvols.size());
        int index =0;
        TreeSet<Tuple> interpolationSet = new TreeSet<>();
        TreeSet<Tuple> extrapolationSet = new TreeSet<>();
        values = new TreeMap<>();
        for(Double atmvol:atmvols){
            values.put(dates.get(index), atmvol);
            double timefraction = volSurfaceDefinition.getDayCountCalculator().getDayCountFactor
                    (referenceDate, dates.get(index));
            interpolationSet.add(new Tuple(timefraction,volSurfaceDefinition.getTenorInterpolatedQuantity().getQuantity
                    (atmvols.get(index), timefraction)));
            extrapolationSet.add(new Tuple(timefraction,volSurfaceDefinition.getTenorExtrapolatedQuantity().getQuantity
                    (atmvols.get(index),timefraction)));
            index++;
        }
        interpolationDataBundle = new InterpolationDataBundle(interpolationSet);
        extrapolationDataBundle = new InterpolationDataBundle(extrapolationSet);
    }

    @Override
    public double getVolatility(final ZonedDateTime datetime){
        Double timefraction = volSurfaceDefinition.getDayCountCalculator().getDayCountFactor(getReferenceDate(),datetime);
        Double volatility = 0.;
        try {
            volatility = volSurfaceDefinition.getTenorInterpolationType().getInterpolatedValue(timefraction,
                    interpolationDataBundle);
        } catch (Exception e) {
            volatility = volSurfaceDefinition.getTenorExtrapolationType().getExtrapolatedValue(timefraction,
                    extrapolationDataBundle,null);
        }
        return volatility;
    }
}
