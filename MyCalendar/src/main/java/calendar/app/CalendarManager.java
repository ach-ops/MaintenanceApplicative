package calendar.app;

import java.util.List;

import java.util.*;
import calendar.evenement.*;
import calendar.objet.DateEvenement;
import calendar.objet.EventId;
import calendar.objet.Periode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


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

    public List<Event> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        return listeEvenements.getAll().stream()
                .flatMap(event -> event.occurrencesDansPeriode(debut, fin).stream())
                .toList();
    }

    public ListeEvenements eventsDansPeriode(Periode periode) {
        List<Event> resultats = eventsDansPeriode(periode.debut(), periode.fin());
        return new ListeEvenements(resultats);
    }


    /*
    public void exporterVersJson(String nomFichier) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //On ajoute si l'extension n'est pas présente
        if (!nomFichier.endsWith(".json")) {
            nomFichier += ".json";
        }

        String cheminComplet = "data/" + nomFichier;

        List<EventDto> dtos = events.values().stream()
                .map(Event::toDto)
                .toList();

        File fichier = new File(cheminComplet);
        if (!fichier.getParentFile().exists()) {
            fichier.getParentFile().mkdirs();
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichier, dtos);
            System.out.println("Événements exportés dans : " + fichier.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erreur export JSON : " + e.getMessage());
        }
    }

    public void importerDepuisJson(String nomFichier) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        if (!nomFichier.endsWith(".json")) {
            nomFichier += ".json";
        }

        String cheminComplet = "data/" + nomFichier;

        try {
            EventDto[] dtos = mapper.readValue(new File(cheminComplet), EventDto[].class);
            for (EventDto dto : dtos) {
                Event event = EventDtoFactory.toEvent(dto);
                this.ajouterEvent(event);
            }
            System.out.println("Événements importés depuis : " + new File(cheminComplet).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erreur import JSON : " + e.getMessage());
        }
    }*/


}