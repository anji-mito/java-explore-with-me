package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.event.dto.State;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndInitiatorId(long id, long userId);

    List<Event> findAllByInitiatorId(long userId, PageRequest of);

    @Query("SELECT e FROM Event e " +
            "WHERE (COALESCE(:users, null) IS NULL OR e.initiator.id IN (:users)) " +
            "AND (COALESCE(:states, null) IS NULL OR e.state IN (:states)) " +
            "AND (COALESCE(:categories, null) IS NULL OR e.category.id IN (:categories)) " +
            "AND (COALESCE(CAST(:rangeStart AS date), null) IS NULL OR e.eventDate >= CAST(:rangeStart AS date)) " +
            "AND (COALESCE(CAST(:rangeEnd AS date), null) IS NULL OR e.eventDate <= CAST(:rangeEnd AS date))")
    Page<Event> filterBy(
            @Param("users") List<Long> users,
            @Param("states") List<State> states,
            @Param("categories") List<Long> categories,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);

    @Query("SELECT e FROM Event AS e WHERE " +
            "(COALESCE(:text, null) IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')))" +
            " AND (COALESCE(:categories, null) IS NULL OR e.category.id IN (:categories))" +
            " AND (COALESCE(:paid, null) IS NULL OR e.paid = :paid)" +
            " AND (COALESCE(:rangeStart, null) IS NULL OR e.eventDate >= :rangeStart)" +
            " AND (COALESCE(:rangeEnd, null) IS NULL OR e.eventDate <= :rangeEnd)" +
            " AND e.state = :state")
    Page<Event> filterBy(@Param("text") String text,
            @Param("categories") List<Long> categories,
            @Param("paid") Boolean paid,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            @Param("state") State state,
            Pageable pageable);

    Optional<Event> findByIdAndState(long id, State state);

    List<Event> findByIdIn(List<Long> ids);
}
