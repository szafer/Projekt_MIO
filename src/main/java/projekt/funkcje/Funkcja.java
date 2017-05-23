package projekt.funkcje;

import org.jzy3d.maths.Range;

public interface Funkcja {
	Double wykonaj(Double wartosc);

	Double wykonaj(double x1, double x2);

	Range getRange();

	/**
	 * Parametry domyślne
	 */
	double getTMax();

	double getStChlodzenia();

	double getPrzedzialOd();

	double getPrzedzialDo();

	int getEpoka();
}
