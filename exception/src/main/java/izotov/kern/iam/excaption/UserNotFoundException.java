package izotov.kern.iam.excaption;

import izotov.kern.iam.excaption.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    
    public UserNotFoundException(Object condition) {
        super(String.format("User matching '%s' not found", condition));
    }
    
    public UserNotFoundException(String username) {
        super(String.format("User matching 'username=%s' not found", username));
    }
}
