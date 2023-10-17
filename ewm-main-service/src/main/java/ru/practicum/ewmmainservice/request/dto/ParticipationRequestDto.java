package ru.practicum.ewmmainservice.request.dto;

import lombok.*;
import ru.practicum.ewmmainservice.request.model.Status;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ParticipationRequestDto {
    long id;
    long event;
    long requester;
    Status status;
    private String created;
}
