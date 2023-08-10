package gov.cdc.nbs.questionbank.section.exception;

public class DeleteSectionException extends RuntimeException {

    private final int errorCode;

    public DeleteSectionException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
