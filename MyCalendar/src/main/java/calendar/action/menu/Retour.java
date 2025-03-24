package calendar.action.menu;

import calendar.action.Action;
import calendar.evenement.ListeEvenements;

public class Retour implements Action<ListeEvenements> {

	@Override
	public ListeEvenements run() {
		return null;
	}

	@Override
	public String description() {
		return "Retour";
	}
}
