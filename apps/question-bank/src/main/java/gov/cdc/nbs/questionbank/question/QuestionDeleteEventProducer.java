package gov.cdc.nbs.questionbank.question;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.kafka.message.DeleteQuestionRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestionDeleteEventProducer {
	
	@Value("${kafkadef.topics.questionbank.deletestatus}")
	private String questionDeleteStatusTopic;
	
	private final KafkaTemplate<String, DeleteQuestionRequest> kafkaQuestionDeleteTemplate;
	
	
	public void send(final DeleteQuestionRequest status ) {
		kafkaQuestionDeleteTemplate.send(questionDeleteStatusTopic,status);
	}
	

}
