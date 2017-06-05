package projekt.funkcje;

import java.math.BigDecimal;

import org.jzy3d.maths.Range;

/**
 * function y = schw(x) % % Schwefel function % Matlab Code by A. Hedar (Nov.
 * 23, 2005). % The number of variables n should be adjusted below. % The
 * default value of n = 2. % n = 2; s = sum(-x.*sin(sqrt(abs(x)))); y =
 * 418.9829*n+s;
 */
public class F_swch implements Funkcja {
    private int n = 2;
    private Double s = new Double(0);

    public Double wykonaj(Double wartosc) {
        Double fitness = 0.0;
        Double innerSum = 0.0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                innerSum += wartosc;
            }

            /*
             * Calculate the outer summation, which is inner summation squared
             */
            fitness += Math.pow(innerSum, 2.0);
        }
        BigDecimal bdfitness = new BigDecimal(fitness).setScale(4,BigDecimal.ROUND_HALF_UP);
        return bdfitness.doubleValue();
    }

    // public Double wykonaj(double x, double y) {
    // return 418.9829 * n - (x * Math.sin(Math.sqrt(Math.abs(x))) + y *
    // Math.sin(Math.sqrt(Math.abs(y))));
    // }
    /**
     * Ta metoda zwraca poprawny wyni wg.
     * https://jamesmccaffrey.wordpress.com/2011/12/10/plotting-schwefels-
     * function-with-scilab/
     */

    public Double wykonaj(double x, double y) {
        double d = x * Math.sin(Math.sqrt(Math.abs(x))) +
                   y * Math.sin(Math.sqrt(Math.abs(y)));
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);
    
        return 418.9829 * n - bd.doubleValue();
    }

    @Override
    public Range getRange() {
        return new Range(-500, 500);
    }

    @Override
    public double getTMax() {
        return 350000;
    }

    @Override
    public double getStChlodzenia() {
        return 0.85d;
    }

    @Override
    public double getPrzedzialOd() {
        return getRange().getMin();
    }

    @Override
    public double getPrzedzialDo() {
        return getRange().getMax();
    }

    @Override
    public int getEpoka() {
        return 550000;
    }
}
