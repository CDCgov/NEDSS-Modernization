package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.response.CreateQuestionResponse;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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
        CreateQuestionRequest request = QuestionRequestMother.localTextRequest();

        // and the creator will create the question
        when(creator.create(eq(user.getId()), Mockito.any(CreateQuestionRequest.class))).thenReturn(19L);

        // when the request is processed
        CreateQuestionResponse response = controller.createQuestion(request);

        // then a valid response is given
        assertEquals(19L, response.questionId());
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
