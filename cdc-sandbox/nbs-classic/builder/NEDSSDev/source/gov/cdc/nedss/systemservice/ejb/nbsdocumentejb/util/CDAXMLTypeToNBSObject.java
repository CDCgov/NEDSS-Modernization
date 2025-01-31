package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util;

import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.phdc.cda.POCDMT000040ClinicalDocument1;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.phdc.cda.TS;
import gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAContactRecordProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAEntityProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDALabReportProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAMorbReportProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.EdxCDAConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.Jurisdiction;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.JurisdictionHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessCaseSummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.CDAEventSummaryParser;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class CDAXMLTypeToNBSObject {
	
	static final LogUtils logger = new LogUtils(CDAXMLTypeToNBSObject.class.getName());
	
	PropertyUtil propertyUtil = PropertyUtil.getInstance();


	public NBSDocumentVO createNBSDocumentVO(NbsInterfaceDT nbsInterfaceDT,
			Timestamp time, NBSDocumentMetadataDT nbsDocMDT, Object payload,
			NBSSecurityObj nbsSecurityObj) throws NEDSSException,
			RemoteException, EJBException, NEDSSSystemException,
			NEDSSConcurrentDataException, CreateException {
		NBSDocumentVO nbsDocumentVO = new NBSDocumentVO();
		Map<String,ArrayList<String>> conditionsMap2 = new HashMap<String,ArrayList<String>>();
		try {
			String xmlPayLoadContent = nbsInterfaceDT.getXmlPayLoadContent();
			NBSDocumentDT nbsDocumentDT = new NBSDocumentDT();

			//Added to write original eICR into document table's phdc doc derived column : ADDED			
			nbsDocumentDT.setPhdcDocDerivedTxt(nbsInterfaceDT.getOriginalPayload());
			
			nbsDocumentDT.setPayLoadTxt(xmlPayLoadContent);
			
			ClinicalDocumentDocument1 clinicalDocumentRoot = parseCaseTypeXml(payload);
		
			setupDocumentVOWithEventTypeInfoWithInDocument(nbsDocumentVO,clinicalDocumentRoot.getClinicalDocument());
			
			nbsDocumentDT.setDocumentObject(clinicalDocumentRoot); 
			String docId = "";
			if (clinicalDocumentRoot.getClinicalDocument().getId() != null){
				docId = clinicalDocumentRoot
						.getClinicalDocument().getId().newCursor()
						.getTextValue();
				nbsDocumentVO.setDocName(docId);
			}
			try{
				if (clinicalDocumentRoot.getClinicalDocument().getSetId() != null)
					nbsDocumentVO.setOngoingCase(clinicalDocumentRoot.getClinicalDocument()
							.getSetId().getDisplayable());
			} catch (Exception ex) {
				logger.debug("Clinical documents does not have setID popuated for source document ID: "
						+ docId);
			}
			String jurisdictionCode = null;
			boolean isJurisdictionFound = false;
			String programAreaCd = null;
			EdxCDAInformationDT informationDT = new EdxCDAInformationDT();
			informationDT.setUserId(new Long(nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID()));
			informationDT.setPatientUid(new Long(-1));
			informationDT.setAddTime(time);
			
			
			PersonVO patientVO = CDAEntityProcessor.getPatient(
					clinicalDocumentRoot.getClinicalDocument()
							.getRecordTargetArray(), informationDT, getSocialHistorySection(clinicalDocumentRoot.getClinicalDocument()));
			EDXEventProcessCaseSummaryDT subjectInvestigationSummary=null;
			String conditionCd = null;
			ArrayList<String> conditionCds = new ArrayList<String>();
			Map<String,ArrayList<String>> conditionsMap = null;
			if(nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap()!=null && nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap().size()>0){// Check if the subject is already created
				 subjectInvestigationSummary = (EDXEventProcessCaseSummaryDT)nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap().get(EdxCDAConstants.SUBJECT_INV);
			}
			if(subjectInvestigationSummary!=null && nbsDocumentVO.isContactRecordDoc()){
				conditionCd = subjectInvestigationSummary.getConditionCd();
				jurisdictionCode = subjectInvestigationSummary.getJurisdictionCd();
				programAreaCd = subjectInvestigationSummary.getProgAreaCd();
				conditionCds.add(conditionCd);
			}
			else if(nbsDocumentVO.isLabReportDoc()){
				Map<Object, Object> returnMap = CDALabReportProcessor.setupProgAreaAndJuristictionForLabDoc(clinicalDocumentRoot
						.getClinicalDocument());
				conditionCd = (String)returnMap.get(NEDSSConstants.CONDITION_CD);
				jurisdictionCode = (String)returnMap.get(NEDSSConstants.JURIS_CD);
				programAreaCd = (String)returnMap.get(NEDSSConstants.PROG_AREA_CD);
				conditionCds.add(conditionCd);
			}
			else {
				conditionCds = getConditionCodeFromDocument(clinicalDocumentRoot.getClinicalDocument(), conditionsMap2);
			}
	
			
			ArrayList<String> programAreaCds = new ArrayList<String>();
			
			ProgramAreaVO programAreaVO = null;
			if(conditionCds!=null && conditionCds.size()!= 0)	{	
				Iterator it3 = conditionCds.iterator();
				StringBuffer conditionCodeDescription = new StringBuffer(), cd= new StringBuffer();
				while(it3.hasNext()){
					String code = it3.next().toString();
					programAreaVO = CachedDropDowns
							.getProgramAreaForCondition(code);
					if(programAreaVO != null ){
						//programAreaVOColl.add(programAreaVO);
						if(programAreaVO.getStateProgAreaCode()!= null && !programAreaCds.contains(programAreaVO.getStateProgAreaCode()))
							programAreaCds.add(programAreaVO.getStateProgAreaCode());
					}
					
					//assign multiple condition codes separated by pipe | 
					Coded conditionCode=null;
					try{
					conditionCode = new Coded(code,
							"v_Condition_code", "PHC_TYPE");
					}catch(Exception ex){
		        		logger.info("Cannot drive the condition code for codesetTableName = v_Condition_code and code: "+code+" "+ex.getMessage());
		        	}
					
					if(conditionCode==null || conditionCode.getCode()== null || conditionCode.isFlagNotFound()){
		        		try{
		        			String codeSystem = getCodeSystem(code, conditionsMap2);
		        			conditionCode  = new Coded(codeSystem, code);
		        		}catch(Exception ex){
		        			logger.info("Cannot drive the condition code for codesetTableName = Code_to_condition and code: "+code+" "+ex.getMessage());
		        		}
		        		if(conditionCode != null && conditionCode.getCode()!= null && !conditionCds.contains(conditionCode.getCode()) )
		        			conditionCds.add(conditionCode.getCode()); 
		        	}
					
					if(conditionCodeDescription.length()!=0 && conditionCode.getCodeDescription()!=null && !conditionCode.getCodeDescription().equalsIgnoreCase(""))
						conditionCodeDescription.append("|");
					if(cd.length()!=0 )
						cd.append("|");
					conditionCodeDescription = conditionCodeDescription.append(conditionCode.getCodeDescription());
					cd = cd.append(code);
				}
				nbsDocumentVO.setConditionName(conditionCodeDescription.toString());
				nbsDocumentDT.setCdDescTxt(conditionCodeDescription.toString());
				nbsDocumentDT.setCd(cd.toString());
				conditionCd = cd.toString();
				if(programAreaCds != null &&programAreaCds.size() ==1 && programAreaCd==null){
					programAreaCd = programAreaCds.get(0);
				}
			}
			
			if (!isJurisdictionFound && jurisdictionCode==null) {
				NedssUtils nedssUtils = new NedssUtils();
				Object lookUpJurisdiction = nedssUtils
						.lookupBean(JNDINames.JURISDICTION_EJB);
				JurisdictionHome jurHome = (JurisdictionHome) PortableRemoteObject
						.narrow(lookUpJurisdiction, JurisdictionHome.class);
				Jurisdiction jurisdiction = jurHome.create();
				Collection<Object> jurColl = jurisdiction
						.findJurisdictionForPatient(patientVO);
				if (jurColl != null && jurColl.size() == 1) {
					for (Iterator<Object> i = jurColl.iterator(); i
							.hasNext();) {
						jurisdictionCode = (String) i.next();
					}
				}
			}
		
			if(jurisdictionCode==null || programAreaCd==null){
				logger.info("program Area: "+programAreaCd+ " Jurisdiction cd: "+jurisdictionCode + " Document will be assigned a default program area and Jurisdiction");
				//TODO: discuss with Requirements for possible options
//				if(jurisdictionCode == null)
//					jurisdictionCode = "NI";
//				if(programAreaCd == null)
//					programAreaCd = "NI";
			}
			/*if (conditionCd != null) {
				Coded conditionCode = new Coded(conditionCd,
						"v_Condition_code", "PHC_TYPE");
				nbsDocumentVO.setConditionName(conditionCode
						.getCodeDescription());
				nbsDocumentDT.setCdDescTxt(conditionCode.getCodeDescription());
				nbsDocumentDT.setCd(conditionCd);
			}*/
			TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
					.getConditionCdAndInvFormCd();
			if (conditionCd==null  || condAndFormCdTreeMap.get(conditionCd) == null) {
				logger.info("Condition code is null for PHDC document.");
				nbsDocumentVO.setConditionFound(false);
			} else {
				nbsDocumentVO.setConditionFound(true);
			}
			nbsDocumentDT.setPayLoadTxt(nbsInterfaceDT.getXmlPayLoadContent());
			if (clinicalDocumentRoot.getClinicalDocument().getCode() != null) {
//				nbsDocumentDT.setDocTypeCd(clinicalDocumentRoot
//						.getClinicalDocument().getCode().getCode());
				nbsDocumentDT.setTxt(clinicalDocumentRoot.getClinicalDocument()
						.getCode().getDisplayName());
			}
			
			if(clinicalDocumentRoot.getClinicalDocument().getEffectiveTime()!=null && clinicalDocumentRoot.getClinicalDocument().getEffectiveTime().getValue()!=null) {
			Date effectiveDate = convertToDate(clinicalDocumentRoot.getClinicalDocument().getEffectiveTime().getValue());
			if(effectiveDate!=null)
				nbsDocumentDT.setEffectiveTime(new Timestamp(effectiveDate.getTime()));
			}
			nbsDocumentDT.setDocTypeCd(NEDSSConstants.PHC_236);
			
			//Calling the prepareVO method to get the next state based on the current. It will go from START to UNPROCESSED initially
			//if later on, we discover it is an update, this prepareVO method will be called again to get the correct status.
			
			 String businessTriggerCd = "DOC_PROCESS";
			 String tableName = "NBS_DOCUMENT";
	         String moduleCd = NEDSSConstants.BASE;
	         PrepareVOUtils pre = new PrepareVOUtils();
	         nbsDocumentDT.setSuperclass("ACT");
	         nbsDocumentDT.setNbsDocumentUid(-1l);

	         nbsDocumentDT = (NBSDocumentDT) pre.prepareVO(nbsDocumentDT, NBSBOLookup.DOCUMENT, businessTriggerCd, tableName, moduleCd, nbsSecurityObj);

	         
	         
	         
	         
	         
			if (jurisdictionCode != null
					&& programAreaCd != null
					&& propertyUtil.getPHDCSkipDRRQ() != null
					&& propertyUtil.getPHDCSkipDRRQ()
							.equals(NEDSSConstants.YES))
				nbsDocumentDT
						.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_PROCESSED);
			//Fatima: no needed, as we are getting this value set from the prepareVO. Remove any other value not needed.
			/*else
				nbsDocumentDT
						.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_UNPROCESSED);*/
			// nbsDocumentDT.setStatusCd(headerType.getResultStatus().getCode());
		//	nbsDocumentDT.setRecordStatusTime(time);
	//		nbsDocumentDT.setAddUserId(new Long(nbsSecurityObj.getEntryID()));
			//nbsDocumentDT.setAddTime(time);
			nbsDocumentDT.setProgAreaCd(programAreaCd);
			nbsDocumentDT.setJurisdictionCd(jurisdictionCode);

			nbsDocumentDT.setSharedInd(NEDSSConstants.TRUE);
			if (nbsDocumentDT.getNbsDocumentMetadataUid() == null)
				nbsDocumentDT.setNbsDocumentMetadataUid(nbsDocMDT
						.getNbsDocumentMetadataUid());
			nbsDocumentDT
					.setPayloadViewIndCd(NEDSSConstants.PAYLOAD_VIEW_IND_CD_PHDC);

			if (!(nbsDocumentDT.getProgAreaCd() == null)
					&& !(nbsDocumentDT.getJurisdictionCd() == null)) {
				String progAreaCd = nbsDocumentDT.getProgAreaCd();
				String jurisdictionCd = nbsDocumentDT.getJurisdictionCd();
				long pajHash = ProgramAreaJurisdictionUtil.getPAJHash(
						progAreaCd, jurisdictionCd);
				Long aProgramJurisdictionOid = new Long(pajHash);
				nbsDocumentDT
						.setProgramJurisdictionOid(aProgramJurisdictionOid);
			}

			nbsDocumentDT.setNbsInterfaceUid(nbsInterfaceDT
					.getNbsInterfaceUid());
			nbsDocumentDT.setVersionCtrlNbr(new Integer(1));
		//	nbsDocumentDT.setLastChgTime(time);
			//nbsDocumentDT
			//		.setLastChgUserId(new Long(nbsSecurityObj.getEntryID()));
			// nbsDocumentDT.setDocPurposeCd(caseType.getSectionHeader().getPurpose().getCode());
			if (clinicalDocumentRoot.getClinicalDocument().getCustodian()
					.getAssignedCustodian()
					.getRepresentedCustodianOrganization().getName() != null)

				nbsDocumentDT.setSendingFacilityNm(clinicalDocumentRoot
						.getClinicalDocument().getCustodian()
						.getAssignedCustodian()
						.getRepresentedCustodianOrganization().getName()
						.newCursor().getTextValue());
			
			if (clinicalDocumentRoot.getClinicalDocument().getCustodian()
					.getAssignedCustodian()
					.getRepresentedCustodianOrganization().getIdArray()!=null && clinicalDocumentRoot.getClinicalDocument().getCustodian()
					.getAssignedCustodian()
					.getRepresentedCustodianOrganization().getIdArray().length>0)

				nbsDocumentDT.setSendingFacilityOID(clinicalDocumentRoot.getClinicalDocument().getCustodian()
						.getAssignedCustodian()
						.getRepresentedCustodianOrganization().getIdArray(0).getExtension());
			
			nbsDocumentDT.setSendingAppEventId(clinicalDocumentRoot
					.getClinicalDocument().getId().getExtension());
			nbsDocumentDT.setSendingAppPatientId(informationDT.getSourcePatientId());
			
			nbsDocumentVO.setNbsDocumentDT(nbsDocumentDT);
			nbsDocumentVO.setPatientVO(patientVO);
			// Create a map for all the event Ids to create reference records in EDX_EVENT_PROCESS table
			extractEventIds(nbsDocumentDT, clinicalDocumentRoot.getClinicalDocument());
		} catch (Exception e) {
			e.printStackTrace();
			String errorString = "CDAXMLTypeToNBSObject.createNBSDocumentVO : Exception raised while creating PHCD Document"
					+ e.getMessage();
			logger.error(errorString);
			throw new NEDSSSystemException(errorString, e);
		}
		return nbsDocumentVO;
	}
	
	private ArrayList<String> getConditionCodeFromDocument(
			POCDMT000040ClinicalDocument1 clinicalDocument, Map<String,ArrayList<String>> conditionsMap ) {
		String condition = null;// /cda:observation[@code='INV169']
		String cdSystem = null;
		ArrayList<String> conditions = null;
		ArrayList<String> conditionCds = new ArrayList<String>();
		
		
		try {
			XmlObject[] childrens = clinicalDocument.selectPath(EdxCDAConstants.CDA_NAMESPACE
					+EdxCDAConstants.CDA_STRUCTURED_XML_ENRTY);
			if (childrens != null) {
				for (XmlObject children : childrens) {
					POCDMT000040Entry entry = (POCDMT000040Entry) children;
					if (entry.getObservation() != null
							&& entry.getObservation().getCode() != null
							&& entry.getObservation().getCode().getCode()
									.equals("INV169")
							&& entry.getObservation().getValueArray() != null){
						for(int i = 0; i < entry.getObservation().getValueArray().length ; i++){
							condition = ((CE) entry.getObservation().getValueArray(
									i)).getCode();
							cdSystem =  ((CE) entry.getObservation().getValueArray(
									i)).getCodeSystem();
							if(conditionsMap.containsKey(cdSystem))
								conditions = conditionsMap.get(cdSystem);
							else
								conditions = new ArrayList<>();
							if(!conditions.contains(condition))
								conditions.add(condition);
							conditionsMap.put(cdSystem, conditions); 
						}
						/*condition = ((CE) entry.getObservation().getValueArray(
								0)).getCode();
						cdSystem =  ((CE) entry.getObservation().getValueArray(
								0)).getCodeSystem();
						if(conditionsMap.containsKey(cdSystem))
							conditions = conditionsMap.get(cdSystem);
						else
							conditions = new ArrayList<>();
						if(!conditions.contains(condition))
							conditions.add(condition);
						conditionsMap.put(cdSystem, conditions); */
						//conditions.add(condition);
					//	break;
					}
					if (entry.getObservation() != null
							&& entry.getObservation().getCode() != null
							&& entry.getObservation().getCode().getCode()
									.equals("CON097")
							&& entry.getObservation().getValueArray() != null){
						condition = ((CE) entry.getObservation().getValueArray(
								0)).getCode();
						if(condition!=null){
							try{
								Coded conditionCodeLookup = new Coded(condition, DataTables.CODE_VALUE_GENERAL, "CASE_DIAGNOSIS");
								if(conditionCodeLookup!=null)
									condition = conditionCodeLookup.getCode();
							}catch(Exception ex){
								logger.info("Cannot drive the concept code for code_set_nm = CASE_DIAGNOSIS and code: "+condition+" "+ex.getMessage());
							}
						}
						cdSystem =  ((CE) entry.getObservation().getValueArray(
								0)).getCodeSystem();
						if(conditionsMap.containsKey(cdSystem))
							conditions = conditionsMap.get(cdSystem);
						else
							conditions = new ArrayList<>();
						if(!conditions.contains(condition))
							conditions.add(condition);
						conditionsMap.put(cdSystem, conditions); 
					
					}
					if (entry.getObservation() != null
							&& entry.getObservation().getCode() != null
							&& entry.getObservation().getCode().getCode()
									.equals("MRB121")
							&& entry.getObservation().getValueArray() != null){
						condition = ((CE) entry.getObservation().getValueArray(
								0)).getCode();
						cdSystem =  ((CE) entry.getObservation().getValueArray(
								0)).getCodeSystem();
						if(conditionsMap.containsKey(cdSystem))
							conditions = conditionsMap.get(cdSystem);
						else
							conditions = new ArrayList<>();
						if(!conditions.contains(condition))
							conditions.add(condition);
						conditionsMap.put(cdSystem, conditions); 
						//break;
					}
				}
			}
		} catch (Exception ex) {
			logger.debug("Condition code canot be derived from document");
		}
		if(conditionsMap==null)
			logger.debug("document does not contain the condition code");
		else{
			Iterator it = conditionsMap.entrySet().iterator();
			int count  = conditionsMap.size();
		    while (it.hasNext()) {
		    	
		        Map.Entry pair = (Map.Entry)it.next();
		        String codeSystem = pair.getKey().toString();
		        ArrayList<String> ar = (ArrayList<String>) pair.getValue();
		        
		        Iterator it2 = ar.iterator();
	      	
		        while (it2.hasNext()) {
		        	String condition1 = it2.next().toString();
		        	Coded conditionCode = null;
		        	try{
		        		conditionCode = new Coded(condition1, "v_Condition_code", "PHC_TYPE");
		        	}catch(Exception ex){
		        		logger.info("Cannot drive the condition code for codesetTableName = v_Condition_code and code: "+condition1+" "+ex.getMessage());
		        	}
		        	if(conditionCode != null && conditionCode.getCode()!= null && conditionCode.isFlagNotFound()==false &&  !conditionCds.contains(conditionCode.getCode())){//&& conditionCode.getCodeSystemCd().equalsIgnoreCase(codeSystem) 
		        		conditionCds.add(conditionCode.getCode()); 
		        	}
		        	else{
		        		try{
		        			conditionCode  = new Coded(codeSystem, condition1);
		        		}catch(Exception ex){
		        			logger.info("Cannot drive the condition code for codesetTableName = Code_to_condition and code: "+condition1+" "+ex.getMessage());
		        		}
		        		if(conditionCode != null && conditionCode.getCode()!= null && !conditionCds.contains(conditionCode.getCode()) )
		        			conditionCds.add(conditionCode.getCode()); 
		        	}
		        }
		    }
		
		if(conditionCds.size() == 0)
			logger.debug("document does not contain the condition code");
		}
	    
		return conditionCds;
	}

	public ClinicalDocumentDocument1 parseCaseTypeXml(Object xmlPayLoadContent)
			throws NEDSSSystemException {
		ClinicalDocumentDocument1 clinicalDocumentRoot = null;
		try {
			ArrayList<Object> validationErrors = new ArrayList<Object>();
			XmlOptions validationOptions = new XmlOptions();
			validationOptions.setErrorListener(validationErrors);

			clinicalDocumentRoot = (ClinicalDocumentDocument1)xmlPayLoadContent;

			boolean isValid = clinicalDocumentRoot.validate(validationOptions);

			// Print the errors if the XML is invalid.
			if (!isValid) {
				Iterator<Object> iter = validationErrors.iterator();
				StringBuffer buff = new StringBuffer();
				buff.append("Inbound message failed in CDACDAXMLTypeToNBSObject.parseCaseTypeXml.");
				while (iter.hasNext()) {
					buff.append(iter.next() + "\n");
				}
				logger.error(buff.toString());
				logger.error("CDACDAXMLTypeToNBSObject.parseCaseTypeXml:-Error thrown as XMl is invalid"
						+ buff.toString());
				throw new NEDSSSystemException("Invalid XML " + buff.toString());
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			throw new NEDSSSystemException("Exception occured during valdating PHDC XML: "+e.getMessage(), e);
		}

		//logger.debug("Received XML:\n" + containerDoc.toString());
		return clinicalDocumentRoot;
	}
	
	private void extractEventIds(NBSDocumentDT documentDT, POCDMT000040ClinicalDocument1 clinicalDocument)
			throws NEDSSSystemException {
		try {
			CDAEventSummaryParser cdaEventSummaryParser = new CDAEventSummaryParser();
			cdaEventSummaryParser.parseEventId(documentDT, clinicalDocument);
		} catch (Exception e) {
			String errString = "CDAXMLTypeToNBSObject: extractEventIds failed extracting event IDs:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
	}
	
	public static String parseDate(TS ts) {
		String stringDate = null;
		if (ts != null) {
			String inStringDate = ts.getValue();
			stringDate = parseStringDate(inStringDate);
		}
		return stringDate;
	}
	/*
	 * for parsing out string date format of yyyyMMddhhmmss
	 */
	public static  String parseStringDate(String inStringDate) {
		Date date = null;
		String stringDate = null;
		if (inStringDate != null) {
			try {
				if (inStringDate != null) {
					DateFormat df = null;
					if (inStringDate.length() > 8)
						df = new SimpleDateFormat("yyyyMMddhhmmss");
					else
						df = new SimpleDateFormat("yyyyMMdd");
					date = df.parse(inStringDate);
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					stringDate = sdf.format((java.util.Date) date);
				}
			} catch (ParseException e) {
				String errString = "CDAXMLTypeToNBSObject.parseDate:  Invalid date:  '"
						+ stringDate + "':  " + e.getMessage();
				logger.error(errString);
				throw new NEDSSSystemException(errString, e);
			}
		}
		return stringDate;
	}
	
	@SuppressWarnings("unused")
	public static Timestamp parseDateAsTimestamp(TS ts) {
		Date date = null;
		String stringDate = null;
		if (ts != null) {
			String inStringDate = ts.getValue();
			Calendar caldate = Calendar.getInstance();
			try {
				if (inStringDate != null) {
					DateFormat df = null;
					if (inStringDate.length() > 8)
						df = new SimpleDateFormat("yyyyMMddhhmmss");
					else
						df = new SimpleDateFormat("yyyyMMdd");
					date = df.parse(inStringDate);
				}
			} catch (ParseException e) {
				String errString = "CDAXMLTypeToNBSObject.parseDate:  Invalid date:  '"
						+ stringDate + "':  " + e.getMessage();
				logger.error(errString);
				throw new NEDSSSystemException(errString, e);
			}

		}
		return new Timestamp(date.getTime());
	}
	
	private String getCodeSystem(String code, Map<String,ArrayList<String>> conditionsMap2){
		
		String codeSystem ="";
		
		Iterator it = conditionsMap2.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry pair = (Map.Entry)it.next();
	        
	    	ArrayList<String> codes = (ArrayList<String>)pair.getValue();
	    	for(int j=0; j<codes.size(); j++){
	    		String code2 = codes.get(j);
	    		
	    		if(code2!=null && code2.equalsIgnoreCase(code))
	    			codeSystem = (String)pair.getKey() ;
	    		
	    	}
	    }
		//Iterator it = conditionsMap2.iterator();
		
		return codeSystem;
	}
	private static void setupDocumentVOWithEventTypeInfoWithInDocument(
			NBSDocumentVO nbsDocumentVO,
			POCDMT000040ClinicalDocument1 clinicalDocumentRoot)
			throws NEDSSSystemException {
		try {
			XmlObject[] childrens = clinicalDocumentRoot
					.selectPath(EdxCDAConstants.CDA_NAMESPACE
							+ EdxCDAConstants.CDA_STRUCTURED_XML_SECTION);
			if (childrens != null) {
				for (XmlObject children : childrens) {
					POCDMT000040Section section = (POCDMT000040Section) children;
					if (section.getCode() != null
							&& section.getCode().getCode() != null) {
						String sectionCode = section.getCode().getCode();
						if (sectionCode
								.equals(EdxCDAConstants.EXPOSURE_INFO_CD)) {
							nbsDocumentVO.setContactRecordDoc(true);
							CDAContactRecordProcessor
									.setupDocumentForContactRecord(
											nbsDocumentVO, section);
						}
						if (sectionCode
								.equals(EdxCDAConstants.LABORATORY_REPORT_CD)) {
							nbsDocumentVO.setLabReportDoc(true);
							CDALabReportProcessor.setupDocumentForLabReport(
									nbsDocumentVO, section);
						}
						if (sectionCode
								.equals(EdxCDAConstants.MORBIDITY_REPORT_CD)) {
							nbsDocumentVO.setMorbReportDoc(true);
							CDAMorbReportProcessor.setupDocumentForMorbReport(
									nbsDocumentVO, section);
						}
					}
				}
			}
		} catch (Exception e) {
			String errString = "CDAXMLTypeToNBSObject.updateDocumentVOWithEventTypeInfoWithInDocument:  error:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
	}

	public static String parseCodedString(String codedString)throws NEDSSSystemException {
		try {
			String[] codedArray = codedString.split("\\^");
			if(codedArray!=null && codedArray.length==1)
				return codedString;
			else if(codedArray!=null && codedArray.length>1)
				return codedArray[0];
		} catch (Exception e) {
			String errString = "CDAXMLTypeToNBSObject.parseCodedString:  error:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return "";
	}
	public static void prepareActRelationship(ActRelationshipDT actDoc,
			Long targetActUid, String targetClassCd, Long sourceActUid, String sourceClassCd,
			String typeCd) {
		actDoc.setItNew(true);
		actDoc.setSourceActUid(sourceActUid);
		actDoc.setSourceClassCd(sourceClassCd);
		actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		actDoc.setTargetActUid(targetActUid);
		actDoc.setTargetClassCd(targetClassCd);
		actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
		actDoc.setTypeCd(typeCd);
		actDoc.setItDirty(false);
	}
	
	public static POCDMT000040Section getSocialHistorySection(
			POCDMT000040ClinicalDocument1 clinicalDocumentRoot)
			throws NEDSSSystemException {
		try {
			XmlObject[] childrens = clinicalDocumentRoot
					.selectPath(EdxCDAConstants.CDA_NAMESPACE
							+ EdxCDAConstants.CDA_STRUCTURED_XML_SECTION);
			if (childrens != null) {
				for (XmlObject children : childrens) {
					POCDMT000040Section section = (POCDMT000040Section) children;
					if (section.getCode() != null
							&& section.getCode().getCode() != null) {
						String sectionCode = section.getCode().getCode();
						if (sectionCode
								.equals(EdxCDAConstants.SOCIAL_HISTORY_CD)) {
							return section;
						}
					}
				}
			}
		} catch (Exception e) {
			String errString = "CDAXMLTypeToNBSObject.getSocialHistorySection:  error:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return null;
	}
	
	private Date convertToDate(String dateString) {
		Timestamp date = null;
		if (dateString != null && !dateString.isEmpty()) {
			SimpleDateFormat formatter = null;

			if (dateString.length() == 8)
				formatter = new SimpleDateFormat("yyyyMMdd");
			else if (dateString.length() == 10)
				formatter = new SimpleDateFormat("yyyyMMddHH");
			else if (dateString.length() == 12)
				formatter = new SimpleDateFormat("yyyyMMddHHmm");
			else if (dateString.length() >= 14 ){
				dateString = dateString.substring(0, 14);
				formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			}
			
			try {
				date = new java.sql.Timestamp(formatter.parse(dateString)
						.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
}


