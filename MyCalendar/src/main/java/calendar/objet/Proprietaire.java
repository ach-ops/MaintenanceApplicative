package calendar.objet;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Proprietaire {
	private final Utilisateur utilisateur;

	@JsonCreator
	public Proprietaire(@JsonProperty("utilisateur") Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@JsonProperty("utilisateur")
	public Utilisateur utilisateur() {
		return utilisateur;
	}
}



