package calendar.app;

import calendar.evenement.Event;
import calendar.evenement.ListeEvenements;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImporterJson {

	public static void importer(ListeEvenements listeEvenements, String nomFichier) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());

			if (!nomFichier.endsWith(".json")) {
				nomFichier += ".json";
			}

			String cheminComplet = "data/" + nomFichier;

			List<Event> events = mapper.readValue(new File(cheminComplet), mapper.getTypeFactory().constructCollectionType(List.class, Event.class));

			for (Event event : events) {
				listeEvenements.ajouter(event);
			}

			System.out.println("Événements importés depuis : " + cheminComplet);
		} catch (IOException e) {
			System.err.println("Erreur lors de l'import des événements : " + e.getMessage());
		}
	}
}