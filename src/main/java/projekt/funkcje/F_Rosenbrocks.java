package projekt.funkcje;

import org.jzy3d.maths.Range;

public class F_Rosenbrocks implements Funkcja {
	int n = 10;

	@Override
	public Double wykonaj(Double wartosc) {
		Double fitness = 0.0;

		/*
		 * Compute the fitness function for Rosenbrock's Valley:
		 * 
		 * f(X) = sigma,n-1 [100(Xi+1 - Xi^2)^2 + (1 - Xi)^2]
		 * 
		 */
		for (int i = 0; i < n - 1; ++i) {
			/* Calculate the fitness */
			fitness += 100 * Math.pow((wartosc - Math.pow(wartosc, 2.0)), 2.0) + Math.pow((1 - wartosc), 2.0);
		}
		return fitness;
	}

	@Override
	public Double wykonaj(double x1, double x2) {
		// 100(y – x^2)^2 + (1 – x)^2
		return 100 * Math.pow((x2 - x1 * x1), 2) + Math.pow((1 - x1), 2);
	}

	@Override
	public Range getRange() {
		return new Range(-2, 2);
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
		return 155000;
	}
}
