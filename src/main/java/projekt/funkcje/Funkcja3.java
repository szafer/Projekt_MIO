package projekt.funkcje;

import org.jzy3d.maths.Range;

public class Funkcja3 implements Funkcja {
	public Double wykonaj(Double wartosc) {
		return Math.pow(wartosc, 3) - wartosc;
	}

	@Override
	public Double wykonaj(double x1, double x2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Range getRange() {
		return new Range(-1, 1);
	}
}
