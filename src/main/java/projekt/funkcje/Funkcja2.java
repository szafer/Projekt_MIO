package projekt.funkcje;

import org.jzy3d.maths.Range;

public class Funkcja2 implements Funkcja {
	public Double wykonaj(Double wartosc) {
		return Math.pow(wartosc - 5, 2);
	}

	@Override
	public Double wykonaj(double x1, double x2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Range getRange() {
		return new Range(-10, 10);
	}
}
