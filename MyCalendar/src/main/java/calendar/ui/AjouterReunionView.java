package calendar.ui;

import calendar.app.CalendarManager;
import calendar.objet.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

	public AjouterReunionView(CalendarManager calendarManager, Utilisateur utilisateur) {
		this.calendarManager = calendarManager;
		this.utilisateur = utilisateur;
	}

	public void show() {
		Stage stage = new Stage();
		stage.setTitle("Ajouter une réunion");

		VBox root = new VBox(10);
		root.setPadding(new javafx.geometry.Insets(15));

		// Champs
		TextField titreField = new TextField();
		titreField.setPromptText("Titre");

		TextField lieuField = new TextField();
		lieuField.setPromptText("Lieu");

		TextField participantsField = new TextField();
		participantsField.setPromptText("Participants (séparés par virgules)");

		DatePicker datePicker = new DatePicker(LocalDate.now());
		Spinner<Integer> heureSpinner = new Spinner<>(0, 23, 9);
		Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
		Spinner<Integer> dureeSpinner = new Spinner<>(1, 480, 60);

		Button ajouterBtn = new Button("Ajouter");
		Label feedback = new Label();

		ajouterBtn.setOnAction(e -> {
			try {
				// Lecture des champs
				String titreInput = titreField.getText().trim();
				String lieuInput = lieuField.getText().trim();
				String participantsInput = participantsField.getText().trim();

				if (titreInput.isEmpty() || lieuInput.isEmpty() || participantsInput.isEmpty()) {
					feedback.setText("Tous les champs sont obligatoires.");
					return;
				}

				LocalDate date = datePicker.getValue();
				int heure = heureSpinner.getValue();
				int minute = minuteSpinner.getValue();
				int duree = dureeSpinner.getValue();

				LocalDateTime dateHeure = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), heure, minute);

				// Transformation en Value Objects
				TitreEvenement titre = new TitreEvenement(titreInput);
				Lieu lieu = new Lieu(lieuInput);
				DateEvenement dateEvenement = new DateEvenement(dateHeure);
				DureeEvenement dureeEvenement = new DureeEvenement(duree);
				Proprietaire proprietaire = new Proprietaire(utilisateur);

				List<Participants> participantsList = Arrays.stream(participantsInput.split(","))
						.map(String::trim)
						.filter(n -> !n.isEmpty())
						.map(Participants::new)
						.toList();

				// Création de la réunion
				Reunion reunion = new Reunion(
						new EventId(UUID.randomUUID().toString()),
						titre,
						dateEvenement,
						dureeEvenement,
						proprietaire,
						lieu,
						participantsList
				);

				boolean ajoute = calendarManager.ajouterEvent(reunion);
				if (ajoute) {
					stage.close();
				} else {
					feedback.setText("Conflit détecté. Réunion non ajoutée.");
				}
			} catch (Exception ex) {
				feedback.setText("Erreur : " + ex.getMessage());
			}
		});

		// Layout
		root.getChildren().addAll(
				new Label("Titre :"), titreField,
				new Label("Lieu :"), lieuField,
				new Label("Participants :"), participantsField,
				new Label("Date :"), datePicker,
				new Label("Heure :"), heureSpinner,
				new Label("Minute :"), minuteSpinner,
				new Label("Durée (min) :"), dureeSpinner,
				ajouterBtn, feedback
		);

		stage.setScene(new Scene(root, 450, 500));
		stage.show();
	}
}
