package calendar.action;

import calendar.action.ListeActions;
import calendar.objet.ListeUtilisateurs;
import calendar.objet.Utilisateur;

import java.util.List;
import java.util.Scanner;

public class Connexion {

	private final ListeUtilisateurs listeUtilisateurs;
	private final ListeActions<Utilisateur> listeActions;

	public Connexion(ListeUtilisateurs listeUtilisateurs, Scanner scanner) {
		this.listeUtilisateurs = listeUtilisateurs;
		this.listeActions = new ListeActions<>(List.of(
				new SeConnecter(listeUtilisateurs, scanner),
				new CreerCompte(listeUtilisateurs, scanner)
		));
	}

	public Utilisateur run(Scanner scanner) {
		System.out.println("==== Connexion ====");
		System.out.println(listeActions); // suppose que ListeActions a un joli toString()

		System.out.print("Choix : ");
		String choix = scanner.nextLine();

		return listeActions.get(Integer.parseInt(choix) - 1).run();
	}
}
