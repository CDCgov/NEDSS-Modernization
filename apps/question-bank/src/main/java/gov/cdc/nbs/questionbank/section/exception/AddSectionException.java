package gov.cdc.nbs.questionbank.section.exception;

public class AddSectionException extends RuntimeException {

    private final int errorCode;

    public AddSectionException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
