package dev.jeppu.orderservice.web.exception;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final URI NOT_FOUND_TYPE = URI.create("http://api.bookstore.com/errors/not-found");
    private static final URI INTERNAL_SERVER_ERROR = URI.create("http://api.bookstore.com/errors/not-found");
    private static final URI BAD_REQUEST_TYPE = URI.create("http://api.bookstore.com/errors/not-found");
    private static final String SERVICE_NAME = "order-service";

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        ;
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(BAD_REQUEST_TYPE);
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" , "));
        problemDetail.setProperty("errors", errorMsg);
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("error_category", "Generic");
        problemDetail.setProperty("service", SERVICE_NAME);
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}
