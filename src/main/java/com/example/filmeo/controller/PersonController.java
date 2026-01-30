package com.example.filmeo.controller;

import com.example.filmeo.entity.Film;
import com.example.filmeo.entity.Person;
import com.example.filmeo.repository.FilmCastRepository;
import com.example.filmeo.repository.FilmRepository;
import com.example.filmeo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class PersonController {

    private final PersonRepository personRepository;
    private final FilmRepository filmRepository;
    private final FilmCastRepository filmCastRepository;

    public PersonController(
            PersonRepository personRepository,
            FilmRepository filmRepository,
            FilmCastRepository filmCastRepository
    ) {
        this.personRepository = personRepository;
        this.filmRepository = filmRepository;
        this.filmCastRepository = filmCastRepository;
    }

    @GetMapping("/persons")
    public String persons(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "persons";
    }

    @GetMapping("/person/{id}")
    public String personDetail(@PathVariable Long id, Model model) {

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable")
                );

        List<Film> filmsDirected = filmRepository.findByDirector_Id(id);

        List<Film> filmsActed = filmCastRepository.findByPerson_Id(id)
                .stream()
                .map(fc -> fc.getFilm())
                .distinct()
                .toList();

        model.addAttribute("person", person);
        model.addAttribute("filmsDirected", filmsDirected);
        model.addAttribute("filmsActed", filmsActed);

        return "person";
    }
}
