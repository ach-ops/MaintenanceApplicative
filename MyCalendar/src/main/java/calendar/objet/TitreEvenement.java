package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class TitreEvenement {
	private final String value;

	public TitreEvenement(String value) {
		this.value = value;
	}

	@JsonCreator
	public static TitreEvenement fromString(String value) {
		return new TitreEvenement(value);
	}

	@JsonValue
	public String value() {
		return value;
	}
}
