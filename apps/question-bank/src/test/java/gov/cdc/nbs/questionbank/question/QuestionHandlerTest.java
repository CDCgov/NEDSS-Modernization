package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.kafka.exception.UserNotAuthorizedException;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DateQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.DropdownQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.NumericQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;

@ExtendWith(MockitoExtension.class)
class QuestionHandlerTest {

    @Mock
    private UserService userService;

    @Mock
    private QuestionCreator creator;

    @Mock
    private RequestStatusProducer producer;

    @InjectMocks
    private QuestionHandler handler;

    private final Long userId = 99L;
    private final String permission = "LDFADMINISTRATION-SYSTEM";

    @Test
    void should_throw_not_authorized() {
        // given i am not authorized
        when(userService.isAuthorized(userId, permission)).thenReturn(false);

        // when i submit a question request,  then i receive an exception
        QuestionRequest request = createTextQuestionRequest();
        assertThrows(UserNotAuthorizedException.class, () ->
            handler.handleQuestionRequest(request));
    }

    @Test
    void should_handle_text_request() {
        // given i am authorized
        when(userService.isAuthorized(userId, permission)).thenReturn(true);

        // and the creator will create an entity
        when(creator.create(Mockito.any(TextQuestionData.class), eq(userId))).thenReturn(textEntity());

        // when i submit a create text question request
        handler.handleQuestionRequest(createTextQuestionRequest());

        // then the creator is called
        ArgumentCaptor<TextQuestionData> captor = ArgumentCaptor.forClass(TextQuestionData.class);
        verify(creator, times(1)).create(captor.capture(), eq(userId));
        assertEquals(createTextQuestionRequest().data(), captor.getValue());
    }

    @Test
    void should_handle_date_request() {
        // given i am authorized
        when(userService.isAuthorized(userId, permission)).thenReturn(true);

        // and the creator will create an entity
        when(creator.create(Mockito.any(DateQuestionData.class), eq(userId))).thenReturn(dateEntity());

        // when i submit a create text question request
        handler.handleQuestionRequest(createDateQuestionRequest());

        // then the creator is called
        ArgumentCaptor<DateQuestionData> captor = ArgumentCaptor.forClass(DateQuestionData.class);
        verify(creator, times(1)).create(captor.capture(), eq(userId));
        assertEquals(createDateQuestionRequest().data(), captor.getValue());
    }

    @Test
    void should_handle_dropdown_request() {
        // given i am authorized
        when(userService.isAuthorized(userId, permission)).thenReturn(true);

        // and the creator will create an entity
        when(creator.create(Mockito.any(DropdownQuestionData.class), eq(userId))).thenReturn(dropdownEntity());

        // when i submit a create text question request
        QuestionRequest.CreateDropdownQuestionRequest request = createDropdownQuestionRequest();
        handler.handleQuestionRequest(request);

        // then the creator is called
        ArgumentCaptor<DropdownQuestionData> captor = ArgumentCaptor.forClass(DropdownQuestionData.class);
        verify(creator, times(1)).create(captor.capture(), eq(userId));
        assertEquals(request.data(), captor.getValue());
    }

    @Test
    void should_handle_numeric_request() {
        // given i am authorized
        when(userService.isAuthorized(userId, permission)).thenReturn(true);

        // and the creator will create an entity
        when(creator.create(Mockito.any(NumericQuestionData.class), eq(userId))).thenReturn(numericEntity());

        // when i submit a create text question request
        QuestionRequest.CreateNumericQuestionRequest request = createNumericQuestionRequest();
        handler.handleQuestionRequest(request);

        // then the creator is called
        ArgumentCaptor<NumericQuestionData> captor = ArgumentCaptor.forClass(NumericQuestionData.class);
        verify(creator, times(1)).create(captor.capture(), eq(userId));
        assertEquals(request.data(), captor.getValue());
    }


    private QuestionRequest.CreateTextQuestionRequest createTextQuestionRequest() {
        return new QuestionRequest.CreateTextQuestionRequest(
                "text request Id",
                userId,
                new TextQuestionData(
                        "label",
                        "tooltip",
                        25,
                        "placeholder",
                        "defaultValue"));
    }

    private QuestionRequest.CreateDateQuestionRequest createDateQuestionRequest() {
        return new QuestionRequest.CreateDateQuestionRequest(
                "text request Id",
                userId,
                new DateQuestionData(
                        "label",
                        "tooltip",
                        true));
    }

    private QuestionRequest.CreateDropdownQuestionRequest createDropdownQuestionRequest() {
        return new QuestionRequest.CreateDropdownQuestionRequest(
                "text request Id",
                userId,
                new DropdownQuestionData(
                        "label",
                        "tooltip",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        true));
    }

    private QuestionRequest.CreateNumericQuestionRequest createNumericQuestionRequest() {
        return new QuestionRequest.CreateNumericQuestionRequest(
                "text request Id",
                userId,
                new NumericQuestionData(
                        "label",
                        "tooltip",
                        1,
                        3,
                        2,
                        UUID.randomUUID()));
    }

    private TextQuestionEntity textEntity() {
        TextQuestionEntity entity = new TextQuestionEntity();
        entity.setId(UUID.randomUUID());
        return entity;
    }

    private DateQuestionEntity dateEntity() {
        DateQuestionEntity entity = new DateQuestionEntity();
        entity.setId(UUID.randomUUID());
        return entity;
    }

    private DropdownQuestionEntity dropdownEntity() {
        DropdownQuestionEntity entity = new DropdownQuestionEntity();
        entity.setId(UUID.randomUUID());
        return entity;
    }

    private NumericQuestionEntity numericEntity() {
        NumericQuestionEntity entity = new NumericQuestionEntity();
        entity.setId(UUID.randomUUID());
        return entity;
    }

}
