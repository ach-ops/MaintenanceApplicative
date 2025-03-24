package calendar.action.menu;

import calendar.action.Action;
import calendar.action.ListeActions;
import calendar.action.afficher_les_evenements.*;
import calendar.app.CalendarManager;
import calendar.evenement.ListeEvenements;

import java.util.List;
import java.util.Scanner;

public class MenuAfficherEvenements implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public MenuAfficherEvenements(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public Boolean run() {
		ListeActions<ListeEvenements> sousMenu = new ListeActions<>(List.of(
				new AfficherTous(calendar),
				new AfficherJour(scanner, calendar),
				new AfficherSemaine(scanner, calendar),
				new AfficherMois(scanner, calendar),
				new AfficherPeriode(scanner, calendar),
				new Retour()
		));

		while (true) {
			System.out.println("\nSous-menu : Afficher les événements");
			System.out.println(sousMenu);
			System.out.print("Votre choix : ");
			String input = scanner.nextLine();

			try {
				int choix = Integer.parseInt(input);
				if (choix < 1 || choix > sousMenu.size()) {
					System.out.println("Choix invalide.");
					continue;
				}

				ListeEvenements evenements = sousMenu.get(choix - 1).run();
				if (evenements == null) {
					return true;
				}
				System.out.println("\nÉvénements trouvés :");
				System.out.println(evenements);

			} catch (NumberFormatException e) {
				System.out.println("Veuillez entrer un nombre.");
			}
		}
	}

	@Override
	public String description() {
		return "Afficher les événements";
	}
}
