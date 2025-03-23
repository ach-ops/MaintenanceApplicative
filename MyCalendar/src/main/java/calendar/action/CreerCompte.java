package calendar.action;

import calendar.objet.ListeUtilisateurs;
import calendar.objet.Utilisateur;

import java.util.Scanner;

public class CreerCompte implements Action<Utilisateur> {
	private final ListeUtilisateurs listeUtilisateurs;
	private final Scanner scanner;

	public CreerCompte(ListeUtilisateurs listeUtilisateurs, Scanner scanner) {
		this.listeUtilisateurs = listeUtilisateurs;
		this.scanner = scanner;
	}

	@Override
	public Utilisateur run() {
		System.out.print("Nom d'utilisateur: ");
		String nom = scanner.nextLine();
		System.out.print("Mot de passe: ");
		String mdp = scanner.nextLine();
		System.out.print("Répéter mot de passe: ");
		String confirmation = scanner.nextLine();

		if (!mdp.equals(confirmation)) {
			System.out.println("Les mots de passe ne correspondent pas.");
			return null;
		}

		Utilisateur nouvelUtilisateur = new Utilisateur(nom, mdp);
		listeUtilisateurs.ajouter(nouvelUtilisateur);
		System.out.println("Compte créé avec succès !");
		return nouvelUtilisateur;
	}

	@Override
	public String description() {
		return "Créer un compte";
	}
}
