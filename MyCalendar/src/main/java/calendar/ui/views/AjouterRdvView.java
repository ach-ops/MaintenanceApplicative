package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.objet.RendezVous;
import calendar.objet.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AjouterRdvView {

	private final CalendarManager calendarManager;
	private final Utilisateur utilisateur;
	private final Runnable onSuccess;

	public AjouterRdvView(CalendarManager calendarManager, Utilisateur utilisateur, Runnable onSuccess) {
		this.calendarManager = calendarManager;
		this.utilisateur = utilisateur;
		this.onSuccess = onSuccess;
	}

	public void show() {
		Stage stage = new Stage();
		stage.setTitle("Ajouter un rendez-vous personnel");

		VBox root = new VBox(20);
		root.setPadding(new javafx.geometry.Insets(30));
		root.setStyle("-fx-background-color: #f5f5f5;");
		root.setAlignment(javafx.geometry.Pos.CENTER);

		Label title = new Label("Nouveau Rendez-vous");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(12);
		form.setAlignment(javafx.geometry.Pos.CENTER);

		TextField titreField = new TextField();
		DatePicker datePicker = new DatePicker(LocalDate.now());
		Spinner<Integer> heureSpinner = new Spinner<>(0, 23, 9);
		Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
		Spinner<Integer> dureeSpinner = new Spinner<>(1, 240, 30);

		form.add(new Label("Titre :"), 0, 0);
		form.add(titreField, 1, 0);
		form.add(new Label("Date :"), 0, 1);
		form.add(datePicker, 1, 1);
		form.add(new Label("Heure :"), 0, 2);
		form.add(heureSpinner, 1, 2);
		form.add(new Label("Minute :"), 0, 3);
		form.add(minuteSpinner, 1, 3);
		form.add(new Label("Durée (min) :"), 0, 4);
		form.add(dureeSpinner, 1, 4);

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
				String titre = titreField.getText().trim();
				if (titre.isEmpty()) {
					feedback.setText("Le titre est requis.");
					return;
				}

				LocalDate date = datePicker.getValue();
				int heure = heureSpinner.getValue();
				int minute = minuteSpinner.getValue();
				int duree = dureeSpinner.getValue();

				LocalDateTime dateHeure = LocalDateTime.of(date, java.time.LocalTime.of(heure, minute));

				RendezVous rdv = new RendezVous(
						new EventId(utilisateur.identifiant() + "_" + UUID.randomUUID()),
						new TitreEvenement(titre),
						new DateEvenement(dateHeure),
						new DureeEvenement(duree),
						new Proprietaire(utilisateur)
				);

				boolean ajoute = calendarManager.ajouterEvent(rdv);
				feedback.setStyle("-fx-text-fill: " + (ajoute ? "green" : "red") + "; -fx-font-weight: bold;");
				feedback.setText(ajoute ? "Rendez-vous ajouté !" : "Conflit détecté. Ajout refusé.");

				if (ajoute) {
					if (onSuccess != null) onSuccess.run();
					stage.close();
				}


			} catch (Exception ex) {
				feedback.setStyle("-fx-text-fill: red;");
				feedback.setText("Erreur : " + ex.getMessage());
			}
		});

		root.getChildren().addAll(title, form, ajouterBtn, feedback);

		stage.setScene(new Scene(root, 450, 450));
		stage.show();
	}

}
