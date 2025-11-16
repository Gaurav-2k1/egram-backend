package in.gram.gov.app.egram_service.constants.exception;

public class MaxAdminsExceededException extends RuntimeException {
    public MaxAdminsExceededException(String message) {
        super(message);
    }
}

