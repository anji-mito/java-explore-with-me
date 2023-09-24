package ru.practicum.ewmmainservice.event.model;

import lombok.*;
import ru.practicum.ewmmainservice.category.model.Category;
import ru.practicum.ewmmainservice.event.dto.State;
import ru.practicum.ewmmainservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String annotation;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private List<Category> categories;

    @Column(name = "confirmed_requests")
    private long confirmedRequests;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private State state;

    private String title;

    private long views;
}
