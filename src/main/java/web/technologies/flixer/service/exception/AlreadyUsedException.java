package web.technologies.flixer.service.exception;

public class AlreadyUsedException extends RuntimeException {
    public AlreadyUsedException(String msg) {
        super(msg);
    }
}
