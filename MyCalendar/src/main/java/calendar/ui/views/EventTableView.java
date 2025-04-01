package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.DateEvenement;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.List;

public class EventTableView {

	private final TableView<Event> table = new TableView<>();
	private final VBox container = new VBox(table);
	private final CalendarManager calendarManager;

	public EventTableView(CalendarManager calendarManager) {
		this.calendarManager = calendarManager;

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
		table.setPrefHeight(400);
		table.setStyle("-fx-border-color: #ccc; -fx-border-radius: 8px; -fx-background-radius: 8px;");

		String colStyle = "-fx-alignment: CENTER; -fx-font-size: 13px;";

		TableColumn<Event, String> uuidCol = new TableColumn<>("UUID");
		uuidCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().value()));
		uuidCol.setStyle(colStyle);

		TableColumn<Event, String> titreCol = new TableColumn<>("Titre");
		titreCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre().value()));
		titreCol.setStyle(colStyle);

		TableColumn<Event, String> dateCol = new TableColumn<>("Date");
		dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
		dateCol.setStyle(colStyle);

		TableColumn<Event, String> dureeCol = new TableColumn<>("Durée");
		dureeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDuree() + " min"));
		dureeCol.setStyle(colStyle);

		TableColumn<Event, String> typeCol = new TableColumn<>("Type");
		typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().description()));
		typeCol.setStyle(colStyle);

		table.getColumns().addAll(uuidCol, titreCol, dateCol, dureeCol, typeCol);

		// clic droit pour supprimer
		table.setRowFactory(tv -> {
			TableRow<Event> row = new TableRow<>();
			ContextMenu contextMenu = new ContextMenu();
			MenuItem supprimerItem = new MenuItem("Supprimer");

			supprimerItem.setOnAction(e -> {
				Event event = row.getItem();
				if (event != null) {
					boolean success = calendarManager.supprimerEvent(event.getId());
					if (success) {
						table.getItems().remove(event);
						new Alert(Alert.AlertType.INFORMATION, "Événement supprimé.").showAndWait();
					} else {
						new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression.").showAndWait();
					}
				}
			});
			contextMenu.getItems().add(supprimerItem);
			row.setContextMenu(contextMenu);

			return row;
		});

		refresh();
	}

	public TableView<Event> getTable() {
		return table;
	}

	public VBox getView() {
		return container;
	}

	public void refresh() {
		LocalDateTime maintenant = LocalDateTime.now();
		DateEvenement debut = new DateEvenement(maintenant.minusMonths(1));
		DateEvenement fin = new DateEvenement(maintenant.plusMonths(9));// 9 mois
		List<Event> affichables = calendarManager.eventsDansPeriode(debut, fin);
		table.getItems().setAll(affichables);
	}
}
