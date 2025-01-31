/*
 * Created on Jan 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.util.*;
import java.io.*;

/**
 * @author xzheng
 *
 * This class holds the results for an XML definition import action.
 */
public class ImporterResult implements ImportData, Serializable {
	
	private Long importUid;
	private List<?> definedFieldImportData;
	private List<?> subformImportData;
	

	/**
	 * @return
	 */
	public List<?> getDefinedFieldImportData() {
		return definedFieldImportData;
	}

	/**
	 * @return
	 */
	public Long getImportUid() {
		return importUid;
	}

	/**
	 * @return
	 */
	public List<?> getSubformImportData() {
		return subformImportData;
	}

	/**
	 * @param list
	 */
	public void setDefinedFieldImportData(List<?> list) {
		definedFieldImportData = list;
	}

	/**
	 * @param long1
	 */
	public void setImportUid(Long long1) {
		importUid = long1;
	}

	/**
	 * @param list
	 */
	public void setSubformImportData(List<?> list) {
		subformImportData = list;
	}

}
