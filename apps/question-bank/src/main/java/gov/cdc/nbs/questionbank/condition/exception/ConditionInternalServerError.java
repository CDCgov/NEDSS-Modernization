package gov.cdc.nbs.questionbank.condition.exception;

import gov.cdc.nbs.questionbank.exception.InternalServerException;


public class ConditionInternalServerError extends InternalServerException{
    public ConditionInternalServerError(String id) {
        super(String.format("There was an internal server error associated with id: %s", id));
    }
}
