/*
 * Created on Jan 2, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.dt.*;

/**
 * @author xzheng
 *
 * This is the interface definition for a custom defined field and
 * subform importer.  Client uses this interface to import custom 
 * defined field and subform definitions.
 * 
 */
public interface Importer {

	/** return the source for the importer
	 * @return the source for the importer
	 */
	public String getSource();
	
	/** This method starts the import process.  
	 * 
	 * @return The import result.
	 * @throws NEDSSAppException If import operation fails.
	 */
	public ImporterResult performImport() throws NEDSSAppException;
	
}
