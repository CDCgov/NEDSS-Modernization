package gov.cdc.nbs.questionbank.page.content.tab.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class OrderTabException extends BadRequestException {

    public OrderTabException(String message) {
        super(message);
    }

}
