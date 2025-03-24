package calendar.objet;

import calendar.evenement.Event;
import calendar.evenement.EventDto;

public class RendezVous extends Event {
	public RendezVous(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire) {
		super(id, titre, date, duree, proprietaire);
	}

	@Override
	public String description() {
		return "Rendez-vous : " + titre.value() + " le " + date.get() +
				" (Propriétaire: " + proprietaire.utilisateur().identifiant() + ")";
	}

	@Override
	public boolean estEnConflitAvec(Event autre) {
		return this.date.chevauche(this.duree, autre.getDate(), autre.getDuree());
	}

	@Override
	public EventDto toDto() {
		return toBaseDto();
	}


}