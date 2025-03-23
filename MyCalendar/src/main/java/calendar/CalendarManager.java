package calendar;

import java.util.List;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Event> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        return events.values().stream()
                .flatMap(event -> event.occurrencesDansPeriode(debut, fin).stream())
                .collect(Collectors.toList());
    }

    public void afficherEvenements() {
        events.values().forEach(event -> System.out.println(event.description()));
    }
}