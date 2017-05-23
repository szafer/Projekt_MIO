package projekt.algorytmy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jzy3d.maths.Range;

import javafx.scene.chart.XYChart;
import projekt.funkcje.Funkcja;

public class Dychotomia implements Algorytm {
	private static final double EPSILON = 0.05;
	private static final int precyzja = 6;
	private double przedzialOd;
	private double przedzialDo;
	private Funkcja funkcja;
	private List<XYChart.Data> lista = new ArrayList<XYChart.Data>();
	private XYChart.Series series = new XYChart.Series();
	private ConcurrentLinkedQueue<XYChart.Data<Double, Double>> data = new ConcurrentLinkedQueue<XYChart.Data<Double, Double>>();

	public Dychotomia(Funkcja funkcja, Range range) {
		super();
		this.przedzialOd = Float.valueOf(range.getMin()).intValue();
		this.przedzialDo = Float.valueOf(range.getMax()).intValue();
		this.funkcja = funkcja;
	}

	public Dychotomia(double przedzialOd, double przedzialDo, Funkcja funkcja) {
		super();
		this.przedzialOd = przedzialOd;
		this.przedzialDo = przedzialDo;
		this.funkcja = funkcja;
	}

	public Dychotomia(Funkcja funkcja, double tMax, double stChlodzenia, int przedzial_od, int przedzial_do,
			int epoka) {
		super();
		this.przedzialOd = przedzial_od;
		this.przedzialDo = przedzial_do;
		this.funkcja = funkcja;
	}

	@Override
	public Boolean wykonaj() {
		double a = przedzialOd;
		double b = przedzialDo;
		double xL;
		double xR;
		Integer it = 0;
		while ((b - a) > EPSILON) {
			xL = a + 0.25 * (b - a);
			xR = a + 0.75 * (b - a);
			try {
				// data.add(new XYChart.Data(xL, a));//TODO nie podawać
				// współrzędnych tylko ilośc iteracji po kßórych schodzi do 0
				// data.add(new XYChart.Data(xR, b));
				data.add(new XYChart.Data(it++, xR));
			} catch (Exception e) {

			}
			System.out.println("xL=" + format(xL) + "\t  xR=" + format(xR));

			if (funkcja.wykonaj(xL) > funkcja.wykonaj(xR)) {
				a = xL;
			} else {
				b = xR;
			}

		}
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

	public XYChart.Series getSeries() {
		return series;
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
