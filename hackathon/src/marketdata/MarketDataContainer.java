package marketdata;

import common.businessobject.CurrencyPair;
import marketdata.curve.CurveDefinition;
import marketdata.curve.RateCurve;
import marketdata.fx.FxAsset;
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
