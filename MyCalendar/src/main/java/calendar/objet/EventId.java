package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class EventId {
	private final String value;

	public EventId(String value) {
		this.value = value;
	}

	@JsonCreator
	public static EventId fromString(String value) {
		return new EventId(value);
	}

	@JsonValue
	public String value() {
		return value;
	}
}
