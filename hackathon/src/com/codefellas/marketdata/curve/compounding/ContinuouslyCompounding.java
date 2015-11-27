package com.codefellas.marketdata.curve.compounding;

import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

/**
 * Created by rjain236 on 27/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class ContinuouslyCompounding implements CompoundingType {
    @Override
    public Double getRate(Double t, Double df) {
        return -Math.log(df)/t;
    }

    @Override
    public Double getDf(Double t, Double rate) {
        return Math.exp(-t*rate);
    }

}
