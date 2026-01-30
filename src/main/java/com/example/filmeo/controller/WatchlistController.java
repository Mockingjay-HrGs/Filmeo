package com.example.filmeo.controller;

import com.example.filmeo.entity.*;
import com.example.filmeo.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class WatchlistController {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final SerieRepository serieRepository;
    private final WatchlistFilmRepository watchlistFilmRepository;
    private final WatchlistSerieRepository watchlistSerieRepository;

    public WatchlistController(UserRepository userRepository,
                               FilmRepository filmRepository,
                               SerieRepository serieRepository,
                               WatchlistFilmRepository watchlistFilmRepository,
                               WatchlistSerieRepository watchlistSerieRepository) {
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
        this.serieRepository = serieRepository;
        this.watchlistFilmRepository = watchlistFilmRepository;
        this.watchlistSerieRepository = watchlistSerieRepository;
    }

    private User currentUser(Authentication auth) {
        return userRepository.findByPseudo(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    @PostMapping("/watchlist/films/add/{filmId}")
    public String addFilm(@PathVariable Long filmId, Authentication auth) {
        User user = currentUser(auth);

        if (!filmRepository.existsById(filmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film introuvable");
        }

        if (!watchlistFilmRepository.existsByUser_IdAndFilm_Id(user.getId(), filmId)) {
            Film film = filmRepository.findById(filmId).orElseThrow();
            WatchlistFilm wf = new WatchlistFilm();
            wf.setUser(user);
            wf.setFilm(film);
            watchlistFilmRepository.save(wf);
        }

        return "redirect:/profile";
    }

    @PostMapping("/watchlist/films/remove/{filmId}")
    public String removeFilm(@PathVariable Long filmId, Authentication auth) {
        User user = currentUser(auth);
        watchlistFilmRepository.deleteByUser_IdAndFilm_Id(user.getId(), filmId);
        return "redirect:/profile";
    }

    @PostMapping("/watchlist/series/add/{serieId}")
    public String addSerie(@PathVariable Long serieId, Authentication auth) {
        User user = currentUser(auth);

        if (!serieRepository.existsById(serieId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Série introuvable");
        }

        if (!watchlistSerieRepository.existsByUser_IdAndSerie_Id(user.getId(), serieId)) {
            Serie serie = serieRepository.findById(serieId).orElseThrow();
            WatchlistSerie ws = new WatchlistSerie();
            ws.setUser(user);
            ws.setSerie(serie);
            watchlistSerieRepository.save(ws);
        }

        return "redirect:/profile";
    }

    @PostMapping("/watchlist/series/remove/{serieId}")
    public String removeSerie(@PathVariable Long serieId, Authentication auth) {
        User user = currentUser(auth);
        watchlistSerieRepository.deleteByUser_IdAndSerie_Id(user.getId(), serieId);
        return "redirect:/profile";
    }
}
