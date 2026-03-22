package izotov.kern.iam.exception.handler.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Schema(description = "Описание ошибки")
public class ErrorResponse {
    
    @Schema(description = "Время и дата фиксации ошибки")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp;
    
    @Schema(description = "Статус код")
    private final HttpStatus code;
    
    @Schema(description = "Тип ошибки")
    private final Class<? extends Exception> exception;
    
    @Schema(description = "Сообщение с описанием ошибки")
    private final String message;
    
}
