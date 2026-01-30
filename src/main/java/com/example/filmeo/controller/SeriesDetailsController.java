package com.example.filmeo.controller;

import com.example.filmeo.entity.Serie;
import com.example.filmeo.repository.SerieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class SeriesDetailsController {

    private final SerieRepository serieRepository;

    public SeriesDetailsController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @GetMapping("/serie/{id}")
    public String serieDetail(@PathVariable Long id, Model model) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Série introuvable"
                ));

        model.addAttribute("serie", serie);
        return "serie";
    }
}
