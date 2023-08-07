package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class PageNotFoundException extends NotFoundException {
    public PageNotFoundException(String message) {
        super(message);
    }

}
