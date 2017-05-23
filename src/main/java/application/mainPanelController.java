package application;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.jzy3d.analysis.AnalysisLauncher;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import projekt.NazwaAlgorytmu;
import projekt.NazwaFunkcji;
import projekt.algorytmy.Algorytm;
import projekt.algorytmy.Dychotomia;
import projekt.algorytmy.Wyzarzanie;
import projekt.algorytmy.ZlotyPodzial;
import projekt.funkcje.F_Ackley;
import projekt.funkcje.F_Rosenbrocks;
import projekt.funkcje.F_mich;
import projekt.funkcje.F_rast;
import projekt.funkcje.F_swch;
import projekt.funkcje.Funkcja;
import projekt.funkcje.Funkcja2;
import projekt.funkcje.Funkcja3;
import projekt.funkcje.FunkcjaKwadratowa;

/**
 * @author marek
 *         http://dev.heuristiclab.com/trac.fcgi/wiki/Documentation/Reference/
 *         Test%20Functions#no1
 *         http://155.158.112.25/~algorytmyewolucyjne/materialy/
 *         funkcje_testowe_2.pdf
 *         https://jamesmccaffrey.wordpress.com/2011/12/10/plotting-schwefels-
 *         function-with-scilab/
 */
public class mainPanelController {
	// @FXML
	// private RadioButton rbF1, rbF2, rbF3;
	@FXML
	private TextField txtTmax, txtStChlodzenia, txtPrzedzialOd, txtPrzedzialDo, txtEpoka;
	@FXML
	private Slider slTMax, slStChlodzenia, slPrzedzialOd, slPrzedzialDo, slEpoka;
	@FXML
	private Button btnStart, btnStop, btnZamknij, btnWykres;
	@FXML
	private LineChart chart;
	@FXML
	private NumberAxis xAxis, yAxis;
	@FXML
	private ComboBox<NazwaFunkcji> cbFunkcja;
	@FXML
	private ComboBox<NazwaAlgorytmu> cbAlgorytm;
	@FXML
	private SplitPane spMainPanel;
	Stage stage;
	private NazwaFunkcji nazwaFunkcji;
	private NazwaAlgorytmu nazwaAlgorytmu;

	private XYChart.Series<Double, Double> series1 = new XYChart.Series<Double, Double>();

	// Główny wątek
	private ExecutorService executor;
	private Algorytm algorytm;
	private Funkcja funkcja;
	@FXML
	private AnchorPane panelFunkcja;
	@FXML
	private StackPane spFunkcja;
	IntegerProperty slTMaxSliderValue = new SimpleIntegerProperty(0);
	StringProperty txtProp = new SimpleStringProperty();
	// private IntegerProperty totalCents = new SimpleIntegerProperty();

	@FXML
	void autoZoom() {
		chart.getXAxis().setAutoRanging(true);
		chart.getYAxis().setAutoRanging(true);
	}

	public void load() {
		stage = (Stage) spMainPanel.getScene().getWindow();

	}

