package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.maths.Range;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import projekt.NazwaAlgorytmu;
import projekt.NazwaFunkcji;
import projekt.algorytmy.Algorytm;
import projekt.algorytmy.Dychotomia;
import projekt.algorytmy.Wyzarzanie;
import projekt.algorytmy.ZlotyPodzial;
import projekt.funkcje.F_Rosenbrocks;
import projekt.funkcje.F_mich;
import projekt.funkcje.F_rast;
import projekt.funkcje.F_swch;
import projekt.funkcje.Funkcja;
import projekt.funkcje.Funkcja1;
import projekt.funkcje.Funkcja2;
import projekt.funkcje.Funkcja3;

/**
 * @author marek
 * http://dev.heuristiclab.com/trac.fcgi/wiki/Documentation/Reference/Test%20Functions#no1
 * http://155.158.112.25/~algorytmyewolucyjne/materialy/funkcje_testowe_2.pdf
 * https://jamesmccaffrey.wordpress.com/2011/12/10/plotting-schwefels-function-with-scilab/
 */
public class mainPanelController {
	// @FXML
	// private RadioButton rbF1, rbF2, rbF3;
	@FXML
	private TextField txtTmax, txtStChlodzenia, txtPrzedzialOd, txtPrzedzialDo, txtEpoka;
	@FXML
	private Button btnStart, btnStop, btnZamknij;
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

	// Licznik x
	private int xSeriesData = 0;
	private XYChart.Series<Double, Double> series1 = new XYChart.Series<Double, Double>();
	// private Wyzarzanie wyzarzanie1;
	// private Algorytm algorytm;
	// Główny wątek
	private ExecutorService executor;
	private Algorytm algorytm;
	private Funkcja funkcja;
	@FXML
	private AnchorPane panelFunkcja;
	@FXML
	private StackPane spFunkcja;

	@FXML
	void autoZoom() {
		chart.getXAxis().setAutoRanging(true);
		chart.getYAxis().setAutoRanging(true);
	}

	public void load() {
		stage = (Stage) spMainPanel.getScene().getWindow();

	}

	public void initialize() {
		cbFunkcja.getItems().addAll(Arrays.asList(NazwaFunkcji.values()));
		cbAlgorytm.getItems().addAll(Arrays.asList(NazwaAlgorytmu.values()));
		// cbFunkcja.setPromptText("Email address");
		// cbFunkcja.setEditable(true);
		// cbFunkcja.valueProperty().addListener(new
		// ChangeListener<NazwaFunkcji>() {
		// @Override
		// public void changed(ObservableValue ov, NazwaFunkcji t, NazwaFunkcji
		// t1) {
		// nazwaFunkcji = t1;
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
	}

	public void ustawFunkcje() {
		nazwaFunkcji = cbFunkcja.getSelectionModel().getSelectedItem();
		funkcja = dajFunkcje();
//		cbAlgorytm.getSelectionModel().clearSelection();
		algorytm = dajAlgorytm(funkcja);
	}

	public void onStart() {
		if (nazwaFunkcji == null || nazwaAlgorytmu == null)
			return;
		series1 = new XYChart.Series<Double, Double>();
		series1.setName(nazwaAlgorytmu.toString());
		chart.getData().add(series1);
		xSeriesData = 0;
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
		// executor.execute(wyzarzanie1);
		// -- Prepare Timeline
		prepareTimeline();
		// Funkcja1 f1 = new Funkcja1();
		// // Funkcja2 f2 = new Funkcja2();
		// // Funkcja3 f3 = new Funkcja3();
		//
		// Dychotomia dychotomia1 = new Dychotomia(-3, 3, f1);
		// System.out.println("Dychotomia f(x^2) dla x z przedzialu [-3; 3]");
		// dychotomia1.wykonaj();
		// Dychotomia dychotomia2 = new Dychotomia(-10, 10, f2);
		// Dychotomia dychotomia3 = new Dychotomia(-1, 1, f3);
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		// chart.getData().add(wyzarzanie1.getSeries());
		// }
		// });

	}

