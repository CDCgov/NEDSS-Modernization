package gov.cdc.nbs.questionbank.page.content.section.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class AddSectionException extends BadRequestException {

    public AddSectionException(String message) {
        super(message);
    }

}
