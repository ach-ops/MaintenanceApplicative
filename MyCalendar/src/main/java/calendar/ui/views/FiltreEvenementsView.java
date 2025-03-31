package calendar.ui.views;

import calendar.app.CalendarManager;
import calendar.evenement.Event;
import calendar.objet.DateEvenement;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

public class FiltreEvenementsView extends VBox {

	private final CalendarManager calendarManager;
	private final TableView<Event> table;

	private final ComboBox<String> filtreCombo = new ComboBox<>();
	private final DatePicker datePicker1 = new DatePicker();
	private final DatePicker datePicker2 = new DatePicker();
	private final Spinner<Integer> semaineSpinner = new Spinner<>(1, 53, 1);
	private final Spinner<Integer> anneeSpinner = new Spinner<>(1900, 2100, LocalDate.now().getYear());
	private final Button appliquerBtn = new Button("Appliquer le filtre");

	private final HBox dynamicInputs = new HBox(10);

	public FiltreEvenementsView(CalendarManager calendarManager, TableView<Event> table) {
		this.calendarManager = calendarManager;
		this.table = table;

		setSpacing(15);
		setPadding(new Insets(20));
		setStyle("-fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 8px; -fx-background-radius: 8px;");
		setAlignment(Pos.CENTER_LEFT);

		Label titre = new Label("Filtrer les événements");
		titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

		// Setup combo
		filtreCombo.setItems(FXCollections.observableArrayList(
				"Tous les événements",
				"Par jour",
				"Par semaine",
				"Par mois",
				"Par période personnalisée"
		));
		filtreCombo.setValue("Tous les événements");
		filtreCombo.setPrefWidth(300);
		filtreCombo.setStyle("-fx-font-size: 14px;");
		filtreCombo.setOnAction(e -> updateInputFields());

		dynamicInputs.setAlignment(Pos.CENTER_LEFT);
		dynamicInputs.setPadding(new Insets(5, 0, 0, 0));

		appliquerBtn.setStyle("""
			-fx-background-color: #4CAF50;
			-fx-text-fill: white;
			-fx-font-size: 14px;
			-fx-background-radius: 6px;
			-fx-padding: 8px 16px;
		""");
		appliquerBtn.setOnMouseEntered(e -> appliquerBtn.setStyle(appliquerBtn.getStyle().replace("#4CAF50", "#45a049")));
		appliquerBtn.setOnMouseExited(e -> appliquerBtn.setStyle(appliquerBtn.getStyle().replace("#45a049", "#4CAF50")));

		appliquerBtn.setOnAction(e -> filtrer());

		getChildren().addAll(titre, filtreCombo, dynamicInputs, appliquerBtn);

		updateInputFields();
	}

	private void updateInputFields() {
		dynamicInputs.getChildren().clear();

		switch (filtreCombo.getValue()) {
			case "Par jour" -> dynamicInputs.getChildren().add(datePicker1);
			case "Par semaine" -> dynamicInputs.getChildren().addAll(new Label("Semaine :"), semaineSpinner, new Label("Année :"), anneeSpinner);
			case "Par mois" -> dynamicInputs.getChildren().addAll(new Label("Mois :"), datePicker1);
			case "Par période personnalisée" -> dynamicInputs.getChildren().addAll(new Label("Début :"), datePicker1, new Label("Fin :"), datePicker2);
			default -> dynamicInputs.getChildren().clear();
		}
	}

	private void filtrer() {
		List<Event> resultat;

		try {
			switch (filtreCombo.getValue()) {
				case "Par jour" -> {
					LocalDate date = datePicker1.getValue();
					LocalDateTime debut = date.atStartOfDay();
					LocalDateTime fin = date.plusDays(1).atStartOfDay().minusSeconds(1);
					resultat = calendarManager.eventsDansPeriode(new DateEvenement(debut), new DateEvenement(fin));
				}
				case "Par semaine" -> {
					int semaine = semaineSpinner.getValue();
					int annee = anneeSpinner.getValue();
					LocalDateTime debut = LocalDate.now()
							.withYear(annee)
							.with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
							.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
							.atStartOfDay();
					LocalDateTime fin = debut.plusDays(6).withHour(23).withMinute(59);
					resultat = calendarManager.eventsDansPeriode(new DateEvenement(debut), new DateEvenement(fin));
				}
				case "Par mois" -> {
					LocalDate date = datePicker1.getValue();
					LocalDateTime debut = date.withDayOfMonth(1).atStartOfDay();
					LocalDateTime fin = debut.plusMonths(1).minusSeconds(1);
					resultat = calendarManager.eventsDansPeriode(new DateEvenement(debut), new DateEvenement(fin));
				}
				case "Par période personnalisée" -> {
					LocalDateTime debut = datePicker1.getValue().atStartOfDay();
					LocalDateTime fin = datePicker2.getValue().atTime(23, 59);
					resultat = calendarManager.eventsDansPeriode(new DateEvenement(debut), new DateEvenement(fin));
				}
				default -> resultat = calendarManager.getTousLesEvenements();
			}

			table.getItems().setAll(resultat);
		} catch (Exception e) {
			new Alert(Alert.AlertType.ERROR, "Erreur de filtre : " + e.getMessage()).showAndWait();
		}
	}
}
