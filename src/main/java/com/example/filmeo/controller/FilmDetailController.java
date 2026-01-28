package com.example.filmeo.controller;

import com.example.filmeo.entity.Film;
import com.example.filmeo.repository.FilmCastRepository;
import com.example.filmeo.repository.FilmRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class FilmDetailController {

    private final FilmRepository filmRepository;
    private final FilmCastRepository filmCastRepository;

    public FilmDetailController(FilmRepository filmRepository,
                                FilmCastRepository filmCastRepository) {
        this.filmRepository = filmRepository;
        this.filmCastRepository = filmCastRepository;
    }

    @GetMapping("/film/{id}")
    public String filmDetail(@PathVariable Long id, Model model) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film introuvable"));

        model.addAttribute("film", film);
        model.addAttribute("cast", filmCastRepository.findByFilm_Id(id));
        return "film";
    }
}
