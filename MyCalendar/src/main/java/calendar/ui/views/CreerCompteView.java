package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;
import calendar.ui.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CreerCompteView {

	private final VBox view = new VBox(15);

	public CreerCompteView(App app, CalendarManager calendarManager) {

		view.setPadding(new Insets(30));
		view.setAlignment(Pos.CENTER);
		view.setStyle("-fx-background-color: #f0f4f8;");

		Label title = new Label("Créer un compte");
		title.setStyle("""
			-fx-font-size: 22px;
			-fx-font-weight: bold;
			-fx-text-fill: #333;
		""");

		TextField usernameField = new TextField();
		usernameField.setPromptText("Nom d'utilisateur");
		usernameField.setPrefWidth(250);

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Mot de passe");
		passwordField.setPrefWidth(250);

		PasswordField confirmField = new PasswordField();
		confirmField.setPromptText("Confirmer le mot de passe");
		confirmField.setPrefWidth(250);

		Button creerButton = new Button("Créer le compte");
		creerButton.setPrefWidth(200);

		String buttonStyle = """
			-fx-background-color: #4CAF50;
			-fx-text-fill: white;
			-fx-font-size: 14px;
			-fx-background-radius: 8px;
			-fx-cursor: hand;
		""";

		String buttonHoverStyle = """
			-fx-background-color: #45a049;
			-fx-text-fill: white;
			-fx-font-size: 14px;
			-fx-background-radius: 8px;
			-fx-cursor: hand;
		""";

		creerButton.setStyle(buttonStyle);
		creerButton.setOnMouseEntered(e -> creerButton.setStyle(buttonHoverStyle));
		creerButton.setOnMouseExited(e -> creerButton.setStyle(buttonStyle));

		Label feedback = new Label();
		feedback.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

		creerButton.setOnAction(e -> {
			String nom = usernameField.getText().trim();
			String mdp = passwordField.getText().trim();
			String confirmation = confirmField.getText().trim();

			if (nom.isEmpty() || mdp.isEmpty() || confirmation.isEmpty()) {
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

			calendarManager.getListeUtilisateurs().ajouter(new Utilisateur(nom, mdp));

			feedback.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
			feedback.setText("Compte créé avec succès !");

			new Thread(() -> {
				try {
					Thread.sleep(1200);
				} catch (InterruptedException ignored) {}
				javafx.application.Platform.runLater(app::showConnexionView);
			}).start();
		});

		Button retourButton = new Button("Retour");
		retourButton.setPrefWidth(200);
		retourButton.setStyle(buttonStyle);
		retourButton.setOnMouseEntered(e -> retourButton.setStyle(buttonHoverStyle));
		retourButton.setOnMouseExited(e -> retourButton.setStyle(buttonStyle));

		retourButton.setOnAction(e -> app.showConnexionView());

		view.getChildren().addAll(
				title,
				usernameField,
				passwordField,
				confirmField,
				creerButton,
				retourButton,
				feedback
		);
	}

	public VBox getView() {
		return view;
	}
}
