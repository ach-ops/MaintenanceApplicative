package calendar.objet;

public final class ImportEchoue extends ImportResult {
	@Override
	public String messageUtilisateur() {
		return "Aucun événement importé. Tous étaient en conflit.";
	}

	@Override
	public boolean contientImport() {
		return false;
	}
}
