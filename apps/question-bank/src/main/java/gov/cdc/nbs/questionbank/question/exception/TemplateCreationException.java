package gov.cdc.nbs.questionbank.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class TemplateCreationException extends BadRequestException {
    public TemplateCreationException(String message) {
        super(message);
    }
}
