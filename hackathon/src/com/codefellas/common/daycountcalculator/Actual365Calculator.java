package com.codefellas.common.daycountcalculator;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.ChronoUnit;

/**
 * Created by rjain236 on 27/11/15.
 */
public class Actual365Calculator implements DayCountCalculator {

    @Override
    public Double getDayCountFactor(ZonedDateTime t1, ZonedDateTime t2) {
        return ChronoUnit.DAYS.between(t1,t2)/365.;
    }

}
