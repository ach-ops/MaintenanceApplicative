package calendar.action;

public class Deconnexion implements Action<Boolean> {

	@Override
	public Boolean run() {
		System.out.println("Vous avez été déconnecté.");
		return false;
	}

	@Override
	public String description() {
		return "Se déconnecter";
	}
}
