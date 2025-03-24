package calendar.action;

import calendar.RendezVous;
import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.*;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class AjouterRdvPersonnel implements Action<Boolean> {

	private final Scanner scanner;
	private final CalendarManager calendar;
	private final Utilisateur utilisateur;

	public AjouterRdvPersonnel(Scanner scanner, CalendarManager calendar, Utilisateur utilisateur) {
		this.scanner = scanner;
		this.calendar = calendar;
		this.utilisateur = utilisateur;
	}

	@Override
	public Boolean run() {
		System.out.print("Titre de l'événement : ");
		String titreSaisi = scanner.nextLine();
		TitreEvenement titre = new TitreEvenement(titreSaisi);

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
		int dureeMinutes = Integer.parseInt(scanner.nextLine());
		DureeEvenement duree = new DureeEvenement(dureeMinutes);

		Event rdv = new RendezVous(
				new EventId(UUID.randomUUID().toString()),
				titre,
				date,
				duree,
				new Proprietaire(utilisateur)
		);

		calendar.ajouterEvent(rdv);
		System.out.println("Rendez-vous personnel ajouté !");
		return true;
	}

	@Override
	public String description() {
		return "Ajouter un rendez-vous personnel";
	}
}
