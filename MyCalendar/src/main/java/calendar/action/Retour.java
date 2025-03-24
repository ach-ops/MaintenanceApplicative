package calendar.action;

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
