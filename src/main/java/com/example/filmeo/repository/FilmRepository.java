package com.example.filmeo.repository;

import com.example.filmeo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByDirector_Id(Long directorId);
    List<Film> findByTitleContainingIgnoreCaseOrderByTitleAsc(String q);
    List<Film> findTop6ByOrderByIdDesc();
    List<Film> findTop6ByOrderByRatingDesc();
    List<Film> findTop6ByOrderByTitleAsc();

}
