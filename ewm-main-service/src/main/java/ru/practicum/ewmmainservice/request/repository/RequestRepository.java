package ru.practicum.ewmmainservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.request.model.ParticipationRequest;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long>  {

    List<ParticipationRequest> findByIdInAndEventId(List<Long> ids, long eventId);
}
