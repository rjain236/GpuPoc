package com.codefellas.marketdata.fx;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.marketdata.volsurface.AtmVolSurface;
import com.codefellas.marketdata.volsurface.VolSurface;
import org.threeten.bp.ZonedDateTime;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjain236 on 25/11/15.
 */
public class FxMatrix {

    protected Map<CurrencyPair,FxAsset> fxAssetMap;

    public FxMatrix(Collection<FxAsset> fxAssets) {
        fxAssetMap = new HashMap<>();
        for(FxAsset fxAsset:fxAssets){
            if(fxAsset == null)continue;
            fxAssetMap.put(fxAsset.getCurrencyPair(), fxAsset);
        }
    }

    public FxAsset getFxAsset(CurrencyPair currencyPair){
        return fxAssetMap.get(currencyPair);
    }

    public void setReferenceDate(ZonedDateTime referenceDate){
        for (Map.Entry<CurrencyPair,FxAsset> e:fxAssetMap.entrySet()){
            ((FxAsset)e.getValue()).setReferenceDate(referenceDate);
        }
    }

    public void setFxSpot(CurrencyPair currencyPair, double fxSpotRate){
        for (Map.Entry<CurrencyPair,FxAsset> e:fxAssetMap.entrySet()){
            ((FxAsset)e.getValue()).setFxSpot(fxSpotRate);
        }
    }
}