	/**
	 * Bindowanie wszystkich pól. Albo ChangeListenery bezpośrednio na pole na X
	 * albo przez Proprery i bindBidiRectional
	 */
	public void initialize() {
		cbFunkcja.getItems().addAll(Arrays.asList(NazwaFunkcji.values()));
		cbAlgorytm.getItems().addAll(Arrays.asList(NazwaAlgorytmu.values()));
		// Temperatura
		slTMax.setMin(0);
		slTMax.setMax(50000);
		slTMax.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				txtTmax.textProperty().set(String.valueOf(newValue.intValue()));
			}
		});
		txtTmax.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txtTmax.setText(newValue.replaceAll("[^\\d]", ""));
				} else
					slTMax.valueProperty().set(Integer.parseInt(newValue));
			}
		});

		// St Chłodzenia
		Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");

		UnaryOperator<Change> filter = c -> {
			if (decimalPattern.matcher(c.getControlNewText()).matches()) {
				return c;
			} else {
				return null;
			}
		};

		TextFormatter<Double> formatter = new TextFormatter<>(filter);
		txtStChlodzenia.setTextFormatter(formatter);
		slStChlodzenia.setMin(0);
		slStChlodzenia.setMax(1);
		slStChlodzenia.setMajorTickUnit(10);
		slStChlodzenia.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.equals(""))
					return;
				txtStChlodzenia.textProperty().set(String.format("%.2f", newValue.doubleValue()).replace(",", "."));
				System.out.println(String.format("%.2f", newValue.doubleValue()).replace(",", "."));

			}
		});
		txtStChlodzenia.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(""))
					return;
				try {
					Float i = Float.valueOf(newValue);
					if (i >= 0 && i <= 1) {
						slStChlodzenia.valueProperty().set(Float.parseFloat(newValue));
						// do what you want with this i
					} else {
						((StringProperty) observable).setValue(oldValue);
					}
				} catch (Exception e) {
					((StringProperty) observable).setValue(oldValue);
				}
				// if (!newValue.matches("\\d+(\\.\\d{1,2})?")) {
				// txtStChlodzenia.setText(newValue.replaceAll("[^\\.d]", ""));
				// txtStChlodzenia.setText(String.format("%.2f", newValue));
				// } else
			}
		});

		// PrzedzialOd
		slPrzedzialOd.setMin(-10);
		slPrzedzialOd.setMax(10);
		slPrzedzialOd.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				txtPrzedzialOd.textProperty().set(String.valueOf(newValue.intValue()));
			}
		});
		txtPrzedzialOd.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txtPrzedzialOd.setText(newValue.replaceAll("[^\\d]", ""));
				} else
					slPrzedzialOd.valueProperty().set(Integer.parseInt(newValue));
			}
		});

		// PrzedzialDo
		slPrzedzialDo.setMin(0);
		slPrzedzialDo.setMax(20);
		slPrzedzialDo.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				txtPrzedzialDo.textProperty().set(String.valueOf(newValue.intValue()));
			}
		});
		txtPrzedzialDo.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txtPrzedzialDo.setText(newValue.replaceAll("[^\\d]", ""));
				} else
					slPrzedzialDo.valueProperty().set(Integer.parseInt(newValue));
			}
		});

		// Epoka
		slEpoka.setMin(0);
		slEpoka.setMax(300000);
		slEpoka.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				txtEpoka.textProperty().set(String.valueOf(newValue.intValue()));
			}
		});
		txtEpoka.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txtEpoka.setText(newValue.replaceAll("[^\\d]", ""));
				} else
					slEpoka.valueProperty().set(Integer.parseInt(newValue));
			}
		});
		// slTMax.valueProperty().bindBidirectional(slTMaxSliderValue);
		// txtTmax.textProperty().bindBidirectional(txtProp);
		// txtProp.addListener(new ChangeListener<String>() {
		//
		// @Override
		// public void changed(ObservableValue<? extends String> observable,
		// String oldValue, String newValue) {
		// slTMaxSliderValue.set(Integer.parseInt(newValue));
		// }
		// });
		// slTMaxSliderValue.addListener(new ChangeListener<Number>() {
		//
		// @Override
		// public void changed(ObservableValue<? extends Number> observable,
		// Number oldValue, Number newValue) {
		// txtProp.set(newValue.toString());
		// }
		// });

		xAxis.setLabel("Przebiegi");
		// xAxis.setLowerBound(-10);
		// xAxis.setUpperBound(10);
		// yAxis.setLowerBound(-10);
		// yAxis.setUpperBound(10);
		chart.setTitle("Wykres funkcji");
		chart.setCreateSymbols(false);// wyłącza kółka w punktach

		// addDataTimeline = new Timeline( new KeyFrame(
		// Duration.millis( 250 ),
		// new EventHandler<ActionEvent>() {
		// @Override
		// public void handle( ActionEvent actionEvent ) {
		// addSample();
		// }
		// }
		// ));
		// addDataTimeline.setCycleCount( Animation.INDEFINITE );
		//
		// chart.setOnMouseMoved( new EventHandler<MouseEvent>() {
		// @Override
		// public void handle( MouseEvent mouseEvent ) {
		// double xStart = chart.getXAxis().getLocalToParentTransform().getTx();
		// double axisXRelativeMousePosition = mouseEvent.getX() - xStart;
		// outputLabel.setText( String.format(
		// "%d, %d (%d, %d); %d - %d",
		// (int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY(),
		// (int) mouseEvent.getX(), (int) mouseEvent.getY(),
		// (int) xStart,
		// chart.getXAxis().getValueForDisplay( axisXRelativeMousePosition
		// ).intValue()
		// ) );
		// }
		// } );

		// Panning works via either secondary (right) mouse or primary with ctrl
		// held down
		ChartPanManager panner = new ChartPanManager(chart);
		panner.setMouseFilter(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseButton.SECONDARY
						|| (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.isShortcutDown())) {
					// let it through
				} else {
					mouseEvent.consume();
				}
			}
		});
		panner.start();

		// Zooming works only via primary mouse button without ctrl held down
		JFXChartUtil.setupZooming(chart, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() != MouseButton.PRIMARY || mouseEvent.isShortcutDown())
					mouseEvent.consume();
			}
		});

		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler(chart);

	}

	public void ustawAlgorytm() {
		nazwaAlgorytmu = cbAlgorytm.getSelectionModel().getSelectedItem();
		if (nazwaFunkcji == null || nazwaAlgorytmu == null)
			return;
		algorytm = dajAlgorytm(funkcja);
		ustawParametryPanelWgAlgorytmu();
	}

	public void ustawFunkcje() {
		nazwaFunkcji = cbFunkcja.getSelectionModel().getSelectedItem();
		funkcja = dajFunkcje();
		// cbAlgorytm.getSelectionModel().clearSelection();
		if (nazwaFunkcji == null)
			return;
		ustawParametryFunkcjiNaPanelu();
		if (nazwaFunkcji == null || nazwaAlgorytmu == null)
			return;
		algorytm = dajAlgorytm(funkcja);
		ustawParametryPanelWgAlgorytmu();
	}

	private void ustawParametryPanelWgAlgorytmu() {
		if (algorytm instanceof Dychotomia || algorytm instanceof ZlotyPodzial) {
			slTMax.setDisable(true);
			slStChlodzenia.setDisable(true);
			slEpoka.setDisable(true);
			txtTmax.setDisable(true);
			txtStChlodzenia.setDisable(true);
			txtEpoka.setDisable(true);
		} else {
			slTMax.setDisable(false);
			slStChlodzenia.setDisable(false);
			slEpoka.setDisable(false);
			txtTmax.setDisable(false);
			txtStChlodzenia.setDisable(false);
			txtEpoka.setDisable(false);
		}
	}

	private void ustawParametryFunkcjiNaPanelu() {
		slTMax.setValue(funkcja.getTMax());
		slStChlodzenia.setValue(funkcja.getStChlodzenia());
		slPrzedzialOd.setValue(funkcja.getPrzedzialOd());
		slPrzedzialDo.setValue(funkcja.getPrzedzialDo());
		slEpoka.setValue(funkcja.getEpoka());
		if (funkcja instanceof FunkcjaKwadratowa || funkcja instanceof Funkcja2 || funkcja instanceof Funkcja3) {
			btnWykres.setDisable(true);
		} else {
			btnWykres.setDisable(false);
		}
	}

	public void onStart() {
		if (nazwaFunkcji == null || nazwaAlgorytmu == null)
			return;
		series1 = new XYChart.Series<Double, Double>();
		series1.setName(nazwaAlgorytmu.toString());
		chart.getData().add(series1);

		// TODO odtąd nornalnie działa
		// F_kwadrat f1 = new F_kwadrat();
		// System.out.println("Wyzarzanie dla x^2");
		// wyzarzanie1 = new Wyzarzanie(f1, 15000, 0.85, -3, 3, 1500);

		// wyzarzanie1.wykonaj();
		// Thread t2 = new Thread(wyzarzanie1);
		// t2.start();
		executor = Executors.newCachedThreadPool(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setDaemon(true);
				return thread;
			}
		});
		executor.execute(algorytm);
		// -- Prepare Timeline
		prepareTimeline();
	}

	private Algorytm dajAlgorytm(Funkcja f) {
		switch (nazwaAlgorytmu) {
		case WYZARZANIE:
			return new Wyzarzanie(f, f.getTMax(), f.getStChlodzenia(), f.getRange().getMin(), f.getRange().getMax(),
					f.getEpoka());
		// return new Wyzarzanie(f, 35000, 0.85, -1, 1, 55000);
		case DYCH:
			return new Dychotomia(f, f.getRange());
		case GOLDER_RATIO:
			return new ZlotyPodzial(Float.valueOf(f.getRange().getMin()).intValue(),
					Float.valueOf(f.getRange().getMax()).intValue(), f);
		}
		return null;
	}

	private Funkcja dajFunkcje() {
		switch (nazwaFunkcji) {
		case KWADRATOWA:
			return new FunkcjaKwadratowa();
		case F2:
			return new Funkcja2();
		case F3:
			return new Funkcja3();
		case MICH:
			return new F_mich();// -3;3
		case RAST:
			return new F_rast();// -5,12 ;5,12
		case SCHW:
			return new F_swch();// -500;500
		case ROSEN:
			return new F_Rosenbrocks();
		case ACK:
			return new F_Ackley();
		}
		return null;
	}

	public void onWykres() {
		try {
			AnalysisLauncher.open(new WykresFunkcji3D(funkcja, funkcja.getRange()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onStop() {

		chart.getData().clear();
		// TODO wymyslić zatrzymanie funkcji

		// ((Node)(event.getSource())).getScene().getWindow().hide();
	}

	public void onZamknij() {
		System.exit(0);
	}

	// -- Timeline gets called in the JavaFX Main thread
	private void prepareTimeline() {
		// Every frame to take any data from queue and add to chart
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				addDataToSeries();
			}
		}.start();
	}

	private void addDataToSeries() {
		for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
			if (algorytm.getData().isEmpty())
				break;
			series1.getData().add(algorytm.getData().remove());
		}
		// // remove points to keep us at no more than MAX_DATA_POINTS
		// if (series1.getData().size() > MAX_DATA_POINTS) {
		// series1.getData().remove(0, series1.getData().size() -
		// MAX_DATA_POINTS);
		// }
		// // update
		// xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
		// xAxis.setUpperBound(xSeriesData - 1);
	}

}
