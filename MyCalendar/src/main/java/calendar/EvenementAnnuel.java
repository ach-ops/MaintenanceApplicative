package calendar;

import java.time.format.DateTimeFormatter;

public class EvenementAnnuel extends Event {
	public EvenementAnnuel(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire) {
		super(id, titre, date, duree, proprietaire);
	}

	@Override
	public String description() {
		String dateFormatted = date.value().format(DateTimeFormatter.ofPattern("dd MMMM"));
		return "Événement annuel : " + titre.value() + " chaque année le " + dateFormatted + " (Propriétaire: " + proprietaire.value() + ")";
	}

	public TitreEvenement getTitre() {
		return titre;
	}
}
