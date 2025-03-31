package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FrequenceEvenement(@JsonProperty("jours") int jours) {
	@JsonCreator
	public FrequenceEvenement {
		if (jours <= 0) {
			throw new IllegalArgumentException("La fréquence doit être positive");
		}
	}
}
