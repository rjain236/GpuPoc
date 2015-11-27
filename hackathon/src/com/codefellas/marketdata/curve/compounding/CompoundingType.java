package com.codefellas.marketdata.curve.compounding;

/**
 * Created by rjain236 on 27/11/15.
 */
public interface CompoundingType {

    Double getRate(Double t, Double df) throws Exception;

    Double getDf(Double t, Double rate) throws Exception;

}
