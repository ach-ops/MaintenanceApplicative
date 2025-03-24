package calendar.objet;

import java.util.Objects;

public record Utilisateur(String identifiant, String motDePasse) {
	public Utilisateur {
		Objects.requireNonNull(identifiant, "L'identifiant ne peut pas être null");
		Objects.requireNonNull(motDePasse, "Le mot de passe ne peut pas être null");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Utilisateur that)) return false;
		return Objects.equals(identifiant, that.identifiant)
				&& Objects.equals(motDePasse, that.motDePasse);
	}

	@Override
	public String toString() {
		return "Utilisateur{" + identifiant + "}";
	}
}

