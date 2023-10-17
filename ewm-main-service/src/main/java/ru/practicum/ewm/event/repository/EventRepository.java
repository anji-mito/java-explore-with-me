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

    @Query("SELECT e FROM Event AS e WHERE "
            + "(:users is null or e.initiator.id in (:users)) "
            + "AND (:states is null or e.state in (:states)) "
            + "AND (:categories is null or e.category.id in (:categories)) "
            + "AND (:rangeStart is null or e.eventDate >= :rangeStart) "
            + "AND (:rangeEnd is null or e.eventDate <= :rangeEnd)")
    Page<Event> FilterBy(
            @Param("users") List<Long> users,
            @Param("states") List<State> states,
            @Param("categories") List<Long> categories,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);

    @Query("SELECT e FROM Event AS e WHERE (:text IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))"
            + " OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%'))) AND (:categories IS NULL OR e.category.id IN (:categories))  "
            + "AND (:paid IS NULL OR e.paid = :paid) "
            + "AND (:rangeStart IS NULL OR e.eventDate >= :rangeStart) "
            + "AND (:rangeEnd IS NULL OR e.eventDate <= :rangeEnd) "
            + "AND (e.state = :state)")
    Page<Event> FilterBy(@Param("text") String text,
            @Param("categories") List<Long> categories,
            @Param("paid") Boolean paid,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            @Param("state") State state,
            Pageable pageable);

    Optional<Event> findByIdAndState(long id, State state);

    List<Event> findByIdIn(List<Long> ids);
}
