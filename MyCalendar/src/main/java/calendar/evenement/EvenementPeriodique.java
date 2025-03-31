package calendar.evenement;

import calendar.objet.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EvenementPeriodique extends Event {
	private final FrequenceEvenement frequence;

	@JsonCreator
	public EvenementPeriodique(
			@JsonProperty("id") EventId id,
			@JsonProperty("titre") TitreEvenement titre,
			@JsonProperty("date") DateEvenement date,
			@JsonProperty("duree") DureeEvenement duree,
			@JsonProperty("proprietaire") Proprietaire proprietaire,
			@JsonProperty("frequence") FrequenceEvenement frequence
	) {
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
		LocalDateTime current = date.get();

		while (!current.isAfter(fin.get())) {
			if (!current.isBefore(debut.get())) {
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
