package projekt.funkcje;

import org.jzy3d.maths.Range;

public class Funkcja1 implements Funkcja {
	public Double wykonaj(Double wartosc) {
		return Math.pow(wartosc, 2);
	}

	@Override
	public Double wykonaj(double x1, double x2) {
		// TODO Auto-generated method stub
		return x1 * x2;
	}

	@Override
	public Range getRange() {
		return new Range(-3, 3);
	}
}
