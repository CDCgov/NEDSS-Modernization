package gov.cdc.nbs.questionbank.kafka.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.kafka.exception.RequestException;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import gov.cdc.nbs.questionbank.question.QuestionHandler;

@ExtendWith(MockitoExtension.class)
class TopicListenerTest {

    @Spy
    ObjectMapper mapper =
            new ObjectMapper()
                    .setSerializationInclusion(Include.NON_NULL);

    @Mock
    private QuestionHandler createHandler;

    @InjectMocks
    private TopicListener consumer;

    @Test
    void testReceivingCreateRequest() throws JsonProcessingException {
        String message = """
                {
                  "type": "QuestionRequest$CreateTextQuestionRequest",
                  "requestId": "requestId",
                  "userId": 123,
                  "label": "some label",
                  "tooltip": "tooltip",
                  "maxLength": 10,
                  "placeholder": "placeholder"
                }
                """;

        consumer.onMessage(message, "requestId");

        ArgumentCaptor<QuestionRequest.CreateTextQuestionRequest> captor =
                ArgumentCaptor.forClass(QuestionRequest.CreateTextQuestionRequest.class);

        verify(createHandler, times(1)).handleQuestionRequest(captor.capture());

        QuestionRequest.CreateTextQuestionRequest actual = captor.getValue();
        assertThat(actual)
                .returns("requestId", QuestionRequest.CreateTextQuestionRequest::requestId)
                .returns(123L, QuestionRequest.CreateTextQuestionRequest::userId)
                .returns("some label", QuestionRequest.CreateTextQuestionRequest::label)
                .returns("tooltip", QuestionRequest.CreateTextQuestionRequest::tooltip)
                .returns(10, QuestionRequest.CreateTextQuestionRequest::maxLength)
                .returns("placeholder", QuestionRequest.CreateTextQuestionRequest::placeholder);
    }

    @Test
    void testReceivingUpdateRequest() throws JsonProcessingException {
        String message = """
                {
                  "type": "QuestionRequest$UpdateTextQuestionRequest",
                  "questionId": 321,
                  "requestId": "requestId",
                  "userId": 123,
                  "label": "some label",
                  "tooltip": "tooltip",
                  "maxLength": 10,
                  "placeholder": "placeholder"
                }
                """;

        consumer.onMessage(message, "requestId");

        ArgumentCaptor<QuestionRequest.UpdateTextQuestionRequest> captor =
                ArgumentCaptor.forClass(QuestionRequest.UpdateTextQuestionRequest.class);

        verify(createHandler, times(1)).handleQuestionRequest(captor.capture());

        QuestionRequest.UpdateTextQuestionRequest actual = captor.getValue();
        assertThat(actual)
                .returns("requestId", QuestionRequest.UpdateTextQuestionRequest::requestId)
                .returns(321L, QuestionRequest.UpdateTextQuestionRequest::questionId)
                .returns(123L, QuestionRequest.UpdateTextQuestionRequest::userId)
                .returns("some label", QuestionRequest.UpdateTextQuestionRequest::label)
                .returns("tooltip", QuestionRequest.UpdateTextQuestionRequest::tooltip)
                .returns(10, QuestionRequest.UpdateTextQuestionRequest::maxLength)
                .returns("placeholder", QuestionRequest.UpdateTextQuestionRequest::placeholder);
    }

    @Test
    void testJsonProcessingException() {
        String message = "bad message";
        assertThrows(RequestException.class, () -> consumer.onMessage(message, "requestId"));
    }

    @Test
    void testInvalidRequest() throws JsonMappingException, JsonProcessingException {
        ObjectMapper mockMapper = Mockito.mock(ObjectMapper.class);
        RequestStatusProducer producer = Mockito.mock(RequestStatusProducer.class);
        TopicListener listener = new TopicListener(mockMapper, createHandler, producer);

        Mockito.when(mockMapper.readValue(Mockito.anyString(), Mockito.eq(QuestionBankRequest.class)))
                .thenReturn(
                        new QuestionBankRequest() {
                            @Override
                            public String requestId() {
                                return "";
                            }

                            @Override
                            public long userId() {
                                return 1L;
                            }
                        });

        assertThrows(RequestException.class, () -> listener.onMessage("any message", "some key"));

        verify(producer, times(1)).failure(Mockito.any(), Mockito.any());
        verify(createHandler, times(0)).handleQuestionRequest((Mockito.any()));
    }

}
