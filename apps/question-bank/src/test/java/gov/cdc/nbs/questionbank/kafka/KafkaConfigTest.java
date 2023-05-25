package gov.cdc.nbs.questionbank.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import gov.cdc.nbs.questionbank.kafka.message.RequestStatus;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;
import gov.cdc.nbs.questionbank.question.QuestionHandler;


@AutoConfigureJson
@SpringBootTest
@ActiveProfiles({"test"})
@EmbeddedKafka(partitions = 1, bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class KafkaConfigTest {

    @Autowired
    private KafkaTemplate<String, RequestStatus> kafkaTemplate;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private ProducerFactory<String, RequestStatus> producerFactory;

    @SpyBean
    private QuestionHandler handler;

    @Test
    void should_resolve_beans_for_kafka_resources() {
        assertNotNull(kafkaTemplate);
        assertNotNull(producer);
        assertNotNull(producerFactory);
    }

    @Test
    void should_send_and_receive_message() {
        var request = new QuestionRequest.CreateTextQuestionRequest(
                "requestId",
                1L,
                new QuestionRequest.TextQuestionData(
                        "a label",
                        "tooltip",
                        100,
                        "placeholder",
                        "some default"));


        // Send a create text question request
        producer.requestEventEnvelope(request);

        // Verify consumer receives request
        ArgumentCaptor<QuestionRequest.CreateTextQuestionRequest> captor =
                ArgumentCaptor.forClass(QuestionRequest.CreateTextQuestionRequest.class);

        verify(handler, timeout(5_000)).handleQuestionRequest(captor.capture());

        QuestionRequest.CreateTextQuestionRequest actual = captor.getValue();
        assertThat(actual)
                .returns("requestId", QuestionRequest.CreateTextQuestionRequest::requestId)
                .returns(1L, QuestionRequest.CreateTextQuestionRequest::userId)
                .returns("a label", (a) -> a.data().label())
                .returns("tooltip", (a) -> a.data().tooltip())
                .returns(100, (a) -> a.data().maxLength())
                .returns("placeholder", (a) -> a.data().placeholder());
    }
}
