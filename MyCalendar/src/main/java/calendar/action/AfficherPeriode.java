package calendar.action;

import calendar.app.CalendarManager;
import calendar.evenement.ListeEvenements;
import calendar.objet.DateEvenement;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AfficherPeriode implements Action<ListeEvenements> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public AfficherPeriode(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public ListeEvenements run() {
		System.out.println("Date de début :");
		DateEvenement debut = demanderDate();

		System.out.println("Date de fin :");
		DateEvenement fin = demanderDate();

		return new ListeEvenements(calendar.eventsDansPeriode(debut, fin));
	}

	private DateEvenement demanderDate() {
		System.out.print("Année : ");
		int annee = Integer.parseInt(scanner.nextLine());
		System.out.print("Mois : ");
		int mois = Integer.parseInt(scanner.nextLine());
		System.out.print("Jour : ");
		int jour = Integer.parseInt(scanner.nextLine());
		System.out.print("Heure : ");
		int heure = Integer.parseInt(scanner.nextLine());
		System.out.print("Minute : ");
		int minute = Integer.parseInt(scanner.nextLine());

		return new DateEvenement(LocalDateTime.of(annee, mois, jour, heure, minute));
	}

	@Override
	public String description() {
		return "Afficher les événements d’une PÉRIODE personnalisée";
	}
}
