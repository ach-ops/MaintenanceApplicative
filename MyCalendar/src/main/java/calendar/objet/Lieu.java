package calendar.objet;

import java.util.Objects;

public record Lieu(String value) {
	public Lieu {
		Objects.requireNonNull(value, "Le lieu ne peut pas être null");
	}
}