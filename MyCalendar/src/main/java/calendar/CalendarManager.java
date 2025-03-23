package calendar;

import java.util.List;

import java.util.*;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CalendarManager {
    private final Map<EventId, Event> events = new HashMap<>();

    public void ajouterEvent(Event event) {
        Optional<Event> conflit = events.values().stream()
                .filter(e -> e.estEnConflitAvec(event) || event.estEnConflitAvec(e))
                .findFirst();

        if (conflit.isPresent()) {
            System.out.println("Conflit détecté avec l'événement : " + conflit.get().description());
            return;
        }

        events.put(event.getId(), event);
        System.out.println("Événement ajouté au calendrier.");
    }

    public void supprimerEvent(EventId id) {
        events.remove(id);
    }

    public List<Event> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        return events.values().stream()
                .flatMap(event -> event.occurrencesDansPeriode(debut, fin).stream())
                .collect(Collectors.toList());
    }

    public void afficherEvenements() {
        events.values().forEach(event ->
                System.out.println("[" + event.getId().value() + "] " + event.description()));
    }

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
    }


}