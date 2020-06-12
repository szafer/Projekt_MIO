package pl.edu.us.mio.projekt.funkcje;

import org.jzy3d.maths.Range;

public interface Funkcja {
    Double wykonaj(Double wartosc);

    Double wykonaj(double x1, double x2);

    Range getRange();

    /**
     * Parametry domyślne
     */
    double getTMax();

    /**
     * Domyślny stopień chłodzenia dla SA
     * @return
     */
    double getStChlodzenia();

    /**
     * Domyślne ograniczenie dolne funkcji
     * @return
     */
    double getPrzedzialOd();

    /**
     * Domyślne ograniczenie górne funkcji
     * @return
     */
    double getPrzedzialDo();

    /**
     * Domyślna liczba epok (iteracji)
     * @return
     */
    int getEpoka();
}
