package calendar;

import java.util.List;

import java.util.*;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void exporterVersJson(String chemin) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<EventDto> dtos = events.values().stream()
                .map(Event::toDto)
                .toList();

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(chemin), dtos);
            System.out.println("Événements exportés dans : " + chemin);
        } catch (IOException e) {
            System.err.println("Erreur export JSON : " + e.getMessage());
        }
    }

    public void importerDepuisJson(String chemin) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            EventDto[] dtos = mapper.readValue(new File(chemin), EventDto[].class);
            for (EventDto dto : dtos) {
                Event event = EventDtoFactory.toEvent(dto);
                this.ajouterEvent(event);
            }
            System.out.println("Événements importés depuis : " + chemin);
        } catch (IOException e) {
            System.err.println("Erreur import JSON : " + e.getMessage());
        }
    }

}