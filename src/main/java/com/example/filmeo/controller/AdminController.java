package com.example.filmeo.controller;

import com.example.filmeo.repository.FilmRepository;
import com.example.filmeo.repository.SerieRepository;
import com.example.filmeo.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FilmRepository filmRepository;
    private final SerieRepository serieRepository;
    private final PersonRepository personRepository;

    public AdminController(FilmRepository filmRepository,
                           SerieRepository serieRepository,
                           PersonRepository personRepository) {
        this.filmRepository = filmRepository;
        this.serieRepository = serieRepository;
        this.personRepository = personRepository;
    }

    @GetMapping({"", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("filmsCount", filmRepository.count());
        model.addAttribute("seriesCount", serieRepository.count());
        model.addAttribute("personsCount", personRepository.count());
        return "admin/dashboard";
    }
}
