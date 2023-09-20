package gov.cdc.nbs.questionbank.page.content.section.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class OrderSectionException extends BadRequestException {

    public OrderSectionException(String message) {
        super(message);
    }

}
