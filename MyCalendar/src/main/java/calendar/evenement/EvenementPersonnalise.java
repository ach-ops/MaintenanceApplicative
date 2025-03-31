package calendar.evenement;

import calendar.objet.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EvenementPersonnalise extends Event {

	private final String typePerso;

	@JsonCreator
	public EvenementPersonnalise(
			@JsonProperty("id") EventId id,
			@JsonProperty("titre") TitreEvenement titre,
			@JsonProperty("date") DateEvenement date,
			@JsonProperty("duree") DureeEvenement duree,
			@JsonProperty("proprietaire") Proprietaire proprietaire,
			@JsonProperty("typePerso") String typePerso
	) {
		super(id, titre, date, duree, proprietaire);
		this.typePerso = typePerso;
	}

	@Override
	public String description() {
		return typePerso + " : " + titre.value() + " le " + date.toString();
	}

	@Override
	public boolean estEnConflitAvec(Event autre) {
		return super.estEnConflitAvec(autre);
	}
}
