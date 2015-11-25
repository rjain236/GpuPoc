package marketdata.fx;

import common.businessobject.Currency;
import common.businessobject.CurrencyPair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjain236 on 25/11/15.
 */
public class FxMatrix {

    protected Map<CurrencyPair,FxAsset> fxAssetMap;

    public FxMatrix(List<FxAsset> fxAssets) {
        fxAssetMap = new HashMap<>();
        for(FxAsset fxAsset:fxAssets) fxAssetMap.put(fxAsset.getCurrencyPair(), fxAsset);
    }

    public FxAsset getFxAsset(CurrencyPair currencyPair){
        return fxAssetMap.get(currencyPair);
    }
}
