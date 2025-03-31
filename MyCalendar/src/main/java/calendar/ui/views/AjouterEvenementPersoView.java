package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.evenement.EvenementPersonnalise;
import calendar.objet.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AjouterEvenementPersoView {

	private final CalendarManager calendarManager;
	private final Utilisateur utilisateur;

	public AjouterEvenementPersoView(CalendarManager calendarManager, Utilisateur utilisateur) {
		this.calendarManager = calendarManager;
		this.utilisateur = utilisateur;
	}

	public void show() {
		Stage stage = new Stage();
		stage.setTitle("Ajouter un événement personnalisé");

		VBox root = new VBox(20);
		root.setPadding(new Insets(30));
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #f5f5f5;");

		Label title = new Label("Nouvel Événement Personnalisé");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(12);
		form.setAlignment(Pos.CENTER);

		TextField titreField = new TextField();
		TextField typeField = new TextField();
		DatePicker datePicker = new DatePicker(LocalDate.now());
		Spinner<Integer> heureSpinner = new Spinner<>(0, 23, 9);
		Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
		Spinner<Integer> dureeSpinner = new Spinner<>(1, 240, 60);

		form.add(new Label("Titre :"), 0, 0);
		form.add(titreField, 1, 0);
		form.add(new Label("Type :"), 0, 1);
		form.add(typeField, 1, 1);
		form.add(new Label("Date :"), 0, 2);
		form.add(datePicker, 1, 2);
		form.add(new Label("Heure :"), 0, 3);
		form.add(heureSpinner, 1, 3);
		form.add(new Label("Minute :"), 0, 4);
		form.add(minuteSpinner, 1, 4);
		form.add(new Label("Durée (min) :"), 0, 5);
		form.add(dureeSpinner, 1, 5);

		Button ajouterBtn = new Button("Ajouter");
		Label feedback = new Label();
		ajouterBtn.setPrefWidth(200);

		String buttonStyle = """
		-fx-background-color: #4CAF50;
		-fx-text-fill: white;
		-fx-font-size: 14px;
		-fx-background-radius: 8px;
		-fx-cursor: hand;
	""";
		String hoverStyle = buttonStyle.replace("#4CAF50", "#45a049");

		ajouterBtn.setStyle(buttonStyle);
		ajouterBtn.setOnMouseEntered(e -> ajouterBtn.setStyle(hoverStyle));
		ajouterBtn.setOnMouseExited(e -> ajouterBtn.setStyle(buttonStyle));

		ajouterBtn.setOnAction(e -> {
			try {
				String titre = titreField.getText().trim();
				String typePerso = typeField.getText().trim();

				if (titre.isEmpty() || typePerso.isEmpty()) {
					feedback.setText("Champs requis.");
					return;
				}

				LocalDateTime dateHeure = LocalDateTime.of(
						datePicker.getValue(),
						java.time.LocalTime.of(heureSpinner.getValue(), minuteSpinner.getValue())
				);

				EvenementPersonnalise event = new EvenementPersonnalise(
						new EventId(utilisateur.identifiant() + "_perso_" + UUID.randomUUID()),
						new TitreEvenement(titre),
						new DateEvenement(dateHeure),
						new DureeEvenement(dureeSpinner.getValue()),
						new Proprietaire(utilisateur),
						typePerso
				);

				boolean ajoute = calendarManager.ajouterEvent(event);
				feedback.setText(ajoute ? "Événement ajouté." : "Conflit détecté.");
				if (ajoute) stage.close();

			} catch (Exception ex) {
				feedback.setText("Erreur : " + ex.getMessage());
			}
		});

		root.getChildren().addAll(title, form, ajouterBtn, feedback);

		stage.setScene(new Scene(root, 460, 420));
		stage.show();
	}

}
