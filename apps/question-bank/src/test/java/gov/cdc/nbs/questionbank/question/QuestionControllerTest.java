package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.model.Question.TextQuestion;
import gov.cdc.nbs.questionbank.question.request.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock
    private QuestionCreator creator;

    @Mock
    private UserDetailsProvider provider;

    @InjectMocks
    private QuestionController controller;

    @Test
    void should_return_create_question_response() {
        // given a valid user is logged in
        NbsUserDetails user = userDetails();
        when(provider.getCurrentUserDetails()).thenReturn(user);

        // and a create text question request is sent
        CreateTextQuestionRequest request = QuestionRequestMother.localTextRequest();

        // and the creator will create the question
        when(creator.create(eq(user.getId()), Mockito.any(CreateTextQuestionRequest.class)))
                .thenReturn(new TextQuestion(19L,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));

        // when the request is processed
        Question response = controller.createTextQuestion(request);

        // then a valid response is given
        assertEquals(19L, response.id());
    }

    private NbsUserDetails userDetails() {
        return new NbsUserDetails(
                1L,
                "test",
                "test",
                "test",
                false,
                false,
                null,
                null,
                null,
                true);
    }
}
