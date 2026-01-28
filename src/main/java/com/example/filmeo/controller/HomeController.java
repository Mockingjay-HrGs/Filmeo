package com.example.filmeo.controller;

import com.example.filmeo.repository.FilmRepository;
import com.example.filmeo.repository.SerieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final FilmRepository filmRepository;
    private final SerieRepository serieRepository;

    public HomeController(FilmRepository filmRepository,
                          SerieRepository serieRepository) {
        this.filmRepository = filmRepository;
        this.serieRepository = serieRepository;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("films", filmRepository.findAll());
        model.addAttribute("series", serieRepository.findAll());
        return "home";
    }
}