	private Algorytm dajAlgorytm(Funkcja f) {
		switch (nazwaAlgorytmu) {
		case WYZARZANIE:
			return new Wyzarzanie(f, 15000, 0.85, Float.valueOf(f.getRange().getMin()).intValue(),
					Float.valueOf(f.getRange().getMax()).intValue(), 1000);
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
			return new Funkcja1();
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

	// size of graph
	int size = 400;

	// variables for mouse interaction
	private double mousePosX, mousePosY;
	private double mouseOldX, mouseOldY;
	private final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
	private final Rotate rotateY = new Rotate(-45, Rotate.Y_AXIS);

	/**
	 * Narazie nie używana - zastąpiłem ją bibliotekami z JZY3D
	 */
	public void startFunkcja() {

		// create axis walls
		Group cube = createCube(size);

		// initial cube rotation
		cube.getTransforms().addAll(rotateX, rotateY);

		// add objects to scene
		// StackPane root = new StackPane();
		panelFunkcja.getChildren();
		spFunkcja.getChildren().add(cube);

		// perlin noise
		float[][] noiseArray = createNoise(size);

		// mesh
		TriangleMesh mesh = new TriangleMesh();

		// create points for x/z
		float amplification = 100; // amplification of noise

		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				mesh.getPoints().addAll(x, noiseArray[x][z] * amplification, z);
			}
		}

		// texture
		int length = size;
		float total = length;

		for (float x = 0; x < length - 1; x++) {
			for (float y = 0; y < length - 1; y++) {

				float x0 = x / total;
				float y0 = y / total;
				float x1 = (x + 1) / total;
				float y1 = (y + 1) / total;

				mesh.getTexCoords().addAll( //
						x0, y0, // 0, top-left
						x0, y1, // 1, bottom-left
						x1, y1, // 2, top-right
						x1, y1 // 3, bottom-right
				);

			}
		}

		// faces
		for (int x = 0; x < length - 1; x++) {
			for (int z = 0; z < length - 1; z++) {

				int tl = x * length + z; // top-left
				int bl = x * length + z + 1; // bottom-left
				int tr = (x + 1) * length + z; // top-right
				int br = (x + 1) * length + z + 1; // bottom-right

				int offset = (x * (length - 1) + z) * 8 / 2; // div 2 because we
																// have u AND v
																// in the list

				// working
				mesh.getFaces().addAll(bl, offset + 1, tl, offset + 0, tr, offset + 2);
				mesh.getFaces().addAll(tr, offset + 2, br, offset + 3, bl, offset + 1);

			}
		}

		// material
		Image diffuseMap = createImage(size, noiseArray);

		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(diffuseMap);
		material.setSpecularColor(Color.WHITE);

		// mesh view
		MeshView meshView = new MeshView(mesh);
		meshView.setTranslateX(-0.5 * size);
		meshView.setTranslateZ(-0.5 * size);
		meshView.setMaterial(material);
		meshView.setCullFace(CullFace.NONE);
		meshView.setDrawMode(DrawMode.FILL);
		meshView.setDepthTest(DepthTest.ENABLE);

		cube.getChildren().addAll(meshView);

		// testing / debugging stuff: show diffuse map on chart
		ImageView iv = new ImageView(diffuseMap);
		iv.setTranslateX(-0.5 * size);
		iv.setTranslateY(-0.10 * size);
		iv.setRotate(90);
		iv.setRotationAxis(new Point3D(1, 0, 0));
		cube.getChildren().add(iv);

		// // scene
		// Scene scene = new Scene(root, 1600, 900, true,
		// SceneAntialiasing.BALANCED);
		// scene.setCamera(new PerspectiveCamera());
		//
		panelFunkcja.setOnMousePressed(me -> {
			mouseOldX = me.getSceneX();
			mouseOldY = me.getSceneY();
		});
		panelFunkcja.setOnMouseDragged(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
			rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;

		});

		makeZoomable(spFunkcja);

