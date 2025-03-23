package calendar;

import org.junit.jupiter.api.Test;

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

		String chemin = "test-events.json";
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

}
