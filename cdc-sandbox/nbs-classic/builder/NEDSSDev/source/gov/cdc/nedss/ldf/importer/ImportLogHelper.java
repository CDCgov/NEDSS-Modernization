/*
 * Created on Jan 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.importer.dao.CdfSubformImportLogDAOImpl;
import gov.cdc.nedss.ldf.importer.dao.CdfSubformImportDataLogDAOImpl;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.ldf.importer.ImportConstants;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ImportLogHelper {
	static final LogUtils logger =
		new LogUtils((ImportLogHelper.class).getName());

	/** This method writes data import log info to database.
	 */
	public void processImportDataLog(CdfSubformImportDataLogDT importDataLogDT)
		throws NEDSSAppException {
		CdfSubformImportDataLogDAOImpl dao =
			(CdfSubformImportDataLogDAOImpl) NEDSSDAOFactory.getDAO(
				"gov.cdc.nedss.ldf.importer.dao.CdfSubformImportDataLogDAOImpl");
		try {

			dao.insert(importDataLogDT);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(
				ImportConstants.CREATE_RECORD_FAILURE_TEXT);
		}

	}

	/** This method writes XML definition import log info  database.
	 * @param dt The dt represent the log info.
	 */
	public Long processImportLog(CdfSubformImportLogDT dt)
		throws NEDSSAppException {

		CdfSubformImportLogDAOImpl dao =
			(CdfSubformImportLogDAOImpl) NEDSSDAOFactory.getDAO(
				"gov.cdc.nedss.ldf.importer.dao.CdfSubformImportLogDAOImpl");
		try {
			prepareLogDT(dt);
			return new Long(dao.insert(dt));
		}
                catch(NEDSSDBUniqueKeyViolation dupKey)
                {
                      logger.error(dupKey.getMessage(), dupKey);
                      throw dupKey;
              }

                catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(
				ImportConstants.CREATE_RECORD_FAILURE_TEXT);
		}
	}

	private void prepareLogDT(CdfSubformImportLogDT dt)
		throws NEDSSAppException {
		if (dt != null) {
			Long version = dt.getImportVersionNbr();
			if (version == null) {
				// this can be caused by import error conditions
				// we'll set the version number to the next available
				// negtive integer
				UidGeneratorHelper uidGen = null;
				try {
					uidGen = new UidGeneratorHelper();
					// new uid
					version =
						new Long(
							uidGen
								.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE)
								.longValue()
								* (-1));
					dt.setImportVersionNbr(version);
					dt.setLogMessageTxt("Failed to import " + dt.getSourceNm() + " - " + dt.getLogMessageTxt());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new NEDSSAppException(
						ImportConstants.NEGATIVE_IMPORT_VERSION_NBR_TEXT);
				}

			}
		}
	}

}
