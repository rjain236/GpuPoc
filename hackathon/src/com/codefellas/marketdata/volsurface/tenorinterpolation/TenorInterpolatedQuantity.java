package com.codefellas.marketdata.volsurface.tenorinterpolation;

/**
 * Created by rjain236 on 27/11/15.
 */
public interface TenorInterpolatedQuantity {
    Double getQuantity(Double volatility, Double time);

    Double getInverse(Double x,Double time);
}
