package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.objet.Utilisateur;
import calendar.ui.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class MenuView {

	private final VBox view;

	public MenuView(App app, CalendarManager calendarManager, Utilisateur utilisateur) {
		view = new VBox(20);
		view.setPadding(new Insets(30));
		view.setAlignment(Pos.TOP_CENTER);
		view.setStyle("-fx-background-color: #f5f5f5;");

		// Top Bar
		Button logout = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/calendar/ui/deconnexion.png"), 20, 20, true, true)));
		logout.setStyle("-fx-background-color: transparent;");
		logout.setOnAction(e -> app.showConnexionView());
		HBox topBar = new HBox(logout);
		topBar.setAlignment(Pos.TOP_RIGHT);
		topBar.setMaxWidth(Double.MAX_VALUE);

		// Titre
		Label titre = new Label("Menu de " + utilisateur.identifiant());
		titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

		// Composants
		ButtonGridView buttonGrid = new ButtonGridView(calendarManager, utilisateur);
		EventTableView eventTable = new EventTableView(calendarManager);
		buttonGrid.setRefreshAction(v -> eventTable.refresh());
		FiltreEvenementsView filtreView = new FiltreEvenementsView(calendarManager, eventTable.getTable());

		buttonGrid.setRefreshAction(v -> eventTable.refresh());

		view.getChildren().addAll(topBar, titre, buttonGrid.getView(), filtreView, eventTable.getView());

	}

	public VBox getView() {
		return view;
	}
}
