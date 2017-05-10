package projekt.algorytmy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import projekt.funkcje.Funkcja;

public class Wyzarzanie implements Algorytm {
	private static final double euler = 2.718281828459;
	private static final double boltzman = 1.380;
	private int epoka = 0; // ilosc obr. glownej petli
	private Double stChlodzenia = 0.85; // stala chlodzenia
//	private XYChart.Series series = new XYChart.Series();
	private Funkcja funkcja;
	private double tMax = 0;
	private double przedzial_od;
	private double przedzial_do;
	private int zmianaSasiad = 0;
	private static Random random = new Random();
	private LineChart chart;
//	private List<Double> wyniki = new ArrayList<Double>();
	private ConcurrentLinkedQueue<XYChart.Data<Double,Double>> data = new ConcurrentLinkedQueue<XYChart.Data<Double, Double>>();

	public Wyzarzanie(Funkcja funkcja, double tMax, double stChlodzenia, double przedzial_od, double przedzial_odo,
			int epoka) {
		this.funkcja = funkcja;
		this.tMax = tMax;
		this.stChlodzenia = stChlodzenia;
		this.przedzial_od = przedzial_od;
		this.przedzial_do = przedzial_odo;
		this.epoka = epoka;
	}

	@Override
	public Boolean wykonaj() {

		// Losowy wybór punktu startowego
		int licz = (int) (random.nextInt((int) ((przedzial_do - 1) - (przedzial_od + 1)))
				- (Math.abs(przedzial_od) - 2));
		Double s = new Double(licz);
		Double T = tMax;
		Double sBis = new Double(0);
		// double smich = funkcja.wykonaj(-1.8013);
		// double srast = funkcja.wykonaj((double) 0);
		// double swch = funkcja.wykonaj((double) 0);

		// GLOWNA PETLA
		for (int obr = 0; obr < epoka; obr++) {
			sBis = sasiad(s);
			// System.out.println("s " +s+ " sBis "+ sBis);
			// System.out.format("s %.10f sBis %.10f%n ", s, sBis);

			Double delta = new Double(0);
			// Wyznaczenie różnicy wartości funkcji w punkcie s i sBis
			delta = (funkcja.wykonaj(sBis) - funkcja.wykonaj(s));
			if (delta < 0) {
				s = sBis;
			} else {
				Double los = random.nextDouble();
				if (los == 0.0) {
					los = 1.0;
				}
				// TODO ??
				Double exp = Math.pow(euler, -1 * delta / (T * boltzman));
				exp = Math.pow(euler, -1 * delta / T);
				// System.out.println("exp " +exp);
				if (los < exp) {
					// System.out.println("wybór gorszego " );
					s = sBis;
				}
			}
//			wyniki.add(new Double(s));
			try {
//				series.getData().add(new XYChart.Data(new Double(s), obr));
				data.add(new XYChart.Data( obr, new Double(s)));

//				data.add(new Double(s));
			} catch (Exception e) {

			}
			// System.out.println("s " +s+ " w "+ obr + " obrocie.");
			System.out.format("s %.10f w %d obrocie%n", s, obr);

			// funkcja zmiany temperatury
			T = stChlodzenia * T;
		}
		System.out.println("koniec ");
		return true;
	}

	private Double sasiad(Double wartosc) {
		Double i = random.nextDouble() / 10;
		if (wartosc - i <= przedzial_do && wartosc - i >= przedzial_od && zmianaSasiad == 0) {
			zmianaSasiad = 1;
			return wartosc - i;
		}
		if (wartosc + i <= przedzial_do && wartosc + i >= przedzial_od && zmianaSasiad == 1) {
			zmianaSasiad = 0;
			return wartosc + i;
		}
		zmianaSasiad = (zmianaSasiad + 1) % 2;
		return wartosc;
	}

//	public List<Double> getWyniki() {
//		return wyniki;
//	}
//
//	public XYChart.Series getSeries() {
//		return series;
//	}

	@Override
	public ConcurrentLinkedQueue<XYChart.Data<Double,Double>> getData() {
		return data;
	}

	@Override
	public void run() {
		wykonaj();
	}
}
