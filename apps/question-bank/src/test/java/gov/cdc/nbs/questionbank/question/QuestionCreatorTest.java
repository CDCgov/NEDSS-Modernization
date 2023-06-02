package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entities.AuditInfo;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionCreatedEventProducer.DisabledProducer;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateDateQuestion;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateDropdownQuestion;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateNumericQuestion;
import gov.cdc.nbs.questionbank.question.QuestionRequest.CreateTextQuestion;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;

@ExtendWith(MockitoExtension.class)
class QuestionCreatorTest {

    @Mock
    private DisplayElementRepository elementRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private DisabledProducer createEvent;

    private final Instant expectedTime = Instant.now();

    @Spy
    Clock clock = Clock.fixed(
            expectedTime,
            ZoneId.of("UTC"));

    @InjectMocks
    private QuestionCreator creator;

    private final Long userId = 123L;

    @Test
    void should_create_text_question() {
        when(elementRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        // given question data
        CreateTextQuestion data = textQuestionData();

        // when a create request is received
        creator.create(data, userId);

        // then the question is saved to the database
        ArgumentCaptor<TextQuestionEntity> captor = ArgumentCaptor.forClass(TextQuestionEntity.class);
        verify(elementRepository, times(1)).save(captor.capture());

        TextQuestionEntity actual = captor.getValue();
        assertEquals(data.label(), actual.getLabel());
        assertEquals(data.tooltip(), actual.getTooltip());
        assertEquals(data.maxLength(), actual.getMaxLength());
        assertEquals(data.placeholder(), actual.getPlaceholder());
        assertEquals(data.defaultValue(), actual.getDefaultTextValue());
        validateAudit(actual.getAudit());

        // and the create event producer is called
        verify(createEvent, times(1)).send(Mockito.any(DisplayElementEntity.class));
    }

    @Test
    void should_create_numeric_question() {
        when(elementRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        // given question data
        CreateNumericQuestion data = numericQuestionData();

        // when a create request is received
        creator.create(data, userId);

        // then the question is saved to the database
        ArgumentCaptor<NumericQuestionEntity> captor = ArgumentCaptor.forClass(NumericQuestionEntity.class);
        verify(elementRepository, times(1)).save(captor.capture());

        NumericQuestionEntity actual = captor.getValue();
        assertEquals(data.label(), actual.getLabel());
        assertEquals(data.tooltip(), actual.getTooltip());
        assertEquals(data.minValue(), actual.getMinValue());
        assertEquals(data.maxValue(), actual.getMaxValue());
        assertEquals(data.defaultValue(), actual.getDefaultNumericValue());
        validateAudit(actual.getAudit());

        // and the create event producer is called
        verify(createEvent, times(1)).send(Mockito.any(DisplayElementEntity.class));
    }

    @Test
    void should_create_dropdown_question() {
        when(elementRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        // given question data
        CreateDropdownQuestion data = dropdownQuestionData();

        // when a create request is received
        creator.create(data, userId);

        // then the question is saved to the database
        ArgumentCaptor<DropdownQuestionEntity> captor = ArgumentCaptor.forClass(DropdownQuestionEntity.class);
        verify(elementRepository, times(1)).save(captor.capture());

        DropdownQuestionEntity actual = captor.getValue();
        assertEquals(data.label(), actual.getLabel());
        assertEquals(data.tooltip(), actual.getTooltip());
        verify(entityManager, times(1))
                .getReference(ValueSet.class, data.valueSet());
        verify(entityManager, times(1))
                .getReference(ValueEntity.class, data.defaultValue());
        validateAudit(actual.getAudit());

        // and the create event producer is called
        verify(createEvent, times(1)).send(Mockito.any(DisplayElementEntity.class));
    }

    @Test
    void should_create_date_question() {
        when(elementRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        // given question data
        CreateDateQuestion data = dateQuestionData();

        // when a create request is received
        creator.create(data, userId);

        // then the question is saved to the database
        ArgumentCaptor<DateQuestionEntity> captor = ArgumentCaptor.forClass(DateQuestionEntity.class);
        verify(elementRepository, times(1)).save(captor.capture());

        DateQuestionEntity actual = captor.getValue();
        assertEquals(data.label(), actual.getLabel());
        assertEquals(data.tooltip(), actual.getTooltip());
        assertEquals(data.allowFutureDates(), actual.isAllowFuture());
        validateAudit(actual.getAudit());

        // and the create event producer is called
        verify(createEvent, times(1)).send(Mockito.any(DisplayElementEntity.class));
    }

    private void validateAudit(AuditInfo auditInfo) {
        assertEquals(userId, auditInfo.getAddUserId());
        assertEquals(expectedTime, auditInfo.getAddTime());
        assertEquals(userId, auditInfo.getLastUpdateUserId());
        assertEquals(expectedTime, auditInfo.getLastUpdate());
    }

    private CreateTextQuestion textQuestionData() {
        return new CreateTextQuestion(
                "test label",
                "test tooltip",
                13,
                "test placeholder",
                "some default value",
                CodeSet.LOCAL);
    }

    private CreateDateQuestion dateQuestionData() {
        return new CreateDateQuestion(
                "test label",
                "test tooltip",
                true,
                CodeSet.LOCAL);
    }

    private CreateNumericQuestion numericQuestionData() {
        return new CreateNumericQuestion(
                "test label",
                "test tooltip",
                -3,
                543,
                -2,
                null,
                CodeSet.LOCAL);
    }

    private CreateDropdownQuestion dropdownQuestionData() {
        return new CreateDropdownQuestion(
                "test label",
                "test tooltip",
                UUID.randomUUID(),
                UUID.randomUUID(),
                true,
                CodeSet.LOCAL);
    }

}
