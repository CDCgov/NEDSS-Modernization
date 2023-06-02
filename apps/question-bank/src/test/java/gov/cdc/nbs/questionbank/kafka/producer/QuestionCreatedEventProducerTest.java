package gov.cdc.nbs.questionbank.kafka.producer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionCreatedEventProducer.EnabledProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.questionnaire.EntityMapper;

@ExtendWith(MockitoExtension.class)
class QuestionCreatedEventProducerTest {
    @Mock
    private KafkaTemplate<String, QuestionCreatedEvent> template;

    @Spy
    private EntityMapper entityMapper = new EntityMapper();

    @Spy
    private RequestProperties properties = new RequestProperties("test-created-topic", "");

    @InjectMocks
    private EnabledProducer producer;

    @Test
    void should_set_correct_data_for_question() {
        // given an entity has been created
        TextQuestionEntity entity = textQuestion();

        // when i send a created event
        producer.send(entity);

        // then the right data is posted to kafka
        ArgumentCaptor<QuestionCreatedEvent> captor = ArgumentCaptor.forClass(QuestionCreatedEvent.class);
        verify(template).send(eq(properties.questionCreated()), captor.capture());

        QuestionCreatedEvent actual = captor.getValue();
        assertEquals(entity.getId(), actual.element().id());

        assertEquals(entity.getAudit().getAddTime(), actual.createdAt());
        assertEquals(entity.getAudit().getAddUserId().longValue(), actual.createdBy());
    }


    private TextQuestionEntity textQuestion() {
        TextQuestionEntity entity = new TextQuestionEntity(add());
        entity.setId(UUID.randomUUID());
        return entity;
    }

    private QuestionCommand.AddTextQuestion add() {
        return new QuestionCommand.AddTextQuestion(
                null,
                123L,
                Instant.now(),
                "Label",
                "Tooltip",
                13,
                "placeHolder",
                "default value",
                CodeSet.LOCAL);
    }
}
