package calendar;

import java.util.Objects;

public abstract class Event {
    protected final EventId id;
    protected final TitreEvenement titre;
    protected final DateEvenement date;
    protected final DureeEvenement duree;
    protected final Proprietaire proprietaire;

    public Event(EventId id, TitreEvenement titre, DateEvenement date, DureeEvenement duree, Proprietaire proprietaire) {
        this.id = Objects.requireNonNull(id);
        this.titre = Objects.requireNonNull(titre);
        this.date = Objects.requireNonNull(date);
        this.duree = Objects.requireNonNull(duree);
        this.proprietaire = Objects.requireNonNull(proprietaire);
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
}