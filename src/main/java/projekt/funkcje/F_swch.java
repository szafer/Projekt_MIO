package projekt.funkcje;

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
		for (int i = 0; i < n; i++) {
			s = +wartosc * (-1) * Math.sin(Math.sqrt(Math.abs(wartosc)));
		}
		return 418.9829 * n + s;
	}

	public Double wykonaj(double x1, double x2) {
		return (-x1 * Math.sin(Math.sqrt(Math.abs(x1)))) + (-x2 * Math.sin(Math.sqrt(Math.abs(x2))));
		// return x1 * Math.sin(Math.abs(x1)) + x2 * Math.sin(Math.abs(x2));
	}

	@Override
	public Range getRange() {
		return new Range(-500,500);
	}

}
