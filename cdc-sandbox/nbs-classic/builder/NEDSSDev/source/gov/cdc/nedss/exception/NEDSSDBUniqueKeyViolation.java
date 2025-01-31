package gov.cdc.nedss.exception;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class NEDSSDBUniqueKeyViolation extends NEDSSDAOAppException {

	public NEDSSDBUniqueKeyViolation() {
		super();
	}

	public NEDSSDBUniqueKeyViolation(String string) {
		super(string);
	}

	public NEDSSDBUniqueKeyViolation(String string, Exception e) {
		super(string, e);
	}

}