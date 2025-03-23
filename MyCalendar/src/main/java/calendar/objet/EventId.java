package calendar.objet;

import java.util.Objects;

public record EventId(String value) {
	public EventId {
		Objects.requireNonNull(value, "L'ID ne peut pas être null");
	}
}
