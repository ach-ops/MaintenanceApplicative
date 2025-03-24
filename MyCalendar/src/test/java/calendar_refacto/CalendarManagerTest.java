package calendar_refacto;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalendarManagerTest {
	@Test
	public void testSupprimerEvenementParIdFonctionne() {
		CalendarManager calendar = new CalendarManager();
		EventId id = new EventId("evt-123");

		Event evenement = new RendezVous(
				id,
				new TitreEvenement("RDV test"),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendar.ajouterEvent(evenement);
		assertEquals(1, calendar.getTousLesEvenements().size());

		calendar.supprimerEvent(id);

		assertTrue(calendar.getTousLesEvenements().isEmpty());
	}

}
