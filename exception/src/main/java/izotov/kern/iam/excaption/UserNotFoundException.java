package izotov.kern.iam.excaption;

import izotov.kern.iam.excaption.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String username) {
        super(String.format("User matching 'username=%s' not found", username));
    }
}
