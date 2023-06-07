package gov.cdc.nbs.questionbank.kafka.message.question;

import java.time.Instant;

public record QuestionCreatedEvent(long id, long createdBy, Instant createdAt) {

}
