package gov.cdc.nbs.questionbank.kafka.config;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RequestProperties.class)
class RequestPropertiesTest {

    @Value("${kafkadef.topics.questionbank.request}")
    private String request;

    @Value("${kafkadef.topics.questionbank.status}")
    private String status;

    @Autowired
    private RequestProperties properties;

    @Test
    void should_have_config_properties() {
        assertEquals(status, properties.status());
        assertEquals(request, properties.request());
    }

    @Test
    void should_have_proper_defaults() {
        RequestProperties newProps = new RequestProperties();
        assertEquals("questionbank-status", newProps.status());
        assertEquals("questionbank", newProps.request());
    }
}
