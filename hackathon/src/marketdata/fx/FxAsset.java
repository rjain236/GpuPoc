package marketdata.fx;

import marketdata.curve.RateCurve;

/**
 * Created by rjain236 on 25/11/15.
 */
public class FxAsset {

    protected RateCurve foreignCurve;
    protected RateCurve discountCurve;
    protected FxSpot fxSpot;

    public FxAsset(RateCurve foreignCurve, RateCurve discountCurve,FxSpot fxSpot) {
        this.foreignCurve = foreignCurve;
        this.discountCurve = discountCurve;
        this.fxSpot = fxSpot;
    }

    public RateCurve getForeignCurve() {
        return foreignCurve;
    }

    public RateCurve getDiscountCurve() {
        return discountCurve;
    }

    public FxSpot getFxSpot() {
        return fxSpot;
    }
}
