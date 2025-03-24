package calendar.action;

import calendar.objet.ListeUtilisateurs;
import calendar.objet.Utilisateur;

import java.util.List;
import java.util.Scanner;

public class Connexion {

	private static final ListeUtilisateurs listeUtilisateurs = new ListeUtilisateurs(List.of(
			new Utilisateur("Roger", "Chat"),
			new Utilisateur("Pierre", "KiRouhl"),
			new Utilisateur("Achraf", "Achraf")
	));

	public static Utilisateur run(Scanner scanner) {
		ListeActions<Utilisateur> actions = new ListeActions<>(List.of(
				new SeConnecter(listeUtilisateurs, scanner),
				new CreerCompte(listeUtilisateurs, scanner)
		));

		System.out.println("=== Connexion ===");
		System.out.println(actions);
		System.out.print("Choix : ");
		try {
			int choix = Integer.parseInt(scanner.nextLine());
			return actions.get(choix - 1).run();
		} catch (Exception e) {
			System.out.println("Entrée invalide. Réessayez.");
			return null;
		}
	}
}
