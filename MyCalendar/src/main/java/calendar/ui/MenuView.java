package calendar.ui;

import calendar.app.CalendarManager;
import calendar.objet.EventId;
import calendar.objet.Utilisateur;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MenuView {

	private final VBox view;

	public MenuView(App app, CalendarManager calendarManager, Utilisateur utilisateur) {
		view = new VBox(10);
		view.setPadding(new Insets(20));

		Label label = new Label("Menu de " + utilisateur.identifiant());

		Button afficherEventsButton = new Button("Afficher les événements");
		Button ajouterRdvButton = new Button("Ajouter un rendez-vous personnel");
		Button ajouterReunionButton = new Button("Ajouter une réunion");
		Button ajouterEventPeriodiqueButton = new Button("Ajouter un événement périodique");
		Button ajouterEventPersoButton = new Button("Ajouter un événement personnalisé");
		Button exporterJsonButton = new Button("Exporter les événements en JSON");
		Button importerJsonButton = new Button("Importer les événements depuis un fichier JSON");
		Button supprimerEventButton = new Button("Supprimer un événement par son UUID");
		Button deconnecterButton = new Button("Se déconnecter");

		// 1 - Afficher les événements
		afficherEventsButton.setOnAction(e -> {
			new AfficherEvenementsView(calendarManager).show();
		});

		// 2 - Ajouter un rendez-vous personnel
		ajouterRdvButton.setOnAction(e -> {
			new AjouterRdvView(calendarManager, utilisateur).show();
		});

		// 3 - Ajouter une réunion
		ajouterReunionButton.setOnAction(e -> {
			new AjouterReunionView(calendarManager, utilisateur).show();
		});

		// 4 - Ajouter un événement périodique
		ajouterEventPeriodiqueButton.setOnAction(e -> {
			new AjouterEventPeriodiqueView(calendarManager, utilisateur).show();
		});

		// 5 - Ajouter un événement personnalisé (ex: Anniversaire, Voyage...)
		ajouterEventPersoButton.setOnAction(e -> {
			new AjouterEvenementPersoView(calendarManager, utilisateur).show();
		});


		// 8. Supprimer événement
		supprimerEventButton.setOnAction(e -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Supprimer un événement");
			dialog.setHeaderText("Entrez l'UUID de l'événement à supprimer :");
			dialog.showAndWait().ifPresent(uuid -> {
				boolean success = calendarManager.supprimerEvent(new EventId(uuid.trim()));
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText(success ? "Événement supprimé." : "UUID introuvable.");
				alert.showAndWait();
			});
		});

		// 9 - Se déconnecter
		deconnecterButton.setOnAction(e -> app.showConnexionView());

		view.getChildren().addAll(
				label,
				afficherEventsButton,
				ajouterRdvButton,
				ajouterReunionButton,
				ajouterEventPeriodiqueButton,
				ajouterEventPersoButton,
				exporterJsonButton,
				importerJsonButton,
				supprimerEventButton,
				deconnecterButton
		);
	}

	public VBox getView() {
		return view;
	}
}
