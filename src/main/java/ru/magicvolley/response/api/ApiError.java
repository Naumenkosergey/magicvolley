package ru.magicvolley.response.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.magicvolley.exceptions.ValidationException;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private int statusCode;
    private String statusName;
    private LocalDateTime timestamp;
    private String errorMessage;
//    private List<ValidationError> errors;

    public static ApiError of(Exception ex, HttpStatus httpStatus, String errorMessage) {
        return ApiError.builder()
                .statusCode(httpStatus.value())
                .statusName(httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .errorMessage(errorMessage)
                .build();
    }

    public static ApiError of(ValidationException validationException, HttpStatus httpStatus, String errorMessage) {

        return ApiError.builder()
                .statusCode(httpStatus.value())
                .statusName(httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .errorMessage(errorMessage)
                .build();
    }

//    @Data
//    @Builder
//    public static class ValidationError {
//        private String field;
//        private String errorText;
//    }
}
