package calendar.objet;

public class Periode {
	private final DateEvenement debut;
	private final DateEvenement fin;

	public Periode(DateEvenement debut, DateEvenement fin) {
		this.debut = debut;
		this.fin = fin;
	}

	public DateEvenement debut() {
		return debut;
	}

	public DateEvenement fin() {
		return fin;
	}
}
