package com.codefellas.marketdata.curve.compounding;

/**
 * Created by rjain236 on 27/11/15.
 */
public class AnnualCompounding implements CompoundingType {
    @Override
    public Double getRate(Double t, Double df) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public Double getDf(Double t, Double rate) throws Exception {
        throw new Exception("Not implemented");
    }
}
