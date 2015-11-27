package com.codefellas.marketdata.volsurface.tenorinterpolation;

/**
 * Created by rjain236 on 27/11/15.
 */
public class Volatility implements TenorInterpolatedQuantity {
    @Override
    public Double getQuantity(Double volatility, Double time) {
        return volatility;
    }

    @Override
    public Double getInverse(Double x, Double time) {
        return x;
    }

}
