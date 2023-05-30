package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entities.AuditInfo;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DateQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DropdownQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.NumericQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;

@ExtendWith(MockitoExtension.class)
class QuestionCreatorTest {

    @Mock
    private DisplayElementRepository elementRepository;

    @Mock
    private EntityManager entityManager;

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
        // given question data
        TextQuestionData data = textQuestionData();

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
    }

    @Test
    void should_create_numeric_question() {
        // given question data
        NumericQuestionData data = numericQuestionData();

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
    }

    @Test
    void should_create_dropdown_question() {
        // given question data
        DropdownQuestionData data = dropdownQuestionData();

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
    }

    @Test
    void should_create_date_question() {
        // given question data
        DateQuestionData data = dateQuestionData();

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
    }

    private void validateAudit(AuditInfo auditInfo) {
        assertEquals(userId, auditInfo.getAddUserId());
        assertEquals(expectedTime, auditInfo.getAddTime());
        assertEquals(userId, auditInfo.getLastUpdateUserId());
        assertEquals(expectedTime, auditInfo.getLastUpdate());
    }

    private TextQuestionData textQuestionData() {
        return new TextQuestionData(
                "test label",
                "test tooltip",
                13,
                "test placeholder",
                "some default value");
    }

    private DateQuestionData dateQuestionData() {
        return new DateQuestionData(
                "test label",
                "test tooltip",
                true);
    }

    private NumericQuestionData numericQuestionData() {
        return new NumericQuestionData(
                "test label",
                "test tooltip",
                -3,
                543,
                -2,
                null);
    }

    private DropdownQuestionData dropdownQuestionData() {
        return new DropdownQuestionData(
                "test label",
                "test tooltip",
                UUID.randomUUID(),
                UUID.randomUUID(),
                true);
    }

}
