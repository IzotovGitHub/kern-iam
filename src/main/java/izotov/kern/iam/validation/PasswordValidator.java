package izotov.kern.iam.validation;

import izotov.kern.iam.validation.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {
    
    private Policy policy;
    
    @Override
    public void initialize(Password constraintAnnotation) {
        this.policy = constraintAnnotation.policy();
    }
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return policy.isValid(password, context);
    }
    
    public enum Policy {
        DEFAULT {
            @Override
            boolean isValid(String password, ConstraintValidatorContext context) {
    
                context.disableDefaultConstraintViolation();
                
                if (StringUtils.isBlank(password)) {
                    context.buildConstraintViolationWithTemplate(
                            "Password is required"
                    ).addConstraintViolation();
                    return false;
                }
    
                boolean isValid = true;
        
                int minLength = 3;
                if (password.length() < minLength) {
                    context.buildConstraintViolationWithTemplate(
                            "Password must be at least " + minLength + " characters"
                    ).addConstraintViolation();
                    isValid = false;
                }
                
                return isValid;
            }
        };
        
        boolean isValid(String password, ConstraintValidatorContext context) {
            return false;
        };
    }
}
