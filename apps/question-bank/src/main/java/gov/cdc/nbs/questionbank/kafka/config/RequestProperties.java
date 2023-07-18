package gov.cdc.nbs.questionbank.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafkadef.topics.questionbank")
public record RequestProperties(
        String questionCreated,
        String questionDeleted, String ruleCreated) {

    private static final String DEFAULT_CREATED = "question-created";
    private static final String DEFAULT_DELETED = "question-deleted";

    private static final String DEFAULT_RULE_CREATED = "rule-created";


    public RequestProperties() {
        this(DEFAULT_CREATED, DEFAULT_DELETED,DEFAULT_RULE_CREATED);
    }

    public RequestProperties(String questionCreated, String questionDeleted, String ruleCreated) {
        this.questionCreated = questionCreated == null ? DEFAULT_CREATED : questionCreated;
        this.questionDeleted = questionDeleted == null ? DEFAULT_DELETED : questionDeleted;
        this.ruleCreated=  ruleCreated ==null? DEFAULT_RULE_CREATED: ruleCreated;

    }
}
