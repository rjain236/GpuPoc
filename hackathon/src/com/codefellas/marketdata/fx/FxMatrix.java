package com.codefellas.marketdata.fx;

import com.codefellas.common.businessobject.CurrencyPair;

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
}
