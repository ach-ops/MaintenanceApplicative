package calendar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EvenementAnnuel extends Event {

	public EvenementAnnuel(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire) {
		super(id, titre, date, duree, proprietaire);
	}

	@Override
	public String description() {
		String dateFormatted = date.value().format(DateTimeFormatter.ofPattern("dd MMMM"));
		return "Événement annuel : " + titre.value() + " chaque année le " + dateFormatted + " (Propriétaire: " + proprietaire.value() + ")";
	}

	@Override
	public boolean estEnConflitAvec(Event autre) {
		List<Event> occurrences = this.occurrencesDansPeriode(autre.getDate(), autre.getDate());
		return occurrences.stream()
				.anyMatch(o -> o.getDate().chevauche(o.getDuree(), autre.getDate(), autre.getDuree()));
	}

	@Override
	public List<Event> occurrencesDansPeriode(DateEvenement debut, DateEvenement fin) {
		List<Event> occurrences = new ArrayList<>();

		int startYear = debut.value().getYear();
		int endYear = fin.value().getYear();

		for (int year = startYear; year <= endYear; year++) {
			LocalDateTime occurrenceDateTime = date.value().withYear(year);
			if (!occurrenceDateTime.isBefore(debut.value()) && !occurrenceDateTime.isAfter(fin.value())) {
				occurrences.add(new EvenementAnnuel(
						new EventId(id.value() + "_" + year),
						titre,
						new DateEvenement(occurrenceDateTime),
						duree,
						proprietaire
				));
			}
		}

		return occurrences;
	}
}
