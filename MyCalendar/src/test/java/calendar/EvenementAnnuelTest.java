package calendar;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvenementAnnuelTest {

	@Test
	void descriptionDoitAfficherDateEtTitre() {
		Event event = new EvenementAnnuel(
				new EventId("anniv"),
				new TitreEvenement("Anniversaire"),
				new DateEvenement(LocalDateTime.of(2025, 5, 10, 0, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		);

		String description = event.description();

		assertTrue(description.contains("Anniversaire"));
		assertTrue(description.contains("10 mai") || description.toLowerCase().contains("mai"));
		assertTrue(description.contains("Propriétaire: Achraf"));
	}

	@Test
	void genereUneOccurrenceParAnDansLaPeriode() {
		Event annuel = new EvenementAnnuel(
				new EventId("anniv"),
				new TitreEvenement("Fête"),
				new DateEvenement(LocalDateTime.of(2020, 7, 15, 12, 0)),
				new DureeEvenement(120),
				new Proprietaire("Achraf")
		);

		List<Event> occurrences = annuel.occurrencesDansPeriode(
				new DateEvenement(LocalDateTime.of(2024, 1, 1, 0, 0)),
				new DateEvenement(LocalDateTime.of(2026, 12, 31, 23, 59))
		);

		assertEquals(3, occurrences.size());

		assertTrue(occurrences.stream().allMatch(e ->
				List.of(2024, 2025, 2026).contains(e.getDate().value().getYear())));
	}
}
