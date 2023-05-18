package gov.cdc.nbs.questionbank.kafka.exception;

public class RequestException extends RuntimeException {
    private final String key;

    public RequestException(String message, String key) {
        super(message);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
