package calendar.action;

import calendar.Reunion;
import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AjouterReunion implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;
	private final Utilisateur utilisateur;

	public AjouterReunion(Scanner scanner, CalendarManager calendar, Utilisateur utilisateur) {
		this.scanner = scanner;
		this.calendar = calendar;
		this.utilisateur = utilisateur;
	}

	@Override
	public Boolean run() {
		System.out.print("Titre de la réunion : ");
		TitreEvenement titre = new TitreEvenement(scanner.nextLine());

		System.out.print("Année : ");
		int annee = Integer.parseInt(scanner.nextLine());
		System.out.print("Mois (1-12) : ");
		int mois = Integer.parseInt(scanner.nextLine());
		System.out.print("Jour : ");
		int jour = Integer.parseInt(scanner.nextLine());
		System.out.print("Heure (0-23) : ");
		int heure = Integer.parseInt(scanner.nextLine());
		System.out.print("Minute (0-59) : ");
		int minute = Integer.parseInt(scanner.nextLine());

		LocalDateTime dateTime = LocalDateTime.of(annee, mois, jour, heure, minute);
		DateEvenement date = new DateEvenement(dateTime);

		System.out.print("Durée (en minutes) : ");
		DureeEvenement duree = new DureeEvenement(Integer.parseInt(scanner.nextLine()));

		System.out.print("Lieu de la réunion : ");
		Lieu lieu = new Lieu(scanner.nextLine());

		List<Participants> participants = new ArrayList<>();
		System.out.println("Ajoutez les participants (laisser vide pour terminer) :");

		while (true) {
			System.out.print("Nom du participant : ");
			String nom = scanner.nextLine().trim();
			if (nom.isEmpty()) break;
			participants.add(new Participants(nom));
		}

		Event reunion = new Reunion(
				new EventId(UUID.randomUUID().toString()),
				titre,
				date,
				duree,
				new Proprietaire(utilisateur),
				lieu,
				participants
		);

		calendar.ajouterEvent(reunion);
		System.out.println("Réunion ajoutée !");
		return true;
	}

	@Override
	public String description() {
		return "Ajouter une réunion";
	}
}
