package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.question.model.DisplayOption;
import gov.cdc.nbs.questionbank.question.model.DisplayControlOptions;
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

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock
    private QuestionCreator creator;

    @Mock
    private UserDetailsProvider provider;

    @Mock
    private QuestionManagementUtil questionManagementUtil;

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

    @Test
    void should_return_displayControlOptions() {
        when(questionManagementUtil.getDisplayControlOptions()).thenReturn(getDisplayControlOptions());
        DisplayControlOptions result = controller.getDisplayControlOptions();
        assertNotNull(result);
        assertFalse(result.codedDisplayControl().isEmpty());
        assertFalse(result.dateDisplayControl().isEmpty());
        assertFalse(result.numericDisplayControl().isEmpty());
        assertFalse(result.textDisplayControl().isEmpty());

    }

    private DisplayControlOptions getDisplayControlOptions() {
        return new DisplayControlOptions(
                Arrays.asList(new DisplayOption(101l, "mock_101"), new DisplayOption(102l, "mock_102")),
                Arrays.asList(new DisplayOption(201l, "mock_201"), new DisplayOption(202l, "mock_202")),
                Arrays.asList(new DisplayOption(301l, "mock_301"), new DisplayOption(302l, "mock_302")),
                Arrays.asList(new DisplayOption(401l, "mock_401"), new DisplayOption(401l, "mock_402")));
    }
}
