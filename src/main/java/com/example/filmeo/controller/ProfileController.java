package com.example.filmeo.controller;

import com.example.filmeo.entity.User;
import com.example.filmeo.repository.UserRepository;
import com.example.filmeo.repository.WatchlistFilmRepository;
import com.example.filmeo.repository.WatchlistSerieRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final WatchlistFilmRepository watchlistFilmRepository;
    private final WatchlistSerieRepository watchlistSerieRepository;

    public ProfileController(
            UserRepository userRepository,
            WatchlistFilmRepository watchlistFilmRepository,
            WatchlistSerieRepository watchlistSerieRepository
    ) {
        this.userRepository = userRepository;
        this.watchlistFilmRepository = watchlistFilmRepository;
        this.watchlistSerieRepository = watchlistSerieRepository;
    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {

        User user = userRepository.findByPseudo(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("isAdmin", "ROLE_ADMIN".equals(user.getRole()));

        model.addAttribute("watchlistFilms",
                watchlistFilmRepository.findByUser_Id(user.getId()));

        model.addAttribute("watchlistSeries",
                watchlistSerieRepository.findByUser_Id(user.getId()));

        return "profile";
    }
}
