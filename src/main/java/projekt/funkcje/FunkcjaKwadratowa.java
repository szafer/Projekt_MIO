package projekt.funkcje;

import org.jzy3d.maths.Range;

public class FunkcjaKwadratowa implements Funkcja {
	public Double wykonaj(Double wartosc) {
		return Math.pow(wartosc, 2);
	}

	@Override
	public Double wykonaj(double x1, double x2) {
		return null;
		// TODO Auto-generated method stub
//		return x1 * x2;
	}

	@Override
	public Range getRange() {
		return new Range(-3, 3);
	}

	@Override
	public double getTMax() {
		return 15000;
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
		return 1000;
	}
}
