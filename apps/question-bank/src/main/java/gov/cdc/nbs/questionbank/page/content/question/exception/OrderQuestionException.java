package gov.cdc.nbs.questionbank.page.content.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class OrderQuestionException extends BadRequestException {

    public OrderQuestionException(String message) {
        super(message);
    }

}
