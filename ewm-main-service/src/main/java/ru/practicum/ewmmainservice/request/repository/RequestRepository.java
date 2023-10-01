package ru.practicum.ewmmainservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.request.model.ParticipationRequest;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long>  {
}
