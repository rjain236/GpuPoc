package com.codefellas.common.math.interpolation;

import java.util.TreeSet;

/**
 * Created by Lenovo on 27/11/2015.
 */
public enum ExtrapolationType {
    Flat {
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
    };
    public abstract double getExtrapolatedValue(Double t, InterpolationDataBundle interpolationDateBundle,Boolean isLeftExtrapolation);
}
