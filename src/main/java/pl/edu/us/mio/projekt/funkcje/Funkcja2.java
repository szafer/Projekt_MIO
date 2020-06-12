package pl.edu.us.mio.projekt.funkcje;

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

	@Override
	public double getTMax() {
		return 35000;
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
		return 55000;
	}
}
