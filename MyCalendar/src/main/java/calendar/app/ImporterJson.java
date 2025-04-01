package calendar.app;

import calendar.evenement.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImporterJson {

	public static List<Event> importer(String nomFichier) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		File fichier = nomFichier.contains(File.separator) ? new File(nomFichier) : new File("data/" + nomFichier);

		return mapper.readValue(fichier,
				mapper.getTypeFactory().constructCollectionType(List.class, Event.class));
	}

}