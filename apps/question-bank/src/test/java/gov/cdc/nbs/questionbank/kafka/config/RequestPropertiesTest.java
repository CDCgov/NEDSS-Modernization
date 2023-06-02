package gov.cdc.nbs.questionbank.kafka.config;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RequestProperties.class)
class RequestPropertiesTest {

    @Value("${kafkadef.topics.questionbank.question-created}")
    private String created;

    @Value("${kafkadef.topics.questionbank.question-deleted}")
    private String deleted;

    @Autowired
    private RequestProperties properties;

    @Test
    void should_have_config_properties() {
        assertEquals(created, properties.questionCreated());
        assertEquals(deleted, properties.questionDeleted());
    }

    @Test
    void should_have_proper_defaults() {
        RequestProperties newProps = new RequestProperties();
        assertEquals("question-created", newProps.questionCreated());
        assertEquals("question-deleted", newProps.questionDeleted());
    }
}
