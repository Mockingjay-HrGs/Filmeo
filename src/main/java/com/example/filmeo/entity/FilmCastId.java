package com.example.filmeo.entity;

import java.io.Serializable;
import java.util.Objects;

public class FilmCastId implements Serializable {

    private Long film;
    private Long person;

    public FilmCastId() {}

    public FilmCastId(Long film, Long person) {
        this.film = film;
        this.person = person;
    }

    public Long getFilm() { return film; }
    public void setFilm(Long film) { this.film = film; }

    public Long getPerson() { return person; }
    public void setPerson(Long person) { this.person = person; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmCastId)) return false;
        FilmCastId that = (FilmCastId) o;
        return Objects.equals(film, that.film) && Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, person);
    }
}
