package gov.cdc.nbs.questionbank.kafka.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankEventResponse {
	private String message;
	private UUID questionId;

}
