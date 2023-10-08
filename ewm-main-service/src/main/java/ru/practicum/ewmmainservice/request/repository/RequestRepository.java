package ru.practicum.ewmmainservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.request.model.ParticipationRequest;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long>  {
}
