package calendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendar = new CalendarManager();
        EventManager eventManager = new EventManager(calendar);
        Scanner scanner = new Scanner(System.in);
        String utilisateur = null;
        boolean continuer = true;

        Map<String, String> utilisateurs = new HashMap<>();
        utilisateurs.put("Roger", "Chat");
        utilisateurs.put("Pierre", "KiRouhl");
        utilisateurs.put("Achraf", "Achraf");

        while (true) {

            if (utilisateur == null) {
                System.out.println("  _____         _                   _                __  __");
                System.out.println(" / ____|       | |                 | |              |  \\/  |");
                System.out.println(
                        "| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __");
                System.out.println(
                        "| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|");
                System.out.println(
                        "| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |");
                System.out.println(
                        " \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|");
                System.out.println(
                        "                                                                                   __/ |");
                System.out.println(
                        "                                                                                  |___/");

                System.out.println("1 - Se connecter");
                System.out.println("2 - Créer un compte");
                System.out.println("Choix : ");

                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Nom d'utilisateur: ");
                        utilisateur = scanner.nextLine();
                        System.out.print("Mot de passe: ");
                        String motDePasse = scanner.nextLine();

                        if (!utilisateurs.containsKey(utilisateur) || !utilisateurs.get(utilisateur).equals(motDePasse)) {
                            System.out.println("Identifiants incorrects.");
                            utilisateur = null;
                        }
                        break;

                    case "2":
                        System.out.print("Nom d'utilisateur: ");
                        String nouveauUtilisateur = scanner.nextLine();
                        System.out.print("Mot de passe: ");
                        String nouveauMotDePasse = scanner.nextLine();
                        System.out.print("Répéter mot de passe: ");
                        if (scanner.nextLine().equals(nouveauMotDePasse)) {
                            utilisateurs.put(nouveauUtilisateur, nouveauMotDePasse);
                            System.out.println("Compte créé avec succès.");
                            utilisateur = nouveauUtilisateur;
                        } else {
                            System.out.println("Les mots de passe ne correspondent pas...");
                        }
                        break;
                }
            }

            while (continuer && utilisateur != null) {
                System.out.println("\nBonjour, " + utilisateur);
                System.out.println("=== Menu Gestionnaire d'Événements ===");
                System.out.println("1 - Voir les événements");
                System.out.println("2 - Ajouter un rendez-vous");
                System.out.println("3 - Ajouter une réunion");
                System.out.println("4 - Ajouter un événement périodique");
                System.out.println("5 - Ajouter un événement hebdomadaire");
                System.out.println("6 - Ajouter un événement annuel");
                System.out.println("7 - Voir les événements d'une période");
                System.out.println("8 - Supprimer un événement par son ID");
                System.out.println("9 - Exporter les événements en JSON");
                System.out.println("10 - Importer les événements depuis un fichier JSON");
                System.out.println("11 - Se déconnecter");
                System.out.print("Votre choix : ");

                String choix = scanner.nextLine();
                switch (choix) {
                    case "1":
                        calendar.afficherEvenements();
                        break;
                    case "2":
                        eventManager.ajouterRendezVous(scanner, utilisateur);
                        break;
                    case "3":
                        eventManager.ajouterReunion(scanner, utilisateur);
                        break;
                    case "4":
                        eventManager.ajouterEvenementPeriodique(scanner, utilisateur);
                        break;
                    case "5":
                        eventManager.ajouterEvenementHebdomadaire(scanner, utilisateur);
                        break;
                    case "6":
                        eventManager.ajouterEvenementAnnuel(scanner, utilisateur);
                        break;
                    case "7":
                        voirEvenementsDansPeriode(scanner, calendar);
                        break;
                    case "8":
                        supprimerEvenementParId(scanner, calendar);
                        break;
                    case "9":
                        exporterDepuisConsole(scanner, calendar);
                        break;

                    case "10":
                        importerDepuisConsole(scanner, calendar);
                        break;
                    case "11":
                        utilisateur = null;
                        break;
                }
            }
        }
    }

    private static void voirEvenementsDansPeriode(Scanner scanner, CalendarManager calendar) {
        System.out.print("Date de début (AAAA-MM-JJ) : ");
        LocalDateTime debut = LocalDate.parse(scanner.nextLine()).atStartOfDay();

        System.out.print("Date de fin (AAAA-MM-JJ) : ");
        LocalDateTime fin = LocalDate.parse(scanner.nextLine()).atTime(23, 59);

        List<Event> resultats = calendar.eventsDansPeriode(
                new DateEvenement(debut),
                new DateEvenement(fin)
        );

        if (resultats.isEmpty()) {
            System.out.println("Aucun événement trouvé pour cette période.");
        } else {
            System.out.println("Événements trouvés :");
            resultats.forEach(e -> System.out.println("- " + e.description()));
        }
    }

    private static void supprimerEvenementParId(Scanner scanner, CalendarManager calendar) {
        System.out.print("Entrez l'identifiant de l'événement à supprimer : ");
        String id = scanner.nextLine().trim();
        calendar.supprimerEvent(new EventId(id));
        System.out.println("Événement supprimé.");
    }

    private static void importerDepuisConsole(Scanner scanner, CalendarManager calendar) {
        System.out.print("Nom du fichier JSON à importer : ");
        String fichierImport = scanner.nextLine().trim();
        calendar.importerDepuisJson(fichierImport);
    }

    private static void exporterDepuisConsole(Scanner scanner, CalendarManager calendar) {
        System.out.print("Nom du fichier JSON à exporter : ");
        String fichierExport = scanner.nextLine().trim();
        calendar.exporterVersJson(fichierExport);
    }


}
