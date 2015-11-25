package marketdata.interpolation;

import marketdata.interpolation.InterpolationDataBundle;
import marketdata.interpolation.Tuple;

import java.util.TreeSet;

/**
 * Created by rjain236 on 25/11/15.
 */
public enum InterpolationType {
    Linear{
        @Override
        public double getInterpolatedValue(Double t, InterpolationDataBundle interpolationDateBundle) {
            TreeSet<Tuple> datapoints = interpolationDateBundle.getDatapoints();
            Tuple low = datapoints.floor(new Tuple(t, null));
            Tuple high = datapoints.ceiling(new Tuple(t, null));
            if(low==high)return low.getY();
            Double y1 = low.getY();
            Double y2 = high.getY();
            Double x1 = low.getX();
            Double x2 = high.getX();
            Double m = (y2-y1)/(x2-x1);
            return y1+m*(t-x1);
        }
    };

    public abstract double getInterpolatedValue(Double t, InterpolationDataBundle interpolationDateBundle);
}