package projekt.algorytmy;

import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.scene.chart.XYChart;

public interface Algorytm extends Runnable {
	Boolean wykonaj();

	ConcurrentLinkedQueue<XYChart.Data<Double,Double>> getData();
}
