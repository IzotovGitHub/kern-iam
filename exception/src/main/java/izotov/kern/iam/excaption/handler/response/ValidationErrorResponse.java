package izotov.kern.iam.excaption.handler.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationErrorResponse extends ErrorResponse {
    
    private final int errorCount;
    
    private final List<Error> errors;
    
    @Builder
    public record Error(
            String code,
            String field,
            String message) {
    }
}
