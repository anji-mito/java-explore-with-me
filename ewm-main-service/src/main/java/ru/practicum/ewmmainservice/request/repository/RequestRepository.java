package ru.practicum.ewmmainservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.request.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findByIdInAndEventId(List<Long> ids, long eventId);

    @Query("SELECT r FROM ParticipationRequest r " +
            "JOIN r.event e " +
            "JOIN e.initiator i " +
            "WHERE e.id = :eventId " +
            "AND i.id = :initiatorId")
    List<ParticipationRequest> findByInitiatorIdAndEventId(@Param("initiatorId") long initiatorId,
            @Param("eventId") long eventId);

    List<ParticipationRequest> findByRequesterId(long userId);

    Optional<ParticipationRequest> findByRequesterIdAndEventId(long userId, long eventId);
}
