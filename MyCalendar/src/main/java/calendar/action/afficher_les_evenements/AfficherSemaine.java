package calendar.action.afficher_les_evenements;

import calendar.action.Action;
import calendar.app.CalendarManager;
import calendar.evenement.ListeEvenements;
import calendar.objet.DateEvenement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Scanner;

public class AfficherSemaine implements Action<ListeEvenements> {

	private final Scanner scanner;
	private final CalendarManager calendar;

	public AfficherSemaine(Scanner scanner, CalendarManager calendar) {
		this.scanner = scanner;
		this.calendar = calendar;
	}

	@Override
	public ListeEvenements run() {
		System.out.print("Entrez l’année : ");
		int annee = Integer.parseInt(scanner.nextLine());
		System.out.print("Numéro de semaine (1-53) : ");
		int semaine = Integer.parseInt(scanner.nextLine());

		LocalDateTime debut = LocalDate
				.now()
				.withYear(annee)
				.with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
				.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
				.atStartOfDay();

		LocalDateTime fin = debut.plusDays(6).withHour(23).withMinute(59);

		return new ListeEvenements(calendar.eventsDansPeriode(
				new DateEvenement(debut),
				new DateEvenement(fin)
		));
	}

	@Override
	public String description() {
		return "Afficher les événements d’une SEMAINE précise";
	}
}
