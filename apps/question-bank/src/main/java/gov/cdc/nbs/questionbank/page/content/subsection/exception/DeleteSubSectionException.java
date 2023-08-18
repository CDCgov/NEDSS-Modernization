package gov.cdc.nbs.questionbank.page.content.subsection.exception;

public class DeleteSubSectionException extends RuntimeException {

    private final int errorCode;

    public DeleteSubSectionException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }

}
