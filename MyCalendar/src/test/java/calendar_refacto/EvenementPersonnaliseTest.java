package calendar_refacto;

import calendar.evenement.EvenementPersonnalise;
import calendar.objet.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementPersonnaliseTest {

	@Test
	public void testDescriptionFormat() {
		EventId id = new EventId(UUID.randomUUID().toString());
		TitreEvenement titre = new TitreEvenement("Voyage au Maroc");
		DateEvenement date = new DateEvenement(LocalDateTime.of(2025, 7, 15, 10, 0));
		DureeEvenement duree = new DureeEvenement(180);
		Proprietaire proprietaire = new Proprietaire(new Utilisateur("Achraf", "Achraf"));
		String type = "Voyage";

		EvenementPersonnalise event = new EvenementPersonnalise(id, titre, date, duree, proprietaire, type);

		assertEquals("Voyage : Voyage au Maroc le 15/07/2025 10:00", event.description());
	}

	@Test
	public void testGettersFonctionnent() {
		EventId id = new EventId(UUID.randomUUID().toString());
		TitreEvenement titre = new TitreEvenement("Anniversaire Achraf");
		DateEvenement date = new DateEvenement(LocalDateTime.of(2025, 3, 20, 18, 30));
		DureeEvenement duree = new DureeEvenement(120);
		Utilisateur user = new Utilisateur("Achraf", "Achraf");
		String type = "Anniversaire";

		EvenementPersonnalise event = new EvenementPersonnalise(id, titre, date, duree, new Proprietaire(user), type);

		assertEquals(titre, event.getTitre());
		assertEquals(date, event.getDate());
		assertEquals(duree, event.getDuree());
		assertEquals("Achraf", event.getProprietaire().getUtilisateur().identifiant());
	}

	@Test
	public void testConflitEntreDeuxEvenements() {
		Utilisateur user = new Utilisateur("Achraf", "mdp");
		DateEvenement date1 = new DateEvenement(LocalDateTime.of(2025, 3, 20, 18, 0));
		DateEvenement date2 = new DateEvenement(LocalDateTime.of(2025, 3, 20, 19, 0));

		EvenementPersonnalise event1 = new EvenementPersonnalise(
				new EventId("1"), new TitreEvenement("Voyage"), date1,
				new DureeEvenement(90), new Proprietaire(user), "Voyage");

		EvenementPersonnalise event2 = new EvenementPersonnalise(
				new EventId("2"), new TitreEvenement("Anniversaire"), date2,
				new DureeEvenement(60), new Proprietaire(user), "Anniversaire");

		assertTrue(event1.estEnConflitAvec(event2));
	}

	@Test
	public void testPasDeConflitSiEloignes() {
		Utilisateur user = new Utilisateur("Achraf", "mdp");

		EvenementPersonnalise matin = new EvenementPersonnalise(
				new EventId("3"), new TitreEvenement("Jogging"),
				new DateEvenement(LocalDateTime.of(2025, 6, 1, 8, 0)),
				new DureeEvenement(60), new Proprietaire(user), "Sport");

		EvenementPersonnalise soir = new EvenementPersonnalise(
				new EventId("4"), new TitreEvenement("Cinéma"),
				new DateEvenement(LocalDateTime.of(2025, 6, 1, 20, 0)),
				new DureeEvenement(120), new Proprietaire(user), "Sortie");

		assertFalse(matin.estEnConflitAvec(soir));
	}


}
