package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.objet.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AjouterReunionView {

	private final CalendarManager calendarManager;
	private final Utilisateur utilisateur;
	private final Runnable onSuccess;

	public AjouterReunionView(CalendarManager calendarManager, Utilisateur utilisateur, Runnable onSuccess) {
		this.calendarManager = calendarManager;
		this.utilisateur = utilisateur;
		this.onSuccess = onSuccess;

	}

	public void show() {
		Stage stage = new Stage();
		stage.setTitle("Ajouter une réunion");

		VBox root = new VBox(20);
		root.setPadding(new javafx.geometry.Insets(30));
		root.setStyle("-fx-background-color: #f5f5f5;");
		root.setAlignment(javafx.geometry.Pos.CENTER);

		Label title = new Label("Nouvelle Réunion");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(12);
		form.setAlignment(javafx.geometry.Pos.CENTER);

		TextField titreField = new TextField();
		TextField lieuField = new TextField();
		TextField participantsField = new TextField();
		DatePicker datePicker = new DatePicker(LocalDate.now());
		Spinner<Integer> heureSpinner = new Spinner<>(0, 23, 9);
		Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
		Spinner<Integer> dureeSpinner = new Spinner<>(1, 480, 60);

		form.add(new Label("Titre :"), 0, 0);
		form.add(titreField, 1, 0);
		form.add(new Label("Lieu :"), 0, 1);
		form.add(lieuField, 1, 1);
		form.add(new Label("Participants :"), 0, 2);
		form.add(participantsField, 1, 2);
		form.add(new Label("Date :"), 0, 3);
		form.add(datePicker, 1, 3);
		form.add(new Label("Heure :"), 0, 4);
		form.add(heureSpinner, 1, 4);
		form.add(new Label("Minute :"), 0, 5);
		form.add(minuteSpinner, 1, 5);
		form.add(new Label("Durée (min) :"), 0, 6);
		form.add(dureeSpinner, 1, 6);

		Button ajouterBtn = new Button("Ajouter");
		Label feedback = new Label();

		String btnStyle = """
		-fx-background-color: #4CAF50;
		-fx-text-fill: white;
		-fx-font-size: 14px;
		-fx-background-radius: 8px;
		-fx-cursor: hand;
	""";

		ajouterBtn.setStyle(btnStyle);
		ajouterBtn.setPrefWidth(200);
		ajouterBtn.setOnMouseEntered(e -> ajouterBtn.setStyle(btnStyle.replace("#4CAF50", "#45a049")));
		ajouterBtn.setOnMouseExited(e -> ajouterBtn.setStyle(btnStyle));

		ajouterBtn.setOnAction(e -> {
			try {
				String titreInput = titreField.getText().trim();
				String lieuInput = lieuField.getText().trim();
				String participantsInput = participantsField.getText().trim();

				if (titreInput.isEmpty() || lieuInput.isEmpty() || participantsInput.isEmpty()) {
					feedback.setText("Tous les champs sont obligatoires.");
					feedback.setStyle("-fx-text-fill: red;");
					return;
				}

				LocalDateTime dateHeure = LocalDateTime.of(
						datePicker.getValue(),
						java.time.LocalTime.of(heureSpinner.getValue(), minuteSpinner.getValue())
				);

				List<Participants> participantsList = Arrays.stream(participantsInput.split(","))
						.map(String::trim)
						.filter(n -> !n.isEmpty())
						.map(Participants::new)
						.toList();

				Reunion reunion = new Reunion(
						new EventId(UUID.randomUUID().toString()),
						new TitreEvenement(titreInput),
						new DateEvenement(dateHeure),
						new DureeEvenement(dureeSpinner.getValue()),
						new Proprietaire(utilisateur),
						new Lieu(lieuInput),
						participantsList
				);

				boolean ajoute = calendarManager.ajouterEvent(reunion);
				if (ajoute) {
					if (onSuccess != null) onSuccess.run();
					stage.close();
				}
				else {
					feedback.setText("Conflit détecté. Réunion non ajoutée.");
					feedback.setStyle("-fx-text-fill: red;");
				}
			} catch (Exception ex) {
				feedback.setText("Erreur : " + ex.getMessage());
				feedback.setStyle("-fx-text-fill: red;");
			}
		});

		root.getChildren().addAll(title, form, ajouterBtn, feedback);

		stage.setScene(new Scene(root, 500, 520));
		stage.show();
	}

}
