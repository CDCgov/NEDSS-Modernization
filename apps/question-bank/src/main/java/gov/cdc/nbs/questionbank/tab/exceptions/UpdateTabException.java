package gov.cdc.nbs.questionbank.tab.exceptions;

public class UpdateTabException extends RuntimeException {

    private final int errorCode;

    public UpdateTabException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
