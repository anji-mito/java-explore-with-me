package ru.practicum.ewmmainservice.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.request.dto.ParticipationRequestDto;
import ru.practicum.ewmmainservice.request.service.ParticipationRequestService;

import java.util.List;

@RestController
@RequestMapping("/users/")
@AllArgsConstructor
public class PrivateRequestController {
    private final ParticipationRequestService requestService;
    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable long userId, @RequestParam long eventId) {
        return requestService.createRequest(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getAllByUser(
            @PathVariable long userId) {
        return requestService.getRequestsByUser(userId);
    }

    @PatchMapping("/{userId}/requests/{requestsId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId, @PathVariable long requestsId) {
        return requestService.cancelRequest(userId, requestsId);
    }
}
