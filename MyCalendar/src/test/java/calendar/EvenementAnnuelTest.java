package calendar;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class EvenementAnnuelTest {
	@Test
	public void testCreationEvenementAnnuel() {
		EventId id = new EventId("anniv123");
		TitreEvenement titre = new TitreEvenement("Anniversaire");
		DateEvenement date = new DateEvenement(LocalDateTime.of(2024, 6, 15, 0, 0));
		DureeEvenement duree = new DureeEvenement(60);
		Proprietaire proprietaire = new Proprietaire("Alice");

		EvenementAnnuel evenement = new EvenementAnnuel(id, titre, date, duree, proprietaire);

		assertEquals("Anniversaire", evenement.getTitre().value());
		assertEquals("Événement annuel : Anniversaire chaque année le 15 juin (Propriétaire: Alice)", evenement.description());
	}

}
