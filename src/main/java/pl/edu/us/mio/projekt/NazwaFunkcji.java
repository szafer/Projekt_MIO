package pl.edu.us.mio.projekt;

public enum NazwaFunkcji {

	KWADRATOWA("Funkcja kwadratowa"), 
	F2("(x-5)^2"), 
	F3("x^3-x"),
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
