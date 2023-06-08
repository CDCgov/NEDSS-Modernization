package gov.cdc.nbs.questionbank.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafkadef.topics.questionbank")
public record RequestProperties(
        String questionCreated,
        String questionDeleted) {

    private static final String DEFAULT_CREATED = "question-created";
    private static final String DEFAULT_DELETED = "question-deleted";

    public RequestProperties() {
        this(DEFAULT_CREATED, DEFAULT_DELETED);
    }

    public RequestProperties(String questionCreated, String questionDeleted) {
        this.questionCreated = questionCreated == null ? DEFAULT_CREATED : questionCreated;
        this.questionDeleted = questionDeleted == null ? DEFAULT_DELETED : questionDeleted;
    }
}
