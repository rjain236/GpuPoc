package com.codefellas.common.math.interpolation;

import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

import java.util.TreeSet;

/**
 * Created by rjain236 on 27/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class LinearInterpolation implements InterpolationType {
    @Override
    public double getInterpolatedValue(Double t, InterpolationDataBundle interpolationDateBundle)  throws Exception{
        TreeSet<Tuple> datapoints = interpolationDateBundle.getDatapoints();
        Tuple low = datapoints.floor(new Tuple(t, null));
        Tuple high = datapoints.ceiling(new Tuple(t, null));
        if(low == null || high == null) throw new Exception("Needs Extrapolation");
        if(low==high)return low.getY();
        Double y1 = low.getY();
        Double y2 = high.getY();
        Double x1 = low.getX();
        Double x2 = high.getX();
        Double m = (y2-y1)/(x2-x1);
        return y1+m*(t-x1);
    }

}
