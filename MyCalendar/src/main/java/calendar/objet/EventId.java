package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class EventId {
	private final String value;

	@JsonCreator
	public EventId(@JsonProperty("value") String value) {
		this.value = value;
	}

	@JsonValue
	public String value() {
		return value;
	}
}

