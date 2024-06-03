package web.technologies.flixer.service.exception;

public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String msg) {
        super(msg);
    }
}
