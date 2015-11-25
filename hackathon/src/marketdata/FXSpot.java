package marketdata;

import common.businessobject.CurrencyPair;

/**
 * Created by rjain236 on 25/11/15.
 */
public class FXSpot {
    CurrencyPair pair;
    double fxspotrate;

    public FXSpot(CurrencyPair pair, double fxspotrate) {
        this.pair = pair;
        this.fxspotrate = fxspotrate;
    }

    public double getFxspotrate() {
        return fxspotrate;
    }
}
