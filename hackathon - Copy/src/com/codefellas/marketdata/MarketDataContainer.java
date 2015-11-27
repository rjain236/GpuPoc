package com.codefellas.marketdata;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.marketdata.curve.RateCurve;
import com.codefellas.marketdata.fx.FxSpot;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
public interface MarketDataContainer {

    void setReferenceDate(ZonedDateTime dateTime);

    void setFxSpot(CurrencyPair currencyPair, double fxSpotRate);

    RateCurve getRateCurve(CurveDefinition curveDefinition) throws Exception;

    FxSpot getFxSpot(CurrencyPair currencyPair);

    Double getForwardFxRate(ZonedDateTime date, CurrencyPair currencyPair);

    ZonedDateTime getReferenceDate();

    Double getVolatility(ZonedDateTime date, CurrencyPair currencyPair);

    double getForwardVolatility(final ZonedDateTime datetime1, final ZonedDateTime datetime2, CurrencyPair currencyPair);

}
