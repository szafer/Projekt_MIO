package projekt.funkcje;

import org.jzy3d.maths.Range;

/**
 * 
 * function y = mich(x) % % Michalewicz function % Matlab Code by A. Hedar (Nov.
 * 23, 2005). % The number of variables n should be adjusted below. % The
 * default value of n =2. % n = 2; m = 10; s = 0; for i = 1:n; s =
 * s+sin(x(i))*(sin(i*x(i)^2/pi))^(2*m); end y = -s;
 *
 */
public class F_mich implements Funkcja {
	private final double PI = 3.14159265;
	private int n = 2;
	private int m = 10;
	private Double s = new Double(0);

	public Double wykonaj(Double wartosc) {
		for (double i = 0; i < n; i++) {
			Double s1 = Math.pow(3, (2.0 / PI));
			s1 = Math.pow(3, 1.6d);
			double x = (i + 1.0) * wartosc;
			double y = 2.0 / PI;
			s = +Math.sin(wartosc) * Math.pow(Math.sin(Math.pow((i + 1.0) * wartosc, (2.0 / PI))), 2.0 * m);
		}
		return -1 * s;
	}

	public Double wykonaj(double x, double y) {
//		s = +Math.sin(x1) * Math.pow(Math.sin(x1) * (x1 / PI), 2.0 * m)
//				+ Math.sin(x2) * Math.pow(Math.sin(x2) * (x2 / PI), 2.0 * m);
//		return -1 * s;
		s = +Math.sin(x) * Math.pow(Math.sin(x*x   / PI), 2.0 * m)
				+Math.sin(y) * Math.pow(Math.sin(2*y*y / PI), 2.0 * m);
			return -1 * s;
	}

	@Override
	public Range getRange() {
		return new Range(0.0f, 3.14159f);// return new Range(0.0f,
											// 3.14159265f);
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
		return 300000;
	}
}
