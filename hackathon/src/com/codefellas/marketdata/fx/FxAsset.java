package com.codefellas.marketdata.fx;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.curve.RateCurve;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
public class FxAsset {

    protected RateCurve foreignCurve;
    protected RateCurve domesticCurve;
    protected FxSpot fxSpot;

    public FxAsset(RateCurve foreignCurve, RateCurve discountCurve,FxSpot fxSpot) {
        this.foreignCurve = foreignCurve;
        this.domesticCurve = discountCurve;
        this.fxSpot = fxSpot;
    }

    public RateCurve getForeignCurve() {
        return foreignCurve;
    }

    public RateCurve getDomesticCurve() {
        return domesticCurve;
    }

    public FxSpot getFxSpot() {
        return fxSpot;
    }

    public Double getFXForward(final ZonedDateTime datetime) {
        try {
            return fxSpot.getValue() * foreignCurve
                    .getDiscountFactor(datetime) / domesticCurve.getDiscountFactor(datetime);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.;
        }
    }

    public CurrencyPair getCurrencyPair(){
        return fxSpot.getCurrencyPair();
    }
}
