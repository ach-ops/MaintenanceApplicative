package calendar.objet;

public record QuantiteEvenements(int value) {
	public QuantiteEvenements {
		if (value < 0) throw new IllegalArgumentException("Quantité négative interdite.");
	}
}
