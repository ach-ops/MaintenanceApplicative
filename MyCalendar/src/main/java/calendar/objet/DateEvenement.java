package calendar.objet;

import java.time.LocalDateTime;
import java.util.Objects;

public record DateEvenement(LocalDateTime value) {
	public static final DateEvenement MIN = new DateEvenement(LocalDateTime.MIN);
	public static final DateEvenement MAX = new DateEvenement(LocalDateTime.MAX);

	public DateEvenement {
		Objects.requireNonNull(value, "La date ne peut pas être null");
	}

	public boolean isBetween(DateEvenement debut, DateEvenement fin) {
		return (value.isAfter(debut.value()) || value.isEqual(debut.value())) &&
				(value.isBefore(fin.value()) || value.isEqual(fin.value()));
	}

	public boolean chevauche(DureeEvenement duree, DateEvenement autreDate, DureeEvenement autreDuree) {
		LocalDateTime fin = value.plusMinutes(duree.value());
		LocalDateTime autreFin = autreDate.value().plusMinutes(autreDuree.value());
		return value.isBefore(autreFin) && fin.isAfter(autreDate.value());
	}
}