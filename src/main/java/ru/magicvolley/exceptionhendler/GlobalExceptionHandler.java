package ru.magicvolley.exceptionhendler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.magicvolley.exceptions.AuthorizationException;
import ru.magicvolley.exceptions.ValidationException;
import ru.magicvolley.response.api.ApiError;

import java.util.Objects;

@Slf4j
@ControllerAdvice
@NoArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllException(Exception ex, HttpServletRequest request) {
        log.error("Error text: {}. Request: {} method{}",
                ex.getMessage(),
                request.getRequestURI() + "?"+ Objects.toString(request.getQueryString(), ""),
                request.getMethod(),
                ex);

        String errorMessage = "Внутренняя ошибка сервера";
        ApiError apiError = ApiError.of(ex, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        log.error("Error text: {}. Request: {} method{}",
                ex.getMessage(),
                request.getRequestURI() + "?"+ Objects.toString(request.getQueryString(), ""),
                request.getMethod(),
                ex);

        ApiError apiError = ApiError.of(ex, HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiError> handleAuthorizationException(AuthorizationException ex, HttpServletRequest request) {
        log.error("Error text: {}. Request: {} method{}",
                ex.getMessage(),
                request.getRequestURI() + "?"+ Objects.toString(request.getQueryString(), ""),
                request.getMethod(),
                ex);

        ApiError apiError = ApiError.of(ex, HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex, HttpServletRequest request) {
        log.error("Error text: {}. Request: {} method{}",
                ex.getMessage(),
                request.getRequestURI() + "?"+ Objects.toString(request.getQueryString(), ""),
                request.getMethod(),
                ex);

        ApiError apiError = ApiError.of(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
