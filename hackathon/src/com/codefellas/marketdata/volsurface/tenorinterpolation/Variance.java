package com.codefellas.marketdata.volsurface.tenorinterpolation;

/**
 * Created by rjain236 on 27/11/15.
 */
public class Variance implements TenorInterpolatedQuantity {
    @Override
    public Double getQuantity(Double volatility, Double time) {
        return volatility*volatility;
    }

    @Override
    public Double getInverse(Double x, Double time) {
        return Math.sqrt(x);
    }

}
