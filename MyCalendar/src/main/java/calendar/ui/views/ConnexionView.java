package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;
import calendar.ui.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Optional;

public class ConnexionView {

	private final VBox view;

	public ConnexionView(App app, CalendarManager calendarManager) {
		view = new VBox(20);
		view.setPadding(new Insets(40));
		view.setAlignment(Pos.CENTER);
		view.setStyle("-fx-background-color: #f0f4f8;");

		Label label = new Label("Bienvenue sur CalendarManager");
		label.setStyle("""
		    -fx-font-size: 24px;
		    -fx-font-weight: bold;
		    -fx-text-fill: #333;
		""");

		TextField usernameField = new TextField();
		usernameField.setPromptText("Nom d'utilisateur");
		usernameField.setPrefWidth(250);

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Mot de passe");
		passwordField.setPrefWidth(250);

		Button loginButton = new Button("Se connecter");
		Button creerCompteButton = new Button("Créer un compte");

		String buttonStyle = """
    -fx-background-color: #4CAF50;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-padding: 8px 16px;
    -fx-background-radius: 8px;
    -fx-cursor: hand;
""";

		String buttonHoverStyle = """
    -fx-background-color: #45a049;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-padding: 8px 16px;
    -fx-background-radius: 8px;
    -fx-cursor: hand;
""";

		loginButton.setStyle(buttonStyle);
		creerCompteButton.setStyle(buttonStyle);

		loginButton.setOnMouseEntered(e -> loginButton.setStyle(buttonHoverStyle));
		loginButton.setOnMouseExited(e -> loginButton.setStyle(buttonStyle));

		creerCompteButton.setOnMouseEntered(e -> creerCompteButton.setStyle(buttonHoverStyle));
		creerCompteButton.setOnMouseExited(e -> creerCompteButton.setStyle(buttonStyle));

		loginButton.setPrefWidth(140);
		creerCompteButton.setPrefWidth(140);


		HBox buttonBox = new HBox(10, loginButton, creerCompteButton);
		buttonBox.setAlignment(Pos.CENTER);

		Label messageLabel = new Label();
		messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

		//  Connexion
		loginButton.setOnAction(e -> {
			String nom = usernameField.getText().trim();
			String mdp = passwordField.getText().trim();

			Optional<Utilisateur> user = calendarManager.getListeUtilisateurs().trouver(nom, mdp);
			if (user.isPresent()) {
				app.showMenuView(user.get());
			} else {
				messageLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
			}
		});

		//  Création de compte
		creerCompteButton.setOnAction(e -> app.showCreerCompteView());

		view.getChildren().addAll(label, usernameField, passwordField, buttonBox, messageLabel);
	}

	public VBox getView() {
		return view;
	}
}
