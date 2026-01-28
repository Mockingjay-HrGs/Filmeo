package com.example.filmeo.repository;

import com.example.filmeo.entity.WatchlistSerie;
import com.example.filmeo.entity.WatchlistSerieId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WatchlistSerieRepository extends JpaRepository<WatchlistSerie, WatchlistSerieId> {

    List<WatchlistSerie> findByUser_Id(Long userId);

    boolean existsByUser_IdAndSerie_Id(Long userId, Long serieId);

    @Modifying
    @Transactional
    int deleteByUser_IdAndSerie_Id(Long userId, Long serieId);
}
