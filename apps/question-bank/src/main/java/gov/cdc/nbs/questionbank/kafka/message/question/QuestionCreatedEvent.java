package gov.cdc.nbs.questionbank.kafka.message.question;

import java.time.Instant;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.DisplayElement;

public record QuestionCreatedEvent(DisplayElement element, long createdBy, Instant createdAt) {

}