		// primaryStage.setResizable(false);
		// primaryStage.setScene(scene);
		// primaryStage.show();

	}

	/**
	 * Create texture for uv mapping
	 * 
	 * @param size
	 * @param noise
	 * @return
	 */
	public Image createImage(double size, float[][] noise) {

		int width = (int) size;
		int height = (int) size;

		WritableImage wr = new WritableImage(width, height);
		PixelWriter pw = wr.getPixelWriter();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				float value = noise[x][y];

				double gray = normalizeValue(value, -.5, .5, 0., 1.);

				gray = clamp(gray, 0, 1);

				Color color = Color.RED.interpolate(Color.YELLOW, gray);

				pw.setColor(x, y, color);

			}
		}

		return wr;

	}

	/**
	 * Axis wall
	 */
	public static class Axis extends Pane {

		Rectangle wall;

		public Axis(double size) {

			// wall
			// first the wall, then the lines => overlapping of lines over walls
			// works
			wall = new Rectangle(size, size);
			getChildren().add(wall);

			// grid
			double zTranslate = 0;
			double lineWidth = 1.0;
			Color gridColor = Color.WHITE;

			for (int y = 0; y <= size; y += size / 10) {

				Line line = new Line(0, 0, size, 0);
				line.setStroke(gridColor);
				line.setFill(gridColor);
				line.setTranslateY(y);
				line.setTranslateZ(zTranslate);
				line.setStrokeWidth(lineWidth);

				getChildren().addAll(line);

			}

			for (int x = 0; x <= size; x += size / 10) {

				Line line = new Line(0, 0, 0, size);
				line.setStroke(gridColor);
				line.setFill(gridColor);
				line.setTranslateX(x);
				line.setTranslateZ(zTranslate);
				line.setStrokeWidth(lineWidth);

				getChildren().addAll(line);

			}

			// labels
			// TODO: for some reason the text makes the wall have an offset
			// for( int y=0; y <= size; y+=size/10) {
			//
			// Text text = new Text( ""+y);
			// text.setTranslateX(size + 10);
			//
			// text.setTranslateY(y);
			// text.setTranslateZ(zTranslate);
			//
			// getChildren().addAll(text);
			//
			// }

		}

		public void setFill(Paint paint) {
			wall.setFill(paint);
		}

	}

	public void makeZoomable(StackPane control) {

		final double MAX_SCALE = 20.0;
		final double MIN_SCALE = 0.1;

		control.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {

				double delta = 1.2;

				double scale = control.getScaleX();

				if (event.getDeltaY() < 0) {
					scale /= delta;
				} else {
					scale *= delta;
				}

				scale = clamp(scale, MIN_SCALE, MAX_SCALE);

				control.setScaleX(scale);
				control.setScaleY(scale);

				event.consume();

			}

		});

	}

	/**
	 * Create axis walls
	 * 
	 * @param size
	 * @return
	 */
	private Group createCube(int size) {

		Group cube = new Group();

		// size of the cube
		Color color = Color.DARKCYAN;

		List<Axis> cubeFaces = new ArrayList<>();
		Axis r;

		// back face
		r = new Axis(size);
		r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.5 * 1), 1.0));
		r.setTranslateX(-0.5 * size);
		r.setTranslateY(-0.5 * size);
		r.setTranslateZ(0.5 * size);

		cubeFaces.add(r);

		// bottom face
		r = new Axis(size);
		r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.4 * 1), 1.0));
		r.setTranslateX(-0.5 * size);
		r.setTranslateY(0);
		r.setRotationAxis(Rotate.X_AXIS);
		r.setRotate(90);

		cubeFaces.add(r);

		// right face
		r = new Axis(size);
		r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.3 * 1), 1.0));
		r.setTranslateX(-1 * size);
		r.setTranslateY(-0.5 * size);
		r.setRotationAxis(Rotate.Y_AXIS);
		r.setRotate(90);

		// cubeFaces.add( r);

		// left face
		r = new Axis(size);
		r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.2 * 1), 1.0));
		r.setTranslateX(0);
		r.setTranslateY(-0.5 * size);
		r.setRotationAxis(Rotate.Y_AXIS);
		r.setRotate(90);

		cubeFaces.add(r);

		// top face
		r = new Axis(size);
		r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0));
		r.setTranslateX(-0.5 * size);
		r.setTranslateY(-1 * size);
		r.setRotationAxis(Rotate.X_AXIS);
		r.setRotate(90);

		// cubeFaces.add( r);

		// front face
		r = new Axis(size);
		r.setFill(color.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0));
		r.setTranslateX(-0.5 * size);
		r.setTranslateY(-0.5 * size);
		r.setTranslateZ(-0.5 * size);

		// cubeFaces.add( r);

		cube.getChildren().addAll(cubeFaces);

		return cube;
	}

	/**
	 * Create an array of the given size with values of perlin noise
	 * 
	 * @param size
	 * @return
	 */
	private float[][] createNoise(int size) {
		float[][] noiseArray = new float[(int) size][(int) size];

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				double frequency = 10.0 / (double) size;

				double noise = ImprovedNoise.noise(x * frequency, y * frequency, 0);

				noiseArray[x][y] = (float) noise;
			}
		}

		return noiseArray;

	}

	public static double normalizeValue(double value, double min, double max, double newMin, double newMax) {

		return (value - min) * (newMax - newMin) / (max - min) + newMin;

	}

	public static double clamp(double value, double min, double max) {

		if (Double.compare(value, min) < 0)
			return min;

		if (Double.compare(value, max) > 0)
			return max;

		return value;
	}

	/**
	 * Perlin noise generator
	 * 
	 * // JAVA REFERENCE IMPLEMENTATION OF IMPROVED NOISE - COPYRIGHT 2002 KEN
	 * PERLIN. // http://mrl.nyu.edu/~perlin/paper445.pdf //
	 * http://mrl.nyu.edu/~perlin/noise/
	 */
	public final static class ImprovedNoise {
		static public double noise(double x, double y, double z) {
			int X = (int) Math.floor(x) & 255, // FIND UNIT CUBE THAT
					Y = (int) Math.floor(y) & 255, // CONTAINS POINT.
					Z = (int) Math.floor(z) & 255;
			x -= Math.floor(x); // FIND RELATIVE X,Y,Z
			y -= Math.floor(y); // OF POINT IN CUBE.
			z -= Math.floor(z);
			double u = fade(x), // COMPUTE FADE CURVES
					v = fade(y), // FOR EACH OF X,Y,Z.
					w = fade(z);
			int A = p[X] + Y, AA = p[A] + Z, AB = p[A + 1] + Z, // HASH
																// COORDINATES
																// OF
					B = p[X + 1] + Y, BA = p[B] + Z, BB = p[B + 1] + Z; // THE 8
																		// CUBE
																		// CORNERS,

			return lerp(w,
					lerp(v, lerp(u, grad(p[AA], x, y, z), // AND ADD
							grad(p[BA], x - 1, y, z)), // BLENDED
							lerp(u, grad(p[AB], x, y - 1, z), // RESULTS
									grad(p[BB], x - 1, y - 1, z))), // FROM 8
					lerp(v, lerp(u, grad(p[AA + 1], x, y, z - 1), // CORNERS
							grad(p[BA + 1], x - 1, y, z - 1)), // OF CUBE
							lerp(u, grad(p[AB + 1], x, y - 1, z - 1), grad(p[BB + 1], x - 1, y - 1, z - 1))));
		}

		static double fade(double t) {
			return t * t * t * (t * (t * 6 - 15) + 10);
		}

		static double lerp(double t, double a, double b) {
			return a + t * (b - a);
		}

		static double grad(int hash, double x, double y, double z) {
			int h = hash & 15; // CONVERT LO 4 BITS OF HASH CODE
			double u = h < 8 ? x : y, // INTO 12 GRADIENT DIRECTIONS.
					v = h < 4 ? y : h == 12 || h == 14 ? x : z;
			return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
		}

		static final int p[] = new int[512], permutation[] = { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194,
				233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0,
				26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136,
				171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211,
				133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209,
				76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3,
				64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16,
				58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155,
				167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246,
				97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
				49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236,
				205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
		static {
			for (int i = 0; i < 256; i++)
				p[256 + i] = p[i] = permutation[i];
		}
	}

}
