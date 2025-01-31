/*
 * Created on Jan 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer.ejb.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.helper.*;
import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.*;

/**
 * @author xzheng
 *
 * The business interface for custom defined field and subform import
 * functionalities.
 */
public interface CustomDataImportBusinessInterface {

  /** The method that imports the custom defined field and subform metadata.
   * The web tier should always call this method to perform iimport action.
   *
   * @param source The source location for the import.
   * @param secObj The NBSSecurity object associated with the user.
   * @return The import uid.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public Long performImport(String source, NBSSecurityObj secObj) throws
      RemoteException, NEDSSAppException;

  /** The method rolls back the last import for the custom defined field and subform metadata.
   * The web tier should always call this method to perform import undo action.
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public void undoLastImport(NBSSecurityObj secObj) throws NEDSSAppException,
      RemoteException;

	/** The method rolls back import for the custom defined field and subform metadata 
	 * based on import version number.  The web tier should always call this method to 
	 * perform import undo action.
	 *
	 * @param secObj The NBSSecurity object associated with the user.
	 * @throws RemoteException
	 * @throws NEDSSAppException
	 */
	public void undoImport(Long importVersionNbr, NBSSecurityObj secObj) throws NEDSSAppException,
		RemoteException;

  /** The method that imports the XML definition only.  It should have a transaction
   * attribute of REQUIRES_NEW
   *
   * @param source The source location for the import.
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public ImporterResult importXMLDefinition(
      String source,
      NBSSecurityObj secObj) throws RemoteException, NEDSSAppException;

  /** The method that imports the defined field metadata.  It should have a transaction
   * attribute of REQUIRES_NEW
   *
   * @param importUid The import uid.
   * @param pageSets The defined field SRT definitions.  It is passed in to save mutiple trips to the database.
   * @param data The data to be imported.
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public void importDefinedFieldMetadata(
      Long importUid,
      DefinedFieldSRTValues srtValues,
      DefinedFieldImportData data,
      NBSSecurityObj secObj) throws NEDSSAppException, RemoteException;

      /** The method that imports the subform metadata.  It should have a transaction
   * attribute of REQUIRES_NEW
   *
   * @param importUid The import uid.
   * @param pageSets The defined field SRT definitions.  It is passed in to save mutiple trips to the database.
   * @param data The data to be imported.
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public void importSubformMetadata(
      Long importUid,
      DefinedFieldSRTValues srtValues,
      SubformImportData data,
      NBSSecurityObj secObj) throws NEDSSAppException, RemoteException;

  /** The returns the file names to be imported.
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public String[] getImportFileNames(NBSSecurityObj secObj) throws
      RemoteException, NEDSSAppException;

  /** The returns a hashmap containing a collection of LogSummary
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public HashMap<Object,Object> getImportDataLogByVersion(
      Long importVersionNbr,
      NBSSecurityObj secObj) throws RemoteException, NEDSSAppException;

  /** The returns a collection of importVersionNbr
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public Collection<Object>  getAllVersion(NBSSecurityObj secObj) throws RemoteException,
      NEDSSAppException;

  /** The returns a import version Number by latest time
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public Long getVersionNbrByImportTime(NBSSecurityObj secObj) throws
      RemoteException,
      NEDSSAppException;

}
