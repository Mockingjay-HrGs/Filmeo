package com.example.filmeo.repository;

import com.example.filmeo.entity.WatchlistFilm;
import com.example.filmeo.entity.WatchlistFilmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WatchlistFilmRepository extends JpaRepository<WatchlistFilm, WatchlistFilmId> {

    List<WatchlistFilm> findByUser_Id(Long userId);

    boolean existsByUser_IdAndFilm_Id(Long userId, Long filmId);

    @Modifying
    @Transactional
    int deleteByUser_IdAndFilm_Id(Long userId, Long filmId);
}
