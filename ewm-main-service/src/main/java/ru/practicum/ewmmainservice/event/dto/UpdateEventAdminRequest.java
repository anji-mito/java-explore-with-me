package ru.practicum.ewmmainservice.event.dto;

import lombok.*;
import ru.practicum.ewmmainservice.event.model.Location;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private long category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
