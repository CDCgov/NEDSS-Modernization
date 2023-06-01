package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionResponse;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@AllArgsConstructor
@Controller
public class UpdateQuestionResolver {

    private final QuestionHandler questionHandler;


    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public QuestionResponse updateQuestion(@Argument QuestionRequest.UpdateTextQuestionRequest data) {
        String reqestId = UUID.randomUUID().toString();
        long userId = getUserId();
        var request = new QuestionRequest.CreateTextQuestionRequest(reqestId, userId, data);

        producer.requestEventEnvelope(request);

        return new QuestionResponse(reqestId);
    }
}
