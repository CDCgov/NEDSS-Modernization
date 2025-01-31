/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import gov.cdc.nedss.systemservice.util.*;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ImportDataParserErrorHandler implements ErrorHandler {
	
	private StringBuffer errorString = new StringBuffer();

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
	 */
	public void error(SAXParseException arg0) throws SAXException {
		if(errorString.length() == 0){
			errorString.append(arg0.getMessage());
		}
		else {
			errorString.append(Validator.VALIDATION_ERROR_MESSAGESEPARATOR);
			errorString.append(arg0.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	public void fatalError(SAXParseException arg0) throws SAXException {
		
		if(errorString.length() == 0){
			errorString.append(arg0.getMessage());
		}
		else {
			errorString.append(Validator.VALIDATION_ERROR_MESSAGESEPARATOR);
			errorString.append(arg0.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
	 */
	public void warning(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub

	}
	
	public String getMessage(){
		return errorString.toString();
	}

}
