package gov.cdc.nbs.questionbank.question;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import lombok.AllArgsConstructor;
import util.SecurityUtil.BusinessObjects;
import util.SecurityUtil.Operations;

@Controller
@AllArgsConstructor
public class QuestionController {

	private static final String AND = " and ";
	private static final String HAS_AUTHORITY = "hasAuthority('";
	private static final String VIEW_QUESTION = HAS_AUTHORITY + Operations.VIEW + "-" + BusinessObjects.QUESTION + "')";
	private static final String DELETE_QUESTION = HAS_AUTHORITY + Operations.DELETE + "-" + BusinessObjects.QUESTION;

	//private static final String UPDATE_QUESTION = HAS_AUTHORITY + Operations.UPDATE + "-" + BusinessObjects.QUESTION;

	private static final String VIEW_AND_DELETE_QUESTION = VIEW_QUESTION + AND + DELETE_QUESTION;
	//private static final String VIEW_AND_UPDATE_QUESTION = VIEW_QUESTION + AND + UPDATE_QUESTION;

	private final QuestionHandler questionController;

	@MutationMapping()
	@PreAuthorize(VIEW_AND_DELETE_QUESTION)
	public QuestionBankEventResponse deleteQuestion(@Argument Long questionId) {
		return questionController.sendDeleteQuestionEvent(questionId);
	}
}

