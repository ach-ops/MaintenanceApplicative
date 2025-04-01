package calendar_refacto;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.*;
import org.junit.jupiter.api.Test;

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
	void testImporterEvenementJson() {
		EventId eventId = new EventId("evt-124");
		Event evenement = new RendezVous(
				eventId,
				new TitreEvenement("RDV Importé"),
				new DateEvenement(LocalDateTime.of(2025, 5, 21, 11, 0)),
				new DureeEvenement(90),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		calendarManager.ajouterEvent(evenement);

		String nomFichier = "events_test_1.json";

		CalendarManager newCalendarManager = new CalendarManager();

		newCalendarManager.importerDepuisJson(nomFichier);

		List<Event> events = newCalendarManager.getTousLesEvenements();
		assertEquals(1, events.size(), "Il devrait y avoir un événement après l'importation.");
		assertEquals("RDV Importé", events.get(0).getTitre().value(), "L'événement importé devrait avoir le bon titre.");
	}

	@Test
	void testImportPlusieursRendezVous() {
		CalendarManager manager = new CalendarManager();
		String fichier = "test_rdv_multiple.json";

		manager.importerDepuisJson(fichier);

		List<Event> events = manager.getTousLesEvenements();
		assertEquals(3, events.size());
		assertTrue(events.stream().anyMatch(e -> e.getTitre().value().equals("RDV A")));
		assertTrue(events.stream().anyMatch(e -> e.getTitre().value().equals("RDV B")));
		assertTrue(events.stream().anyMatch(e -> e.getTitre().value().equals("RDV C")));
	}

	//Test de réimportation dans un calendrier déjà rempli
	@Test
	void testImportAvecEvenementsExistants() {
		CalendarManager manager = new CalendarManager();

		Event event = new RendezVous(
				new EventId("evt-100"),
				new TitreEvenement("RDV Original"),
				new DateEvenement(LocalDateTime.of(2025, 7, 15, 10, 0)),
				new DureeEvenement(45),
				new Proprietaire(new Utilisateur("Bob", "pass"))
		);

		manager.ajouterEvent(event);

		manager.importerDepuisJson("events_test_1.json");

		List<Event> events = manager.getTousLesEvenements();
		assertTrue(events.size() >= 2);
		assertTrue(events.stream().anyMatch(e -> e.getTitre().value().equals("RDV Original")));
		assertTrue(events.stream().anyMatch(e -> e.getTitre().value().equals("RDV Importé")));
	}

	@Test
	void testImportEvenementConflit() {
		CalendarManager calendarManager = new CalendarManager();
		Event evenement = new RendezVous(
				new EventId("evt-125"),
				new TitreEvenement("RDV Conflit"),
				new DateEvenement(LocalDateTime.of(2025, 5, 22, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire(new Utilisateur("Achraf", "mdp"))
		);

		// Ajouter l'événement initial
		calendarManager.ajouterEvent(evenement);
		assertEquals(1, calendarManager.getTousLesEvenements().size());

		// Exporter l'événement dans un fichier JSON
		String nomFichier = "event_conflit_test.json";
		calendarManager.exporterVersJson(nomFichier);

		// Réimporter le fichier JSON
		CalendarManager newCalendarManager = new CalendarManager();
		newCalendarManager.importerDepuisJson(nomFichier);

		// Vérifier que l'événement n'a pas été ajouté en raison du conflit
		List<Event> events = newCalendarManager.getTousLesEvenements();
		assertEquals(1, events.size(), "L'événement en conflit ne devrait pas être ajouté.");
		assertEquals("RDV Conflit", events.get(0).getTitre().value(), "L'événement initial devrait être présent.");
	}

}
