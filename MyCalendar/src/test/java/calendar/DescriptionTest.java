package calendar;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {

	@Test
	void descriptionRendezVous() {
		Event event = new RendezVous(
				new EventId("id1"),
				new TitreEvenement("Dentiste"),
				new DateEvenement(LocalDateTime.of(2025, 6, 14, 14, 0)),
				new DureeEvenement(30),
				new Proprietaire("Achraf")
		);
		assertTrue(event.description().contains("Dentiste"));
		assertTrue(event.description().contains("2025-06-14"));
	}

	@Test
	void descriptionReunion() {
		Event event = new Reunion(
				new EventId("id2"),
				new TitreEvenement("Projet X"),
				new DateEvenement(LocalDateTime.of(2025, 6, 20, 10, 0)),
				new DureeEvenement(90),
				new Proprietaire("Julie"),
				new Participants("Achraf, Julie"),
				new Lieu("Salle 101")
		);
		assertTrue(event.description().contains("Projet X"));
		assertTrue(event.description().contains("Salle 101"));
	}

	@Test
	void descriptionPeriodique() {
		Event event = new EvenementPeriodique(
				new EventId("id3"),
				new TitreEvenement("Vaccin"),
				new DateEvenement(LocalDateTime.of(2025, 1, 1, 8, 0)),
				new DureeEvenement(10),
				new Proprietaire("Achraf"),
				new FrequenceEvenement(30)
		);
		assertTrue(event.description().contains("tous les 30 jours"));
	}

	@Test
	void descriptionHebdomadaire() {
		Event event = new EvenementHebdomadaire(
				new EventId("id4"),
				new TitreEvenement("Sport"),
				new DateEvenement(LocalDateTime.of(2025, 6, 2, 18, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		);
		assertTrue(event.description().toLowerCase().contains("lundi"));
		assertTrue(event.description().contains("18:00"));
	}

	@Test
	void descriptionAnnuel() {
		Event event = new EvenementAnnuel(
				new EventId("id5"),
				new TitreEvenement("Fête nationale"),
				new DateEvenement(LocalDateTime.of(2020, 7, 14, 0, 0)),
				new DureeEvenement(1440),
				new Proprietaire("Etat")
		);
		assertTrue(event.description().contains("14"));
		assertTrue(event.description().toLowerCase().contains("juillet"));
	}
}
