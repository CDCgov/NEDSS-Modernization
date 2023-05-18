package gov.cdc.nbs.questionbank.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafkadef.topics.questionbank")
public record RequestProperties(
        String request,
        String status) {

    private static final String DEFAULT_REQUEST = "questionbank";
    private static final String DEFAULT_STATUS = "questionbank-status";

    public RequestProperties() {
        this(DEFAULT_REQUEST, DEFAULT_STATUS);
    }

    public RequestProperties(String request, String status) {
        this.request = request == null ? DEFAULT_REQUEST : request;
        this.status = status == null ? request + DEFAULT_STATUS : status;
    }
}
