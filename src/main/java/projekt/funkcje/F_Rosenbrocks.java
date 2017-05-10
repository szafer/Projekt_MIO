package projekt.funkcje;

import org.jzy3d.maths.Range;

public class F_Rosenbrocks implements Funkcja {

	@Override
	public Double wykonaj(Double wartosc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double wykonaj(double x1, double x2) {
		// 100(y – x^2)^2 + (1 – x)^2
		return 100 * Math.pow((x2 - x1*x1), 2) + Math.pow((1 - x1), 2);
	}

	@Override
	public Range getRange() {
		return new Range(-2,2);
	}

}
