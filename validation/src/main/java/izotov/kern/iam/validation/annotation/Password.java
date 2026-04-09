package izotov.kern.iam.validation.annotation;

import izotov.kern.iam.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    
    PasswordValidator.Policy policy() default PasswordValidator.Policy.DEFAULT;
    
    // Обязательный параметр message
    String message() default "Invalid password format";
    
    // Обязательные параметры groups и payload
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
