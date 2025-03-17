package calendar;

public class EvenementPeriodique extends Event {
	private final int frequenceJours;

	public EvenementPeriodique(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire, int frequenceJours) {
		super(id, titre, date, duree, proprietaire);
		this.frequenceJours = frequenceJours;
	}

	@Override
	public String description() {
		return "Événement périodique : " + titre.value() + " tous les " + frequenceJours + " jours. (Propriétaire: " + proprietaire.value() + ")";
	}
}
