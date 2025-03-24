package calendar.action;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;

import java.util.List;
import java.util.Scanner;

public class Menu {

	public static boolean run(Scanner scanner, CalendarManager calendar, Utilisateur utilisateur) {
		ListeActions<Boolean> actions = new ListeActions<>(List.of(
				new AjouterRdvPersonnel(scanner, calendar, utilisateur),
				new AjouterReunion(scanner, calendar, utilisateur),
				new MenuAfficherEvenements(scanner, calendar),
				new Deconnexion()
		));


		while (true) {
			System.out.println("\n Menu principal de " + utilisateur.identifiant());
			System.out.println(actions);

			System.out.print("Votre choix : ");
			String input = scanner.nextLine();

			try {
				int choix = Integer.parseInt(input);
				if (choix < 1 || choix > actions.size()) {
					System.out.println("Choix invalide.");
					continue;
				}

				Boolean continuer = actions.get(choix - 1).run();
				if (continuer == null || !continuer) {
					return false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez entrer un nombre.");
			}
		}
	}
}
