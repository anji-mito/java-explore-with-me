package ru.practicum.ewmmainservice.request.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmmainservice.request.model.Status;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private Status status;
}
