package com.codefellas.marketdata.fx;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.curve.GenericRateCurve;
import com.codefellas.marketdata.curve.RateCurve;
import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
@NonSpringXlService
@ExposeConstructors
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

    public void setReferenceDate(ZonedDateTime referenceDate){
        ((GenericRateCurve)foreignCurve).setReferenceDate(referenceDate);
        ((GenericRateCurve)domesticCurve).setReferenceDate(referenceDate);
    }

    public void setFxSpot(double fxSpotRate){
        this.fxSpot.setValue(fxSpotRate);
    }
}
