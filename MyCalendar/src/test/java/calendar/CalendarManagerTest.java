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
}
