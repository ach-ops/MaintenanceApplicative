package calendar.evenement;

import calendar.objet.*;

public class EvenementPersonnalise extends Event {

	private final String typePerso;

	public EvenementPersonnalise(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree,
								 Proprietaire proprietaire, String typePerso) {
		super(id, titre, date, duree, proprietaire);
		this.typePerso = typePerso;
	}

	@Override
	public String description() {
		return typePerso + " : " + titre.value() + " le " + date.toString();
	}

	@Override
	public boolean estEnConflitAvec(Event autre) {
		return this.date.chevauche(this.duree, autre.getDate(), autre.getDuree());
	}

	@Override
	public EventDto toDto() {
		EventDto dto = toBaseDto();
		dto.type = this.typePerso;
		return dto;
	}
}
