package calendar.action.menu;

import calendar.action.Action;
import calendar.app.CalendarManager;

import java.util.Scanner;

public class ExporterJsonAction implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public ExporterJsonAction(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public Boolean run() {
		System.out.print("Entrez le nom du fichier à exporter (sans extension) : ");
		String nomFichier = scanner.nextLine();

		calendar.exporterVersJson(nomFichier);

		return true;
	}

	@Override
	public String description() {
		return "Exporter les événements en JSON";
	}
}