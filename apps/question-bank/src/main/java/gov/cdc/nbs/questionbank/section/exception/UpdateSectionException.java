package gov.cdc.nbs.questionbank.section.exception;

public class UpdateSectionException extends RuntimeException {

    private final int errorCode;

    public UpdateSectionException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
