package ru.practicum.ewm;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
