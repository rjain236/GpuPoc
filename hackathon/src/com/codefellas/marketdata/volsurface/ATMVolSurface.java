package com.codefellas.marketdata.volsurface;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.common.daycountcalculator.Actual365Calculator;
import com.codefellas.common.daycountcalculator.DayCountCalculator;
import com.codefellas.common.math.interpolation.InterpolationDataBundle;
import com.codefellas.common.math.interpolation.Tuple;
import com.codefellas.marketdata.MarketDataElement;
import com.codefellas.marketdata.MarketDataType;
import com.codefellas.marketdata.volsurface.tenorinterpolation.TenorInterpolatedQuantity;
import com.codefellas.marketdata.volsurface.tenorinterpolation.TotalVariance;
import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.threeten.bp.ZonedDateTime;

import java.util.*;

/**
 * Created by Lenovo on 27/11/2015.
 */
@NonSpringXlService
@ExposeConstructors
public class ATMVolSurface extends MarketDataElement implements VolSurface {

    protected TreeMap<ZonedDateTime,Double> values;
    protected InterpolationDataBundle extrapolationDataBundle;
    protected InterpolationDataBundle interpolationDataBundle;
    protected VolSurfaceDefinition volSurfaceDefinition;

    public static final Logger logger = Logger.getLogger(ATMVolSurface.class);

    public ATMVolSurface(ZonedDateTime referenceDate, Collection<ZonedDateTime> dates,
                         Collection<Double> atmvols, VolSurfaceDefinition volSurfaceDefinition) {
        super(referenceDate, MarketDataType.FxVolSurface);
        Assert.assertTrue("Need equal length pillars and values", dates.size() == atmvols.size());
        int index =0;
        this.volSurfaceDefinition = volSurfaceDefinition;
        TreeSet<Tuple> interpolationSet = new TreeSet<>();
        TreeSet<Tuple> extrapolationSet = new TreeSet<>();
        values = new TreeMap<>();
        List<ZonedDateTime> dateTimeList = new ArrayList<>(dates);
        for(Double atmvol:atmvols){
            values.put(dateTimeList.get(index), atmvol);
            double timefraction = new Actual365Calculator().getDayCountFactor
                    (referenceDate, dateTimeList.get(index));
            interpolationSet.add(new Tuple(timefraction,volSurfaceDefinition.getTenorInterpolatedQuantity().getQuantity
                    (atmvol, timefraction)));
            extrapolationSet.add(new Tuple(timefraction,volSurfaceDefinition.getTenorExtrapolatedQuantity().getQuantity
                    (atmvol,timefraction)));
            index++;
        }
        interpolationDataBundle = new InterpolationDataBundle(interpolationSet);
        extrapolationDataBundle = new InterpolationDataBundle(extrapolationSet);
    }

    @Override
    public double getVolatility(final ZonedDateTime datetime){
        final double timefraction = new Actual365Calculator().getDayCountFactor(getReferenceDate(), datetime);
        double volatility = 0.;
        try {
            volatility = volSurfaceDefinition.getTenorInterpolatedQuantity().getInverse(volSurfaceDefinition.getTenorInterpolationType().getInterpolatedValue(timefraction,
                    interpolationDataBundle),timefraction);
        } catch (Exception e) {
            volatility = volSurfaceDefinition.getTenorExtrapolatedQuantity().getInverse(volSurfaceDefinition.getTenorExtrapolationType().getExtrapolatedValue(timefraction,
                    extrapolationDataBundle,null),timefraction);
        }
        return volatility;
    }

    @Override
    public double getForwardVolatility(final ZonedDateTime datetime1,final ZonedDateTime datetime2 ){
        final double timefraction = new Actual365Calculator().getDayCountFactor(datetime1, datetime2);
        final double totalvol1 = new TotalVariance().getQuantity(getVolatility(datetime1),
                new Actual365Calculator().getDayCountFactor(getReferenceDate(), datetime1));
        final double totalvar2 = new TotalVariance().getQuantity(getVolatility(datetime2),
                new Actual365Calculator().getDayCountFactor(getReferenceDate(), datetime2));
        return Math.sqrt((totalvar2-totalvol1)/timefraction);
    }

    @Override
    public CurrencyPair getCurrencyPair() {
        return volSurfaceDefinition.getCurrencyPair();
    }
}
