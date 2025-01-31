/*
 * Created on Feb 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.helper;

import java.util.*;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.ldf.importer.*;
/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedFieldSRTValues {

	private DefinedFieldDataTypes dataTypes;
	private DefinedFieldValidationTypes validationTypes;
	private PageSets pageSets;
	private DefinedFieldClassCodes classCodes;
	private DefinedFieldSRTCodeSets srtCodeSets;

	public DefinedFieldSRTValues() throws NEDSSAppException {
		dataTypes = new DefinedFieldDataTypes();
		validationTypes = new DefinedFieldValidationTypes();
		pageSets = new PageSets();
		classCodes = new DefinedFieldClassCodes();
		srtCodeSets = new DefinedFieldSRTCodeSets();
	}

	/**
	 * @return
	 */
	public DefinedFieldDataTypes getDataTypes() {
		return dataTypes;
	}

	/**
	 * @return
	 */
	public PageSets getPageSets() {
		return pageSets;
	}

	/**
	 * @return
	 */
	public DefinedFieldValidationTypes getValidationTypes() {
		return validationTypes;
	}

	public List<Object> validate(ImportData data) {
		List<Object> retVal = new ArrayList<Object> ();
		retVal.addAll(dataTypes.validate(data));
		retVal.addAll(validationTypes.validate(data));
		retVal.addAll(pageSets.validate(data));
		retVal.addAll(classCodes.validate(data));
		retVal.addAll(srtCodeSets.validate(data));
		return retVal;
	}

	/**
	 * @return
	 */
	public DefinedFieldClassCodes getClassCodes() {
		return classCodes;
	}

	/**
	 * @return
	 */
	public DefinedFieldSRTCodeSets getSrtCodeSets() {
		return srtCodeSets;
	}

}
