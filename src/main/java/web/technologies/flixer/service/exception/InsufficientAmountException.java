package web.technologies.flixer.service.exception;

public class InsufficientAmountException extends RuntimeException {
    public InsufficientAmountException(String msg) {
        super(msg);
    }
}
