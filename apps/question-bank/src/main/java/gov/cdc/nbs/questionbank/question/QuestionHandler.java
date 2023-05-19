package gov.cdc.nbs.questionbank.question;

import java.util.UUID;


import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import util.SecurityUtil;

@Service
@RequiredArgsConstructor
public class QuestionHandler {
	
	private final KafkaProducer producer;

    public void handleQuestionRequest(QuestionRequest request) {
        // stub
    }
    
    public QuestionBankEventResponse sendDeleteQuestionEvent(Long questionId) {
    	var user = SecurityUtil.getUserDetails();
    	var deleteEvent  = new QuestionRequest.DeleteQuestionRequest(getRequestId(),questionId, user.getId());
    	return sendQuestionDeleteEvent(deleteEvent,questionId);
	}
    
    
    private QuestionBankEventResponse sendQuestionDeleteEvent(QuestionRequest request, Long questionId) {
    	producer.requestEventEnvelope(request);
    	return new QuestionBankEventResponse(request.requestId(), questionId);
    }
    
    private String getRequestId() {
        return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
    }

}
