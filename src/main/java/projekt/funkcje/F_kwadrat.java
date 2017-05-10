package projekt.funkcje;

import org.jzy3d.maths.Range;

public class F_kwadrat implements Funkcja{

	public Double wykonaj(Double wartosc) {
		return Math.pow(wartosc, 2);
	}

	public Double wykonaj(double x1, double x2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Range getRange() {
		// TODO Auto-generated method stub
		return null;
	}

}
