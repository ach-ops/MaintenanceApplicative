package calendar;

public record FrequenceEvenement(int jours) {
	public FrequenceEvenement {
		if (jours <= 0) {
			throw new IllegalArgumentException("La fréquence doit être positive");
		}
	}
}
