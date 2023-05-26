package gov.cdc.nbs.questionbank.question;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.authentication.SecurityUtil;
import gov.cdc.nbs.authentication.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.authentication.SecurityUtil.Operations;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class DeleteQuestionResolver {


	private final QuestionHandler questionHandler;

	@MutationMapping()
	@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	public QuestionBankEventResponse deleteQuestion(@Argument Long questionId) {
		Long userId = SecurityUtil.getUserDetails().getId();
		return questionHandler.sendDeleteQuestionEvent(questionId,userId);
	}

}
