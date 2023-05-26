package gov.cdc.nbs.questionbank.kafka.message.question;

import java.util.UUID;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankRequest;

public sealed interface QuestionRequest extends QuestionBankRequest {

    record CreateTextQuestionRequest(
            String requestId,
            long questionId,
            long userId,
            TextQuestionData data) implements QuestionRequest {
    }

    record TextQuestionData(
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder,
            String defaultValue) {
    }

    record CreateDateQuestionRequest(
            String requestId,
            long questionId,
            long userId,
            DateQuestionData data) implements QuestionRequest {
    }

    record DateQuestionData(
            String label,
            String tooltip,
            boolean allowFutureDates) {
    }

    record CreateDropdownQuestionRequest(
            String requestId,
            long questionId,
            long userId,
            DropdownQuestionData data) implements QuestionRequest {
    }

    record DropdownQuestionData(
            String label,
            String tooltip,
            UUID valueSet,
            UUID defaultValue,
            boolean isMultiSelect) {
    }

    record CreateNumericQuestionRequest(
            String requestId,
            long questionId,
            long userId,
            NumericQuestionData data) implements QuestionRequest {
    }

    record NumericQuestionData(
            String label,
            String tooltip,
            Integer minValue,
            Integer maxValue,
            Integer defaultValue,
            UUID unitValueSet) {
    }

    record UpdateTextQuestionRequest(
            String requestId,
            long questionId,
            long userId,
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder) implements QuestionRequest {
    }
    
    record DeleteQuestionRequest(
    		String requestId,	
    		long questionId,	
    		long userId	
    		) implements QuestionRequest {

    }
    

}
