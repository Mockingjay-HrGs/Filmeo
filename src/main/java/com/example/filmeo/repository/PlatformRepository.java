package com.example.filmeo.repository;

import com.example.filmeo.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    List<Platform> findTop10ByOrderByNameAsc();
}
