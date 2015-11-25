package marketdata.fx;

import marketdata.MarketDataElement;
import marketdata.MarketDataType;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
public class FxSpot extends MarketDataElement{

    protected Double value;
    protected ZonedDateTime spotDate;

    public FxSpot(ZonedDateTime referenceDate, Double value, ZonedDateTime spotDate) {
        super(referenceDate, MarketDataType.FxSpot);
        this.value = value;
        this.spotDate = spotDate;
    }

    public ZonedDateTime getSpotDate() {
        return spotDate;
    }

    public Double getValue() {
        return value;
    }
}
