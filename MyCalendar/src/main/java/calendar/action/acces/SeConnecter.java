package calendar.action.acces;

import calendar.action.Action;
import calendar.objet.ListeUtilisateurs;
import calendar.objet.Utilisateur;

import java.util.Scanner;

public class SeConnecter implements Action<Utilisateur> {
	private final ListeUtilisateurs listeUtilisateurs;
	private final Scanner scanner;

	public SeConnecter(ListeUtilisateurs listeUtilisateurs, Scanner scanner) {
		this.listeUtilisateurs = listeUtilisateurs;
		this.scanner = scanner;
	}

	@Override
	public Utilisateur run() {
		System.out.print("Nom d'utilisateur: ");
		String nom = scanner.nextLine();
		System.out.print("Mot de passe: ");
		String mdp = scanner.nextLine();

		return listeUtilisateurs.trouver(nom, mdp)
				.orElseGet(() -> {
					System.out.println("Échec de la connexion.");
					return null;
				});
	}

	@Override
	public String description() {
		return "Se connecter";
	}
}
