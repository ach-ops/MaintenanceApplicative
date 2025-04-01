package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.objet.ImportResult;
import calendar.objet.Utilisateur;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.function.Consumer;

public class ButtonGridView {
	private final GridPane view = new GridPane();
	private Consumer<Void> refreshAction = v -> {};

	public ButtonGridView(CalendarManager calendarManager, Utilisateur utilisateur) {
		view.setHgap(10);
		view.setVgap(10);
		view.setAlignment(Pos.CENTER);

		String style = """
			-fx-background-color: #4CAF50;
			-fx-text-fill: white;
			-fx-font-size: 14px;
			-fx-padding: 10px;
		""";

		String hover = "-fx-background-color: #45a049;";

		Button[] buttons = {
				new Button("Ajouter un rendez-vous personnel"),
				new Button("Ajouter une réunion"),
				new Button("Ajouter un événement périodique"),
				new Button("Ajouter un événement personnalisé"),
				new Button("Exporter les événements en JSON"),
				new Button("Importer les événements depuis JSON"),
		};

		for (Button b : buttons) {
			b.setStyle(style);
			b.setPrefWidth(250);
			b.setPrefHeight(60);
			b.setOnMouseEntered(e -> b.setStyle(style + hover));
			b.setOnMouseExited(e -> b.setStyle(style));
		}

		// Actions
		buttons[0].setOnAction(e -> {
			new AjouterRdvView(calendarManager, utilisateur, this::refresh).show();
		});

		buttons[1].setOnAction(e -> {
			new AjouterReunionView(calendarManager, utilisateur, this::refresh).show();
		});
		buttons[2].setOnAction(e -> {
			new AjouterEventPeriodiqueView(calendarManager, utilisateur, this::refresh).show();
		});
		buttons[3].setOnAction(e -> {
			new AjouterEvenementPersoView(calendarManager, utilisateur, this::refresh).show();
		});

		buttons[4].setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Exporter JSON");
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
			File file = fc.showSaveDialog(view.getScene().getWindow());
			if (file != null) {
				calendarManager.exporterVersJson(file.getAbsolutePath());
				alert("Événements exportés !");
			}
		});
		buttons[5].setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Importer JSON");
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
			File file = fc.showOpenDialog(view.getScene().getWindow());
			if (file != null) {
				ImportResult result = calendarManager.importerDepuisJson(file.getAbsolutePath());
				refresh();

				Alert alert = new Alert(result.contientImport() ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING);
				alert.setTitle("Résultat de l'import");
				alert.setHeaderText(null);
				alert.setContentText(result.messageUtilisateur());
				alert.showAndWait();
			}
		});


		int col = 0, row = 0;
		for (Button b : buttons) {
			view.add(b, col, row);
			col++;
			if (col == 3) {
				col = 0;
				row++;
			}
		}
	}

	public GridPane getView() {
		return view;
	}

	public void setRefreshAction(Consumer<Void> action) {
		this.refreshAction = action;
	}

	private void refresh() {
		refreshAction.accept(null);
	}

	private void alert(String message) {
		new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
	}
}
