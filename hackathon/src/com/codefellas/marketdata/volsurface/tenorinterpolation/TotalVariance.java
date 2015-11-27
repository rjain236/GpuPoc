package com.codefellas.marketdata.volsurface.tenorinterpolation;

import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import org.boris.xlloop.util.Maths;

/**
 * Created by rjain236 on 27/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class TotalVariance implements TenorInterpolatedQuantity {
    @Override
    public Double getQuantity(Double volatility, Double time) {
        return volatility*volatility*time;
    }

    @Override
    public Double getInverse(Double x, Double time) {
        return Math.sqrt(x/time);
    }

}
