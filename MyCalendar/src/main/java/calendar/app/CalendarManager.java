package calendar.app;

import java.util.*;
import calendar.evenement.*;
import calendar.objet.*;

import static calendar.action.acces.Connexion.listeUtilisateurs;

public class CalendarManager {
    private final ListeEvenements listeEvenements = new ListeEvenements(new ArrayList<>());

    public boolean ajouterEvent(Event event) {
        Optional<Event> conflit = listeEvenements.getAll().stream()
                .filter(e -> e.estEnConflitAvec(event) || event.estEnConflitAvec(e))
                .findFirst();

        if (conflit.isPresent()) {
            System.out.println("Conflit détecté avec l'événement : " + conflit.get().description());
            return false;
        }

        listeEvenements.ajouter(event);
        System.out.println("Événement ajouté au calendrier.");
        return true;
    }

    public boolean supprimerEvent(EventId eventId) {
        return listeEvenements.supprimerEvenement(eventId);
    }

    public List<Event> getTousLesEvenements() {
        return listeEvenements.getAll();
    }

    public ListeEvenements getListeEvenements() {
        return listeEvenements;
    }

    public ListeUtilisateurs getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public List<Event> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        return listeEvenements.getAll().stream()
                .flatMap(event -> event.occurrencesDansPeriode(debut, fin).stream())
                .toList();
    }

    public ListeEvenements eventsDansPeriode(Periode periode) {
        List<Event> resultats = eventsDansPeriode(periode.debut(), periode.fin());
        return new ListeEvenements(resultats);
    }

    public void exporterVersJson(String nomFichier) {
        ExporterJson.exporter(listeEvenements.getAll(), nomFichier);
    }

    public void importerDepuisJson(String nomFichier) {
        ImporterJson.importer(listeEvenements, nomFichier);
    }


}