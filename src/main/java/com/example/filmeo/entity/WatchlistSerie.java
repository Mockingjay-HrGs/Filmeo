package com.example.filmeo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlist_series")
@IdClass(WatchlistSerieId.class)
public class WatchlistSerie {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Serie getSerie() { return serie; }
    public void setSerie(Serie serie) { this.serie = serie; }
}
