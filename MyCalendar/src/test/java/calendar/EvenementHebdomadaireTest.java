package calendar;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvenementHebdomadaireTest {

	@Test
	void descriptionDoitAfficherJourEtTitre() {
		Event event = new EvenementHebdomadaire(
				new EventId("ev001"),
				new TitreEvenement("Salle"),
				new DateEvenement(LocalDateTime.of(2025, 3, 24, 18, 0)),
				new DureeEvenement(90),
				new Proprietaire("Achraf")
		);

		String desc = event.description();

		assertTrue(desc.contains("Salle"));
		assertTrue(desc.toLowerCase().contains("lundi"));
	}

	@Test
	void descriptionContientJourCorrectLundi() {
		Event event = new EvenementHebdomadaire(
				new EventId("ev-lundi"),
				new TitreEvenement("Cours Yoga"),
				new DateEvenement(LocalDateTime.of(2025, 3, 24, 9, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		);

		String desc = event.description();
		assertTrue(desc.toLowerCase().contains("lundi"));
		assertTrue(desc.contains("Cours Yoga"));
		assertTrue(desc.contains("09:00"));
	}

	@Test
	void evenementHebdomadaireGenereDesOccurrences() {
		Event original = new EvenementHebdomadaire(
				new EventId("ev-hebd"),
				new TitreEvenement("Training"),
				new DateEvenement(LocalDateTime.of(2025, 3, 3, 18, 0)), // Lundi
				new DureeEvenement(90),
				new Proprietaire("Achraf")
		);

		List<Event> occ = original.occurrencesDansPeriode(
				new DateEvenement(LocalDateTime.of(2025, 3, 1, 0, 0)),
				new DateEvenement(LocalDateTime.of(2025, 3, 20, 23, 59))
		);

		assertEquals(3, occ.size());
		assertTrue(occ.get(0).description().contains("Training"));
	}


}
