package gov.cdc.nbs.questionbank.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class NullObjectException extends BadRequestException {

    public NullObjectException(String message) {
        super(message);
    }
}
