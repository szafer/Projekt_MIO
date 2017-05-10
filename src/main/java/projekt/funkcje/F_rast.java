package projekt.funkcje;

import org.jzy3d.maths.Range;

/**
 * function y = rast(x) % % Rastrigin function % Matlab Code by A. Hedar (Nov.
 * 23, 2005). % The number of variables n should be adjusted below. % The
 * default value of n = 2. % n = 2; s = 0; for j = 1:n s =
 * s+(x(j)^2-10*cos(2*pi*x(j))); end y = 10*n+s;
 */
public class F_rast implements Funkcja {
	private final double PI = 3.14159265;
	private int n = 2;
	private Double s = new Double(0);

	public Double wykonaj(Double wartosc) {
		for (int i = 0; i < n; i++) {
			s = +Math.pow(wartosc, 2) - (10 * Math.cos(2 * PI * wartosc));
		}
		return 10 * n + s;
	}

	public Double wykonaj(double x1, double x2) {
		return x1 * x1 + x2 * x2 - Math.cos(18 * x1) - Math.cos(18 * x2) + 2;
	}

	@Override
	public Range getRange() {
		return new Range(-1, 1);
	}

}
