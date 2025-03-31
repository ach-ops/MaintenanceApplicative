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

			File fichier;

			if (nomFichier.contains(File.separator)) {
				fichier = new File(nomFichier);
			} else {
				fichier = new File("data/" + nomFichier);
			}

			List<Event> events = mapper.readValue(fichier,
					mapper.getTypeFactory().constructCollectionType(List.class, Event.class));

			for (Event event : events) {
				listeEvenements.ajouter(event);
			}

			System.out.println("Événements importés depuis : " + fichier.getAbsolutePath());

		} catch (IOException e) {
			System.err.println("Erreur lors de l'import des événements : " + e.getMessage());
		}
	}
}