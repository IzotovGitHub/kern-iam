package izotov.kern.iam.exception.handler;

import izotov.kern.iam.exception.base.BadRequestException;
import izotov.kern.iam.exception.base.ConflictException;
import izotov.kern.iam.exception.base.NotFoundException;
import izotov.kern.iam.exception.handler.response.ErrorResponse;
import izotov.kern.iam.exception.handler.response.ValidationErrorResponse;
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
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass())
                .message(ex.getMessage())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(AccessDeniedException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN)
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass())
                .message(ex.getMessage())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }
    
    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass())
                .message(ex.getMessage())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass())
                .message(ex.getMessage())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .exception(ex.getClass())
                .message(ex.getMessage())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
