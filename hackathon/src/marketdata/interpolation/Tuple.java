package marketdata.interpolation;

import java.util.Comparator;

/**
 * Created by rjain236 on 25/11/15.
 */
public class Tuple implements Comparable<Tuple>{

    private Double x;
    private Double y;

    public Tuple(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public int compareTo(Tuple o) {
        return x.compareTo(o.x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (!x.equals(tuple.x)) return false;
        return !(y != null ? !y.equals(tuple.y) : tuple.y != null);

    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}
