package calendar.objet;


public record Proprietaire(Utilisateur utilisateur) {
	public Proprietaire {
		if (utilisateur == null) {
			throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
		}
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Proprietaire p)) return false;
		return utilisateur.equals(p.utilisateur);
	}

	@Override
	public String toString() {
		return utilisateur.toString();
	}
}

