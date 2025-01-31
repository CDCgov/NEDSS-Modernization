package gov.cdc.nedss.exception;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;

public class NedssAppLogException extends InvocationTargetException {

	private static final long serialVersionUID = 1L;
	EDXActivityLogDT Logdt;

	public NedssAppLogException() {
		super();
	}

	public NedssAppLogException(Exception e) {
		super(e);
	}

	public NedssAppLogException(Exception e, String s) {
		super(e, s);
	}

	public NedssAppLogException(EDXActivityLogDT dt) {
		// super(errorCd);
		this.Logdt = dt;
	}

	public EDXActivityLogDT getEDXActivityLogDT() {
		return Logdt;
	}

}
