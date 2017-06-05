package projekt.algorytmy;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jzy3d.plot3d.primitives.Point;

import javafx.scene.chart.XYChart;
import projekt.funkcje.Funkcja;
import projekt.funkcje.FunkcjaKwadratowa;

public class Wyzarzanie implements Algorytm {
    private static final double euler = 2.718281828459;
    private static final double boltzman = 1.380;
    private int epoka = 0; // ilosc obr. glownej petli
    private Double stalaChlodzenia = 0.85; // stala chlodzenia
    private Funkcja funkcja;
    private double temperaturaMax = 0;
    private double przedzial_od;
    private double przedzial_do;
    private int zmianaSasiad = 0;
    private static Random random = new Random();
    private int dzielnik = 0;

    private ConcurrentLinkedQueue<XYChart.Data<Double, Double>> data = new ConcurrentLinkedQueue<XYChart.Data<Double, Double>>();

    public Wyzarzanie(Funkcja funkcja, double temperaturaMax, double stalaChlodzenia, double przedzial_od,
        double przedzial_odo, int epoka) {
        this.funkcja = funkcja;
        this.temperaturaMax = temperaturaMax;
        this.stalaChlodzenia = stalaChlodzenia;
        this.przedzial_od = przedzial_od;
        this.przedzial_do = przedzial_odo;
        this.epoka = epoka;
        System.out.println(
            temperaturaMax + " " + stalaChlodzenia + " " + przedzial_od + " " + przedzial_do + " " + epoka);

        if (epoka / 100 > 100)
            if (epoka / 500 > 100)
                if (epoka / 1000 > 100)
                    if (epoka / 2000 > 100)
                        if (epoka / 5000 > 100)
                            dzielnik = 5000;
                        else
                            dzielnik = epoka / 5000;
                    else
                        dzielnik = epoka / 2000;
                else
                    dzielnik = epoka / 1000;
            else
                dzielnik = epoka / 500;
        else
            dzielnik = epoka / 100;
    }

    @Override
    public Boolean wykonaj() {
        Punkt punkt = new Punkt();
        Punkt punktBis = new Punkt();
        int przedzial = (int) (przedzial_do - przedzial_od);
        int losP = (int) (random.nextInt(przedzial) + przedzial_od);
        punkt.setX(losP);
        losP = (int) (random.nextInt(przedzial) + przedzial_od);
        punkt.setY(losP);

        // Losowy wybór punktu startowego
        // int licz = (int) (random.nextInt((int) (Math.abs((int) ((przedzial_do
        // - 1) - (przedzial_od + 1)))
        // - (Math.abs(przedzial_od) - 2))));
        // int licz = (int) (random.nextInt((int) (Math.abs((int) ((przedzial_do
        // - 1) - (przedzial_od + 1)))
        // - (Math.abs(przedzial_od) - 2))));
        // Double x = new Double(licz);
        // licz = (int) (random.nextInt((int) (Math.abs((int) ((przedzial_do -
        // 1) - (przedzial_od + 1)))
        // - (Math.abs(przedzial_od) - 2))));
        // Double y = new Double(licz);
        // punkt.setX(x);
        // punkt.setY(y);

        Double temperaturaLokalna = temperaturaMax;
        Double exp = 0d;
        // GLOWNA PETLA
        for (int obr = 0; obr < epoka; obr++) {
            punktBis = sasiad(punkt);

            Double delta = new Double(0);
            // Wyznaczenie różnicy wartości funkcji w punkcie s i sBis
            delta = (funkcja.wykonaj(punktBis.x, punktBis.y) - funkcja.wykonaj(punkt.x, punkt.y));
            if (delta <= 0) {
                punkt.setX(punktBis.getX());
                punkt.setY(punktBis.getY());
            } else {
                Double los = random.nextDouble();
                if (los == 0.0) {
                    los = 1.0;
                }
                // TODO ??
                exp = Math.pow(euler, -1 * delta / (temperaturaLokalna * boltzman));
                exp = Math.pow(euler, -1 * delta / temperaturaLokalna);
                if (los < exp) {
                    punkt.setX(punktBis.getX());
                    punkt.setY(punktBis.getY());
                }
            }
            try {
                if (obr < 1000 || obr % dzielnik == 0)
                    data.add(new XYChart.Data(obr, new Double(funkcja.wykonaj(punkt.x, punkt.y))));
            } catch (Exception e) {

            }

            System.out.format("s.x %.10f s.y %.10f  w %d obrocie wartosc funkcji %.10f  temperatura  %.10f exp %.10f %n", punkt.getX(), punkt.getY(),
                obr, funkcja.wykonaj(punkt.x, punkt.y), temperaturaLokalna, exp);
            if (obr % 1000 == 0) {
                // funkcja zmiany temperatury
//                if (temperaturaLokalna < 1)
//                    temperaturaLokalna = temperaturaMax / 100;
                temperaturaLokalna = stalaChlodzenia * temperaturaLokalna;
            }
        }
        System.out.println("koniec ");
        return true;
    }

