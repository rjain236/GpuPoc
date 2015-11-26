package com.codefellas.marketdata.volsurface;

/**
 * Created by Lenovo on 27/11/2015.
 */
public enum TenorInterpolatedQuantity {
    TotalVariance{
        @Override
        public Double getQuantity(Double volatility, Double time) {
            return volatility*volatility*time;
        }
    },
    Variance{
        @Override
        public Double getQuantity(Double volatility, Double time) {
            return volatility*volatility;
        }
    },
    Volatility{
        @Override
        public Double getQuantity(Double volatility, Double time) {
            return volatility;
        }
    };

    public abstract Double getQuantity(final Double volatility, final Double time);
}
