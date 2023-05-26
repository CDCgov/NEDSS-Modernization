package gov.cdc.nbs.questionbank.kafka.message.question;

import gov.cdc.nbs.questionbank.kafka.message.QuestionBankRequest;

public sealed interface QuestionRequest extends QuestionBankRequest {

    record CreateTextQuestionRequest(
            String requestId,
            long questionId,
            String label,
            String tooltip,
            int maxLength,
            String placeholder) implements QuestionRequest {

        @Override
        public long userId() {
            return 0;
        }
    }

    record UpdateTextQuestionRequest(
            String requestId,
            long questionId,
            long userId
    ) implements QuestionRequest {
    }
    
    record DeleteQuestionRequest(
    		String requestId,	
    		long questionId,	
    		long userId	
    		) implements QuestionRequest {

    }
    

}
