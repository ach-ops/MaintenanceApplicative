package calendar.ui;

import calendar.app.CalendarManager;
import calendar.evenement.EvenementPeriodique;
import calendar.objet.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AjouterEventPeriodiqueView {

	private final CalendarManager calendarManager;
	private final Utilisateur utilisateur;

	public AjouterEventPeriodiqueView(CalendarManager calendarManager, Utilisateur utilisateur) {
		this.calendarManager = calendarManager;
		this.utilisateur = utilisateur;
	}

	public void show() {
		Stage stage = new Stage();
		stage.setTitle("Ajouter un événement périodique");

		VBox root = new VBox(10);
		root.setPadding(new javafx.geometry.Insets(15));

		TextField titreField = new TextField();
		titreField.setPromptText("Titre");

		DatePicker datePicker = new DatePicker(LocalDate.now());
		Spinner<Integer> heureSpinner = new Spinner<>(0, 23, 9);
		Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
		Spinner<Integer> dureeSpinner = new Spinner<>(1, 480, 60);
		Spinner<Integer> frequenceSpinner = new Spinner<>(1, 365, 7);

		Button ajouterBtn = new Button("Ajouter");
		Label feedback = new Label();

		ajouterBtn.setOnAction(e -> {
			try {
				String titre = titreField.getText().trim();
				if (titre.isEmpty()) {
					feedback.setText("Le titre est obligatoire.");
					return;
				}

				LocalDate date = datePicker.getValue();
				int heure = heureSpinner.getValue();
				int minute = minuteSpinner.getValue();
				int duree = dureeSpinner.getValue();
				int frequence = frequenceSpinner.getValue();

				LocalDateTime dateHeure = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), heure, minute);

				// Création de l’événement
				EvenementPeriodique evenement = new EvenementPeriodique(
						new EventId(utilisateur.identifiant() + "_periodique_" + UUID.randomUUID()),
						new TitreEvenement(titre),
						new DateEvenement(dateHeure),
						new DureeEvenement(duree),
						new Proprietaire(utilisateur),
						new FrequenceEvenement(frequence)
				);

				boolean ajoute = calendarManager.ajouterEvent(evenement);
				if (ajoute) {
					stage.close();
				} else {
					feedback.setText("Conflit détecté. Événement non ajouté.");
				}

			} catch (Exception ex) {
				feedback.setText("Erreur : " + ex.getMessage());
			}
		});

		root.getChildren().addAll(
				new Label("Titre :"), titreField,
				new Label("Date :"), datePicker,
				new Label("Heure :"), heureSpinner,
				new Label("Minute :"), minuteSpinner,
				new Label("Durée (minutes) :"), dureeSpinner,
				new Label("Fréquence (jours) :"), frequenceSpinner,
				ajouterBtn, feedback
		);

		stage.setScene(new Scene(root, 450, 500));
		stage.show();
	}
}
