/*
 * Created on Jan 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.systemservice.util;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author xzheng
 *
 * The singleton that validates validatable objects.
 */
public class Validator {

	public static final String VALIDATION_METHOD_PREFIX = "validate";
	public static final String VALIDATION_METHOD_RETURN_CLSNM =
		"gov.cdc.nedss.systemservice.util.ValidationResult";
	public static final String VALIDATION_ERROR_MESSAGESEPARATOR = ";";

	/** 
	 * unique instance 
	 * */
	private static Validator sInstance = null;

	/** 
	 * Private constuctor
	 */
	private Validator() {
	}

	/** 
	 * Get the unique instance of this class.
	 */
	public static synchronized Validator getInstance() {

		if (sInstance == null) {
			sInstance = new Validator();
		}

		return sInstance;

	}

	/** Validates the object.  
	 * 
	 * @param object The object to be validated.
	 * @return A list of ValidationResult.
	 */
	public List<Object> validate(Validatable object) {
		List<Object> retVal = new ArrayList<Object> ();

		if (object != null) {
			Method[] methods = object.getClass().getMethods();
			if (methods != null) {
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method != null
						&& method.getName().startsWith(VALIDATION_METHOD_PREFIX)
						&& method.getReturnType() != null
						&& method.getReturnType().getName().equals(
							VALIDATION_METHOD_RETURN_CLSNM)
						&& method.getParameterTypes().length == 0) {
						try {
							ValidationResult result =
								(ValidationResult) method.invoke(object, (Object[])null);
							retVal.add(result);
						} catch (Exception e) {
							// do not do any thing
						}
					}

				}
			}

		}

		return retVal;
	}

	/** Generate message for a list of validation results.  
	 * 
	 * @param validationResults The list of validation results.
	 * @return The message.
	 */
	public String generateMessage(List<Object>validationResults) {
		StringBuffer buffer = new StringBuffer();
		if (validationResults != null) {
			for (Iterator<Object> iter = validationResults.iterator();
				iter.hasNext();
				) {
				ValidationResult element = (ValidationResult) iter.next();
				if (!element.isValid()) {
					if (buffer.length() == 0) {
						buffer.append(element.getMessage());
					} else {
						buffer.append(VALIDATION_ERROR_MESSAGESEPARATOR);
						buffer.append(element.getMessage());
					}
				}
			}
		}
		return buffer.toString();
	}

}
