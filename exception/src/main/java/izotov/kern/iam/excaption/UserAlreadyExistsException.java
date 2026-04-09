package izotov.kern.iam.excaption;


import izotov.kern.iam.excaption.base.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(String username) {
        super(String.format("User with username %s already exists", username));
    }
}
