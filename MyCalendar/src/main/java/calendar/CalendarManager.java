package calendar;

import java.util.List;

import java.util.*;
import java.util.stream.Collectors;

public class CalendarManager {
    private final Map<EventId, Event> events = new HashMap<>();

    public void ajouterEvent(Event event) {
        boolean enConflit = events.values().stream()
                .anyMatch(e -> e.estEnConflitAvec(event));

        if (enConflit) {
            System.out.println("Conflit détecté : cet événement chevauche un autre événement existant !");
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
                .filter(event -> event.getDate().isBetween(debut, fin))
                .collect(Collectors.toList());
    }

    public boolean conflit(Event e1, Event e2) {
        return e1.getDate().chevauche(e1.getDuree(), e2.getDate(), e2.getDuree());
    }

    public void afficherEvenements() {
        events.values().forEach(event -> System.out.println(event.description()));
    }
}