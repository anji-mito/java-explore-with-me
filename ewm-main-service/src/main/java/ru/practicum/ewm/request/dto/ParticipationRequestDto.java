package ru.practicum.ewm.request.dto;

import lombok.*;
import ru.practicum.ewm.request.model.Status;

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
