package calendar;

public class RendezVous extends Event {
	public RendezVous(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire) {
		super(id, titre, date, duree, proprietaire);
	}

	@Override
	public String description() {
		return "Rendez-vous : " + titre.value() + " le " + date.value() + " (Propriétaire: " + proprietaire.value() + ")";
	}

	@Override
	public boolean estEnConflitAvec(Event autre) {
		return this.date.chevauche(this.duree, autre.getDate(), autre.getDuree());
	}

	@Override
	public EventDto toDto() {
		EventDto dto = new EventDto();
		dto.id = this.id;
		dto.titre = this.titre;
		dto.date = this.date;
		dto.duree = this.duree;
		dto.proprietaire = this.proprietaire;
		dto.type = this.getClass().getSimpleName();
		return dto;
	}

}