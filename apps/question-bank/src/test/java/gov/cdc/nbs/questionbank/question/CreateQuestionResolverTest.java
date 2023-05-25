package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DateQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DropdownQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.NumericQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;

@ExtendWith(MockitoExtension.class)
class CreateQuestionResolverTest {

    @Mock
    private KafkaProducer producer;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @InjectMocks
    private CreateQuestionResolver resolver;

    @Test
    void should_send_create_text_request() {
        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data = textQuestionData();
        resolver.createTextQuestion(data);

        // then the request is send to kafka
        ArgumentCaptor<QuestionRequest.CreateTextQuestionRequest> captor = 
            ArgumentCaptor.forClass(QuestionRequest.CreateTextQuestionRequest.class);
        verify(producer, times(1))
            .requestEventEnvelope(captor.capture());
        QuestionRequest.CreateTextQuestionRequest actual = captor.getValue();
        assertEquals(userDetails().getId().longValue(), actual.userId());
        assertEquals(data, actual.data());
    }

    @Test
    void should_send_create_date_request() {
        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data = dateQuestionData();
        resolver.createDateQuestion(data);

        // then the request is send to kafka
        ArgumentCaptor<QuestionRequest.CreateDateQuestionRequest> captor = 
            ArgumentCaptor.forClass(QuestionRequest.CreateDateQuestionRequest.class);
        verify(producer, times(1))
            .requestEventEnvelope(captor.capture());
        QuestionRequest.CreateDateQuestionRequest actual = captor.getValue();
        assertEquals(userDetails().getId().longValue(), actual.userId());
        assertEquals(data, actual.data());
    }

    @Test
    void should_send_create_numeric_request() {
        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data = numericQuestionData();
        resolver.createNumericQuestion(data);

        // then the request is send to kafka
        ArgumentCaptor<QuestionRequest.CreateNumericQuestionRequest> captor = 
            ArgumentCaptor.forClass(QuestionRequest.CreateNumericQuestionRequest.class);
        verify(producer, times(1))
            .requestEventEnvelope(captor.capture());
        QuestionRequest.CreateNumericQuestionRequest actual = captor.getValue();
        assertEquals(userDetails().getId().longValue(), actual.userId());
        assertEquals(data, actual.data());
    }

    @Test
    void should_send_create_dropdown_request() {
        // given I am authenticated
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

        // given I submit a create text question request
        var data =dropdownQuestionData();
        resolver.createDropdownQuestion(data);

        // then the request is send to kafka
        ArgumentCaptor<QuestionRequest.CreateDropdownQuestionRequest> captor = 
            ArgumentCaptor.forClass(QuestionRequest.CreateDropdownQuestionRequest.class);
        verify(producer, times(1))
            .requestEventEnvelope(captor.capture());
        QuestionRequest.CreateDropdownQuestionRequest actual = captor.getValue();
        assertEquals(userDetails().getId().longValue(), actual.userId());
        assertEquals(data, actual.data());
    }

    private NbsUserDetails userDetails() {
        return new NbsUserDetails(1234L,
                null,
                null,
                null,
                false,
                false,
                null,
                null,
                null,
                null,
                true);
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
