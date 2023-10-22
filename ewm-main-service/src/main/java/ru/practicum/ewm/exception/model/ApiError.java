package ru.practicum.ewm.exception.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ApiError {
    private String message;
    private List<String> errors;
    private String reason;
    private HttpStatus status;
    private String timestamp;

    public String getError() {
        return message;
    }
}
