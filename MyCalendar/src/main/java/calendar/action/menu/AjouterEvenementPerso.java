package calendar.action.menu;

import calendar.evenement.*;
import calendar.action.Action;
import calendar.app.CalendarManager;
import calendar.objet.*;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class AjouterEvenementPerso implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;
	private final Utilisateur utilisateur;

	public AjouterEvenementPerso(Scanner scanner, CalendarManager calendar, Utilisateur utilisateur) {
		this.scanner = scanner;
		this.calendar = calendar;
		this.utilisateur = utilisateur;
	}

	@Override
	public Boolean run() {
		try {
			System.out.print("Type d'événement (ex: Anniversaire, Voyage...) : ");
			String type = scanner.nextLine();

			System.out.print("Titre : ");
			String titre = scanner.nextLine();

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

			System.out.print("Durée (en minutes) : ");
			int duree = Integer.parseInt(scanner.nextLine());

			calendar.ajouterEvent(new EvenementPersonnalise(
					new EventId(utilisateur.identifiant() + "_" + type.toLowerCase() + "_" + UUID.randomUUID()),
					new TitreEvenement(titre),
					new DateEvenement(LocalDateTime.of(annee, mois, jour, heure, minute)),
					new DureeEvenement(duree),
					new Proprietaire(utilisateur),
					type
			));

			System.out.println("Événement '" + type + "' ajouté !");
			return true;

		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			return true;
		}
	}

	@Override
	public String description() {
		return "Ajouter un événement personnalisé (ex: Anniversaire, Voyage...)";
	}
}
