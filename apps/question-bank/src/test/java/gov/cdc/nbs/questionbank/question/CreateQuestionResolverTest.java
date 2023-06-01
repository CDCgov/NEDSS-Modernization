package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;
import gov.cdc.nbs.questionbank.support.QuestionDataMother;

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
        var data = QuestionDataMother.textQuestionData();
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
        var data = QuestionDataMother.dateQuestionData();
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
        var data = QuestionDataMother.numericQuestionData();
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
        var data = QuestionDataMother.dropdownQuestionData();
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

}
