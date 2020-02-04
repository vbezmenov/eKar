package com.test.test_app.mvc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class ErrorResponse {
    private HttpStatus error;
    private String message;
    private int status;
    private final OffsetDateTime timestamp = OffsetDateTime.now();
}
