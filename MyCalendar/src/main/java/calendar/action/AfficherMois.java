package calendar.action;

import calendar.app.CalendarManager;
import calendar.evenement.ListeEvenements;
import calendar.objet.DateEvenement;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AfficherMois implements Action<ListeEvenements> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public AfficherMois(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public ListeEvenements run() {
		System.out.print("Entrez l'année (AAAA) : ");
		int annee = Integer.parseInt(scanner.nextLine());
		System.out.print("Entrez le mois (1-12) : ");
		int mois = Integer.parseInt(scanner.nextLine());

		LocalDateTime debut = LocalDateTime.of(annee, mois, 1, 0, 0);
		LocalDateTime fin = debut.plusMonths(1).minusSeconds(1);

		return new ListeEvenements(calendar.eventsDansPeriode(
				new DateEvenement(debut),
				new DateEvenement(fin)
		));
	}

	@Override
	public String description() {
		return "Afficher les événements d’un MOIS précis";
	}
}
