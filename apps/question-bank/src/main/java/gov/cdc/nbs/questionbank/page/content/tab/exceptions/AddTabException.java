package gov.cdc.nbs.questionbank.page.content.tab.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class AddTabException extends BadRequestException {

    public AddTabException(String message) {
        super(message);
    }

}
