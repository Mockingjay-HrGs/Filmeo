package com.example.filmeo.controller.admin;

import com.example.filmeo.entity.Film;
import com.example.filmeo.entity.FilmCast;
import com.example.filmeo.entity.Person;
import com.example.filmeo.repository.FilmCastRepository;
import com.example.filmeo.repository.FilmRepository;
import com.example.filmeo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/films")
public class AdminFilmsController {

    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;
    private final FilmCastRepository filmCastRepository;

    public AdminFilmsController(
            FilmRepository filmRepository,
            PersonRepository personRepository,
            FilmCastRepository filmCastRepository
    ) {
        this.filmRepository = filmRepository;
        this.personRepository = personRepository;
        this.filmCastRepository = filmCastRepository;
    }

    /* =======================
       LIST
       ======================= */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("films", filmRepository.findAll());
        return "admin/films/index";
    }

    /* =======================
       CREATE
       ======================= */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("directors", sortedPersons());
        return "admin/films/form";
    }

    @PostMapping("/new")
    public String create(@RequestParam String title,
                         @RequestParam(required = false) Long directorId,
                         @RequestParam(required = false) String posterUrl,
                         @RequestParam(required = false) String releaseDate,
                         @RequestParam(required = false) String rating) {

        Film film = new Film();
        film.setTitle(title);
        film.setPosterUrl(clean(posterUrl));
        film.setReleaseDate(parseDate(releaseDate));
        film.setRating(parseRating(rating));

        if (directorId != null) {
            Person director = personRepository.findById(directorId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Réalisateur invalide"));
            film.setDirector(director);
        }

        filmRepository.save(film);
        return "redirect:/admin/films";
    }

    /* =======================
       EDIT
       ======================= */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film introuvable"));

        model.addAttribute("film", film);
        model.addAttribute("directors", sortedPersons());

        // casting admin
        model.addAttribute("castings", filmCastRepository.findByFilm_Id(id));
        model.addAttribute("actors", personRepository.findAll());

        return "admin/films/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam(required = false) Long directorId,
                       @RequestParam(required = false) String posterUrl,
                       @RequestParam(required = false) String releaseDate,
                       @RequestParam(required = false) String rating) {

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film introuvable"));

        film.setTitle(title);
        film.setPosterUrl(clean(posterUrl));
        film.setReleaseDate(parseDate(releaseDate));
        film.setRating(parseRating(rating));

        if (directorId == null) {
            film.setDirector(null);
        } else {
            Person director = personRepository.findById(directorId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Réalisateur invalide"));
            film.setDirector(director);
        }

        filmRepository.save(film);
        return "redirect:/admin/films";
    }

    /* =======================
       DELETE
       ======================= */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (!filmRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film introuvable");
        }
        filmRepository.deleteById(id);
        return "redirect:/admin/films";
    }

    /* =======================
       CASTING (film_cast)
       ======================= */
    @PostMapping("/cast/add")
    public String addCast(@RequestParam Long filmId,
                          @RequestParam Long personId,
                          @RequestParam(required = false) String characterName,
                          @RequestParam(required = false, defaultValue = "false") boolean main) {

        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film introuvable"));

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable"));

        FilmCast cast = new FilmCast();
        cast.setFilm(film);
        cast.setPerson(person);
        cast.setCharacterName(clean(characterName));
        cast.setMain(main);

        filmCastRepository.save(cast);
        return "redirect:/admin/films/edit/" + filmId;
    }

    @PostMapping("/cast/delete")
    public String deleteCast(@RequestParam Long filmId,
                             @RequestParam Long personId) {

        filmCastRepository.deleteByFilm_IdAndPerson_Id(filmId, personId);
        return "redirect:/admin/films/edit/" + filmId;
    }

    /* =======================
       HELPERS
       ======================= */
    private List<Person> sortedPersons() {
        List<Person> persons = personRepository.findAll();
        persons.sort(Comparator.comparing(p ->
                (p.getLastName() == null ? "" : p.getLastName().toLowerCase())
        ));
        return persons;
    }

    private String clean(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private LocalDate parseDate(String s) {
        s = clean(s);
        return s == null ? null : LocalDate.parse(s);
    }

    private BigDecimal parseRating(String s) {
        s = clean(s);
        if (s == null) return null;
        try {
            return new BigDecimal(s);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating invalide (ex: 8.8)");
        }
    }
}
