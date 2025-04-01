package calendar.objet;

public final class ImportAvecConflits extends ImportResult {
	private final QuantiteEvenements importes;
	private final QuantiteEvenements conflits;

	public ImportAvecConflits(QuantiteEvenements importes, QuantiteEvenements conflits) {
		this.importes = importes;
		this.conflits = conflits;
	}

	@Override
	public String messageUtilisateur() {
		return importes.value() + " événement(s) importé(s).\n" +
				conflits.value() + " événement(s) en conflit (non importés).";
	}

	@Override
	public boolean contientImport() {
		return importes.value() > 0;
	}
}
