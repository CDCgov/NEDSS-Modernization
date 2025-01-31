package gov.cdc.nedss.systemservice.exception;

public class EdxTransactionControlException extends Exception 
{
	public EdxTransactionControlException(String s) {
		super(s);
	}
	public EdxTransactionControlException(String s, Exception e) {
		super(s, e);
	}
}
