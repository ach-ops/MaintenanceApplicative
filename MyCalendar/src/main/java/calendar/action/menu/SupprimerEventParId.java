package calendar.action.menu;

import calendar.action.Action;
import calendar.app.CalendarManager;
import calendar.objet.EventId;

import java.util.Scanner;

public class SupprimerEventParId implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public SupprimerEventParId(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public Boolean run() {
		System.out.print("Entrez l'UUID de l'événement à supprimer : ");
		String uuid = scanner.nextLine().trim();

		boolean supprime = calendar.supprimerEvent(new EventId(uuid));

		if (supprime) {
			System.out.println("Événement supprimé.");
		} else {
			System.out.println("Aucun événement trouvé avec cet UUID.");
		}

		return true;
	}

	@Override
	public String description() {
		return "Supprimer un événement par son UUID";
	}
}

