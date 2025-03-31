package calendar.objet;

import calendar.evenement.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RendezVous extends Event {
	@JsonCreator
	public RendezVous(
			@JsonProperty("id") EventId id,
			@JsonProperty("titre") TitreEvenement titre,
			@JsonProperty("date") DateEvenement date,
			@JsonProperty("duree") DureeEvenement duree,
			@JsonProperty("proprietaire") Proprietaire proprietaire) {
		super(id, titre, date, duree, proprietaire);
	}

	@Override
	public String description() {
		return "Rendez-vous : " + titre.value() + " le " + date.get() +
				" (Propriétaire: " + proprietaire.getUtilisateur().identifiant() + ")";
	}

	@Override
	public boolean estEnConflitAvec(Event autre) {
		return super.estEnConflitAvec(autre);
	}

}