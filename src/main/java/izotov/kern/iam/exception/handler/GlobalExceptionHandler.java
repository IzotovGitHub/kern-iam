package izotov.kern.iam.exception.handler;

import izotov.kern.iam.exception.base.BadRequestException;
import izotov.kern.iam.exception.base.ConflictException;
import izotov.kern.iam.exception.base.NotFoundException;
import izotov.kern.iam.exception.handler.response.ErrorResponse;
import izotov.kern.iam.exception.handler.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebInputException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(WebExchangeBindException ex) {
        List<ValidationErrorResponse.Error> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(error -> ValidationErrorResponse.Error.builder()
                        .code(error.getCode())
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();
        
        ErrorResponse response = ValidationErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass())
                .message(ex.getReason())
                .errorCount(ex.getErrorCount())
                .errors(errors)
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    
    @ExceptionHandler({BadRequestException.class, ServerWebInputException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
        return defaultResponseEntity(HttpStatus.BAD_REQUEST, ex);
    }
    
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(AccessDeniedException ex) {
        return defaultResponseEntity(HttpStatus.FORBIDDEN, ex);
    }
    
    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        return defaultResponseEntity(HttpStatus.NOT_FOUND, ex);
    }
    
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        return defaultResponseEntity(HttpStatus.CONFLICT, ex);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return defaultResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    
    private ResponseEntity<ErrorResponse> defaultResponseEntity(HttpStatus status, Exception ex) {
        log.debug("Exception received", ex);
        ErrorResponse response = ErrorResponse.builder()
                .code(status)
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass())
                .message(ex.getMessage())
                .build();
        
        return ResponseEntity
                .status(status)
                .body(response);
    }
}
