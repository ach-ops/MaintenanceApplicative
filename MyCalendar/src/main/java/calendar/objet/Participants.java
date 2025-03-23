package calendar.objet;

import java.util.Objects;
public record Participants(String value) {
	public Participants {
		Objects.requireNonNull(value, "Les participants ne peuvent pas être null");
	}
}