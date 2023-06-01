package gov.cdc.nbs.questionbank.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankEventResponse {
	private String message;
	private Long questionId;

}
