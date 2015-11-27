package com.codefellas.common.math.interpolation;


import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

import java.util.TreeSet;

/**
 * Created by rjain236 on 25/11/15.
 */
public interface InterpolationType {
    double getInterpolatedValue(Double t, InterpolationDataBundle interpolationDateBundle) throws Exception;
}