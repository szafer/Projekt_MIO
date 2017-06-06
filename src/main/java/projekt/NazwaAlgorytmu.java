package projekt;

public enum NazwaAlgorytmu {

    WYZARZANIE("Symulowane wyżarzanie", "SA"), DYCH("Dychotomia", "DYCH"), GOLDER_RATIO("Złoty podział", "GR");

    String opis;
    String nazwa;

    private NazwaAlgorytmu(String opis, String nazwa) {
        this.opis = opis;
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

}
