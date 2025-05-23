package calendar.app;

import calendar.evenement.Event;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExporterJson {

	public static void exporter(List<Event> events, String nomFichier) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

			File fichier = nomFichier.contains(File.separator)
					? new File(nomFichier)
					: new File("data/" + nomFichier);

			// Sérialisation avec TypeReference pour forcer le typage
			mapper.writerFor(new TypeReference<List<Event>>() {})
					.withDefaultPrettyPrinter()
					.writeValue(fichier, events);

			System.out.println("Événements exportés dans : " + fichier.getAbsolutePath());

		} catch (IOException e) {
			System.err.println("Erreur lors de l'export des événements : " + e.getMessage());
		}
	}
}
