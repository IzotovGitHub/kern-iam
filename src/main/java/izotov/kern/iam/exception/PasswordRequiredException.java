package izotov.kern.iam.exception;

import izotov.kern.iam.exception.base.BadRequestException;

public class PasswordRequiredException extends BadRequestException {
    
    public PasswordRequiredException(String message) {
        super(message);
    }
}
