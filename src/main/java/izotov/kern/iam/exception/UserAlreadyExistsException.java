package izotov.kern.iam.exception;

import izotov.kern.iam.exception.base.ConflictException;
import izotov.kern.iam.webapi.dto.NewUserDto;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(NewUserDto user) {
        super(String.format("User with username %s already exists", user.username()));
    }
}
