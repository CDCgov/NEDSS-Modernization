package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlObject;

import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.POCDMT000040ClinicalDocument1;
import gov.cdc.nedss.phdc.cda.POCDMT000040Component4;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040Reference;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessCaseSummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

public class CDALabReportProcessor {
	static final LogUtils logger = new LogUtils(CDAPHCProcessor.class.getName());
	
	private static final String LAB_COND_IDENTIFIER = "LAB099";
	private static final String LAB_PA_IDENTIFIER = "LAB169";
	private static final String LAB_JURIS_IDENTIFIER = "LAB168";
	
	public static void setupDocumentForLabReport(
			NBSDocumentVO nbsDocumentVO, POCDMT000040Section section)
			throws NEDSSAppException {
		try {
				for( POCDMT000040Entry entry : section.getEntryArray()){
					ArrayList<EDXEventProcessCaseSummaryDT> eventProcessCaseSummaryList = null;
					String relationshipType = null;
					if(entry.getAct()!=null && entry.getAct().getCode()!=null && entry.getAct().getCode().getCode()!=null)
						relationshipType = entry.getAct().getCode().getCode();
					NbsDocumentDAOImpl nbsDocumentDAOImpl = new NbsDocumentDAOImpl();
					if (relationshipType!=null && entry.getAct().getReferenceArray()!=null 
							&& entry.getAct().getReferenceArray(0).getExternalDocument() != null
							&& entry.getAct().getReferenceArray(0).getExternalDocument().getIdArray() != null
							&& entry.getAct().getReferenceArray(0).getExternalDocument().getIdArray(0).getExtension() != null) {
						for(POCDMT000040Reference reference : entry.getAct().getReferenceArray()){
							String sourceEventId  = reference.getExternalDocument().getIdArray(0).getExtension();
							if (sourceEventId != null) {
								eventProcessCaseSummaryList = nbsDocumentDAOImpl.getEDXEventProcessCaseSummaryByEventId(sourceEventId);
								if (eventProcessCaseSummaryList != null && eventProcessCaseSummaryList.size()>0) {
									if (nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap() == null)
										nbsDocumentVO.seteDXEventProcessCaseSummaryDTMap(new HashMap<String, EDXEventProcessCaseSummaryDT>());
									nbsDocumentVO.geteDXEventProcessCaseSummaryDTMap().put(relationshipType+sourceEventId, eventProcessCaseSummaryList.get(0));
							}
						}
					}
				}
			}
		} catch (IndexOutOfBoundsException ex) {
			String errString = "IndexOutOfBoundsException while retreiving external document linkage of Lab Report"
					+ ex.getMessage();
			logger.error(errString);
		}
		catch (Exception e) {
			String errString = "Exception while preparing the document for lab report PHDC  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSAppException(errString, e);
		}
	}

	public static Map<Object, Object> setupProgAreaAndJuristictionForLabDoc(
			 POCDMT000040ClinicalDocument1 clinicalDocument) throws NEDSSAppException{
		ProgramAreaVO programAreaVO = null;
		String conditionCd=null;
		String programAreaCd=null;
		String jurisdictionCode=null;
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		try {
			XmlObject[] childrens = clinicalDocument.selectPath(EdxCDAConstants.CDA_NAMESPACE
					+EdxCDAConstants.CDA_STRUCTURED_XML_LAB_COMPONENT);
			
			if (childrens != null) {
				for (XmlObject children : childrens) {
					POCDMT000040Component4 component = (POCDMT000040Component4) children;
					if (component.getObservation() != null
							&& component.getObservation().getCode() != null
							&& component.getObservation().getCode().getCode()
									.equals(LAB_COND_IDENTIFIER)
							&& component.getObservation().getValueArray() != null){
						conditionCd = ((CE) component.getObservation().getValueArray(
								0)).getCode();
						if(conditionCd!=null)
							returnMap.put(NEDSSConstants.CONDITION_CD, conditionCd);
					}
					else if(component.getObservation() != null
							&& component.getObservation().getCode() != null
							&& component.getObservation().getCode().getCode()
									.equals(LAB_PA_IDENTIFIER)
							&& component.getObservation().getValueArray() != null){
						programAreaCd =  ((CE) component.getObservation().getValueArray(
								0)).getCode();
						if(programAreaCd!=null)
							returnMap.put(NEDSSConstants.PROG_AREA_CD, programAreaCd);
					}
					else if(component.getObservation() != null
							&& component.getObservation().getCode() != null
							&& component.getObservation().getCode().getCode()
									.equals(LAB_JURIS_IDENTIFIER)
							&& component.getObservation().getValueArray() != null){
						jurisdictionCode =  ((CE) component.getObservation().getValueArray(
								0)).getCode();
						if(jurisdictionCode!=null)
							returnMap.put(NEDSSConstants.JURIS_CD, jurisdictionCode);
					}
				}
				if(conditionCd!=null && programAreaCd==null)	{	
					programAreaVO = CachedDropDowns
						.getProgramAreaForCondition(conditionCd);
				if(programAreaVO != null)
					programAreaCd = programAreaVO.getStateProgAreaCode();
				if(programAreaCd!=null )
					returnMap.put(NEDSSConstants.PROG_AREA_CD, programAreaCd);
				}
			}
		} catch (Exception e) {
			String errString = "Exception while setting up program area and jurisdiction for lab report PHDC  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSAppException(errString, e);
		}
		return returnMap;
	}
	
