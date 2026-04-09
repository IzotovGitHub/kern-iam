package izotov.kern.iam.exception;

import izotov.kern.iam.exception.base.ConflictException;
import izotov.kern.iam.service.request.CreateUser;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(CreateUser user) {
        super(String.format("User with username %s already exists", user.username()));
    }
}
