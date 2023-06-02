package gov.cdc.nbs.questionbank.exception;

public class DeleteException extends RuntimeException {
	
	public DeleteException() {
		super("The delete operation failed for question.");
	}

}
