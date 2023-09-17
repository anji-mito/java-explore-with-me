package ru.practicum.ewm.client.hit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.EndpointHitDto;
import ru.practicum.ewm.ViewStatsDto;

import java.util.*;

@Service
public class EndpointControllerClient {
    private static final String BASE_URL = "http://localhost:9090";
    private static final String GET_STATS_URL = BASE_URL + "/stats";
    private static final String ADD_HIT_URL = BASE_URL + "/hits";

    private final RestTemplate restTemplate;

    public EndpointControllerClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        String url = GET_STATS_URL + "?start={start}&end={end}&uris={uris}&unique={unique}";

        ResponseEntity<ViewStatsDto[]> response = restTemplate.getForEntity(url, ViewStatsDto[].class,
                start, end, uris, unique);

        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            return Collections.emptyList();
        }
    }

    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EndpointHitDto> requestEntity = new HttpEntity<>(endpointHitDto, headers);

        ResponseEntity<EndpointHitDto> response = restTemplate.postForEntity(ADD_HIT_URL, requestEntity,
                EndpointHitDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            return response.getBody();
        }
        return null;
    }
}
