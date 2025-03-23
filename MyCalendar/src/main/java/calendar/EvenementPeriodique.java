package calendar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvenementPeriodique extends Event {
	private final FrequenceEvenement frequence;


	public EvenementPeriodique(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire, FrequenceEvenement frequence) {
		super(id, titre, date, duree, proprietaire);
		this.frequence = frequence;
	}

	@Override
	public String description() {
		return "Événement périodique : " + titre.value() + " tous les " + frequence.jours() + " jours.";
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
		LocalDateTime current = date.value();

		while (!current.isAfter(fin.value())) {
			if (!current.isBefore(debut.value())) {
				occurrences.add(new EvenementPeriodique(
						new EventId(id.value() + "_" + current.toLocalDate()),
						titre,
						new DateEvenement(current),
						duree,
						proprietaire,
						frequence
				));
			}
			current = current.plusDays(frequence.jours());
		}

		return occurrences;
	}
}