	public static void processLabDocument(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT,
			NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		try {
			// The Map contains all the investigations linked to the lab
			// document
			Map<String, EDXEventProcessCaseSummaryDT> caseSummaryMap = edxRuleAlgorithmManagerDT
					.geteDXEventProcessCaseSummaryDTMap();
			NbsDocumentDAOImpl nbsDAO = new NbsDocumentDAOImpl();
			Long documentUid = edxRuleAlgorithmManagerDT.getDocumentDT()
					.getNbsDocumentUid();
			// The Map contains the EDX Event created during import of lab
			// document
			Map<String, EDXEventProcessDT> edxEventsMap = nbsDAO
					.getEDXEventProcessMap(documentUid);
			EDXEventProcessDT labEDXEventProcessDT = null;
			for (EDXEventProcessDT eventProcessDT : edxEventsMap.values()) {
				if (eventProcessDT.getParsedInd() != null
						&& eventProcessDT.getParsedInd().equals(
								NEDSSConstants.NO)) {
					labEDXEventProcessDT = eventProcessDT;
					break;
				}

			}
			// create the act relationships between document and investigations
			// as well as the labs contained in the document

			Collection<ActRelationshipDT> actRelationships = new ArrayList<ActRelationshipDT>();
			for (EDXEventProcessCaseSummaryDT caseSummaryDT : caseSummaryMap
					.values()) {
				ActRelationshipDT actRelationshipCaseAndDoc = new ActRelationshipDT();
				ActRelationshipDT actRelationshipCaseAndlab = new ActRelationshipDT();
				// 1. Act relationships between document and cases
				CDAXMLTypeToNBSObject.prepareActRelationship(
						actRelationshipCaseAndDoc,
						caseSummaryDT.getNbsEventUid(), NEDSSConstants.CASE,
						documentUid, NEDSSConstants.ACT_CLASS_CD_FOR_DOC,
						NEDSSConstants.DocToPHC);
				actRelationships.add(actRelationshipCaseAndDoc);
				// 2. Act relationships between lab and cases
				if (labEDXEventProcessDT != null) {
					CDAXMLTypeToNBSObject.prepareActRelationship(
							actRelationshipCaseAndlab,
							caseSummaryDT.getNbsEventUid(),
							NEDSSConstants.CASE,
							labEDXEventProcessDT.getNbsEventUid(),
							NEDSSConstants.CLASS_CD_OBS,
							NEDSSConstants.LABRESULT_CODE);
					actRelationships.add(actRelationshipCaseAndlab);
				}
			}
			// Store the act relationships
			ActRelationshipDAOImpl actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory
					.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
			for (ActRelationshipDT actRelationshipDT : actRelationships) {
				actRelationshipDAOImpl.store(actRelationshipDT);
				logger.debug("processLabDocument: Got into The ActRelationship, The ActUid is "
						+ actRelationshipDT.getTargetActUid());
			}
		} catch (Exception ex) {
			String errorTxt = "Exception while processing lab document PHDC in processLabDocument :"
					+ ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(errorTxt, ex);
		}
	}
}
