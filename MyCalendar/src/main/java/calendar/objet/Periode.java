package calendar.objet;

public class Periode {
	private final DateEvenement debut;
	private final DateEvenement fin;

	public Periode(DateEvenement debut, DateEvenement fin) {
		if (fin.estAvant(debut)) {
			throw new IllegalArgumentException("Date de fin avant la date de début.");
		}
		this.debut = debut;
		this.fin = fin;
	}

	public DateEvenement debut() {
		return debut;
	}

	public DateEvenement fin() {
		return fin;
	}

	public boolean contient(DateEvenement date) {
		return !date.estAvant(debut) && !date.estApres(fin);
	}

	public boolean chevauche(Periode autre) {
		return !this.fin.estAvant(autre.debut) && !autre.fin.estAvant(this.debut);
	}

	@Override
	public String toString() {
		return "du " + debut + " au " + fin;
	}
}
