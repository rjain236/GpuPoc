package com.codefellas.marketdata;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.curve.RateCurve;
import com.codefellas.marketdata.fx.FxMatrix;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.marketdata.fx.FxAsset;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import org.threeten.bp.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjain236 on 25/11/15.
 */
@NonSpringXlService
public class MarketDataContainerImpl implements MarketDataContainer {

    protected ZonedDateTime referenceDate;
    protected Map<CurveDefinition,RateCurve> rateCurveMap;
    protected FxMatrix fxMatrix;

    public MarketDataContainerImpl(List<FxAsset> fxAssets, Collection<RateCurve> rateCurves, ZonedDateTime referenceDate){
        fxMatrix = new FxMatrix(fxAssets);
        rateCurveMap = new HashMap<>();
        for(RateCurve rateCurve:rateCurves)
            rateCurveMap.put(rateCurve.getCurveDefinition(),rateCurve);
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
