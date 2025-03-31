package calendar.objet;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Lieu(@JsonProperty("value") String value) {
	@JsonCreator
	public Lieu {
		Objects.requireNonNull(value, "Le lieu ne peut pas être null");
	}
}
