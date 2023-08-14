package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class ConceptNotFoundException extends NotFoundException {
    public ConceptNotFoundException(String codeSetNm, String code) {
        super(String.format("Failed to find concept for Valueset: %s with code: %s", codeSetNm, code));
    }

    public ConceptNotFoundException(String message) {
        super(message);
    }
}
