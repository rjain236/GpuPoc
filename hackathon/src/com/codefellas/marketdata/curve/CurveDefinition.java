package com.codefellas.marketdata.curve;

import com.codefellas.common.businessobject.Currency;
import com.codefellas.common.daycountcalculator.DayCountCalculator;
import com.codefellas.common.math.interpolation.InterpolationType;
import com.codefellas.marketdata.curve.compounding.CompoundingType;
import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

/**
 * Created by rjain236 on 25/11/15.
 */
@ExposeConstructors
@NonSpringXlService
public class CurveDefinition {

    private Currency currency;
    private String name;
    private CompoundingType compoundingType;
    private InterpolationType interpolationType;
    private DayCountCalculator dayCountCalculator;

    public CurveDefinition(Currency currency, String name, CompoundingType compoundingType, InterpolationType interpolationType, DayCountCalculator dayCountCalculator) {
        this.currency = currency;
        this.name = name;
        this.compoundingType = compoundingType;
        this.interpolationType = interpolationType;
        this.dayCountCalculator = dayCountCalculator;
    }

    public Currency getCurrency() {
        return currency;
    }

    public DayCountCalculator getDayCountCalculator() {
        return dayCountCalculator;
    }

    public String getName() {
        return name;
    }

    public CompoundingType getCompoundingType() {
        return compoundingType;
    }

    public InterpolationType getInterpolationType() {
        return interpolationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurveDefinition that = (CurveDefinition) o;

        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (compoundingType != that.compoundingType) return false;
        return interpolationType == that.interpolationType;

    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (compoundingType != null ? compoundingType.hashCode() : 0);
        result = 31 * result + (interpolationType != null ? interpolationType.hashCode() : 0);
        return result;
    }
}
