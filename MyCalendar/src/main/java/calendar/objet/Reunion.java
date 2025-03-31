package calendar.objet;

import calendar.evenement.Event;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Reunion extends Event {

	private final Lieu lieu;
	private final List<Participants> participants;

	@JsonCreator
	public Reunion(
			@JsonProperty("id") EventId id,
			@JsonProperty("titre") TitreEvenement titre,
			@JsonProperty("date") DateEvenement date,
			@JsonProperty("duree") DureeEvenement duree,
			@JsonProperty("proprietaire") Proprietaire proprietaire,
			@JsonProperty("lieu") Lieu lieu,
			@JsonProperty("participants") List<Participants> participants
	) {
		super(id, titre, date, duree, proprietaire);
		this.lieu = lieu;
		this.participants = participants;
	}

	@Override
	public String description() {
		return "Réunion : " + titre.value() +
				" à " + lieu.value() +
				" le " + date +
				" avec " + participants.size() + " participant(s)";
	}

	@Override
	public boolean estEnConflitAvec(Event autre) {
		return super.estEnConflitAvec(autre);
	}

}

