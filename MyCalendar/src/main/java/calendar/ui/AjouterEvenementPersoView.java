package calendar.ui;

import calendar.app.CalendarManager;
import calendar.evenement.EvenementPersonnalise;
import calendar.objet.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

		VBox root = new VBox(10);
		root.setPadding(new javafx.geometry.Insets(15));

		TextField titreField = new TextField();
		titreField.setPromptText("Titre");

		TextField typeField = new TextField();
		typeField.setPromptText("Type (ex : Anniversaire, Voyage...)");

		DatePicker datePicker = new DatePicker(LocalDate.now());
		Spinner<Integer> heureSpinner = new Spinner<>(0, 23, 9);
		Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
		Spinner<Integer> dureeSpinner = new Spinner<>(1, 240, 60);

		Button ajouterBtn = new Button("Ajouter");
		Label feedback = new Label();

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

		root.getChildren().addAll(
				new Label("Titre :"), titreField,
				new Label("Type personnalisé :"), typeField,
				new Label("Date :"), datePicker,
				new Label("Heure :"), heureSpinner,
				new Label("Minute :"), minuteSpinner,
				new Label("Durée (min) :"), dureeSpinner,
				ajouterBtn, feedback
		);

		stage.setScene(new Scene(root, 450, 480));
		stage.show();
	}
}
