package gov.cdc.nbs.questionbank.condition.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class ConditionBadRequest extends BadRequestException {
    public ConditionBadRequest(String id) {
        super(String.format("There was a problem in the request for condition with id: %s", id));
    }
}
