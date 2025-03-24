package calendar_refacto;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalendarManagerTest {

	private final CalendarManager calendarManager = new CalendarManager();

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

	@Test
	public void testAjouterEventSansConflit() {
		CalendarManager calendar = new CalendarManager();
		Event evenement = new RendezVous(
				new EventId("evt-123"),
				new TitreEvenement("RDV test"),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendar.ajouterEvent(evenement);
		assertEquals(1, calendar.getTousLesEvenements().size());
	}

	@Test
	public void testAjouterEventAvecConflit() {
		CalendarManager calendar = new CalendarManager();
		Event evenement1 = new RendezVous(
				new EventId("evt-123"),
				new TitreEvenement("RDV test 1"),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		Event evenement2 = new RendezVous(
				new EventId("evt-124"),
				new TitreEvenement("RDV test 2"),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 10, 30)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendar.ajouterEvent(evenement1);
		calendar.ajouterEvent(evenement2);
		assertEquals(1, calendar.getTousLesEvenements().size());
	}

	@Test
	public void testGetTousLesEvenements() {
		CalendarManager calendar = new CalendarManager();
		Event evenement = new RendezVous(
				new EventId("evt-123"),
				new TitreEvenement("RDV test"),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendar.ajouterEvent(evenement);
		List<Event> events = calendar.getTousLesEvenements();
		assertEquals(1, events.size());
	}

	@Test
	public void testEventsDansPeriode() {
		CalendarManager calendar = new CalendarManager();
		Event evenement = new RendezVous(
				new EventId("evt-123"),
				new TitreEvenement("RDV test"),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendar.ajouterEvent(evenement);
		List<Event> events = calendar.eventsDansPeriode(
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 9, 0)),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 11, 0))
		);
		assertEquals(1, events.size());
	}

	@Test
	void testExporterEvenementJson() {
		EventId eventId = new EventId("evt-123");
		Event evenement = new RendezVous(
				eventId,
				new TitreEvenement("RDV Test"),
				new DateEvenement(LocalDateTime.of(2025, 5, 20, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendarManager.ajouterEvent(evenement);

		String nomFichier = "events_test";
		calendarManager.exporterVersJson(nomFichier);

		File fichier = new File("data/" + nomFichier + ".json");
		assertTrue(fichier.exists(), "Le fichier JSON devrait exister après l'export.");
	}

	@Test
	void testImporterEvenementJson() {
		EventId eventId = new EventId("evt-124");
		Event evenement = new RendezVous(
				eventId,
				new TitreEvenement("RDV Test Import"),
				new DateEvenement(LocalDateTime.of(2025, 5, 21, 11, 0)),
				new DureeEvenement(90),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendarManager.ajouterEvent(evenement);

		String nomFichier = "events_test.json";

		CalendarManager newCalendarManager = new CalendarManager();

		newCalendarManager.importerDepuisJson(nomFichier);

		List<Event> events = newCalendarManager.getTousLesEvenements();
		assertEquals(1, events.size(), "Il devrait y avoir un événement après l'importation.");
		assertEquals("RDV Test Import", events.get(0).getTitre().value(), "L'événement importé devrait avoir le bon titre.");
	}

}
