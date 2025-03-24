package calendar.evenement;
import calendar.objet.*;
import java.util.ArrayList;
import java.util.List;

public class ListeEvenements {

	private final List<Event> evenements = new ArrayList<>();

	public ListeEvenements(List<Event> events) {
		this.evenements.addAll(events);
	}

	public void ajouter(Event event) {
		evenements.add(event);
	}

	public boolean supprimerEvenement(EventId eventId) {
		return evenements.removeIf(e -> e.getId().equals(eventId));
	}

	public List<Event> getAll() {
		return evenements;
	}

	@Override
	public String toString() {
		if (evenements.isEmpty()) {
			return "Aucun événement trouvé.";
		}
		StringBuilder sb = new StringBuilder();
		for (Event event : evenements) {
			sb.append("- [").append(event.getId().value()).append("] ")
					.append(event.description())
					.append("\n");
		}
		return sb.toString();
	}

}




