package com.example.filmeo.controller;

import com.example.filmeo.repository.SerieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SerieController {

    private final SerieRepository serieRepository;

    public SerieController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @GetMapping("/series")
    public String series(@RequestParam(required = false) String q, Model model) {

        String query = (q == null) ? "" : q.trim();

        if (query.isEmpty()) {
            model.addAttribute("series", serieRepository.findAll());
        } else {
            model.addAttribute(
                    "series",
                    serieRepository.findByTitleContainingIgnoreCaseOrderByTitleAsc(query)
            );
        }

        model.addAttribute("q", query);
        return "series";
    }
}
