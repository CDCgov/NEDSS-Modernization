package gov.cdc.nbs.questionbank.question;

import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionResponse;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;

@Controller
public class CreateQuestionResolver {
    private final KafkaProducer producer;
    private final UserDetailsProvider userDetailsProvider;

    CreateQuestionResolver(
            KafkaProducer producer,
            UserDetailsProvider userDetailsProvider) {
        this.producer = producer;
        this.userDetailsProvider = userDetailsProvider;
    }

    private long getUserId() {
        return userDetailsProvider.getCurrentUserDetails().getId();
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public QuestionResponse createTextQuestion(@Argument QuestionRequest.TextQuestionData data) {
        String reqestId = UUID.randomUUID().toString();
        long userId = getUserId();
        var request = new QuestionRequest.CreateTextQuestionRequest(reqestId,0, userId, data);

        producer.requestEventEnvelope(request);

        return new QuestionResponse(reqestId);
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public QuestionResponse createDateQuestion(@Argument QuestionRequest.DateQuestionData data) {
        String reqestId = UUID.randomUUID().toString();
        long userId = getUserId();
        var request = new QuestionRequest.CreateDateQuestionRequest(reqestId,0, userId, data);

        producer.requestEventEnvelope(request);

        return new QuestionResponse(reqestId);
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public QuestionResponse createDropdownQuestion(@Argument QuestionRequest.DropdownQuestionData data) {
        String reqestId = UUID.randomUUID().toString();
        long userId = getUserId();
        var request = new QuestionRequest.CreateDropdownQuestionRequest(reqestId, 0,userId, data);

        producer.requestEventEnvelope(request);

        return new QuestionResponse(reqestId);
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public QuestionResponse createNumericQuestion(@Argument QuestionRequest.NumericQuestionData data) {
        String reqestId = UUID.randomUUID().toString();
        long userId = getUserId();
        var request = new QuestionRequest.CreateNumericQuestionRequest(reqestId,0, userId, data);

        producer.requestEventEnvelope(request);

        return new QuestionResponse(reqestId);
    }


}
