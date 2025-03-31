package calendar.ui;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private final CalendarManager calendarManager = new CalendarManager();
	private Stage primaryStage;

	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		showConnexionView();
	}

	public void showConnexionView() {
		ConnexionView connexionView = new ConnexionView(this, calendarManager);
		primaryStage.setScene(new Scene(connexionView.getView(), 400, 300));
		primaryStage.setTitle("Connexion");
		primaryStage.show();
	}

	public void showMenuView(Utilisateur utilisateur) {
		MenuView menuView = new MenuView(this, calendarManager, utilisateur);
		primaryStage.setScene(new Scene(menuView.getView(), 600, 400));
		primaryStage.setTitle("Menu - " + utilisateur.identifiant());
	}

	public void showCreerCompteView() {
		CreerCompteView creerCompteView = new CreerCompteView(calendarManager);
		creerCompteView.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}