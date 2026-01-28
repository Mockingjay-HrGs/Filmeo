package com.example.filmeo.repository;

import com.example.filmeo.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByTitleContainingIgnoreCaseOrderByTitleAsc(String q);
}
