package calendar;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarManagerTest {

	@Test
	void neDoitPasAjouterEvenementEnConflit() {
		CalendarManager calendar = new CalendarManager();

		Event rdv1 = new RendezVous(
				new EventId("rdv1"),
				new TitreEvenement("RDV"),
				new DateEvenement(LocalDateTime.of(2025, 3, 23, 14, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		);

		Event rdv2 = new RendezVous(
				new EventId("rdv2"),
				new TitreEvenement("RDV 2"),
				new DateEvenement(LocalDateTime.of(2025, 3, 23, 14, 30)),
				new DureeEvenement(30),
				new Proprietaire("Achraf")
		);

		calendar.ajouterEvent(rdv1);
		calendar.ajouterEvent(rdv2);

		List<Event> result = calendar.eventsDansPeriode(
				new DateEvenement(LocalDateTime.of(2025, 3, 23, 0, 0)),
				new DateEvenement(LocalDateTime.of(2025, 3, 23, 23, 59))
		);

		assertEquals(1, result.size());
	}

	@Test
	void retourneLesOccurrencesDansUnePeriode() {
		CalendarManager calendar = new CalendarManager();

		Event hebdo = new EvenementHebdomadaire(
				new EventId("ev-hebdo"),
				new TitreEvenement("Cours Yoga"),
				new DateEvenement(LocalDateTime.of(2025, 3, 3, 14, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		);

		Event annuel = new EvenementAnnuel(
				new EventId("ev-annuel"),
				new TitreEvenement("Fête nationale"),
				new DateEvenement(LocalDateTime.of(2020, 7, 14, 0, 0)),
				new DureeEvenement(1440),
				new Proprietaire("Etat")
		);

		calendar.ajouterEvent(hebdo);
		calendar.ajouterEvent(annuel);

		List<Event> result = calendar.eventsDansPeriode(
				new DateEvenement(LocalDateTime.of(2025, 3, 1, 0, 0)),
				new DateEvenement(LocalDateTime.of(2025, 7, 31, 23, 59))
		);

		assertFalse(result.isEmpty());
		assertTrue(result.stream().anyMatch(e -> e.getTitre().value().contains("Yoga")));
		assertTrue(result.stream().anyMatch(e -> e.getTitre().value().contains("Fête")));
	}

	@Test
	void retourneTousLesEvenementsDansUnePeriode() {
		CalendarManager calendar = new CalendarManager();

		Event rdv = new RendezVous(
				new EventId("ev-rdv"),
				new TitreEvenement("Dentiste"),
				new DateEvenement(LocalDateTime.of(2025, 6, 12, 10, 0)),
				new DureeEvenement(30),
				new Proprietaire("Achraf")
		);

		Event hebdo = new EvenementHebdomadaire(
				new EventId("ev-hebdo"),
				new TitreEvenement("Sport"),
				new DateEvenement(LocalDateTime.of(2025, 6, 2, 18, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		);

		Event annuel = new EvenementAnnuel(
				new EventId("ev-annuel"),
				new TitreEvenement("Anniversaire"),
				new DateEvenement(LocalDateTime.of(2020, 7, 1, 0, 0)),
				new DureeEvenement(1440),
				new Proprietaire("Famille")
		);

		calendar.ajouterEvent(rdv);
		calendar.ajouterEvent(hebdo);
		calendar.ajouterEvent(annuel);

		DateEvenement debut = new DateEvenement(LocalDateTime.of(2025, 6, 1, 0, 0));
		DateEvenement fin = new DateEvenement(LocalDateTime.of(2025, 7, 31, 23, 59));

		List<Event> result = calendar.eventsDansPeriode(debut, fin);

		assertFalse(result.isEmpty(), "La liste ne doit pas être vide");
		assertTrue(result.stream().anyMatch(e -> e.getTitre().value().equals("Dentiste")));
		assertTrue(result.stream().anyMatch(e -> e.getTitre().value().equals("Sport")));
		assertTrue(result.stream().anyMatch(e -> e.getTitre().value().equals("Anniversaire")));
	}


}
