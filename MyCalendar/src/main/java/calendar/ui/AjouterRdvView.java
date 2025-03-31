package calendar.ui;

import calendar.app.CalendarManager;
import calendar.objet.RendezVous;
import calendar.objet.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AjouterRdvView {

	private final CalendarManager calendarManager;
	private final Utilisateur utilisateur;

	public AjouterRdvView(CalendarManager calendarManager, Utilisateur utilisateur) {
		this.calendarManager = calendarManager;
		this.utilisateur = utilisateur;
	}

	public void show() {
		Stage stage = new Stage();
		stage.setTitle("Ajouter un rendez-vous personnel");

		VBox root = new VBox(10);
		root.setPadding(new javafx.geometry.Insets(15));

		TextField titreField = new TextField();
		titreField.setPromptText("Titre");

		DatePicker datePicker = new DatePicker(LocalDate.now());

		Spinner<Integer> heureSpinner = new Spinner<>(0, 23, 9);
		Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
		Spinner<Integer> dureeSpinner = new Spinner<>(1, 240, 30);

		Button ajouterBtn = new Button("Ajouter");
		Label feedback = new Label();

		ajouterBtn.setOnAction(e -> {
			try {
				String titre = titreField.getText().trim();
				if (titre.isEmpty()) {
					feedback.setText("Le titre ne peut pas être vide.");
					return;
				}

				LocalDate date = datePicker.getValue();
				int heure = heureSpinner.getValue();
				int minute = minuteSpinner.getValue();
				int duree = dureeSpinner.getValue();

				LocalDateTime dateHeure = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), heure, minute);

				RendezVous rdv = new RendezVous(
						new EventId(utilisateur.identifiant() + "_" + UUID.randomUUID()),
						new TitreEvenement(titre),
						new DateEvenement(dateHeure),
						new DureeEvenement(duree),
						new Proprietaire(utilisateur)
				);

				boolean ajoute = calendarManager.ajouterEvent(rdv);
				feedback.setText(ajoute ? "Rendez-vous ajouté." : "Conflit détecté, ajout refusé.");
				if (ajoute) stage.close();

			} catch (Exception ex) {
				feedback.setText("Erreur : " + ex.getMessage());
			}
		});

		root.getChildren().addAll(
				new Label("Titre du rendez-vous :"), titreField,
				new Label("Date :"), datePicker,
				new Label("Heure :"), heureSpinner,
				new Label("Minute :"), minuteSpinner,
				new Label("Durée (en minutes) :"), dureeSpinner,
				ajouterBtn, feedback
		);

		stage.setScene(new Scene(root, 400, 450));
		stage.show();
	}
}
