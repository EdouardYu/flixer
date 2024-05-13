package web.technologies.flixer.service.exception;

public class ValidationCodeException extends RuntimeException {
    public ValidationCodeException(String msg) {
        super(msg);
    }
}
