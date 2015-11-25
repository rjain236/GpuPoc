package marketdata.curve;

/**
 * Created by rjain236 on 25/11/15.
 */
public enum CompoundingType {
    Annual {
        @Override
        public Double getRate(Double t, Double df) throws Exception {
            throw new Exception("Not implemented");
        }

        @Override
        public Double getDf(Double t, Double rate) throws Exception {
            throw new Exception("Not implemented");
        }
    },
    Continuous {
        @Override
        public Double getRate(Double t, Double df) {
            return -Math.log(df)/t;
        }

        @Override
        public Double getDf(Double t, Double rate) {
            return Math.exp(-t*rate);
        }
    };

    public abstract Double getRate(Double t, Double df) throws Exception;

    public abstract Double getDf(Double t, Double rate) throws Exception;
}
