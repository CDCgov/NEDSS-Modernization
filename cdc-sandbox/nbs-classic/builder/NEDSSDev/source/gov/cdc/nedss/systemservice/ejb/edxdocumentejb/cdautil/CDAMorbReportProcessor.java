package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppException;
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
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

public class CDAMorbReportProcessor {
	static final LogUtils logger = new LogUtils(CDAPHCProcessor.class.getName());
	
	public static void setupDocumentForMorbReport(
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
			String errString = "IndexOutOfBoundsException while retreiving external document linkage of Morb Report "
					+ ex.getMessage();
			logger.error(errString);
		}
		catch (Exception e) {
			String errString = "Exception while preparing the document for Morb report PHDC  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSAppException(errString, e);
		}
	}


	public static void processMorbDocument(
			EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT,
			NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		try {
			// The Map contains all the investigations linked to the Morb Report
			// document
			Map<String, EDXEventProcessCaseSummaryDT> caseSummaryMap = edxRuleAlgorithmManagerDT
					.geteDXEventProcessCaseSummaryDTMap();
			NbsDocumentDAOImpl nbsDAO = new NbsDocumentDAOImpl();
			Long documentUid = edxRuleAlgorithmManagerDT.getDocumentDT()
					.getNbsDocumentUid();
			// The Map contains the EDX Event created during import of morb
			// document
			Map<String, EDXEventProcessDT> edxEventsMap = nbsDAO
					.getEDXEventProcessMap(documentUid);
			EDXEventProcessDT morbEDXEventProcessDT = null;
			for (EDXEventProcessDT eventProcessDT : edxEventsMap.values()) {
				if (eventProcessDT.getParsedInd() != null
						&& eventProcessDT.getParsedInd().equals(
								NEDSSConstants.NO)) {
					morbEDXEventProcessDT = eventProcessDT;
					break;
				}

			}
			// create the act relationships between document and investigations
			// as well as the morbs contained in the document

			Collection<ActRelationshipDT> actRelationships = new ArrayList<ActRelationshipDT>();
			for (EDXEventProcessCaseSummaryDT caseSummaryDT : caseSummaryMap
					.values()) {
				ActRelationshipDT actRelationshipCaseAndDoc = new ActRelationshipDT();
				ActRelationshipDT actRelationshipCaseAndMorb = new ActRelationshipDT();
				// 1. Act relationships between document and cases
				CDAXMLTypeToNBSObject.prepareActRelationship(
						actRelationshipCaseAndDoc,
						caseSummaryDT.getNbsEventUid(), NEDSSConstants.CASE,
						documentUid, NEDSSConstants.ACT_CLASS_CD_FOR_DOC,
						NEDSSConstants.DocToPHC);
				actRelationships.add(actRelationshipCaseAndDoc);
				// 2. Act relationships between morb and cases
				if (morbEDXEventProcessDT != null) {
					CDAXMLTypeToNBSObject.prepareActRelationship(
							actRelationshipCaseAndMorb,
							caseSummaryDT.getNbsEventUid(),
							NEDSSConstants.CASE,
							morbEDXEventProcessDT.getNbsEventUid(),
							NEDSSConstants.CLASS_CD_OBS,
							NEDSSConstants.MORBIDITY_CODE);
					actRelationships.add(actRelationshipCaseAndMorb);
				}
			}
			// Store the act relationships
			ActRelationshipDAOImpl actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory
					.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
			for (ActRelationshipDT actRelationshipDT : actRelationships) {
				actRelationshipDAOImpl.store(actRelationshipDT);
				logger.debug("processMorbDocument: Got into The ActRelationship, The ActUid is "
						+ actRelationshipDT.getTargetActUid());
			}
		} catch (Exception ex) {
			String errorTxt = "Exception while processing morb document PHDC in processMorbDocument :"
					+ ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(errorTxt, ex);
		}
	}
}
