/*
 * Created on Jan 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import java.util.*;
import java.sql.Timestamp;
import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.ldf.helper.*;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.ldf.importer.dao.*;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedFieldImporterHelper extends AbstractDataImporterHelper {

	//For logging
	static final LogUtils logger =
		new LogUtils(DefinedFieldImporterHelper.class.getName());

	private Long importUid;
	private DefinedFieldSRTValues srtValues;
	private CachedDropDownValues cdv = new CachedDropDownValues();

	public DefinedFieldImporterHelper(
		Long importUid,
		DefinedFieldSRTValues srtValues) {
		this.importUid = importUid;
		this.srtValues = srtValues;
	}
	/** This method import a DefinedFieldImportData object into ODS database.
	 *
	 * @param data
	 * @throws NEDSSAppException
	 */
	public void importDefinedFieldMetadata(
		DefinedFieldImportData data,
		NBSSecurityObj secObj)
		throws NEDSSAppException {

		StateDefinedFieldMetaDataDT metaDataDT =
			new StateDefinedFieldMetaDataDT();
		
		// set default values
		setDefaultValues(data);

		// generic validation first
		CdfSubformImportDataLogDT dataLogDT = null;
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

		// persist defined field metadata
		ArrayList<Object> ldfMetaDataColl = new ArrayList<Object> ();
		metaDataDT.setAdminComment(data.getComment()); //1
		metaDataDT.setBusinessObjNm(
			srtValues
				.getPageSets()
				.getPageSetDT(data.getPageSetID())
				.getBusinessObjNm());
		//2
		metaDataDT.setCategoryType(data.getCategoryType()); //3
		metaDataDT.setCdcNationalId(data.getNationalIdentifier()); //4
		metaDataDT.setClassCd(data.getClassCode()); //5
		metaDataDT.setCodeSetNm(data.getCodeSetName()); //6
		String ccode =
			srtValues
				.getPageSets()
				.getPageSetDT(data.getPageSetID())
				.getConditionCd();
		String ccDesc = cdv.getConditionDesc(ccode);
		metaDataDT.setDisplayOrderNbr(new Integer(data.getDisplayOrder()));
		//7
		metaDataDT.setConditionCd(ccode); //8
		metaDataDT.setConditionDescTxt(ccDesc); //9
		metaDataDT.setDataType(data.getDataType()); //10
		metaDataDT.setLabelTxt(data.getLabelText()); //11
		metaDataDT.setLdfPageId(data.getPageSetID()); //12
		if (data.getActionType().equalsIgnoreCase(ImportConstants.REMOVE))
			//13
			metaDataDT.setActiveInd(ImportConstants.NO);
		else
			metaDataDT.setActiveInd(ImportConstants.YES);

		metaDataDT.setLdfOid(data.getObjectID()); //14
		metaDataDT.setNndInd(data.getNNDIndicator()); //15
		metaDataDT.setImportVersionNbr(new Long(data.getImportVersionNbr()));
		//16
		metaDataDT.setValidationJscriptTxt(data.getValidationJavaScriptText());
		//17
		metaDataDT.setFieldSize(data.getFieldSize()); //18
		metaDataDT.setValidationTxt(data.getValidationText()); //19
		metaDataDT.setRequiredInd(data.getRequiredIndicator()); //20
		metaDataDT.setStatusCd(data.getStateCode()); //21
		metaDataDT.setDeploymentCd(data.getDeploymentCode()); //22
		metaDataDT.setAddTime(new Timestamp(new Date().getTime())); //23
		metaDataDT.setRecordStatusCd(
			NEDSSConstants.LDF_CREATE_RECORD_STATUS_CD);
		//24
		metaDataDT.setVersionCtrlNbr(new Integer(1)); //25
		metaDataDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
		//26

		metaDataDT.setItNew(true);

		ldfMetaDataColl.add(metaDataDT);

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
			// if this happens, we got to roll back
			logger.error(e.getMessage(), e);
			throw new NEDSSAppException(e.toString());
		}
		dataLogDT =
			populateImportDataLogDT(
				data,
				ImportConstants.SUCCESS_CODE,
				ImportConstants.SUCCESS_TEXT);
		persistImportDataLogDT(dataLogDT);

	}

	/**
	 * @param data
	 */
	private void setDefaultValues(DefinedFieldImportData data) {
		if(data != null){
			data.setClassCode(ImportConstants.CLASS_CODE_CDC);
		}
		
	}
	public void persistImportDataLogDT(CdfSubformImportDataLogDT datalogDT)
		throws NEDSSAppException {
		CdfSubformImportDataLogDAOImpl dataDAO =
			new CdfSubformImportDataLogDAOImpl();
		dataDAO.insert(datalogDT);

	}

	public CdfSubformImportDataLogDT populateImportDataLogDT(
		DefinedFieldImportData data,
		String processCd,
		String logMessageTxt) {
		CdfSubformImportDataLogDT dataLogDT = new CdfSubformImportDataLogDT();
		dataLogDT.setImportLogUid(importUid);
		dataLogDT.setDataOid(data.getObjectID());
		dataLogDT.setDataType(NEDSSConstants.DF_DATA_TYPE);
		dataLogDT.setActionType(data.getActionType());
		dataLogDT.setImportTime(new Timestamp(new Date().getTime()));
		dataLogDT.setLogMessageTxt(logMessageTxt);
		dataLogDT.setProcessCd(processCd);

		return dataLogDT;
	}

}
