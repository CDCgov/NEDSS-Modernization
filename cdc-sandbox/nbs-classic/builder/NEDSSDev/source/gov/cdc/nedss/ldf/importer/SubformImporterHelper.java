/*
 * Created on Jan 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.io.*;
import java.util.*;
import java.sql.Timestamp;
import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.ldf.subform.dt.CustomSubformMetadataDT;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.ldf.helper.*;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.ldf.importer.dao.*;
/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubformImporterHelper extends AbstractDataImporterHelper {

	//For logging
	static final LogUtils logger =
		new LogUtils(SubformImporterHelper.class.getName());

	private Long importUid;
	private DefinedFieldSRTValues srtValues;
	private CachedDropDownValues cdv = new CachedDropDownValues();

	public SubformImporterHelper(
		Long importUid,
		DefinedFieldSRTValues srtValues) {
		this.importUid = importUid;
		this.srtValues = srtValues;
	}
	/** This method import a SubformImportData object into ODS database.
	 *
	 * @param data
	 * @throws NEDSSAppException
	 */
	public void importSubformMetadata(
		SubformImportData data,
		NBSSecurityObj secObj)
		throws NEDSSAppException {

		String subformFileName = data.getHTMLSource();
		CdfSubformImportDataLogDT dataLogDT = null;

		// set subform default values
		setSubformDefaultvalues(data);

		Validator validator = Validator.getInstance();
		String message = validator.generateMessage(validator.validate(data));
		if (message != null && message.trim().length() != 0) {
			// data validation failed, log it and move on
			dataLogDT =
				populateImportDataLogDT(
					data,
					ImportConstants.INCONSISTENT_DATA,
					message);
			persistImportDataLogDT(dataLogDT);
			return;

		}

		// SRT validation 
		message = validator.generateMessage(srtValues.validate(data));
		if (message != null && message.trim().length() != 0) {
			// data validation failed, log it and move on
			dataLogDT =
				populateImportDataLogDT(
					data,
					ImportConstants.INVALID_SRT_VALUE_CODE,
					message);
			persistImportDataLogDT(dataLogDT);
			return;

		}

		//read the file contents (XHTML) 
		String xhtmlData = null;
		try {
			xhtmlData = readSubformFile(subformFileName);
		} catch (NEDSSAppException e) {
			// can not get the subform data, log and return
			dataLogDT =
				this.populateImportDataLogDT(
					data,
					ImportConstants.INVALID_FILE_CODE,
					ImportConstants.INVALID_FILE_TEXT + subformFileName);
			persistImportDataLogDT(dataLogDT);
			return;
		}

		//set the HTML Source as the clob read in above step
		data.setHTMLSource(xhtmlData);

		// parse the subform
		Collection<Object>  subformElements = null;
		try {
			ByteArrayInputStream bis =
				new ByteArrayInputStream(xhtmlData.getBytes());
			SubformDataParser xhtmlParser = new SubformDataParser();

			//extract xhtml subform fields to persist in SDFMetaData table
			subformElements = xhtmlParser.extractFields(bis);
		} catch (NEDSSAppException e) {
			// can not parse the subform
			dataLogDT =
				this.populateImportDataLogDT(
					data,
					ImportConstants.INVALID_XHTML_FORMAT_CODE,
					e.getMessage());
			persistImportDataLogDT(dataLogDT);
			return;
		}

		// set embeded fields default values
		setDefaultFieldValues(subformElements);

		try {
			validateSubformElements(subformElements);
		} catch (NEDSSAppException e) {
			// can not parse the subform
			dataLogDT =
				this.populateImportDataLogDT(
					data,
					ImportConstants.INCONSISTENT_XHTML_FORMAT_CODE,
					e.getMessage());
			persistImportDataLogDT(dataLogDT);
			return;
		}

		//call the subformMetaData EJB to persist the subform meta data
		Long subformUid = persistSubformMetaData(data, secObj);

		//pre process the subform elements in accordance with SDFMetaData structure
		Collection<Object>  ldfMetaDataColl =
			processSubformElements(subformUid, subformElements, data);

		//call the appropriate EJB to persist the metaData collection
		persistLDFMetaData(ldfMetaDataColl, secObj);

		dataLogDT =
			this.populateImportDataLogDT(
				data,
				ImportConstants.SUCCESS_CODE,
				ImportConstants.SUCCESS_TEXT);
		persistImportDataLogDT(dataLogDT);

	}

	/**
	 * @param subformElements
	 */
	private void setDefaultFieldValues(Collection<Object> subformElements) {
		if (subformElements != null) {
			for (Iterator<Object> iter = subformElements.iterator(); iter.hasNext();) {
				SubformElement element = (SubformElement) iter.next();
				String classCode = element.getSource();
				if (classCode == null || classCode.trim().length() == 0) {
					element.setSource(ImportConstants.CLASS_CODE_CDC);
				}
				String dataType = element.getDataType();
				if (dataType == null || dataType.trim().length() == 0) {
					element.setDataType(ImportConstants.DATA_TYPE_ST_CODE);
				}

			}
		}

	}
	/**
	 * @param data
	 */
	private void setSubformDefaultvalues(SubformImportData data) {
		if (data != null) {
			String classCode = data.getClassCode();
			if (classCode == null || classCode.trim().length() == 0) {
				data.setClassCode(ImportConstants.CLASS_CODE_CDC);
			}
		}

	}
	/**
	 * @param subformElements
	 */
	private void validateSubformElements(Collection<Object> subformElements)
		throws NEDSSAppException {

		List<Object> uids = new ArrayList<Object> ();
		List<Object> formIds = new ArrayList<Object> ();
		for (Iterator<Object> iter = subformElements.iterator(); iter.hasNext();) {
			SubformElement element = (SubformElement) iter.next();

			// Generic validation
			Validator validator = Validator.getInstance();
			String message =
				validator.generateMessage(validator.validate(element));
			if (message != null && message.trim().length() != 0) {
				throw new NEDSSAppException(message);
			}
			// SRT validation 
			message = validator.generateMessage(srtValues.validate(element));
			if (message != null && message.trim().length() != 0) {
				// data validation failed, log it and move on
				throw new NEDSSAppException(message);

			}
			// unique oid validation
			if (!uids.contains(element.getObjectId())) {
				uids.add(element.getObjectId());
			} else {
				// data validation failed, log it and move on
				throw new NEDSSAppException(
					ImportConstants.DUPLICATED_DF_OBJECT_ID_TEXT
						+ element.getObjectId());
			}
			// unique form id validation
			if (!formIds.contains(element.getId())) {
				formIds.add(element.getId());
			} else {
				// data validation failed, log it and move on
				throw new NEDSSAppException(
					ImportConstants.DUPLICATED_DF_FORM_ID_TEXT
						+ element.getObjectId());
			}
		}

	}
	/**
	 *
	 * @param data
	 * @param secObj
	 * @throws NEDSSAppException
	 */
	private Long persistSubformMetaData(
		SubformImportData data,
		NBSSecurityObj secObj)
		throws NEDSSAppException {

		NedssUtils nedssUtils = new NedssUtils();
		CustomSubformMetadataDT subformMetaDataDT =
			new CustomSubformMetadataDT();

		subformMetaDataDT.setAddTime(new Timestamp(new Date().getTime())); //1
		subformMetaDataDT.setAdminComment(data.getComment()); //2
		subformMetaDataDT.setBusinessObjectNm(
			srtValues
				.getPageSets()
				.getPageSetDT(data.getPageSetID())
				.getBusinessObjNm());
		//3

		String ccode =
			srtValues
				.getPageSets()
				.getPageSetDT(data.getPageSetID())
				.getConditionCd();
		String ccDesc = cdv.getConditionDesc(ccode);
		subformMetaDataDT.setConditionCd(ccode); //4
		subformMetaDataDT.setConditionDescTxt(ccDesc); //5
		subformMetaDataDT.setDeploymentCd(data.getDeploymentCode()); //6
		subformMetaDataDT.setDisplayOrderNbr(
			Integer.valueOf(data.getDisplayOrder()));
		//7
		subformMetaDataDT.setHtmlData(data.getHTMLSource()); //8
		subformMetaDataDT.setImportVersionNbr(
			new Long(data.getImportVersionNbr()));
		//9
		subformMetaDataDT.setPageSetId(data.getPageSetID()); //10
		subformMetaDataDT.setSubformOid(data.getObjectID()); //11
		subformMetaDataDT.setSubformNm(data.getName()); //12
		subformMetaDataDT.setRecordStatusCd(
			NEDSSConstants.SUBFORM_CREATE_RECORD_STATUS_CD);
		//13
		subformMetaDataDT.setVersionCtrlNbr(new Integer(1)); //14
		subformMetaDataDT.setRecordStatusTime(
			new Timestamp(new Date().getTime()));
		//15
		subformMetaDataDT.setStateCd(data.getStateCode()); //16
		if (data.getActionType().equalsIgnoreCase(ImportConstants.REMOVE))
			subformMetaDataDT.setStatusCd(ImportConstants.INACTIVE);
		else
			subformMetaDataDT.setStatusCd(ImportConstants.ACTIVE); //17
		subformMetaDataDT.setClassCd(data.getClassCode()); //18
		subformMetaDataDT.setItNew(true);
		try {
			Object obj = nedssUtils.lookupBean(JNDINames.SUBFORMMetaDataEJB);
			SubformMetaDataHome home =
				(SubformMetaDataHome) PortableRemoteObject.narrow(
					obj,
					SubformMetaDataHome.class);
			SubformMetaData subformMetaData = home.create();
			Long subformUid =
				subformMetaData.setSubformMetaData(subformMetaDataDT, secObj);
			return subformUid;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}

	}

	/**
	 *
	 * @param ldfMetaDataColl
	 * @param secObj
	 * @throws NEDSSAppException
	 */
	private void persistLDFMetaData(
		Collection<Object>  ldfMetaDataColl,
		NBSSecurityObj secObj)
		throws NEDSSAppException {

		logger.debug("ldfMetaDataColl to Create = " + ldfMetaDataColl.size());
		NedssUtils nedssUtils = new NedssUtils();
		try {
			Object obj = nedssUtils.lookupBean(JNDINames.LDFMetaData_EJB);
			LDFMetaDataHome home =
				(LDFMetaDataHome) PortableRemoteObject.narrow(
					obj,
					LDFMetaDataHome.class);
			LDFMetaData ldfMetaData = home.create();
			ldfMetaData.setLDFMetaData(ldfMetaDataColl, secObj);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}

	}

	/**
	 *
	 * @param sfElementColl
	 * @param subform
	 * @return
	 */
	private Collection<Object>  processSubformElements(
		Long subformUid,
		Collection<Object>  sfElementColl,
		SubformImportData subform) {

		ArrayList<Object> ldfMetaDataColl = new ArrayList<Object> ();
		for (Iterator<Object> anIter = sfElementColl.iterator(); anIter.hasNext();) {
			SubformElement htmlField = (SubformElement) anIter.next();

			StateDefinedFieldMetaDataDT metaDataDT =
				new StateDefinedFieldMetaDataDT();
			metaDataDT.setCustomSubformMetadataUid(subformUid); //1
			metaDataDT.setAdminComment(htmlField.getComment()); //2
			metaDataDT.setBusinessObjNm(
				srtValues
					.getPageSets()
					.getPageSetDT(subform.getPageSetID())
					.getBusinessObjNm());
			//3
			metaDataDT.setCdcNationalId(htmlField.getCdcnationalId()); //4
			metaDataDT.setClassCd(htmlField.getSource()); //5
			metaDataDT.setCodeSetNm(htmlField.getCodeSetName()); //6
			String ccode =
				srtValues
					.getPageSets()
					.getPageSetDT(subform.getPageSetID())
					.getConditionCd();
			String ccDesc = cdv.getConditionDesc(ccode);
			metaDataDT.setConditionCd(ccode); //7
			metaDataDT.setConditionDescTxt(ccDesc); //8
			metaDataDT.setDataType(htmlField.getDataType()); //9
			metaDataDT.setLabelTxt(htmlField.getLabelText()); //10
			metaDataDT.setHtmlTag(htmlField.getId()); //11
			metaDataDT.setLdfPageId(subform.getPageSetID()); //12
			if (subform
				.getActionType()
				.equalsIgnoreCase(ImportConstants.REMOVE))
				metaDataDT.setActiveInd(ImportConstants.NO);
			else
				metaDataDT.setActiveInd(ImportConstants.YES); //13
			metaDataDT.setStateCd(subform.getStateCode()); //14
			metaDataDT.setDeploymentCd(subform.getDeploymentCode()); //15
			metaDataDT.setNndInd(htmlField.getNndInd()); // 16
			metaDataDT.setImportVersionNbr(
				new Long(subform.getImportVersionNbr()));

			metaDataDT.setAddTime(new Timestamp(new Date().getTime())); //17
			metaDataDT.setRecordStatusCd(
				NEDSSConstants.LDF_CREATE_RECORD_STATUS_CD);
			//18
			metaDataDT.setVersionCtrlNbr(new Integer(1)); //19
			metaDataDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
			//20
			metaDataDT.setLdfOid(htmlField.getObjectId());
			//21

			metaDataDT.setItNew(true);
			ldfMetaDataColl.add(metaDataDT);
		}

		return ldfMetaDataColl;
	}

	/**
	 *
	 * @param fileName
	 * @return
	 * @throws NEDSSAppException
	 */
	private String readSubformFile(String fileName) throws NEDSSAppException {

		StringBuffer html = new StringBuffer();
		String line = null;

		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));

			while ((line = in.readLine()) != null) {
				html.append(line).append("\n");
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
			throw new NEDSSAppException(ioe.toString());
		}
		//System.out.println("XHTML = \n\n " + html.toString());
		return html.toString();
	}

	public void persistImportDataLogDT(CdfSubformImportDataLogDT datalogDT)
		throws NEDSSAppException {
		CdfSubformImportDataLogDAOImpl dataDAO =
			new CdfSubformImportDataLogDAOImpl();
		dataDAO.insert(datalogDT);

	}

	public CdfSubformImportDataLogDT populateImportDataLogDT(
		SubformImportData data,
		String processCd,
		String logMessageTxt) {
		CdfSubformImportDataLogDT dataLogDT = new CdfSubformImportDataLogDT();
		dataLogDT.setImportLogUid(importUid);
		dataLogDT.setDataOid(data.getObjectID());
		dataLogDT.setDataType(NEDSSConstants.SF_DATA_TYPE);
		dataLogDT.setActionType(data.getActionType());
		dataLogDT.setImportTime(new Timestamp(new Date().getTime()));
		dataLogDT.setLogMessageTxt(logMessageTxt);
		dataLogDT.setProcessCd(processCd);

		return dataLogDT;
	}

	public static void main(String args[]) throws NEDSSAppException {
	}
}
