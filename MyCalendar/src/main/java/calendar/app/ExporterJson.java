package calendar.app;

import calendar.evenement.Event;
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

			if (!nomFichier.endsWith(".json")) {
				nomFichier += ".json";
			}

			String cheminComplet = "data/" + nomFichier;


			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(cheminComplet), events);
			System.out.println("Événements exportés dans : " + cheminComplet);
		} catch (IOException e) {
			System.err.println("Erreur lors de l'export des événements : " + e.getMessage());
		}
	}
}
