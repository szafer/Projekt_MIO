package pl.edu.us.mio.projekt.algorytmy;

import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.scene.chart.XYChart;
import pl.edu.us.mio.projekt.funkcje.Funkcja;

public interface Algorytm extends Runnable {
	Boolean wykonaj();

	ConcurrentLinkedQueue<XYChart.Data<Double,Double>> getData();
	
	void ustawParametry( double temperaturaMax, double stalaChlodzenia, double przedzial_od,
			double przedzial_odo, int epoka);
}
