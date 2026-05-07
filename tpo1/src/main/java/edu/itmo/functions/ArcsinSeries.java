package edu.itmo.functions;

public final class ArcsinSeries {

    private static final double PI = 3.14159265358979323846264338327950288419716939937510;
    private static final double EPS = 1e-15;
    private static final int MAX_TERMS = 50_000;

    private ArcsinSeries() {
    }

    public static double arcsin(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }
        if (x > 1.0) {
            return Double.NaN;
        }
        if (x < -1.0) {
            return Double.NaN;
        }
        if (x == 1.0) {
            return PI / 2.0;
        }
        if (x == -1.0) {
            return -PI / 2.0;
        }
        if (x == 0.0) {
            return 0.0;
        }
        if (x < 0.0) {
            return -arcsinPositiveSeries(-x, EPS, MAX_TERMS);
        }
        return arcsinPositiveSeries(x, EPS, MAX_TERMS);
    }

    static double arcsinPositiveSeries(double x, double convergenceEps, int maxTerms) {
        double term = x;
        double sum = x;
        double x2 = x * x;
        for (int n = 1; n < maxTerms; n++) {
            double factor = ((2.0 * n - 1.0) * (2.0 * n - 1.0) * x2) / ((2.0 * n) * (2.0 * n + 1.0));
            term *= factor;
            sum += term;
            if (Math.abs(term) < convergenceEps) {
                break;
            }
        }
        return sum;
    }
}
