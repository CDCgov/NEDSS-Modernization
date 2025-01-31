package gov.cdc.nedss.ldf.importer.ejb.bean;

/**
 * Title: Session EJB Class using CustomDataImport
 * Description: This class is the EJB for CustomDataImport Session EJB
 * Copyright:    Copyright (c) 2003
 * Company:csc
 * @author:
 */
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.ldf.helper.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.rmi.*;
import java.util.*;

import javax.ejb.*;

import gov.cdc.nedss.ldf.importer.dao.*;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.*;
import gov.cdc.nedss.ldf.subform.dao.*;
import gov.cdc.nedss.ldf.subform.dt.*;
import gov.cdc.nedss.ldf.dt.*;

/**
 *
 */
public class CustomDataImportEJB
    implements SessionBean, CustomDataImportBusinessInterface {

  //For logging
  static final LogUtils logger =
      new LogUtils(CustomDataImportEJB.class.getName());
  private SessionContext cntx;

  public CustomDataImportEJB() {
  }

  public void ejbCreate() {
  }

  public void ejbRemove() {
  }

  public void ejbActivate() {
  }

  public void ejbPassivate() {
  }

  public void setSessionContext(SessionContext sc) {
    this.cntx = sc;
  }

  /* (non-Javadoc)
   * @see gov.cdc.nedss.ldf.ejb.customdataimportejb.bean.CustomDataImportBusinessInterface#performImport(java.lang.String, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
   */
  public Long performImport(String source, NBSSecurityObj secObj) throws
      RemoteException, NEDSSAppException {

    try {
		boolean check1 =
		    secObj.getPermission(
		    NBSBOLookup.SYSTEM,
		    NBSOperationLookup.LDFADMINISTRATION);

		if (check1 == false) {
		  throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
		}

		DefinedFieldSRTValues srtValues = new DefinedFieldSRTValues();

		// if this throws exception, none of the indivisual import
		// will proceed
		ImporterResult result = importXMLDefinition(source, secObj);

		if (result != null) {
		  try {
		    if (result.getDefinedFieldImportData() != null) {
		      for (Iterator<?> iter =
		           result.getDefinedFieldImportData().iterator();
		           iter.hasNext();
		           ) {

		        DefinedFieldImportData element =
		            (DefinedFieldImportData) iter.next();
		        importDefinedFieldMetadata(
		            result.getImportUid(),
		            srtValues,
		            element,
		            secObj);
		      }
		    }
		    if (result.getSubformImportData() != null) {
		      for (Iterator<?> iter =
		           result.getSubformImportData().iterator();
		           iter.hasNext();
		           ) {

		        SubformImportData element =
		            (SubformImportData) iter.next();
		        importSubformMetadata(
		            result.getImportUid(),
		            srtValues,
		            element,
		            secObj);
		      }
		    }

		  }
		  catch (NEDSSAppException e) {
		    // do nothing here.  indivisual import failer
		    // willl be logged in log table, but it does
		    // not causes this method to throw exception.
		  }
		  return result.getImportUid();
		}

		return null;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
		
	}
  }

  /* (non-Javadoc)
   * @see gov.cdc.nedss.ldf.ejb.customdataimportejb.bean.CustomDataImportBusinessInterface#importXMLDefinition(java.lang.String, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
   */
  public ImporterResult importXMLDefinition(
      String source,
      NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {
	  try{
    boolean check1 =
        secObj.getPermission(
        NBSBOLookup.SYSTEM,
        NBSOperationLookup.LDFADMINISTRATION);

    if (check1 == false) {
      throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
    }

    // for now use the default importer ********************
    Importer importer =
        ImporterFactory.getInstance().getDefaultImporter(source);
    
      return importer.performImport();
    }
    catch (NEDSSAppException e) {
      // Since this method requires new transaction, rollback transaction here
      // if there is an exception from the helper
      cntx.setRollbackOnly();
      logger.fatal(e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }

  }

  /* (non-Javadoc)
   * @see gov.cdc.nedss.ldf.ejb.customdataimportejb.bean.CustomDataImportBusinessInterface#importDefinedFieldMetadata(gov.cdc.nedss.ldf.helper.importer.DefinedFieldImportData, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
   */
  public void importDefinedFieldMetadata(
      Long importUid,
      DefinedFieldSRTValues srtValues,
      DefinedFieldImportData data,
      NBSSecurityObj secObj) throws NEDSSAppException, RemoteException {
	  try{
    boolean check1 =
        secObj.getPermission(
        NBSBOLookup.SYSTEM,
        NBSOperationLookup.LDFADMINISTRATION);

    if (check1 == false) {
      throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
    }

    DefinedFieldImporterHelper helper =
        new DefinedFieldImporterHelper(importUid, srtValues);

    
      helper.importDefinedFieldMetadata(data, secObj);
    }
    catch (NEDSSAppException e) {
      // Since thi method requires new transaction, rollback transaction here
      // if there is an exception from the helper
      cntx.setRollbackOnly();
      logger.fatal(e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }

  }

  /* (non-Javadoc)
   * @see gov.cdc.nedss.ldf.ejb.customdataimportejb.bean.CustomDataImportBusinessInterface#importSubformMetadata(gov.cdc.nedss.ldf.helper.importer.SubformImportData, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
   */
  public void importSubformMetadata(
      Long importUid,
      DefinedFieldSRTValues srtValues,
      SubformImportData data,
      NBSSecurityObj secObj) throws NEDSSAppException, RemoteException {
	  
	  try{
    boolean check1 =
        secObj.getPermission(
        NBSBOLookup.SYSTEM,
        NBSOperationLookup.LDFADMINISTRATION);

    if (check1 == false) {
      throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
    }

    SubformImporterHelper helper = new SubformImporterHelper(importUid,
        srtValues);

    
      helper.importSubformMetadata(data, secObj);
    }
    catch (NEDSSAppException e) {
      // Since thi method requires new transaction, rollback transaction here
      // if there is an exception from the helper
      cntx.setRollbackOnly();
      logger.fatal(e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }
  }

  /* (non-Javadoc)
   * @see gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportBusinessInterface#undoLastImport(gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
   */
  public void undoLastImport(NBSSecurityObj secObj) throws NEDSSAppException,
      RemoteException {

    try {
		boolean check1 =
		    secObj.getPermission(
		    NBSBOLookup.SYSTEM,
		    NBSOperationLookup.LDFADMINISTRATION);

		if (check1 == false) {
		  throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
		}
		//		for now use the default importer helper ********************
		ImporterHelper helper =
		    ImporterFactory.getInstance().getDefaultImporterHelper();
		helper.undoLastImport();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}

  }

  /* (non-Javadoc)
   * @see gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportBusinessInterface#getImportFileNames(gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
   */
  public String[] getImportFileNames(NBSSecurityObj secObj) throws
      RemoteException, NEDSSAppException {

    try {
		boolean check1 =
		    secObj.getPermission(
		    NBSBOLookup.SYSTEM,
		    NBSOperationLookup.LDFADMINISTRATION);

		if (check1 == false) {
		  throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
		}
		//		for now use the default importer helper ********************
		ImporterHelper helper =
		    ImporterFactory.getInstance().getDefaultImporterHelper();
		return helper.getImportFileNames();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  /** The returns a collection of LogSummary
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public HashMap<Object,Object> getImportDataLogByVersion(
      Long importVersionNbr,
      NBSSecurityObj secObj) throws RemoteException, NEDSSAppException {

    try {
		boolean check1 =
		            secObj.getPermission(
		            NBSBOLookup.SYSTEM,
		            NBSOperationLookup.LDFADMINISTRATION);

		    if (check1 == false) {
		      throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
		    }


		CdfSubformImportLogDAOImpl cdfSubformImportLogDAO =
		    new CdfSubformImportLogDAOImpl();
		CdfSubformImportLogDT cdfSubformImportLogDT =
		    cdfSubformImportLogDAO.selectByVersion(importVersionNbr);
		LogSummary logSummary = new LogSummary();
		if (cdfSubformImportLogDT != null) {
		  Long importLogUid = cdfSubformImportLogDT.getImportLogUid();
		  logSummary.setAdminComments(
		      cdfSubformImportLogDT.getAdminComment());
		  logSummary.setError(cdfSubformImportLogDT.getLogMessageTxt());
		  logSummary.setImportDate(cdfSubformImportLogDT.getImportTime());
		  logSummary.setStatus(cdfSubformImportLogDT.getProcessCd());
		  logSummary.setDataLogSummaryColl(importVersionNbr, importLogUid);
		}
		HashMap<Object,Object> logSummaryMap = new HashMap<Object,Object>();
		logSummaryMap.put(ImportConstants.LOG_SUMMARY, logSummary);

		return logSummaryMap;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  /** The returns a collection of import version Number
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public Collection<Object>  getAllVersion(NBSSecurityObj secObj) throws RemoteException,
      NEDSSAppException {

    try {
		boolean check1 =
		            secObj.getPermission(
		            NBSBOLookup.SYSTEM,
		            NBSOperationLookup.LDFADMINISTRATION);

		    if (check1 == false) {
		      throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
		    }


		CdfSubformImportLogDAOImpl importLogDAOImpl =
		    new CdfSubformImportLogDAOImpl();
		Collection<Object>  importVersionNbrColl = importLogDAOImpl.selectAllVersion();
		return importVersionNbrColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}

  }

  /** The returns a import version Number by latest time
   *
   * @param secObj The NBSSecurity object associated with the user.
   * @throws RemoteException
   * @throws NEDSSAppException
   */
  public Long getVersionNbrByImportTime(NBSSecurityObj secObj) throws RemoteException,
      NEDSSAppException {

    try {
		boolean check1 =
		                secObj.getPermission(
		                NBSBOLookup.SYSTEM,
		                NBSOperationLookup.LDFADMINISTRATION);

		        if (check1 == false) {
		          throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
		        }

		CdfSubformImportLogDAOImpl cdfSubformImportLogDAOImpl = new CdfSubformImportLogDAOImpl();
		CdfSubformImportLogDT cdfSubformImportLogDT = cdfSubformImportLogDAOImpl.selectbyImportTime();
		Long importVersionNbr = null;
		if (cdfSubformImportLogDT != null)
		 importVersionNbr = cdfSubformImportLogDT.getImportVersionNbr();
		return importVersionNbr;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}

  }

/* (non-Javadoc)
 * @see gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportBusinessInterface#undoImport(java.lang.Long, gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj)
 */
public void undoImport(Long importVersionNbr, NBSSecurityObj secObj) throws NEDSSAppException, RemoteException {

	try {
		boolean check1 =
			secObj.getPermission(
			NBSBOLookup.SYSTEM,
			NBSOperationLookup.LDFADMINISTRATION);

		if (check1 == false) {
		  throw new NEDSSAppException(ImportConstants.NO_PERMISSION_TEXT);
		}
		//		for now use the default importer helper ********************
		ImporterHelper helper =
			ImporterFactory.getInstance().getDefaultImporterHelper();
		helper.undoImport(importVersionNbr);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}

}

} //CustomDataImportEJB
