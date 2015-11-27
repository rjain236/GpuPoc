package com.codefellas.cva;

import com.codefellas.mcengine.McEngine;
import com.codefellas.payoffs.Payoff;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Lenovo on 27/11/2015.
 */
public interface ExposureForTimeSlice {

    public double[] getValues(ZonedDateTime dateTime);
    public double getPotentialFutureExposure(ZonedDateTime dateTime,double confidenceInterval);
    public double getExpectedExposure(ZonedDateTime dateTime);

}
