package gov.cdc.nbs.questionbank.tab.exceptions;

public class DeleteTabException extends RuntimeException {

    private final int errorCode;

    public DeleteTabException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
