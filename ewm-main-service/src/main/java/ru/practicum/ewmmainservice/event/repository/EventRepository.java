package ru.practicum.ewmmainservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
