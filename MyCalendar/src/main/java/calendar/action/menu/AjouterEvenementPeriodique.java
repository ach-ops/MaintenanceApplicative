package calendar.action.menu;

import calendar.action.Action;
import calendar.evenement.EvenementPeriodique;
import calendar.app.CalendarManager;
import calendar.objet.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class AjouterEvenementPeriodique implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;
	private final Utilisateur utilisateur;

	public AjouterEvenementPeriodique(Scanner scanner, CalendarManager calendar, Utilisateur utilisateur) {
		this.scanner = scanner;
		this.calendar = calendar;
		this.utilisateur = utilisateur;
	}

	@Override
	public Boolean run() {
		try {
			System.out.print("Titre : ");
			String titre = scanner.nextLine();

			System.out.print("Année : ");
			int annee = Integer.parseInt(scanner.nextLine());
			System.out.print("Mois (1-12) : ");
			int mois = Integer.parseInt(scanner.nextLine());
			System.out.print("Jour : ");
			int jour = Integer.parseInt(scanner.nextLine());
			System.out.print("Heure (0-23) : ");
			int heure = Integer.parseInt(scanner.nextLine());
			System.out.print("Minute (0-59) : ");
			int minute = Integer.parseInt(scanner.nextLine());

			LocalDateTime dateTime = LocalDateTime.of(annee, mois, jour, heure, minute);

			System.out.print("Durée (en minutes) : ");
			int duree = Integer.parseInt(scanner.nextLine());

			System.out.print("Fréquence (en jours) : ");
			int frequence = Integer.parseInt(scanner.nextLine());

			EvenementPeriodique evenementPeriodique = new EvenementPeriodique(
					new EventId(utilisateur.identifiant() + "_periodique_" + UUID.randomUUID()),
					new TitreEvenement(titre),
					new DateEvenement(dateTime),
					new DureeEvenement(duree),
					new Proprietaire(utilisateur),
					new FrequenceEvenement(frequence)
			);

			boolean added = calendar.ajouterEvent(evenementPeriodique);

			if (!added) {
				System.out.println("Conflit détecté. L'événement n'a pas été ajouté.");
			} else {
				System.out.println("Événement périodique ajouté !");
			}

			return true;

		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			return true;
		}
	}

	@Override
	public String description() {
		return "Ajouter un événement périodique";
	}
}
