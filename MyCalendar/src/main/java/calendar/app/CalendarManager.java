package calendar.app;

import java.io.IOException;
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

    public void exporterVersJson(String cheminComplet) {
        try {
            ExporterJson.exporter(listeEvenements.getAll(), cheminComplet);
        } catch (Exception e) {
            System.err.println("Erreur export JSON : " + e.getMessage());
        }
    }

    public ImportResult importerDepuisJson(String cheminComplet) {
        int total = 0;
        int ajoutes = 0;

        try {
            List<Event> events = ImporterJson.importer(cheminComplet);
            total = events.size();

            for (Event event : events) {
                if (ajouterEvent(event)) {
                    ajoutes++;
                }
            }

            int conflits = total - ajoutes;
            QuantiteEvenements qAjoutes = new QuantiteEvenements(ajoutes);
            QuantiteEvenements qConflits = new QuantiteEvenements(conflits);

            if (ajoutes == 0) return new ImportEchoue();
            if (conflits == 0) return new ImportReussi(qAjoutes);
            return new ImportAvecConflits(qAjoutes, qConflits);

        } catch (IOException e) {
            return new ImportEchoue();
        }
    }





}