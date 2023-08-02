package gov.cdc.nbs.questionbank.addsubsection.exception;

public class AddSubSectionException extends RuntimeException {

    private final int errorCode;

    public AddSubSectionException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
