package gov.cdc.nbs.questionbank.addsection.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class AddSectionException extends BadRequestException {

    public AddSectionException(String message) {
        super(message);
    }

}
