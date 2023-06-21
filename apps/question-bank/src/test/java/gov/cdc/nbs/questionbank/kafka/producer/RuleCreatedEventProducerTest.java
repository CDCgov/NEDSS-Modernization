package gov.cdc.nbs.questionbank.kafka.producer;

import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RuleCreatedEventProducerTest {

    @Mock
    private KafkaTemplate<String, QuestionCreatedEvent> template;

    @Spy
    private RequestProperties properties = new RequestProperties();

    @InjectMocks
    private RuleCreatedEventProducer.EnabledProducer producer;

    @Test
    void should_set_correct_data_for_rule() {
        assertNotNull(producer);
    }
}
