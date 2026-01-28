package com.example.filmeo.repository;

import com.example.filmeo.entity.FilmCast;
import com.example.filmeo.entity.FilmCastId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmCastRepository extends JpaRepository<FilmCast, FilmCastId> {

    List<FilmCast> findByFilm_Id(Long filmId);

    List<FilmCast> findByPerson_Id(Long personId);

    void deleteByFilm_IdAndPerson_Id(Long filmId, Long personId);
}
