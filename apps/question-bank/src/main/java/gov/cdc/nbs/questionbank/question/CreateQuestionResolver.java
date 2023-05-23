package gov.cdc.nbs.questionbank.question;

import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionResponse;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;

@Controller
public class CreateQuestionResolver {
    private final KafkaProducer producer;

    CreateQuestionResolver(KafkaProducer producer) {
        this.producer = producer;
    }

    private long getUserId() {
        return ((NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public QuestionResponse createTextQuestion(@Argument QuestionRequest.TextQuestionData data) {
        // Build request
        String reqestId = UUID.randomUUID().toString();
        long userId = getUserId();
        var request = new QuestionRequest.CreateTextQuestionRequest(reqestId, userId, data);

        // post request to kafka
        producer.requestEventEnvelope(request);

        // return response
        return new QuestionResponse(reqestId);
    }
}
