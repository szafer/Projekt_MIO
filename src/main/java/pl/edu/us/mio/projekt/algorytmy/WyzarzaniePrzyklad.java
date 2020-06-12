package pl.edu.us.mio.projekt.algorytmy;


import java.awt.*;
import java.util.Random;

public class WyzarzaniePrzyklad {

  static final int
    l = 30,          // ile bitow/genow na chromosom
    N = 10,          // licznosc populacji
    M = 5000;        // ilosc obr. glownej petli
  final double
    c = 0.01;        // stala chlodzenia


  static Random random = new Random();
  Wykres wykres = new Wykres(1000, M, 600, 0.0, 1.0, 4,
    "Wykres, c = " + c + ", N = " + N + ", M = " + M);

  public WyzarzaniePrzyklad() {
    // tworze populacje i progi
    Genom P[] = new Genom[N];
    double t[] = new double[N];
    for (int i = 0; i < N; i++) {
      P[i] = new Genom(random.nextInt(), random.nextInt());
      t[i] = f(P[i]);
    }

    // GLOWNA PETLA
    for (int obr = 0; obr < M; obr++) {

      // mutacja-selekcja
      // im c wieksze tym bardziej dopuszczam gorszych
      // gdy c = 0 to biore zawsze lepszego
      for (int i = 0; i < N; i++) {
        Genom mutant = mutuj(P[i]);
        if (f(mutant) > t[i])
          P[i] = mutant;
      }

      // modyfikacja progow
      // gdy c = 1 to srednio progi sie nie zmieniaja
      double delta = 0; // to minus (suma wzrostow)
      for (int i = 0; i < N; i++) {
        double fPi = f(P[i]);
        if (fPi > t[i]) {
          delta += (t[i] - fPi);
          t[i] = fPi;
        }
      }

      // chlodzenie populacji
      // delta < 0
      for (int i = 0; i < N; i++)
        t[i] += (c * delta / N);

      // rysuje na wykresie besciaka i srednia wartosc
      double suma = 0; // suma wart. f. wszystkich osobnikow
      double max = f(P[0]);
      for (int i = 0; i < N; i++) {
        double fPi = f(P[i]);
        suma += fPi;
        if (fPi > max)
          max = fPi;
      }

      wykres.nastepny(0, suma / N);
      wykres.nastepny(1, max);
      if (max == 1.0) {
        System.out.println("Koniec w " + obr + " obrocie.");
        wykres.nastepny(2, 1.0);
        //wykres.nastepny(3, 1.0);
      } else {
        double ile9 = Math.floor(-log10(1.0 - max));
        wykres.nastepny(2, ile9 / 20.0);
        //wykres.nastepny(3, -log10(1.0 - max) / 20.0);
        System.out.println("Max = " + max + ", dziewiatek: " + ile9);
      }

    } // GLOWNA PETLA
  } // Wyzarzanie()

  double log10(double x)
  {
    final double log_10 = Math.log(10);
    return Math.log(x) / log_10;
  }

  // zlozenie funkcji f_przyst() i fenotyp()
  double f(Genom g)
  {
    //return (g.fenx / 200.0 + 0.5);
    double pom1 = (g.fenx() * g.fenx()) + (g.feny() * g.feny());
    double pom2 = Math.sin(Math.sqrt(pom1)),
           pom3 = 1.0 + (pom1 / 1000.0);
    return 0.5 - (((pom2 * pom2) - 0.5) / (pom3 * pom3));
  }

  final double odwr_p = l;       // czyli iloscf genow
  final double ln_q =
    Math.log(odwr_p - 1) - Math.log(odwr_p);
    // tj. ln_q = ln(q) = ln(1 - p) = ln(1 - (1/odwr_p)) =
    //          = ln((odwr_p - 1) / odwr_p) = ln(odwr_p - 1) - ln(odwr_p)

  // Zwraca k z prawd. (1-p)^k * p
  int losujWykl() {
    double los;
    los = random.nextDouble();
    if (los == 0.0)
      los = 1.0;
    return (int) (Math.log(los) / ln_q);
  }

  private Genom mutuj(Genom g) {
    return new Genom(mutuj_chr(g.x()), mutuj_chr(g.y()));
  }

  // tworzy genom powstajacy z mutacji zadanego
  private int mutuj_chr(int genom)
  { // mutacja rownolegla z prawdopod. p
    // obliczam maske mutowania
    int maska = 0, i = 0;
    for (;;) {
      i += losujWykl();
      if (i >= l)
        break;
      maska |= (1 << i);
      if (i == l - 1)
        break;
      i++;
    }
    return genom ^ maska;
  }

  private int __mutuj_chr(int genom)
  { // mutacja jako dodanie/odjecie liczby
    final int r = (1 << 28);
    return genom + random.nextInt(2 * r + 1) - r;
  }

  public static void main(String[] args) {
    new WyzarzaniePrzyklad();
  }
}

class Genom {
  private int x, y;            // genotyp (chromosomy)
  private double fenx, feny;   // fenotyp

