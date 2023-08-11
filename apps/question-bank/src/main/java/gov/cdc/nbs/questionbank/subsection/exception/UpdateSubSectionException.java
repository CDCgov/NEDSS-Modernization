package gov.cdc.nbs.questionbank.subsection.exception;

public class UpdateSubSectionException extends RuntimeException {

    private final int errorCode;

    public UpdateSubSectionException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
