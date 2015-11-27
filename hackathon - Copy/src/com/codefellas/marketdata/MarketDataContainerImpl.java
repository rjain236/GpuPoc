package com.codefellas.marketdata;

import com.codefellas.common.businessobject.Currency;
import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.curve.GenericRateCurve;
import com.codefellas.marketdata.curve.RateCurve;
import com.codefellas.marketdata.fx.FxMatrix;
import com.codefellas.marketdata.curve.CurveDefinition;
import com.codefellas.marketdata.fx.FxAsset;
import com.codefellas.marketdata.fx.FxSpot;
import com.codefellas.marketdata.volsurface.AtmVolSurface;
import com.codefellas.marketdata.volsurface.VolSurface;
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
    protected Map<CurrencyPair,VolSurface> volSurfaceMap;
    protected FxMatrix fxMatrix;

    public MarketDataContainerImpl(List<FxAsset> fxAssets, Collection<RateCurve> rateCurves, ZonedDateTime referenceDate, Map<CurrencyPair,VolSurface> volSurfaceMap){
        fxMatrix = new FxMatrix(fxAssets);
        rateCurveMap = new HashMap<>();
        for(RateCurve rateCurve:rateCurves)
            rateCurveMap.put(rateCurve.getCurveDefinition(),rateCurve);
        this.referenceDate = referenceDate;
        this.volSurfaceMap = volSurfaceMap;
    }

    @Override
    public void setReferenceDate(ZonedDateTime referenceDate) {
        this.referenceDate = referenceDate;
        for (Map.Entry<CurveDefinition,RateCurve> e:rateCurveMap.entrySet()){
            ((GenericRateCurve)e.getValue()).setReferenceDate(referenceDate);
        }
        for (Map.Entry<CurrencyPair,VolSurface> e:volSurfaceMap.entrySet()){
            ((AtmVolSurface)e.getValue()).setReferenceDate(referenceDate);
        }
    }

    @Override
    public void setFxSpot(CurrencyPair currencyPair, double fxSpotRate) {
        fxMatrix.setFxSpot(currencyPair,fxSpotRate);
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
        return volSurfaceMap.get(currencyPair).getForwardVolatility(datetime1,datetime2);
    }
}
