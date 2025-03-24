package calendar;

import calendar.evenement.Event;
import calendar.evenement.EventDto;
import calendar.objet.*;

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
		return this.date.chevauche(this.duree, autre.getDate(), autre.getDuree());
	}

	@Override
	public EventDto toDto() {
		EventDto dto = toBaseDto();
		dto.lieu = this.lieu;
		if (!participants.isEmpty()) {
			dto.participants = participants.get(0);
		}
		return dto;
	}
}

