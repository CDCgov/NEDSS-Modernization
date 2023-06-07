package gov.cdc.nbs.questionbank.question;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;

@ExtendWith(MockitoExtension.class)
class DeleteQuestionResolverTest {

	@Mock
	private UserDetailsProvider userDetailsProvider;

	@Mock
	private DeleteQuestionService deleteQuestionService;

	@InjectMocks
	DeleteQuestionResolver resolver;

	@Test
	void should_send_delete_question_request() {
		UUID questionId = UUID.randomUUID();

		when(deleteQuestionService.deleteQuestion(Mockito.any())).thenReturn(1);

		// given I am authenticated
		when(userDetailsProvider.getCurrentUserDetails()).thenReturn(userDetails());

		// given I submit a delete question request
		resolver.deleteQuestion(questionId);

		verify(deleteQuestionService, times(1)).deleteQuestion(Mockito.any());

	}

	private NbsUserDetails userDetails() {
		return new NbsUserDetails(1234L, null, null, null, false, false, null, null, null, null, true);
	}

}
