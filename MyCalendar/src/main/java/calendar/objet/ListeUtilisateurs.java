package calendar.objet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ListeUtilisateurs {

	private final List<Utilisateur> utilisateurs;

	public ListeUtilisateurs(List<Utilisateur> utilisateursInitial) {
		this.utilisateurs = new ArrayList<>(utilisateursInitial); // copie défensive
	}

	public void ajouter(Utilisateur utilisateur) {
		utilisateurs.add(utilisateur);
	}

	public Optional<Utilisateur> trouver(String nom, String motDePasse) {
		return utilisateurs.stream()
				.filter(u -> u.identifiant().equals(nom) && u.motDePasse().equals(motDePasse))
				.findFirst();
	}

	public boolean contient(Utilisateur utilisateur) {
		return utilisateurs.contains(utilisateur);
	}

	public List<Utilisateur> tous() {
		return Collections.unmodifiableList(utilisateurs);
	}
}
