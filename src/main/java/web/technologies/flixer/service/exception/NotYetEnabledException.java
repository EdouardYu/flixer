package web.technologies.flixer.service.exception;

public class NotYetEnabledException extends RuntimeException {
    public NotYetEnabledException(String msg) {
        super(msg);
    }
}
