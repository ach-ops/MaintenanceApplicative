package calendar.ui;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;
import calendar.ui.views.ConnexionView;
import calendar.ui.views.CreerCompteView;
import calendar.ui.views.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

	private final CalendarManager calendarManager = new CalendarManager();
	private Stage primaryStage;
	private Scene scene;

	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		this.scene = new Scene(new VBox(), 800, 600);
		primaryStage.setScene(scene);
		showConnexionView(); // remplit le contenu
		primaryStage.setTitle("Connexion");
		primaryStage.show();
	}

	public void showConnexionView() {
		ConnexionView connexionView = new ConnexionView(this, calendarManager);
		scene.setRoot(connexionView.getView());
		primaryStage.setTitle("Connexion");
	}

	public void showMenuView(Utilisateur utilisateur) {
		MenuView menuView = new MenuView(this, calendarManager, utilisateur);
		scene.setRoot(menuView.getView());
		primaryStage.setTitle("Menu - " + utilisateur.identifiant());
	}

	public void showCreerCompteView() {
		scene.setRoot(new CreerCompteView(this, calendarManager).getView());
		primaryStage.setTitle("Créer un compte");
	}


	public static void main(String[] args) {
		launch(args);
	}
}
