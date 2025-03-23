package calendar.evenement;

import calendar.RendezVous;
import calendar.Reunion;
import calendar.objet.*;

public class EventDtoFactory {

	public static Event toEvent(EventDto dto) {
		EventId id = dto.id;
		TitreEvenement titre = dto.titre;
		DateEvenement date = dto.date;
		DureeEvenement duree = dto.duree;
		Proprietaire proprietaire = dto.proprietaire;

		return switch (dto.type) {
			case "RendezVous" -> new RendezVous(id, titre, date, duree, proprietaire);
			case "Reunion" -> new Reunion(id, titre, date, duree, proprietaire, dto.participants, dto.lieu);
			case "EvenementPeriodique" -> new EvenementPeriodique(id, titre, date, duree, proprietaire, dto.frequence);
			case "EvenementHebdomadaire" -> new EvenementHebdomadaire(id, titre, date, duree, proprietaire);
			case "EvenementAnnuel" -> new EvenementAnnuel(id, titre, date, duree, proprietaire);
			default -> throw new IllegalArgumentException("Type inconnu : " + dto.type);
		};
	}
}
