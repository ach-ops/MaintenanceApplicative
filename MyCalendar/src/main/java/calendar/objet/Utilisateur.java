package calendar.objet;

import java.util.Objects;

public class Utilisateur {
	private final String identifiant;
	private final String motDePasse;

	public Utilisateur(String identifiant, String motDePasse) {
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
	}

	public String identifiant() {
		return identifiant;
	}

	public String motDePasse() {
		return motDePasse;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Utilisateur that)) return false;
		return Objects.equals(identifiant, that.identifiant)
				&& Objects.equals(motDePasse, that.motDePasse);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifiant, motDePasse);
	}

	@Override
	public String toString() {
		return "Utilisateur{" + identifiant + "}";
	}
}

