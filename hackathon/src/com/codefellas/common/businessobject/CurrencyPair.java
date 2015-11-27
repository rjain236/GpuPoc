package com.codefellas.common.businessobject;

import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;

/**
 * Created by rjain236 on 25/11/15.
 */
@NonSpringXlService
@ExposeConstructors
public class CurrencyPair extends AbstractBusinessObject{

    private Currency fgnCurrency;
    private Currency domCurrency;

    public CurrencyPair(Currency fgnCurrency, Currency domCurrency) {
        this.fgnCurrency = fgnCurrency;
        this.domCurrency = domCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyPair that = (CurrencyPair) o;

        if (!fgnCurrency.equals(that.fgnCurrency)) return false;
        return domCurrency.equals(that.domCurrency);

    }

    @Override
    public int hashCode() {
        int result = fgnCurrency.hashCode();
        result = 31 * result + domCurrency.hashCode();
        return result;
    }
}
