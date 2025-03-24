package calendar.action;

import calendar.app.CalendarManager;
import calendar.evenement.ListeEvenements;
import calendar.objet.DateEvenement;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AfficherJour implements Action<ListeEvenements> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public AfficherJour(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public ListeEvenements run() {
		System.out.print("Entrez l'année (AAAA) : ");
		int anneeJour = Integer.parseInt(scanner.nextLine());

		System.out.print("Entrez le mois (1-12) : ");
		int moisJour = Integer.parseInt(scanner.nextLine());

		System.out.print("Entrez le jour (1-31) : ");
		int jour = Integer.parseInt(scanner.nextLine());

		LocalDateTime debutJour = LocalDateTime.of(anneeJour, moisJour, jour, 0, 0);
		LocalDateTime finJour = debutJour.plusDays(1).minusSeconds(1);

		DateEvenement debut = new DateEvenement(debutJour);
		DateEvenement fin = new DateEvenement(finJour);

		return (ListeEvenements) calendar.eventsDansPeriode(debut, fin);

	}

	@Override
	public String description() {
		return "Afficher les événements d'un JOUR précis";
	}
}
