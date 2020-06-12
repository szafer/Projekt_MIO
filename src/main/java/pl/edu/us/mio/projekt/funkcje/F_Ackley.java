package pl.edu.us.mio.projekt.funkcje;

import org.jzy3d.maths.Range;

//f(x,y) = -20 * exp(-0.2*sqrt(0.5*(x^2 + y^2))) -
//- exp(0.5*cos(2*pi*x) + cos(2*pi*y)))
//+ 20 + e

//-->[x,y]=meshgrid(-4:.10:4,-4:.10:4);
//-->z=-20 * exp(-0.2*sqrt(0.5 * (x.^2 + y.^2)))
//     - exp(0.5 * (cos(2*%pi*x) + cos(2*%pi*y)))
//     + 20 + %e;
//-->g=scf();
//-->g.color_map=jetcolormap(64);
//-->surf(x,y,z);
public class F_Ackley implements Funkcja {

	private Double n = 10.0;
	private Double s = new Double(0);

	@Override
	public Double wykonaj(Double wartosc) {
		double sum1 = 0.0;
		double sum2 = 0.0;

		for (int i = 0; i < n; i++) {
			sum1 += Math.pow(wartosc, 2);
			sum2 += (Math.cos(2 * Math.PI * wartosc));
		}
		// TODo do sprawdzenia
		return -20.0 * Math.exp(-0.2 * Math.sqrt(sum1 / (n)) - Math.exp(sum2 / (n)) + 20.0 + Math.exp(1));
	}
	//
	// @Override
	// public Double wykonaj(double x1, double x2) {
	//
	// double sumsq = 0.0;
	// double sumcos = 0.0;
	// for (int i = 0; i < n; ++i) {
	// sumsq += x1 * x1;
	// sumcos += Math.cos(2 * Math.PI * x2);
	// }
	// return -20.0 * Math.exp(-0.2 * Math.sqrt(sumsq / n)) - Math.exp(sumcos /
	// n) + 20 + Math.E;
	// }

	@Override
	public Double wykonaj(double x, double y) {
		return -20 * Math.exp(-0.2 * Math.sqrt((0.5) * (x * x + y * y)))
				- Math.exp((0.5) * Math.cos(2 * Math.PI * x) + Math.cos(2 * Math.PI * y)) + 20 + Math.E;

	}

	@Override
	public Range getRange() {
		return new Range(-4, 4);
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
