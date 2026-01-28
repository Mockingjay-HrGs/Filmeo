package com.example.filmeo.entity;

import java.io.Serializable;
import java.util.Objects;

public class WatchlistFilmId implements Serializable {
    private Long user;
    private Long film;

    public WatchlistFilmId() {}

    public WatchlistFilmId(Long user, Long film) {
        this.user = user;
        this.film = film;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WatchlistFilmId)) return false;
        WatchlistFilmId that = (WatchlistFilmId) o;
        return Objects.equals(user, that.user) && Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, film);
    }
}
