package izotov.kern.iam.excaption.base;

public class BadRequestException extends  Exception {
    
    public BadRequestException(String message) {
        super(message);
    }
}
