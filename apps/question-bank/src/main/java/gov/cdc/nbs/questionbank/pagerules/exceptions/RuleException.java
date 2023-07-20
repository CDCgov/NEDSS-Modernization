package gov.cdc.nbs.questionbank.pagerules.exceptions;

public class RuleException extends RuntimeException {

    private final int errorCode;

    public RuleException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
