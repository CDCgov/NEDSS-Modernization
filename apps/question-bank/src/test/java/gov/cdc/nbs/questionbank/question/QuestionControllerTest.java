package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.question.model.DisplayOption;
import gov.cdc.nbs.questionbank.question.model.DisplayTypeOptions;
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
import java.util.List;

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
    void should_return_displayTypeOptions() {
        when(questionManagementUtil.getDisplayTypeOptions()).thenReturn(getDisplayTypeOptions());
        DisplayTypeOptions result = controller.getDisplayTypeOptions();
        assertNotNull(result);
        assertFalse(result.codedQuestionTypes().isEmpty());
        assertFalse(result.dateQuestionTypes().isEmpty());
        assertFalse(result.numericQuestionTypes().isEmpty());
        assertFalse(result.textQuestionTypes().isEmpty());

    }

    private DisplayTypeOptions getDisplayTypeOptions() {
        return new DisplayTypeOptions(
                Arrays.asList(new DisplayOption(101l, "desc_101"), new DisplayOption(102l, "desc_102")),
                Arrays.asList(new DisplayOption(201l, "desc_201"), new DisplayOption(202l, "desc_202")),
                Arrays.asList(new DisplayOption(301l, "desc_301"), new DisplayOption(302l, "desc_302")),
                Arrays.asList(new DisplayOption(401l, "desc_401"), new DisplayOption(401l, "desc_402")));
    }
}
