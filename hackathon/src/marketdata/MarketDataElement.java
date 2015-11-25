package marketdata;

import common.businessobject.AbstractBusinessObject;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
public class MarketDataElement extends AbstractBusinessObject {

    private ZonedDateTime referenceDate;
    private MarketDataType marketDataType;

    public MarketDataElement(ZonedDateTime referenceDate, MarketDataType marketDataType) {
        this.referenceDate = referenceDate;
        this.marketDataType = marketDataType;
    }

    public ZonedDateTime getReferenceDate() {
        return referenceDate;
    }

    public MarketDataType getMarketDataType() {
        return marketDataType;
    }
}
