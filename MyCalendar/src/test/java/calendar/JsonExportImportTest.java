package calendar;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonExportImportTest {
	@Test
	void peutExporterEtReimporterDesEvenements() {
		CalendarManager original = new CalendarManager();

		original.ajouterEvent(new RendezVous(
				new EventId("ev1"),
				new TitreEvenement("Dentiste"),
				new DateEvenement(LocalDateTime.of(2025, 6, 10, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		));

		String chemin = "data/test-events.json";
		original.exporterVersJson(chemin);

		CalendarManager restored = new CalendarManager();
		restored.importerDepuisJson(chemin);

		List<Event> events = restored.eventsDansPeriode(
				new DateEvenement(LocalDateTime.of(2025, 6, 1, 0, 0)),
				new DateEvenement(LocalDateTime.of(2025, 6, 30, 23, 59))
		);

		assertEquals(1, events.size());
		assertEquals("Dentiste", events.get(0).getTitre().value());
	}

	@Test
	void peutExporterEvenementEnJson() {
		CalendarManager calendar = new CalendarManager();

		calendar.ajouterEvent(new RendezVous(
				new EventId("ev-export-only"),
				new TitreEvenement("Test Export"),
				new DateEvenement(LocalDateTime.of(2025, 7, 15, 9, 30)),
				new DureeEvenement(45),
				new Proprietaire("Achraf")
		));

		String chemin = "data/test_export_event.json";
		File fichier = new File(chemin);

		if (fichier.exists()) {
			fichier.delete();
		}

		calendar.exporterVersJson("test_export_event");

		assert fichier.exists() : "Le fichier JSON n'a pas été créé !";
		assert fichier.length() > 0 : "Le fichier JSON est vide !";
	}


}
