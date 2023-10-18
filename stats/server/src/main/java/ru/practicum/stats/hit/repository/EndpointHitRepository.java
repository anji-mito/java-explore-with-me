package ru.practicum.stats.hit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.hit.model.EndpointHit;
import ru.practicum.stats.hit.model.ViewStats;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.stats.hit.model.ViewStats(h.app, h.uri, COUNT(h.ip))  " +
            "FROM EndpointHit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND (h.uri IN ?3 or ?3 is NULL) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC ")
    List<ViewStats> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.stats.hit.model.ViewStats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM EndpointHit h " +
            "WHERE (:start IS NULL OR h.timestamp >= :start) AND (:end IS NULL OR h.timestamp <= :end) " +
            "AND (h.uri IN :uris OR :uris IS NULL) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC ")
    List<ViewStats> findViewStatsUniqueIp(@Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable List<String> uris);
}