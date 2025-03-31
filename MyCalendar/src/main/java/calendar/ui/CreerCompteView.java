package calendar.ui;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreerCompteView {

	private final CalendarManager calendarManager;

	public CreerCompteView(CalendarManager calendarManager) {
		this.calendarManager = calendarManager;
	}

	public void show() {
		Stage stage = new Stage();
		VBox root = new VBox(10);
		root.setPadding(new javafx.geometry.Insets(20));

		TextField usernameField = new TextField();
		usernameField.setPromptText("Nom d'utilisateur");

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Mot de passe");

		PasswordField confirmField = new PasswordField();
		confirmField.setPromptText("Confirmer le mot de passe");

		Button creerButton = new Button("Créer");
		Label feedback = new Label();

		creerButton.setOnAction(e -> {
			String nom = usernameField.getText().trim();
			String mdp = passwordField.getText().trim();
			String confirmation = confirmField.getText().trim();

			if (nom.isEmpty() || mdp.isEmpty()) {
				feedback.setText("Veuillez remplir tous les champs.");
				return;
			}

			if (!mdp.equals(confirmation)) {
				feedback.setText("Les mots de passe ne correspondent pas.");
				return;
			}

			if (calendarManager.getListeUtilisateurs().contient(new Utilisateur(nom, mdp))) {
				feedback.setText("Ce nom d'utilisateur est déjà utilisé.");
				return;
			}

			Utilisateur nouvelUtilisateur = new Utilisateur(nom, mdp);
			calendarManager.getListeUtilisateurs().ajouter(nouvelUtilisateur);

			feedback.setText("Compte créé avec succès !");
		});

		root.getChildren().addAll(
				new Label("Créer un compte"),
				usernameField, passwordField, confirmField,
				creerButton, feedback
		);

		stage.setScene(new Scene(root, 350, 300));
		stage.setTitle("Créer un compte");
		stage.show();
	}
}