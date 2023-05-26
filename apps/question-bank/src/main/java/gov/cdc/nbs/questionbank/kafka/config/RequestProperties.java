package gov.cdc.nbs.questionbank.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafkadef.topics.questionbank")
public record RequestProperties(
        String request,
        String status,
        String questionCreated) {

    private static final String DEFAULT_REQUEST = "questionbank";
    private static final String DEFAULT_STATUS = "questionbank-status";
    private static final String DEFAULT_CREATED = "question-created";

    public RequestProperties() {
        this(DEFAULT_REQUEST, DEFAULT_STATUS, DEFAULT_CREATED);
    }

    public RequestProperties(String request, String status, String questionCreated) {
        this.request = request == null ? DEFAULT_REQUEST : request;
        this.status = status == null ? request + DEFAULT_STATUS : status;
        this.questionCreated = questionCreated == null ? request + DEFAULT_CREATED : questionCreated;
    }
}
