package com.codefellas.common.math.interpolation;

import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

import java.util.TreeSet;

/**
 * Created by rjain236 on 27/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class FlatExtrapolation implements ExtrapolationType {

    @Override
    public double getExtrapolatedValue(Double t, InterpolationDataBundle interpolationDateBundle,Boolean
            isLeftExtrapolation) {
        TreeSet<Tuple> datapoints = interpolationDateBundle.getDatapoints();
        if (isLeftExtrapolation == null) {
            Tuple low = datapoints.floor(new Tuple(t, null));
            if (low == null) getExtrapolatedValue(t, interpolationDateBundle, true);
            Tuple high = datapoints.ceiling(new Tuple(t, null));
            if (high == null) getExtrapolatedValue(t, interpolationDateBundle, false);
            return 0;
        } else if (isLeftExtrapolation == true) {
            return datapoints.first().getY();
        } else {
            return datapoints.last().getY();
        }
    }

}
