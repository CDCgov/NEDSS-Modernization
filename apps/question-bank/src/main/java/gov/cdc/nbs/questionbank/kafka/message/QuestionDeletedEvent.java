package gov.cdc.nbs.questionbank.kafka.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDeletedEvent {
	
	private String requestId;
	private UUID questionId;
	private long userId;

}
