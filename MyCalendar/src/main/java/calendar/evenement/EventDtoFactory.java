package calendar.evenement;

import calendar.objet.RendezVous;
import calendar.objet.Reunion;
import calendar.objet.*;

import java.util.List;

public class EventDtoFactory {

	public static Event toEvent(EventDto dto) {
		EventId id = dto.id;
		TitreEvenement titre = dto.titre;
		DateEvenement date = dto.date;
		DureeEvenement duree = dto.duree;
		Proprietaire proprietaire = dto.proprietaire;

		return switch (dto.type) {
			case "RendezVous" -> new RendezVous(id, titre, date, duree, proprietaire);
			case "Reunion" -> new Reunion(id, titre, date, duree, proprietaire, dto.lieu, List.of(dto.participants));
			case "EvenementPeriodique" -> new EvenementPeriodique(id, titre, date, duree, proprietaire, dto.frequence);
			case "EvenementHebdomadaire" -> new EvenementHebdomadaire(id, titre, date, duree, proprietaire);
			case "EvenementAnnuel" -> new EvenementAnnuel(id, titre, date, duree, proprietaire);
			default -> throw new IllegalArgumentException("Type inconnu : " + dto.type);
		};
	}
}
