package calendar.objet;


public class Proprietaire {
	private final Utilisateur utilisateur;

	public Proprietaire(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Utilisateur utilisateur() {
		return utilisateur;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Proprietaire p)) return false;
		return utilisateur.equals(p.utilisateur);
	}

	@Override
	public int hashCode() {
		return utilisateur.hashCode();
	}

	@Override
	public String toString() {
		return utilisateur.toString();
	}
}

