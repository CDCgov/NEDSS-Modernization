package gov.cdc.nbs.patientlistener.exception;

public class KafkaException extends RuntimeException {
    private final String key;

    public KafkaException(String message, String key) {
        super(message);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
