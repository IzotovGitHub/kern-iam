package izotov.kern.iam.excaption;

import izotov.kern.iam.excaption.base.BadRequestException;

public class PasswordRequiredException extends BadRequestException {
    
    public PasswordRequiredException(String message) {
        super(message);
    }
}
