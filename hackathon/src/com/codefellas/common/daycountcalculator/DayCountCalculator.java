package com.codefellas.common.daycountcalculator;

import org.threeten.bp.ZonedDateTime;

/**
 * Created by rjain236 on 27/11/15.
 */
public interface DayCountCalculator {
    Double getDayCountFactor(ZonedDateTime t1, ZonedDateTime t2);
}
