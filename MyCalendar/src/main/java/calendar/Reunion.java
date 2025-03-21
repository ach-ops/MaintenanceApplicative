package calendar;

import java.util.Objects;

public class Reunion extends Event {
	private final Participants participants;
	private final Lieu lieu;

	public Reunion(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire, Participants participants, Lieu lieu) {
		super(id, titre, date, duree, proprietaire);
		this.participants = Objects.requireNonNull(participants);
		this.lieu = Objects.requireNonNull(lieu);
	}

	@Override
	public String description() {
		return "Réunion : " + titre.value() + " avec " + participants.value() + " à " + lieu.value() + " (Propriétaire: " + proprietaire.value() + ")";
	}
}