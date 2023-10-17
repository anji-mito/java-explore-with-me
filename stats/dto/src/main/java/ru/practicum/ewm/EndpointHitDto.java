package ru.practicum.ewm;

import lombok.*;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
