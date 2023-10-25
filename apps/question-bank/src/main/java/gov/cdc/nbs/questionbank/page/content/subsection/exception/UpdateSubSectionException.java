package gov.cdc.nbs.questionbank.page.content.subsection.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UpdateSubsectionException extends BadRequestException {

    public UpdateSubsectionException(String message) {
        super(message);
    }

}
