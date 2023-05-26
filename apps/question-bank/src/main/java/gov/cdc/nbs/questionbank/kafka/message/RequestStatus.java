package gov.cdc.nbs.questionbank.kafka.message;

public record RequestStatus(
        boolean isSuccessful,
        String message,
        String requestId,
        String id) {


}
