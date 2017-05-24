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
		return null;
	}

	public Double wykonaj(double x, double y) {
//	  dla 0 zwraca 0 co jest ok https://www.sfu.ca/~ssurjano/rastr.html
			return 10*n +
					(x*x - 10* Math.cos(2 * PI * x))+
					(y*y - 10* Math.cos(2 * PI * y));
			
//		  dla 0 zwraca -2 co jest ok wg http://155.158.112.25/~algorytmyewolucyjne/materialy/funkcje_testowe_2.pdf		
//			return 	(x*x -  Math.cos(18 * PI * x))+
//					(y*y -  Math.cos(18 * PI * y));   
		}

	public Double wykonaj_2(double x1, double x2) {
		return x1 * x1 + x2 * x2 - Math.cos(18 * x1) - Math.cos(18 * x2) + 2;
	}

	@Override
	public Range getRange() {
		return new Range(-5.12f, 5.12f);
	}

	@Override
	public double getTMax() {
		return 350000;
	}

	@Override
	public double getStChlodzenia() {
		return 0.92d;
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
