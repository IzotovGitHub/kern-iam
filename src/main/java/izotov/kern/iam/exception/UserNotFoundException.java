package izotov.kern.iam.exception;

import izotov.kern.iam.exception.base.NotFoundException;
import org.jooq.Condition;

public class UserNotFoundException extends NotFoundException {
    
    public UserNotFoundException(Condition condition) {
        super(String.format("User matching '%s' not found", condition));
    }
    
    public UserNotFoundException(String username) {
        super(String.format("User matching 'username=%s' not found", username));
    }
}
