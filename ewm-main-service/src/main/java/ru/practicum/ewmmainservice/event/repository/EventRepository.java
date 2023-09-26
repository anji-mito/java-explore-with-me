package ru.practicum.ewmmainservice.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.event.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event>  findByIdAndInitiatorId(long id, long userId);

    List<Event> findAllByInitiatorId(long userId, PageRequest of);
}
