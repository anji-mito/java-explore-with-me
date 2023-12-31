package ru.practicum.stats.hit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
