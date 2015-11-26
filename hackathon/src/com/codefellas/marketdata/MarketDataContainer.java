package com.codefellas.marketdata;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.marketdata.curve.RateCurve;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
public interface MarketDataContainer {

    RateCurve getRateCurve(CurveDefinition curveDefinition) throws Exception;

    FXSpot getFxSpot(CurrencyPair currencyPair);

    Double getForwardFxRate(ZonedDateTime date);

    ZonedDateTime getReferenceDate();
}