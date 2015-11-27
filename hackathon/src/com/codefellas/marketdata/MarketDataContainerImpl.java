package com.codefellas.marketdata;

import com.codefellas.common.businessobject.Currency;
import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.curve.RateCurve;
import com.codefellas.marketdata.fx.FxMatrix;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.marketdata.fx.FxAsset;
import com.codefellas.marketdata.fx.FxSpot;
import com.codefellas.marketdata.volsurface.VolSurface;
import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import org.threeten.bp.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rjain236 on 25/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class MarketDataContainerImpl implements MarketDataContainer {

    protected ZonedDateTime referenceDate;
    protected Map<CurveDefinition,RateCurve> rateCurveMap;
    protected Map<CurrencyPair,VolSurface> volSurfaceMap;
    protected FxMatrix fxMatrix;

    public MarketDataContainerImpl(Collection<FxAsset> fxAssets, Collection<RateCurve> rateCurves, ZonedDateTime referenceDate, Collection<VolSurface> volSurfaces){
        fxMatrix = new FxMatrix(fxAssets);
        rateCurveMap = new ConcurrentHashMap<>();
        for(RateCurve rateCurve:rateCurves) {
            if(rateCurve==null)continue;
            rateCurveMap.put(rateCurve.getCurveDefinition(), rateCurve);
        }
        this.referenceDate = referenceDate;
        volSurfaceMap = new ConcurrentHashMap<>();
        for(VolSurface volSurface:volSurfaces){
            if(volSurface == null)continue;
            volSurfaceMap.put(volSurface.getCurrencyPair(),volSurface);
        }
    }

    @Override
    public RateCurve getRateCurve(CurveDefinition curveDefinition) throws Exception {
        RateCurve curve = rateCurveMap.get(curveDefinition);
        if(curve==null)throw new Exception("Do not have required rate curve");
        return curve;
    }

    @Override
    public FxSpot getFxSpot(CurrencyPair currencyPair) {
        return null;
    }

    @Override
    public Double getForwardFxRate(ZonedDateTime date, CurrencyPair currencyPair) {
        FxAsset fxAsset = fxMatrix.getFxAsset(currencyPair);
        return fxAsset.getFXForward(date);
    }

    @Override
    public ZonedDateTime getReferenceDate() {
        return null;
    }

    @Override
    public Double getVolatility(ZonedDateTime date, CurrencyPair currencyPair) {
        return volSurfaceMap.get(currencyPair).getVolatility(date);
    }

    @Override
    public double getForwardVolatility(ZonedDateTime datetime1, ZonedDateTime datetime2, CurrencyPair currencyPair) {
        return volSurfaceMap.get(currencyPair).getForwardVolatility(datetime1, datetime2);
    }
}
