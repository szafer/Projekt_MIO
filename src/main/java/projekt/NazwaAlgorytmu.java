ackage projekt;

public enum NazwaAlgorytmu {

	WYZARZANIE("Symulowane wyżarzanie"), 
	DYCH("Dychotomia"), 
	GOLDER_RATIO("Złoty podział");

	String opis;
	private NazwaAlgorytmu(String opis) {
		this.opis = opis;
	}

	@Override
	public String toString() {
		return opis;
	}
	
}
