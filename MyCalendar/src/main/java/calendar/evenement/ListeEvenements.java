package calendar.evenement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import calendar.objet.Utilisateur;

public class ListeEvenements {

	private final List<Event> evenements;

	public ListeEvenements(List<Event> evenements) {
		this.evenements = new ArrayList<>(evenements);
	}

	public void ajouter(Event event) {
		evenements.add(event);
	}

	public List<Event> tous() {
		return Collections.unmodifiableList(evenements);
	}

	public boolean isEmpty() {
		return evenements.isEmpty();
	}

	public ListeEvenements filtrerParUtilisateur(Utilisateur utilisateur) {
		List<Event> filtres = evenements.stream()
				.filter(e -> e.getProprietaire().utilisateur().equals(utilisateur))
				.collect(Collectors.toList());
		return new ListeEvenements(filtres);
	}

	public ListeEvenements conflitsAvec(Event nouvelEvent) {
		List<Event> enConflit = evenements.stream()
				.filter(e -> e.estEnConflitAvec(nouvelEvent))
				.collect(Collectors.toList());
		return new ListeEvenements(enConflit);
	}

	@Override
	public String toString() {
		if (evenements.isEmpty()) return "Aucun événement.";
		return evenements.stream()
				.map(Event::description)
				.collect(Collectors.joining("\n"));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ListeEvenements that)) return false;
		return Objects.equals(evenements, that.evenements);
	}

	@Override
	public int hashCode() {
		return Objects.hash(evenements);
	}
}
