package com.codefellas.marketdata.volsurface;


import com.codefellas.common.businessobject.CurrencyPair;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Lenovo on 27/11/2015.
 */
public interface VolSurface {
    double getVolatility(final ZonedDateTime datetime);
    double getForwardVolatility(final ZonedDateTime datetime1, final ZonedDateTime datetime2);
    CurrencyPair getCurrencyPair();

}