    public Boolean wykonaj(int fun) {

        // Losowy wybór punktu startowego
        int licz = (int) (random.nextInt((int) ((przedzial_do - 1) - (przedzial_od + 1)))
            - (Math.abs(przedzial_od) - 2));
        Double s = new Double(licz);
        Double temperaturaLokalna = temperaturaMax;
        Double sBis = new Double(0);
        Double poprzedniaWartosc = 0d;
        Double aktualnaWartosc = 0d;
        Double delta = new Double(0);

        // GLOWNA PETLA
        for (int obr = 0; obr < epoka; obr++) {
            sBis = sasiad(s);

            // Wyznaczenie różnicy wartości funkcji w punkcie s i sBis
            aktualnaWartosc = funkcja.wykonaj(sBis);
            poprzedniaWartosc = funkcja.wykonaj(s);
            delta = (aktualnaWartosc - poprzedniaWartosc);
            if (delta < 0) {
                s = sBis;
            } else {
                Double los = random.nextDouble();
                if (los == 0.0) {
                    los = 1.0;
                }
                // TODO ??
                Double exp = Math.pow(euler, -1 * delta / (temperaturaLokalna * boltzman));
                exp = Math.pow(euler, -1 * delta / temperaturaLokalna);
                if (los < exp) {
                    s = sBis;
                }
            }
            try {
                if (obr < 1000 || obr % dzielnik == 0)
                    data.add(new XYChart.Data(obr, poprzedniaWartosc));
            } catch (Exception e) {

            }
            System.out.format("s %.10f w %d obrocie  wartosc funkcji %.10f %n", s, obr, funkcja.wykonaj(s));

            // funkcja zmiany temperatury
            temperaturaLokalna = stalaChlodzenia * temperaturaLokalna;
        }
        System.out.println("koniec ");
        return true;
    }

    private Punkt sasiad(Punkt punkt) {
        Double i = random.nextDouble() / 10;
        Double j = random.nextDouble() / 10;

        Punkt sasiad = new Punkt();
        sasiad.y = punkt.y;
        sasiad.x = punkt.x;

        if (sasiad.x - i <= przedzial_do && sasiad.x - i >= przedzial_od && zmianaSasiad == 0) {
            sasiad.x = sasiad.x - i;
        }
        if (sasiad.y - j <= przedzial_do && sasiad.y - j >= przedzial_od && zmianaSasiad == 0) {
            sasiad.y = sasiad.y - j;

        }
        if (sasiad.x + i <= przedzial_do && sasiad.x + i >= przedzial_od && zmianaSasiad == 1) {
            sasiad.x = sasiad.x + i;
        }
        if (sasiad.y + j <= przedzial_do && sasiad.y + j >= przedzial_od && zmianaSasiad == 1) {
            sasiad.y = sasiad.y + j;

        }
        if (zmianaSasiad == 0) {
            zmianaSasiad++;
        } else {
            zmianaSasiad--;
        }
        if (sasiad.x > przedzial_do || sasiad.y > przedzial_do) {
            System.out.println("===============================================================");
        }
        return sasiad;
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

    @Override
    public ConcurrentLinkedQueue<XYChart.Data<Double, Double>> getData() {
        return data;
    }

    @Override
    public void run() {
        if (funkcja instanceof FunkcjaKwadratowa)
            wykonaj(1);
        else
            wykonaj();
    }

    @Override
    public void ustawParametry(double temperaturaMax, double stalaChlodzenia, double przedzial_od, double przedzial_odo,
        int epoka) {
        this.temperaturaMax = temperaturaMax;
        this.stalaChlodzenia = stalaChlodzenia;
        this.przedzial_od = przedzial_od;
        this.przedzial_do = przedzial_odo;
        this.epoka = epoka;
        System.out.println(
            temperaturaMax + " " + stalaChlodzenia + " " + przedzial_od + " " + przedzial_do + " " + epoka);

        if (epoka / 100 > 100)
            if (epoka / 500 > 100)
                if (epoka / 1000 > 100)
                    if (epoka / 2000 > 100)
                        if (epoka / 5000 > 100)
                            dzielnik = 5000;
                        else
                            dzielnik = epoka / 5000;
                    else
                        dzielnik = epoka / 2000;
                else
                    dzielnik = epoka / 1000;
            else
                dzielnik = epoka / 500;
        else
            dzielnik = epoka / 100;
    }
}
