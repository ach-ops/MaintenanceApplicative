package calendar.objet;

import java.util.Objects;

public final class ImportReussi extends ImportResult {
	private final QuantiteEvenements nombre;

	public ImportReussi(QuantiteEvenements nombre) {
		this.nombre = Objects.requireNonNull(nombre);
	}

	@Override
	public String messageUtilisateur() {
		return nombre.value() + " événement(s) importé(s) avec succès.";
	}

	@Override
	public boolean contientImport() {
		return true;
	}
}
