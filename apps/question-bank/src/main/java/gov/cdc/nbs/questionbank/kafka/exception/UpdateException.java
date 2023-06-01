package gov.cdc.nbs.questionbank.kafka.exception;

public class UpdateException extends RuntimeException {
        public UpdateException() {
            super("The update operation failed for question.");
        }
}
