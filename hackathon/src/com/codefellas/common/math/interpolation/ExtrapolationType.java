package com.codefellas.common.math.interpolation;

import java.util.TreeSet;

/**
 * Created by Lenovo on 27/11/2015.
 */
public interface ExtrapolationType {
    double getExtrapolatedValue(Double t, InterpolationDataBundle interpolationDateBundle,Boolean isLeftExtrapolation);
}
