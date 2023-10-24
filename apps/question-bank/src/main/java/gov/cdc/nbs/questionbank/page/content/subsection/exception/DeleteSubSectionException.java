package gov.cdc.nbs.questionbank.page.content.subsection.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeleteSubsectionException extends BadRequestException {

    public DeleteSubsectionException(String message) {
        super(message);
    }

}
