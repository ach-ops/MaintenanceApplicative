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

		boolean supprime = calendar.supprimerEvent(id);
		assertTrue(supprime, "L'événement aurait dû être supprimé avec succès.");

		assertTrue(calendar.getTousLesEvenements().isEmpty());
	}

	@Test
	public void testSupprimerEvenementAvecIdInexistantRetourneFalse() {
		CalendarManager calendar = new CalendarManager();
		EventId inexistant = new EventId("id-faux");

		boolean result = calendar.supprimerEvent(inexistant);

		assertEquals(false, result, "Supprimer un ID inexistant doit retourner false.");
	}


}
