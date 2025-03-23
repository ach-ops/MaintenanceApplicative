package calendar;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EvenementHebdomadaire extends Event {

	public EvenementHebdomadaire(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire) {
		super(id, titre, date, duree, proprietaire);
	}

	@Override
	public String description() {
		String jourSemaine = date.value().getDayOfWeek()
				.getDisplayName(TextStyle.FULL, Locale.FRENCH);
		return "Événement hebdomadaire : " + titre.value()
				+ " chaque " + jourSemaine
				+ " à " + date.value().toLocalTime()
				+ " (Propriétaire: " + proprietaire.value() + ")";
	}

	@Override
	public List<Event> occurrencesDansPeriode(DateEvenement debut, DateEvenement fin) {
		List<Event> occurrences = new ArrayList<>();
		LocalDateTime current = date.value();

		while (!current.isAfter(fin.value())) {
			if (!current.isBefore(debut.value())) {
				occurrences.add(new EvenementHebdomadaire(
						new EventId(id.value() + "_" + current.toLocalDate()),
						titre,
						new DateEvenement(current),
						duree,
						proprietaire
				));
			}
			current = current.plusWeeks(1);
		}

		return occurrences;
	}


	@Override
	public boolean estEnConflitAvec(Event autre) {
		List<Event> occurrences = this.occurrencesDansPeriode(autre.getDate(), autre.getDate());
		return occurrences.stream()
				.anyMatch(o -> o.getDate().chevauche(o.getDuree(), autre.getDate(), autre.getDuree()));
	}

	@Override
	public EventDto toDto() {
		return toBaseDto();
	}

}
