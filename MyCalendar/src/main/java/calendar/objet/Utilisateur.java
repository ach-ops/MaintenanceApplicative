package calendar.objet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Utilisateur {
	private final String identifiant;
	private final String motDePasse;

	@JsonCreator
	public Utilisateur(
			@JsonProperty("identifiant") String identifiant,
			@JsonProperty("motDePasse") String motDePasse) {
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
	}

	@JsonProperty("identifiant")
	public String identifiant() {
		return identifiant;
	}

	@JsonProperty("motDePasse")
	public String motDePasse() {
		return motDePasse;
	}
}


