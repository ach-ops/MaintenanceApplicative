package calendar;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class EventConflitTest {

	@Test
	public void deuxRendezVousQuiSeChevauchentSontEnConflit() {
		Event rdv1 = new RendezVous(
				new EventId("ev1"),
				new TitreEvenement("RDV1"),
				new DateEvenement(LocalDateTime.of(2025, 3, 23, 10, 0)),
				new DureeEvenement(60),
				new Proprietaire("Achraf")
		);

		Event rdv2 = new RendezVous(
				new EventId("ev2"),
				new TitreEvenement("RDV2"),
				new DateEvenement(LocalDateTime.of(2025, 3, 23, 10, 30)),
				new DureeEvenement(30),
				new Proprietaire("Achraf")
		);

		assertTrue(rdv1.estEnConflitAvec(rdv2));
	}

}
