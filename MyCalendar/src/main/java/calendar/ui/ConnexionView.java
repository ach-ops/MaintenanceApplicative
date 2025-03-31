package calendar.ui;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Optional;

public class ConnexionView {

	private final VBox view;

	public ConnexionView(App app, CalendarManager calendarManager) {
		view = new VBox(10);
		view.setPadding(new Insets(20));

		Label label = new Label("Connexion");
		TextField usernameField = new TextField();
		usernameField.setPromptText("Nom d'utilisateur");

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Mot de passe");

		Button loginButton = new Button("Se connecter");
		Button creerCompteButton = new Button("Créer un compte");

		Label messageLabel = new Label();

		loginButton.setOnAction(e -> {
			String nom = usernameField.getText().trim();
			String mdp = passwordField.getText().trim();

			Optional<Utilisateur> user = calendarManager.getListeUtilisateurs().trouver(nom, mdp);
			if (user.isPresent()) {
				app.showMenuView(user.get());
			} else {
				messageLabel.setText("Échec de la connexion.");
			}
		});

		creerCompteButton.setOnAction(e -> app.showCreerCompteView());

		view.getChildren().addAll(label, usernameField, passwordField, loginButton, creerCompteButton, messageLabel);
	}

	public VBox getView() {
		return view;
	}
}