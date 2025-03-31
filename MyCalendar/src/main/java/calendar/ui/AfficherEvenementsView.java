package calendar.ui;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AfficherEvenementsView {

	private final CalendarManager calendarManager;

	public AfficherEvenementsView(CalendarManager calendarManager) {
		this.calendarManager = calendarManager;
	}

	public void show() {
		Stage stage = new Stage();
		stage.setTitle("Liste des événements");

		VBox root = new VBox(10);
		root.setPadding(new Insets(15));

		TableView<Event> table = new TableView<>();

		// Colonne : Titre
		TableColumn<Event, String> titreCol = new TableColumn<>("Titre");
		titreCol.setCellValueFactory(data -> new SimpleStringProperty(
				data.getValue().getTitre().value())
		);

		// Colonne : Date (formatée)
		TableColumn<Event, String> dateCol = new TableColumn<>("Date");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		dateCol.setCellValueFactory(data -> new SimpleStringProperty(
				data.getValue().getDate().get().format(formatter))
		);

		// Colonne : Durée
		TableColumn<Event, String> dureeCol = new TableColumn<>("Durée");
		dureeCol.setCellValueFactory(data -> new SimpleStringProperty(
				data.getValue().getDuree() + " min")
		);

		// Colonne : Type (via description polymorphe)
		TableColumn<Event, String> typeCol = new TableColumn<>("Description");
		typeCol.setCellValueFactory(data -> new SimpleStringProperty(
				data.getValue().description())
		);

		table.getColumns().addAll(titreCol, dateCol, dureeCol, typeCol);

		List<Event> events = calendarManager.getTousLesEvenements();
		table.setItems(FXCollections.observableArrayList(events));

		root.getChildren().add(table);

		stage.setScene(new Scene(root, 700, 400));
		stage.show();
	}
}
