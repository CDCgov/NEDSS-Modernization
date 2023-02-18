package gov.cdc.nbs.patientlistener.exception;

public class UpdateException extends RuntimeException {
	
	public  UpdateException() {
		super("Could not update patientEvent data.");
	}

}
