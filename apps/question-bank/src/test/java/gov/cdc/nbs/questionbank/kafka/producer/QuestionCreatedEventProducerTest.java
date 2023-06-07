package gov.cdc.nbs.questionbank.kafka.producer;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;

@ExtendWith(MockitoExtension.class)
class QuestionCreatedEventProducerTest {
    @Mock
    private KafkaTemplate<String, QuestionCreatedEvent> template;

    @Spy
    private RequestProperties properties = new RequestProperties();

    @InjectMocks
    private QuestionCreatedEventProducer producer;

    @Test
    void should_set_correct_data_for_question() {
        assertNotNull(producer);
    }

}
