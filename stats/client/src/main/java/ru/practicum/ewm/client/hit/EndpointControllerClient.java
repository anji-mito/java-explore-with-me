package ru.practicum.ewm.client.hit;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.*;

@Service
public class EndpointControllerClient {

    private final WebClient webClient;

    public EndpointControllerClient() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:9090").build();
    }

    public List<ViewStatsDto> getStats(List<String> uris, boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ViewStatsDto>>() {})
                .block();
    }


    public void createHit(EndpointHitDto endpointHitDto) {
        webClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(endpointHitDto)
                .retrieve()
                .bodyToMono(EndpointHitDto.class)
                .block();
    }
}
