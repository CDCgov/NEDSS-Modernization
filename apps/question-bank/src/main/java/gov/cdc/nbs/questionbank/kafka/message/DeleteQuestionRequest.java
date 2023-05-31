package gov.cdc.nbs.questionbank.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteQuestionRequest {
	
	private String requestId;
	private long questionId;
	private long userId;

}
