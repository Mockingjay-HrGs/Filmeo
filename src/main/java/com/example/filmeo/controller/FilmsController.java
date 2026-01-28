package com.example.filmeo.controller;

import com.example.filmeo.repository.FilmRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FilmsController {

    private final FilmRepository filmRepository;

    public FilmsController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping("/films")
    public String films(@RequestParam(required = false) String q, Model model) {
        String query = (q == null) ? "" : q.trim();

        if (query.isEmpty()) {
            model.addAttribute("films", filmRepository.findAll());
        } else {
            model.addAttribute("films",
                    filmRepository.findByTitleContainingIgnoreCaseOrderByTitleAsc(query)
            );
        }

        model.addAttribute("q", query);
        return "films";
    }
}
