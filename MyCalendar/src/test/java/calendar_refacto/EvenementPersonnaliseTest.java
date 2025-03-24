package calendar_refacto;

import calendar.objet.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementPersonnaliseTest {

	@Test
	public void testDescriptionFormat() {
		EventId id = new EventId(UUID.randomUUID().toString());
		TitreEvenement titre = new TitreEvenement("Voyage au Maroc");
		DateEvenement date = new DateEvenement(LocalDateTime.of(2025, 7, 15, 10, 0));
		DureeEvenement duree = new DureeEvenement(180);
		Proprietaire proprietaire = new Proprietaire(new Utilisateur("Achraf", "Achraf"));
		String type = "Voyage";

		EvenementPersonnalise event = new EvenementPersonnalise(id, titre, date, duree, proprietaire, type);

		assertEquals("Voyage : Voyage au Maroc le 15/07/2025 10:00", event.description());
	}
}
