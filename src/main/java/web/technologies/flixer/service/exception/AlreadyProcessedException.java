package web.technologies.flixer.service.exception;

public class AlreadyProcessedException extends RuntimeException {
    public AlreadyProcessedException(String msg) {
        super(msg);
    }
}
