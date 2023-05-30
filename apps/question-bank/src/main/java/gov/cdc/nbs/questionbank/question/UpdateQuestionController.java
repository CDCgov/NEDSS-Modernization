package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import util.SecurityUtil;

@Controller
@AllArgsConstructor
public class UpdateQuestionController {


    private static final String AND = " and ";
    private static final String HAS_AUTHORITY = "hasAuthority('";
    private static final String VIEW_QUESTION = HAS_AUTHORITY + SecurityUtil.Operations.VIEW + "-" + SecurityUtil.BusinessObjects.QUESTION + "')";

    private static final String UPDATE_QUESTION = HAS_AUTHORITY + SecurityUtil.Operations.UPDATE + "-" + SecurityUtil.BusinessObjects.QUESTION;
    private static final String VIEW_AND_UPDATE_QUESTION = VIEW_QUESTION + AND + UPDATE_QUESTION;


    private final QuestionHandler updateQuestionController;

    @MutationMapping()
    @PreAuthorize(VIEW_AND_UPDATE_QUESTION)
    public QuestionBankEventResponse updateQuestion(@Argument Long questionId) {
        return updateQuestionController.sendUpdateQuestionEvent(questionId);
    }


}
