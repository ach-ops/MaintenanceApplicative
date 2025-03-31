package calendar.ui;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.Utilisateur;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuView {

	private final VBox view;
	private final TableView<Event> table = new TableView<>();

	private final String buttonStyle = """
        -fx-background-color: #4CAF50;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10px;
        -fx-alignment: center;
        """;

	private final String labelStyle = """
        -fx-font-size: 24px;
        -fx-font-weight: bold;
        -fx-padding: 0 0 20px 0;
        """;

	public MenuView(App app, CalendarManager calendarManager, Utilisateur utilisateur) {
		view = new VBox(15);
		view.setPadding(new Insets(30));
		view.setStyle("-fx-background-color: #f5f5f5;");

		// Déconnexion
		Button logoutButton = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/calendar/ui/deconnexion.png"), 20, 20, true, true)));
		logoutButton.setStyle(buttonStyle + "-fx-min-width: 40px;");
		logoutButton.setOnAction(e -> app.showConnexionView());
		HBox topBar = new HBox(logoutButton);
		topBar.setAlignment(Pos.TOP_RIGHT);

		Label label = new Label("Menu de " + utilisateur.identifiant());
		label.setStyle(labelStyle);

		// Grille de boutons
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);

		Button[] buttons = {
				new Button("Ajouter un rendez-vous personnel"),
				new Button("Ajouter une réunion"),
				new Button("Ajouter un événement périodique"),
				new Button("Ajouter un événement personnalisé"),
				new Button("Exporter les événements en JSON"),
				new Button("Importer les événements depuis JSON"),
		};

		int col = 0;
		int row = 0;
		for (Button button : buttons) {
			button.setStyle(buttonStyle);
			button.setPrefWidth(250);
			button.setPrefHeight(60);
			button.setMaxWidth(250);
			button.setMinWidth(250);
			button.setMaxHeight(60);
			button.setMinHeight(60);
			button.setAlignment(Pos.CENTER);
			button.setOnMouseEntered(e -> button.setStyle(buttonStyle + "-fx-background-color: #45a049;"));
			button.setOnMouseExited(e -> button.setStyle(buttonStyle));
			gridPane.add(button, col, row);
			col++;
			if (col == 3) {
				col = 0;
				row++;
			}
		}

		// TableView des événements
		configureTable(calendarManager);

		refreshTable(calendarManager);

		// Handlers
		buttons[0].setOnAction(e -> {
			new AjouterRdvView(calendarManager, utilisateur).show();
			refreshTable(calendarManager);
		});
		buttons[1].setOnAction(e -> {
			new AjouterReunionView(calendarManager, utilisateur).show();
			refreshTable(calendarManager);
		});
		buttons[2].setOnAction(e -> {
			new AjouterEventPeriodiqueView(calendarManager, utilisateur).show();
			refreshTable(calendarManager);
		});
		buttons[3].setOnAction(e -> {
			new AjouterEvenementPersoView(calendarManager, utilisateur).show();
			refreshTable(calendarManager);
		});
		buttons[4].setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Exporter les événements en JSON");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
			File file = fileChooser.showSaveDialog(view.getScene().getWindow());

			if (file != null) {
				calendarManager.exporterVersJson(file.getAbsolutePath());
				new Alert(Alert.AlertType.INFORMATION, "Événements exportés avec succès !").showAndWait();
			}
		});
		buttons[5].setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Importer des événements depuis un fichier JSON");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
			File file = fileChooser.showOpenDialog(view.getScene().getWindow());

			if (file != null) {
				calendarManager.importerDepuisJson(file.getAbsolutePath());
				refreshTable(calendarManager);
				new Alert(Alert.AlertType.INFORMATION, "Événements importés avec succès !").showAndWait();
			}
		});

		view.getChildren().addAll(topBar, label, gridPane, table);
		view.setAlignment(Pos.TOP_CENTER);
	}

	private void configureTable(CalendarManager calendarManager) {
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(300);

		TableColumn<Event, String> uuidCol = new TableColumn<>("UUID");
		uuidCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId().value()));

		TableColumn<Event, String> titreCol = new TableColumn<>("Titre");
		titreCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre().value()));

		TableColumn<Event, String> dateCol = new TableColumn<>("Date");
		dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));

		TableColumn<Event, String> dureeCol = new TableColumn<>("Durée");
		dureeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDuree()+ " min"));

		TableColumn<Event, String> typeCol = new TableColumn<>("Type");
		typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().description()));

		table.getColumns().addAll(uuidCol, titreCol, dateCol, dureeCol, typeCol);

		table.setRowFactory(tv -> {
			TableRow<Event> row = new TableRow<>();
			ContextMenu contextMenu = new ContextMenu();

			MenuItem supprimerItem = new MenuItem("Supprimer cet événement");
			supprimerItem.setOnAction(e -> {
				Event event = row.getItem();
				if (event != null) {
					boolean success = calendarManager.supprimerEvent(event.getId());
					if (success) {
						table.getItems().remove(event);
						new Alert(Alert.AlertType.INFORMATION, "Événement supprimé.").showAndWait();
					} else {
						new Alert(Alert.AlertType.ERROR, "Échec de la suppression.").showAndWait();
					}
				}
			});

			contextMenu.getItems().add(supprimerItem);
			row.setContextMenu(contextMenu);
			return row;
		});

	}

	private void refreshTable(CalendarManager calendarManager) {
		table.getItems().setAll(calendarManager.getTousLesEvenements());
	}

	public VBox getView() {
		return view;
	}
}
