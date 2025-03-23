package calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class EventManager {
	private final CalendarManager calendar;

	public EventManager(CalendarManager calendar) {
		this.calendar = calendar;
	}

	public void ajouterRendezVous(Scanner scanner, String utilisateur) {
		System.out.print("Titre de l'événement : ");
		String titre = scanner.nextLine();
		System.out.print("Date et heure (AAAA-MM-JJ HH:MM) : ");
		LocalDateTime date = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));
		System.out.print("Durée (en minutes) : ");
		int duree = Integer.parseInt(scanner.nextLine());

		calendar.ajouterEvent(new RendezVous(
				new EventId(utilisateur + "_rdv_" + UUID.randomUUID()),
				new TitreEvenement(titre),
				new DateEvenement(date),
				new DureeEvenement(duree),
				new Proprietaire(utilisateur)
		));
	}

	public void ajouterReunion(Scanner scanner, String utilisateur) {
		System.out.print("Titre de la réunion : ");
		String titre = scanner.nextLine();
		System.out.print("Lieu : ");
		String lieu = scanner.nextLine();
		System.out.print("Participants (séparés par des virgules) : ");
		String participants = scanner.nextLine();
		System.out.print("Date et heure (AAAA-MM-JJ HH:MM) : ");
		LocalDateTime date = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));
		System.out.print("Durée (en minutes) : ");
		int duree = Integer.parseInt(scanner.nextLine());

		calendar.ajouterEvent(new Reunion(
				new EventId(utilisateur + "_reunion" + UUID.randomUUID()),
				new TitreEvenement(titre),
				new DateEvenement(date),
				new DureeEvenement(duree),
				new Proprietaire(utilisateur),
				new Participants(participants),
				new Lieu(lieu)
		));
	}

	public void ajouterEvenementPeriodique(Scanner scanner, String utilisateur) {
		System.out.print("Titre de l'événement : ");
		String titre = scanner.nextLine();
		System.out.print("Fréquence (en jours) : ");
		int frequence = Integer.parseInt(scanner.nextLine());
		System.out.print("Date de début (AAAA-MM-JJ) : ");
		LocalDate date = LocalDate.parse(scanner.nextLine());
		LocalDateTime dateTime = date.atStartOfDay();

		calendar.ajouterEvent(new EvenementPeriodique(
				new EventId(utilisateur + "_periodique" + UUID.randomUUID()),
				new TitreEvenement(titre),
				new DateEvenement(dateTime),
				new DureeEvenement(30),
				new Proprietaire(utilisateur),
				new FrequenceEvenement(frequence)
		));
	}

	public void ajouterEvenementHebdomadaire(Scanner scanner, String utilisateur) {
		System.out.print("Titre de l'événement : ");
		String titre = scanner.nextLine();
		System.out.print("Date et heure de début (AAAA-MM-JJ HH:MM) : ");
		LocalDateTime date = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));
		System.out.print("Durée (en minutes) : ");
		int duree = Integer.parseInt(scanner.nextLine());

		calendar.ajouterEvent(new EvenementHebdomadaire(
				new EventId(utilisateur + "_hebdo_" + UUID.randomUUID()),
				new TitreEvenement(titre),
				new DateEvenement(date),
				new DureeEvenement(duree),
				new Proprietaire(utilisateur)
		));
	}

	public void ajouterEvenementAnnuel(Scanner scanner, String utilisateur) {
		System.out.print("Titre de l'événement : ");
		String titre = scanner.nextLine();

		System.out.print("Date et heure de l'événement (AAAA-MM-JJ HH:MM) : ");
		LocalDateTime date = LocalDateTime.parse(scanner.nextLine().replace(" ", "T"));

		System.out.print("Durée (en minutes) : ");
		int duree = Integer.parseInt(scanner.nextLine());

		calendar.ajouterEvent(new EvenementAnnuel(
				new EventId(utilisateur + "_annuel_" + UUID.randomUUID()),
				new TitreEvenement(titre),
				new DateEvenement(date),
				new DureeEvenement(duree),
				new Proprietaire(utilisateur)
		));
	}


}
