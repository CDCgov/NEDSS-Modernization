/*
 * Created on Jan 6, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.util.List;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.dt.CdfSubformImportLogDT;
import gov.cdc.nedss.ldf.importer.dao.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.dao.*;
import gov.cdc.nedss.ldf.subform.dao.*;

/**
 * @author xzheng
 *
 * This is the singlton utility class that performs misc import actions.
 */
public abstract class ImporterHelper {

	public ImporterHelper() {

	}

	/** This method rolls back the last imported version.
	 *
	 * @throws NEDSSAppException
	 */
	public void undoLastImport() throws NEDSSAppException {
		CdfSubformImportLogDAOImpl cdfSubformImportLogDAOImpl =
			new CdfSubformImportLogDAOImpl();
		CdfSubformImportLogDT cdfSubformImportLogDT =
			cdfSubformImportLogDAOImpl.selectbyImportTime();
		Long importLogUid = cdfSubformImportLogDT.getImportLogUid();
		Long importVersionNbr = cdfSubformImportLogDT.getImportVersionNbr();
		undoImport(importLogUid, importVersionNbr);
	}

	/** This method rolls back an import based on a version.
	 *
	 * @throws NEDSSAppException
	 */
	public void undoImport(Long importVersionNbr) throws NEDSSAppException {
		CdfSubformImportLogDAOImpl cdfSubformImportLogDAOImpl =
			new CdfSubformImportLogDAOImpl();
		CdfSubformImportLogDT cdfSubformImportLogDT =
			cdfSubformImportLogDAOImpl.selectByVersion(importVersionNbr);
		Long importLogUid = cdfSubformImportLogDT.getImportLogUid();
		undoImport(importLogUid, importVersionNbr);
	}

	/** This method rolls back a particular import.
		 *  Because of the dependency constraints, the following deleting squence should
		 *  be followed.
		 *  1) Delete data in ImportDataLog Table
		 *  2) Delete data in ImportLog Table
		 *  3) Delete data in StateDefinedMetaData Table
		 *  4) Delete data in CustomSubFormMetaData Table
	 *
	 * @throws NEDSSAppException
	 */
	private void undoImport(Long importLogUid, Long importVersionNbr)
		throws NEDSSAppException {

		// 1) Delete data in ImportDataLog Table
		CdfSubformImportDataLogDAOImpl cdfSubformImportDataLogDAOImpl =
			new CdfSubformImportDataLogDAOImpl();
		cdfSubformImportDataLogDAOImpl.delete(importLogUid);

		// 2) Delete data in ImportLog Table
		CdfSubformImportLogDAOImpl cdfSubformImportLogDAOImpl =
			new CdfSubformImportLogDAOImpl();
		cdfSubformImportLogDAOImpl.delete(importLogUid);

		// 3) Delete data in StateDefinedMetaData Table
		StateDefinedFieldMetaDataDAOImpl stateDefinedFieldMetaDataDAO =
			new StateDefinedFieldMetaDataDAOImpl();
		stateDefinedFieldMetaDataDAO.deleteByImportVersionNbr(importVersionNbr);

		// 4) Delete data in CustomSubformMetaData Table
		CustomSubformMetadataDAO customSubformMetadataDAO =
			new CustomSubformMetadataDAO();
		customSubformMetadataDAO.deleteByImportVersionNbr(importVersionNbr);
	}

	/** This method returns a list of file names to be imported.
	 *
	 * @return A list of file names to be imported.
	 */
	public abstract String[] getImportFileNames();
}
