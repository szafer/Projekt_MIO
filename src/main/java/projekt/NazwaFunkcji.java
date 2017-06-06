package projekt;

public enum NazwaFunkcji {

	KWADRATOWA("Funkcja kwadratowa"), 
	F2("F2"), 
	F3("F3"),
	MICH("Funkcja Michalewicza"),
	RAST("Rastrigin"),
	SCHW("Schwefel"),
	ROSEN("Rosenbrocks"),
	ACK("Ackley");

	String opis;

	private NazwaFunkcji(String opis) {
		this.opis = opis;
	}

	@Override
	public String toString() {
		return opis;
	}
}
