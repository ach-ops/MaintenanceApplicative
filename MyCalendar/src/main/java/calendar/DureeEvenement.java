package calendar;

public record DureeEvenement(int value) {
	public DureeEvenement {
		if (value <= 0) throw new IllegalArgumentException("La durée doit être positive");
	}
}