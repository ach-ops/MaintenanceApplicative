package calendar.action.menu;

import calendar.action.Action;
import calendar.evenement.EvenementPeriodique;
import calendar.app.CalendarManager;
import calendar.objet.*;

import java.time.LocalDate;
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

			System.out.print("Date de début (AAAA-MM-JJ) : ");
			LocalDate date = LocalDate.parse(scanner.nextLine());

			System.out.print("Durée (en minutes) : ");
			int duree = Integer.parseInt(scanner.nextLine());

			System.out.print("Fréquence (en jours) : ");
			int frequence = Integer.parseInt(scanner.nextLine());

			calendar.ajouterEvent(new EvenementPeriodique(
					new EventId(utilisateur.identifiant() + "_periodique_" + UUID.randomUUID()),
					new TitreEvenement(titre),
					new DateEvenement(date.atStartOfDay()),
					new DureeEvenement(duree),
					new Proprietaire(utilisateur),
					new FrequenceEvenement(frequence)
			));

			System.out.println("Événement périodique ajouté !");
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
