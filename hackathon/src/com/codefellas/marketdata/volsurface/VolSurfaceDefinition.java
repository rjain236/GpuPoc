package com.codefellas.marketdata.volsurface;

import com.codefellas.common.businessobject.CurrencyPair;
import com.codefellas.common.math.interpolation.ExtrapolationType;
import com.codefellas.common.math.interpolation.InterpolationType;
import com.codefellas.marketdata.volsurface.tenorinterpolation.TenorInterpolatedQuantity;
import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

/**
 * Created by Lenovo on 27/11/2015.
 */
@NonSpringXlService
@ExposeConstructors
public class VolSurfaceDefinition {

    private CurrencyPair currencyPair;
    private String name;
    private InterpolationType tenorInterpolationType;
    private TenorInterpolatedQuantity tenorInterpolatedQuantity;
    private ExtrapolationType tenorExtrapolationType;
    private TenorInterpolatedQuantity tenorExtrapolatedQuantity;

    public VolSurfaceDefinition(CurrencyPair currencyPair, String name, InterpolationType tenorInterpolationType, TenorInterpolatedQuantity tenorInterpolatedQuantity, ExtrapolationType tenorExtrapolationType, TenorInterpolatedQuantity tenorExtrapolatedQuantity) {
        this.currencyPair = currencyPair;
        this.name = name;
        this.tenorInterpolationType = tenorInterpolationType;
        this.tenorInterpolatedQuantity = tenorInterpolatedQuantity;
        this.tenorExtrapolationType = tenorExtrapolationType;
        this.tenorExtrapolatedQuantity = tenorExtrapolatedQuantity;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public String getName() {
        return name;
    }

    public TenorInterpolatedQuantity getTenorExtrapolatedQuantity() {
        return tenorExtrapolatedQuantity;
    }

    public ExtrapolationType getTenorExtrapolationType() {
        return tenorExtrapolationType;
    }

    public InterpolationType getTenorInterpolationType() {
        return tenorInterpolationType;
    }

    public TenorInterpolatedQuantity getTenorInterpolatedQuantity() {
        return tenorInterpolatedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VolSurfaceDefinition that = (VolSurfaceDefinition) o;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (currencyPair != null ? !currencyPair.equals(that.currencyPair) : that.currencyPair != null) return false;
        if (tenorInterpolationType != null ? !tenorInterpolationType.equals(that.tenorInterpolationType) : that.tenorInterpolationType != null) return false;
        if (tenorInterpolatedQuantity != null ? !tenorInterpolatedQuantity.equals(that.tenorInterpolatedQuantity) : that.tenorInterpolatedQuantity != null) return false;
        if (tenorExtrapolatedQuantity != null ? !tenorExtrapolatedQuantity.equals(that.tenorExtrapolatedQuantity) : that.tenorExtrapolatedQuantity != null) return false;
        return tenorExtrapolationType != that.tenorExtrapolationType;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (currencyPair != null ? currencyPair.hashCode() : 0);
        result = 31 * result + (tenorInterpolationType != null ? tenorInterpolationType.hashCode() : 0);
        result = 31 * result + (tenorInterpolatedQuantity != null ? tenorInterpolatedQuantity.hashCode() : 0);
        result = 31 * result + (tenorExtrapolatedQuantity != null ? tenorExtrapolatedQuantity.hashCode() : 0);
        result = 31 * result + (tenorExtrapolationType != null ? tenorExtrapolationType.hashCode() : 0);
        return result;
    }

}
