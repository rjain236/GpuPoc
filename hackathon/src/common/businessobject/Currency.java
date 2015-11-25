package common.businessobject;

/**
 * Created by rjain236 on 25/11/15.
 */
public class Currency extends AbstractBusinessObject{

    String currency;

    public Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency1 = (Currency) o;

        return !(currency != null ? !currency.equals(currency1.currency) : currency1.currency != null);

    }

    @Override
    public int hashCode() {
        return currency != null ? currency.hashCode() : 0;
    }
}
