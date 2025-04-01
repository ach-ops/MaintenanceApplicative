package calendar.action.menu;

import calendar.action.Action;
import calendar.app.CalendarManager;
import calendar.objet.ImportResult;

import java.util.Scanner;

public class ImporterJsonAction implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public ImporterJsonAction(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public Boolean run() {
		System.out.print("Entrez le nom du fichier à importer (sans extension) : ");
		String nomFichier = scanner.nextLine();

		ImportResult result = calendar.importerDepuisJson(nomFichier);
		System.out.println(result.messageUtilisateur());

		return result.contientImport();
	}

	@Override
	public String description() {
		return "Importer les événements depuis un fichier JSON";
	}
}