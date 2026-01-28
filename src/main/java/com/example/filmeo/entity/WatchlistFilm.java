package com.example.filmeo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlist_films")
@IdClass(WatchlistFilmId.class)
public class WatchlistFilm {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
}
