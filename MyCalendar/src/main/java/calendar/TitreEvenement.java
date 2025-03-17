package calendar;

import java.util.Objects;

public record TitreEvenement(String value) {
	public TitreEvenement {
		Objects.requireNonNull(value, "Le titre ne peut pas être null");
	}
}