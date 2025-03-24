package calendar.objet;

import calendar.evenement.Event;

import java.util.Objects;
import java.util.List;


public class Reunion extends Event {

	private final Lieu lieu;
	private final List<Participants> participants;

	public Reunion(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree,
				   Proprietaire proprietaire, Lieu lieu, List<Participants> participants) {
		super(id, titre, date, duree, proprietaire);
		this.lieu = Objects.requireNonNull(lieu);
		this.participants = Objects.requireNonNull(participants);
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

