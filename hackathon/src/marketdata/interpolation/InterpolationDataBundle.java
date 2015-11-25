package marketdata.interpolation;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by rjain236 on 25/11/15.
 */
public class InterpolationDataBundle{

    TreeSet<Tuple> datapoints;

    public InterpolationDataBundle(TreeSet<Tuple> datapoints) {
        this.datapoints = datapoints;
    }

    public InterpolationDataBundle(List<Double> x, List<Double> y) {
        int index = 0;
        datapoints = new TreeSet<>();
        for(Double pointx:x){
            datapoints.add(new Tuple(pointx,y.get(index)));
            index++;
        }
    }

    public TreeSet<Tuple> getDatapoints() {
        return datapoints;
    }

}
