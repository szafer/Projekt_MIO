package projekt.algorytmy;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.scene.chart.XYChart;
import projekt.funkcje.Funkcja;

public class ZlotyPodzial implements Algorytm {
	private static final double EPSILON = 0.05;
	private static final int precyzja = 6;
	private ConcurrentLinkedQueue<XYChart.Data<Double, Double>> data = new ConcurrentLinkedQueue<XYChart.Data<Double, Double>>();

	private double przedzialOd;
	private double przedzialDo;
	private Funkcja f;

	public ZlotyPodzial(double przedzialOd, double przedzialDo, Funkcja f) {
		super();
		this.przedzialOd = przedzialOd;
		this.przedzialDo = przedzialDo;
		this.f = f;
	}

	@Override
	public Boolean wykonaj() {
		double a = przedzialOd;
		double b = przedzialDo;

		// współczynnik złotego podziału
		double k = (double) ((Math.sqrt(5) - 1) / 2);

		// lewa i prawa próbka
		double xL = b - k * (b - a);
		double xR = a + k * (b - a);
        Integer it = 0;
		// pętla póki nie zostanie spełniony warunek stopu
		while ((b - a) > EPSILON) {
			System.out.println("xL=" + format(xL) + "\t  xR=" + format(xR));

			// porównaj wartości funkcji celu lewej i prawej próbki
			if (f.wykonaj(xL) < f.wykonaj(xR)) {
				// wybierz przedział [a, xR]
				b = xR;
				xR = xL;
				xL = b - k * (b - a);
			} else {
				// wybierz przedział [xL, b]
				a = xL;
				xL = xR;
				xR = a + k * (b - a);
			}
			try {
                data.add(new XYChart.Data(it++, xR));
            } catch (Exception e) {

            }
		}

		// zwróć wartość środkową przedziału
		System.out.println("Optimum " + (a + b) / 2);
		return true;
	}

	public static String format(double liczba) {
		String zwrot = null;
		zwrot = "" + (new BigDecimal("" + liczba).setScale(precyzja, BigDecimal.ROUND_HALF_UP).doubleValue());
		if (liczba > 0) {
			zwrot = " " + zwrot;
		}
		return zwrot;
	}

	@Override
	public void run() {
		wykonaj();
	}

	@Override
	public ConcurrentLinkedQueue<XYChart.Data<Double, Double>> getData() {
		return data;
	}

	@Override
	public void ustawParametry(double temperaturaMax, double stalaChlodzenia, double przedzial_od, double przedzial_odo,
			int epoka) {
		this.przedzialOd = przedzial_od;
		this.przedzialDo = przedzial_odo;
	}
}
