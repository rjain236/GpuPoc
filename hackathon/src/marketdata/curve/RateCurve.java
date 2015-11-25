package marketdata.curve;

import common.DayCountCalculator;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 25/11/15.
 */
public interface RateCurve {

    double getDiscountFactor(ZonedDateTime date) throws Exception;

    DayCountCalculator getDayCountCalculator();

    double getRate(ZonedDateTime date) throws Exception;
}
