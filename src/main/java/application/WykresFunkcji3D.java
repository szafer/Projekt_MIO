package application;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import projekt.funkcje.Funkcja;

public class WykresFunkcji3D extends AbstractAnalysis {
	private final double PI = 3.14159265;
	private int n = 2;
	private int m = 2;
	private Double s = new Double(0);
	private Funkcja f;
	private Range range;

	public WykresFunkcji3D(Funkcja f, Range range) {
		super();
		this.f = f;
		this.range = range;
	}

	@Override
	public void init() {
		// Define a function to plot
		Mapper mapper = new Mapper() {
			@Override
			public double f(double x, double y) {
				return f.wykonaj(x, y);
			}
		};

		// Define range and precision for the function to plot
//		Range range = new Range(-3, 3);
		int steps = 80;

		// Create the object to represent the function over the given range.
		final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(),
				surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(false);

		// Create a chart
		chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
		chart.getScene().getGraph().add(surface);
	}
}
