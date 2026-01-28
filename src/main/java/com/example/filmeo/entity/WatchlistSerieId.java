package com.example.filmeo.entity;

import java.io.Serializable;
import java.util.Objects;

public class WatchlistSerieId implements Serializable {
    private Long user;
    private Long serie;

    public WatchlistSerieId() {}

    public WatchlistSerieId(Long user, Long serie) {
        this.user = user;
        this.serie = serie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WatchlistSerieId)) return false;
        WatchlistSerieId that = (WatchlistSerieId) o;
        return Objects.equals(user, that.user) && Objects.equals(serie, that.serie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, serie);
    }
}
