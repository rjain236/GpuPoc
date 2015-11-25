package marketdata;

import common.businessobject.Currency;
import common.businessobject.CurrencyPair;
import marketdata.curve.Curve;
import marketdata.curve.CurveDefinition;
import marketdata.curve.RateCurve;
import marketdata.fx.FxAsset;
import marketdata.fx.FxMatrix;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by rjain236 on 25/11/15.
 */
public class MarketDataContainerImpl implements MarketDataContainer {

    protected ZonedDateTime referenceDate;
    protected Map<CurveDefinition,RateCurve> rateCurveMap;
    protected FxMatrix fxMatrix;

    public MarketDataContainerImpl(List<FxAsset> fxAssets, Collection<RateCurve> rateCurves, Currency crossCurrency, ZonedDateTime referenceDate){
        fxMatrix = new FxMatrix(fxAssets,crossCurrency);
        this.referenceDate = referenceDate;
    }

    @Override
    public RateCurve getRateCurve(CurveDefinition curveDefinition) throws Exception {
        RateCurve curve = rateCurveMap.get(curveDefinition);
        if(curve==null)throw new Exception("Do not have required rate curve");
        return curve;
    }

    @Override
    public FXSpot getFxSpot(CurrencyPair currencyPair) {
        return null;
    }

    @Override
    public Double getForwardFxRate(ZonedDateTime date) {
        return null;
    }

    @Override
    public ZonedDateTime getReferenceDate() {
        return null;
    }
}
