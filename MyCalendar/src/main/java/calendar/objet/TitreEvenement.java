package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class TitreEvenement {
	private final String value;

	@JsonCreator
	public TitreEvenement(@JsonProperty("value") String value) {
		this.value = value;
	}

	@JsonValue
	public String value() {
		return value;
	}
}
