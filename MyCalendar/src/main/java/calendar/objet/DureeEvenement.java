package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DureeEvenement(@JsonProperty("value") int value) {
	@JsonCreator
	public DureeEvenement {
		if (value <= 0) throw new IllegalArgumentException("La durée doit être positive");
	}
}
