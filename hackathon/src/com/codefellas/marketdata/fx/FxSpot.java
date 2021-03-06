package com.codefellas.marketdata.fx;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.MarketDataElement;
import com.codefellas.marketdata.MarketDataType;
import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class FxSpot extends MarketDataElement {

    protected Double value;
    protected ZonedDateTime spotDate;
    protected CurrencyPair currencyPair;

    public FxSpot(ZonedDateTime referenceDate, Double value, ZonedDateTime spotDate, CurrencyPair currencyPair) {
        super(referenceDate, MarketDataType.FxSpot);
        this.value = value;
        this.spotDate = spotDate;
        this.currencyPair = currencyPair;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public ZonedDateTime getSpotDate() {
        return spotDate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
