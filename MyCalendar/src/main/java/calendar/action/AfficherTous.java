package calendar.action;

import calendar.app.CalendarManager;
import calendar.evenement.ListeEvenements;

public class AfficherTous implements Action<ListeEvenements> {

	private final CalendarManager calendar;

	public AfficherTous(CalendarManager calendar) {
		this.calendar = calendar;
	}

	@Override
	public ListeEvenements run() {
		return new ListeEvenements(calendar.getTousLesEvenements());
	}

	@Override
	public String description() {
		return "Afficher TOUS les événements";
	}
}