  public Genom(int x, int y) {
    this.x = x;
    this.y = y;
    if (WyzarzaniePrzyklad.l < 32) {
      this.x &= ((1 << WyzarzaniePrzyklad.l) - 1);
      this.y &= ((1 << WyzarzaniePrzyklad.l) - 1);
    }
    fenx = fenotyp(this.x);
    feny = fenotyp(this.y);
  }

  // podaje liczbe z przedzialu [0, 1) odpowiadajaca danemu genotypowi
  private double fenotyp(int chromosom) // chromosom
  {
    double wyn = chromosom;

    wyn /= (1L << WyzarzaniePrzyklad.l);
    // wyn == [-0.5, 0.5), jezeli ile_genow == 32
    // wyn == [0, 1), wpp.

    if (wyn < 0)           // to moze sie zdarzyc wtw ile_genow == 32
      wyn += 1.0;

    if (wyn < 0 || wyn > 1)
      System.exit(12);

    return (wyn - 0.5) * 200.0;    // -100..100
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  public double fenx() {
    return fenx;
  }

  public double feny() {
    return feny;
  }
}









// Okno i Wykres - wspolne do wszystkich zadan

class Okno extends Frame {
  Wykres wykres;

  public Okno(String tytul, Wykres w)
  {
    super(tytul);
    wykres = w;

    setLayout(new BorderLayout());
    add("North", new Label(tytul, Label.CENTER));
    add("South", this.wykres);
    // Ustawiam wiecej niz preferredSize, bo tytul okienka i brzegi
    setSize(10 + getPreferredSize().width, 50 + getPreferredSize().height);
    setVisible(true);
  }

  public boolean handleEvent(Event evt)
  {
    if (evt.id == Event.WINDOW_DESTROY)
      System.exit(0);
    return false;
  }
}

// ulepszam wykres o mozliwosc rysowania wiekszej ilosci petli niz
// jest pikseli po wsp. x
class Wykres extends Canvas {
    final Color kolory[] = {
    new Color(0, 0, 0xff),
    new Color(0xf0, 0xb0, 0),
    new Color(0, 0x90, 0),
    new Color(0xe0, 0, 0),
    new Color(0, 0xff, 0),
  };

  int wym_x, wym_y,       // wymiary okna wykresu
      ile_kr;             // liczba krokow po wspolrzednej x
  double y_od, y_do;      // zakres zmian wartosci
  Okno okno;
  int ost_x[],            // indeks ostatnio dodanego punktu
      ile_wykr;           // ile wykresow rysuje naraz (roznymi kol)
  double wartosci[][];

  public Wykres(int wym_x, int ile_kr, int wym_y, double y_od, double y_do,
      int ile_wykr, String tytul)
  {
    super();
    this.wym_x = wym_x;
    this.wym_y = wym_y;
    this.y_od = y_od;
    this.y_do = y_do;
    this.ile_wykr = ile_wykr;
    this.ile_kr = ile_kr;
    setSize(wym_x + 2, wym_y + 2);
    wartosci = new double[ile_wykr][ile_kr];
    ost_x = new int[ile_wykr];
    for (int i = 0; i < ile_wykr; i++)
      ost_x[i] = -1;
    okno = new Okno(tytul, this);
  }

  public void paint(Graphics g)
  {
    g.setColor(Color.black);
    g.drawRect(0, 0, wym_x + 1, wym_y + 1);
    g.setColor(Color.white);
    g.fillRect(1, 1, wym_x, wym_y);

    for (int w = 0; w < ile_wykr; w++) {
      g.setColor(kolory[w % kolory.length]);
      for (int x = 0; x < ost_x[w]; x++)
        g.drawLine(skalujX(x),
                   skalujY(wartosci[w][x]),
                   skalujX(x + 1),
                   skalujY(wartosci[w][x + 1]));
    }
  }

  // dostaje numer obroru, a ma wyznaczyc wspolrzedna x na ekranie
  private int skalujX(int x) {
    //return (int) ((double) x * (double) wym_x / (double) ile_kr);
    return x * wym_x / ile_kr;
  }

  // na postawie wartosc funkcji y wyznacza wsp. y na ekranie
  private int skalujY(double y)
  {
    if (y < y_od || y > y_do)
      System.out.println("skalujY: Uwaga! Wartosc poza zakresem");

    double zakres = y_do - y_od,
           wymiar = wym_y;
    return (int) (wymiar - y / zakres * wymiar) + 1;
  }

  public void nastepny(int nr_wykr, double y)
  {
    if (ost_x[nr_wykr] == ile_kr - 1) {
      System.out.println("Juz nie rysuje");
      return;
    }
    ost_x[nr_wykr]++;

    wartosci[nr_wykr][ost_x[nr_wykr]] = y;
    if (ost_x[nr_wykr] != 0) {
      Graphics g = getGraphics();
      g.setColor(kolory[nr_wykr % kolory.length]);
      g.drawLine(skalujX(ost_x[nr_wykr] - 1),
                 skalujY(wartosci[nr_wykr][ost_x[nr_wykr] - 1]),
                 skalujX(ost_x[nr_wykr]),
                 skalujY(wartosci[nr_wykr][ost_x[nr_wykr]]));
    }
  }
}
