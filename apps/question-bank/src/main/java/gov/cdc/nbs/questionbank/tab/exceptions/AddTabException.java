package gov.cdc.nbs.questionbank.tab.exceptions;

public class AddTabException extends RuntimeException {

    private final int errorCode;

    public AddTabException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
