package calendar.evenement;

import calendar.objet.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RendezVous.class, name = "rendezvous"),
        @JsonSubTypes.Type(value = Reunion.class, name = "reunion"),
        @JsonSubTypes.Type(value = EvenementPeriodique.class, name = "periodique"),
        @JsonSubTypes.Type(value = EvenementPersonnalise.class, name = "personnalise")
})

public abstract class Event {
    protected final EventId id;
    protected final TitreEvenement titre;
    protected final DateEvenement date;
    protected final DureeEvenement duree;
    protected final Proprietaire proprietaire;

    public Event(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire) {
        this.id = id;
        this.titre = titre;
        this.date = date;
        this.duree = duree;
        this.proprietaire = proprietaire;
    }

    public abstract String description();

    public EventId getId() {
        return id;
    }
    public DateEvenement getDate() {
        return date;
    }
    public DureeEvenement getDuree() {
        return duree;
    }
    public Proprietaire getProprietaire() {
        return proprietaire;
    }
    public TitreEvenement getTitre() {
        return titre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public List<Event> occurrencesDansPeriode(DateEvenement debut, DateEvenement fin) {
        if (date.isBetween(debut, fin)) {
            return List.of(this);
        }
        return List.of();
    }

    public boolean estEnConflitAvec(Event autre) {
        return this.date.chevauche(this.duree, autre.getDate(), autre.getDuree());
    }

}