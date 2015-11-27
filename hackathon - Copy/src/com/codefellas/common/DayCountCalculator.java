package com.codefellas.common;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.ChronoUnit;

/**
 * Created by rjain236 on 25/11/15.
 */
public enum DayCountCalculator {
    Actual360 {
        @Override
        public Double getDayCountFactor(ZonedDateTime t1, ZonedDateTime t2) {
            return ChronoUnit.DAYS.between(t1,t2)/360.;
        }
    },
    Actual365 {
        @Override
        public Double getDayCountFactor(ZonedDateTime t1, ZonedDateTime t2) {
            return ChronoUnit.DAYS.between(t1,t2)/365.;
        }
    };

    public abstract Double getDayCountFactor(ZonedDateTime t1, ZonedDateTime t2);

}
