package com.example.filmeo.controller.admin;

import com.example.filmeo.entity.Serie;
import com.example.filmeo.repository.SerieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin/series")
public class AdminSeriesController {

    private final SerieRepository serieRepository;

    public AdminSeriesController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("series", serieRepository.findAll());
        return "admin/series/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("serie", new Serie());
        return "admin/series/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Serie serie) {
        if (serie.getTitle() == null || serie.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titre obligatoire");
        }
        serieRepository.save(serie);
        return "redirect:/admin/series";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Série introuvable"));
        model.addAttribute("serie", serie);
        return "admin/series/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Serie form) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Série introuvable"));

        serie.setTitle(form.getTitle());
        serie.setSeasonsCount(form.getSeasonsCount());
        serie.setEpisodesCount(form.getEpisodesCount());
        serie.setStatus(form.getStatus());
        serie.setPosterUrl(form.getPosterUrl());

        serieRepository.save(serie);
        return "redirect:/admin/series";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (!serieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Série introuvable");
        }
        serieRepository.deleteById(id);
        return "redirect:/admin/series";
    }
}
