package gov.cdc.nbs.questionbank.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankEventResponse {
	private String requestId;
	private Long questionId;

}
