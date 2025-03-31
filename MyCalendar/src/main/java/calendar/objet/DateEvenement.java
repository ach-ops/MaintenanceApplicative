package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateEvenement {

	private final LocalDateTime valeur;

	public DateEvenement(LocalDateTime valeur) {
		this.valeur = valeur;
	}

	@JsonCreator
	public static DateEvenement fromString(String value) {
		return new DateEvenement(LocalDateTime.parse(value));
	}

	public boolean estAvant(DateEvenement autre) {
		return valeur.isBefore(autre.valeur);
	}

	public boolean estApres(DateEvenement autre) {
		return valeur.isAfter(autre.valeur);
	}

	@JsonValue
	public LocalDateTime get() {
		return valeur;
	}

	@Override
	public String toString() {
		return valeur.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

	public boolean chevauche(DureeEvenement duree, DateEvenement autreDate, DureeEvenement autreDuree) {
		LocalDateTime debut1 = this.get();
		LocalDateTime fin1 = debut1.plusMinutes(duree.value());

		LocalDateTime debut2 = autreDate.get();
		LocalDateTime fin2 = debut2.plusMinutes(autreDuree.value());

		return !fin1.isBefore(debut2) && !fin2.isBefore(debut1);
	}

	public boolean isBetween(DateEvenement debut, DateEvenement fin) {
		return !this.estAvant(debut) && !this.estApres(fin);
	}
}
