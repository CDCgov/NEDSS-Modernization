package gov.cdc.nedss.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.EdxCDAConstants;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;	
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CdaMessageParser;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.action.nbsDocument.util.NBSDocumentActionUtil;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.phdc.cda.II;
import gov.cdc.nedss.phdc.cda.IVLTS;
import gov.cdc.nedss.phdc.cda.ON;
import gov.cdc.nedss.phdc.cda.PN;
import gov.cdc.nedss.phdc.cda.POCDMT000040Author;
import gov.cdc.nedss.phdc.cda.POCDMT000040ClinicalDocument1;
import gov.cdc.nedss.phdc.cda.POCDMT000040Component2;
import gov.cdc.nedss.phdc.cda.POCDMT000040Component3;
import gov.cdc.nedss.phdc.cda.POCDMT000040Component4;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040LabeledDrug;
import gov.cdc.nedss.phdc.cda.POCDMT000040Participant2;
import gov.cdc.nedss.phdc.cda.POCDMT000040Performer2;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.phdc.cda.POCDMT000040StructuredBody;
import gov.cdc.nedss.phdc.cda.POCDMT000040SubstanceAdministration;
import gov.cdc.nedss.phdc.cda.TS;

import javax.rmi.PortableRemoteObject;

import org.apache.xmlbeans.XmlObject;

public class CDAEventSummaryParser {
	static final LogUtils logger = new LogUtils(
			CdaMessageParser.class.getName());
	NBSDocumentActionUtil util = new NBSDocumentActionUtil();
	private ClinicalDocumentDocument1 clinicalDocumentRoot;
	private POCDMT000040ClinicalDocument1 clinicalDocument;
	
	public Collection<TreatmentSummaryVO> parseTreatmentSummaryFromCDADoc(
			Collection<Object> docColl, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		Collection<TreatmentSummaryVO> treatments = new ArrayList<TreatmentSummaryVO>();
		try{
		for (Object docSummaryDT : docColl) {
			NBSDocumentVO doc = getNBSDocument(
					((SummaryDT) docSummaryDT).getNbsDocumentUid(),
					nbsSecurityObj);
			if(doc!=null && doc.getNbsDocumentDT()!=null && doc.getNbsDocumentDT().getNbsDocumentMetadataUid()!=null && doc.getNbsDocumentDT().getNbsDocumentMetadataUid().longValue()!=1002){
			ArrayList<POCDMT000040SubstanceAdministration> treatmentList = extractTretmentsFromDoc(doc);
			fillTreatmentSummaryVO(treatments, treatmentList, doc, nbsSecurityObj);
			}
		 }
		}catch(Exception ex){
			String errorMsg = "Exception while creating treatment summary VO from PHDC: "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(errorMsg, ex);
		}
		return treatments;
	}
	
	public Collection<LabReportSummaryVO> parseLabSummaryFromCDADoc(
			Collection<Object> docColl, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		
		Collection<LabReportSummaryVO> labs = new ArrayList<LabReportSummaryVO>();
		try{
		for (Object docSummaryDT : docColl) {
			NBSDocumentVO doc = getNBSDocument(
					((SummaryDT) docSummaryDT).getNbsDocumentUid(),
					nbsSecurityObj);
			if(doc!=null && doc.getNbsDocumentDT()!=null && doc.getNbsDocumentDT().getNbsDocumentMetadataUid()!=null && doc.getNbsDocumentDT().getNbsDocumentMetadataUid().longValue()!=1002)
				fillLabSummaryVO(labs, doc, nbsSecurityObj);
		}
		}catch(Exception ex){
			String errorMsg = "Exception while creating lab summary VO from lab PHDC: "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(errorMsg, ex);
		}
		return labs;
	}
	
	public Collection<MorbReportSummaryVO> parseMorbSummaryFromCDADoc(
			Collection<Object> docColl, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		
		Collection<MorbReportSummaryVO> morbs = new ArrayList<MorbReportSummaryVO>();
		try{
		for (Object docSummaryDT : docColl) {
			NBSDocumentVO doc = getNBSDocument(
					((SummaryDT) docSummaryDT).getNbsDocumentUid(),
					nbsSecurityObj);
			if(doc!=null && doc.getNbsDocumentDT()!=null && doc.getNbsDocumentDT().getNbsDocumentMetadataUid()!=null && doc.getNbsDocumentDT().getNbsDocumentMetadataUid().longValue()!=1002)
				fillMorbSummaryVO(morbs, doc, nbsSecurityObj);
		}
		}catch(Exception ex){
			String errorMsg = "Exception while creating morb summary VO from morb PHDC: "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(errorMsg, ex);
		}
		return morbs;
	}
	
	public Map<Long, TreatmentSummaryVO> getTreatmentMapByPHCUid(
			Map<String, EDXEventProcessDT> edxProcessMap,
			NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Collection<TreatmentSummaryVO> treatments = new ArrayList<TreatmentSummaryVO>();
		Map<Long, TreatmentSummaryVO> returnMap = new HashMap<Long, TreatmentSummaryVO>();
		try{
		long previousDocUid = 0;
		for (EDXEventProcessDT processDT : edxProcessMap.values()) {
			//To avoid duplicated document calls
			if(processDT.getNbsDocumentUid() == null || previousDocUid==processDT.getNbsDocumentUid().longValue())
				continue;
			NBSDocumentVO doc = getNBSDocument(processDT.getNbsDocumentUid(),
					nbsSecurityObj);
			ArrayList<POCDMT000040SubstanceAdministration> treatmentList = extractTretmentsFromDoc(doc);
			fillTreatmentSummaryVO(treatments, treatmentList, doc,
					nbsSecurityObj);
			previousDocUid= processDT.getNbsDocumentUid();
		}

		for (TreatmentSummaryVO treatmentSummaryVO : treatments) {
			if (edxProcessMap.containsKey(treatmentSummaryVO.getLocalId()))
				returnMap.put(treatmentSummaryVO.getTreatmentUid(),
						treatmentSummaryVO);
		}
		}catch(Exception ex){
			logger.error("Exception in getTreatmentMapByPHCUid");
			ex.printStackTrace();
			throw new NEDSSSystemException("Exception in getTreatmentMapByPHCUid", ex);
		}
		return returnMap;
	}
	
	public Map<Long, LabReportSummaryVO> getLabReportMapByPHCUid(
			Map<String, EDXEventProcessDT> edxProcessMap,
			NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Collection<LabReportSummaryVO> labs = new ArrayList<LabReportSummaryVO>();
		Map<Long, LabReportSummaryVO> returnMap = new HashMap<Long, LabReportSummaryVO>();
		try{
		long previousDocUid = 0;
		for (EDXEventProcessDT processDT : edxProcessMap.values()) {
			//To avoid duplicated document calls
			if(processDT.getNbsDocumentUid() == null || previousDocUid==processDT.getNbsDocumentUid().longValue())
				continue;
			NBSDocumentVO doc = getNBSDocument(processDT.getNbsDocumentUid(),
					nbsSecurityObj);
			fillLabSummaryVO(labs, doc,  nbsSecurityObj);
			previousDocUid= processDT.getNbsDocumentUid();
		}
		for (LabReportSummaryVO labSummaryVO : labs) {
			if (edxProcessMap.containsKey(labSummaryVO.getLocalId()))
				returnMap.put(labSummaryVO.getObservationUid(), 	labSummaryVO);
		}
		}catch(Exception ex){
			String errorString = "Exception in getLabReportMapByPHCUid: "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSSystemException(errorString, ex);
		}
		return returnMap;
	}
	
	public Map<Long, MorbReportSummaryVO> getMorbReportMapByPHCUid(
			Map<String, EDXEventProcessDT> edxProcessMap,
			NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Collection<MorbReportSummaryVO> morbs = new ArrayList<MorbReportSummaryVO>();
		Map<Long, MorbReportSummaryVO> returnMap = new HashMap<Long, MorbReportSummaryVO>();
		try{
		long previousDocUid = 0;
		for (EDXEventProcessDT processDT : edxProcessMap.values()) {
			//To avoid duplicated document calls
			if(processDT.getNbsDocumentUid() == null || previousDocUid==processDT.getNbsDocumentUid().longValue())
				continue;
			NBSDocumentVO doc = getNBSDocument(processDT.getNbsDocumentUid(),
					nbsSecurityObj);
			fillMorbSummaryVO(morbs, doc,  nbsSecurityObj);
			previousDocUid= processDT.getNbsDocumentUid();
		}
		for (MorbReportSummaryVO morbSummaryVO : morbs) {
			if (edxProcessMap.containsKey(morbSummaryVO.getLocalId()))
				returnMap.put(morbSummaryVO.getObservationUid(), 	morbSummaryVO);
		}
		}catch(Exception ex){
			String errorString = "Exception in getMorbReportMapByPHCUid: "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSSystemException(errorString, ex);
		}
		return returnMap;
	}

	
	public Map<Long, TreatmentSummaryVO> getTreatmentMapByUid(
			Collection<Object> docColl, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		Map<Long, TreatmentSummaryVO> returnMap = new HashMap<Long, TreatmentSummaryVO>();
		try {
			Collection<TreatmentSummaryVO> treatments = parseTreatmentSummaryFromCDADoc(
					docColl, nbsSecurityObj);

			for (TreatmentSummaryVO treatmentSummaryVO : treatments) {
				returnMap.put(treatmentSummaryVO.getTreatmentUid(),
						treatmentSummaryVO);
			}
		} catch (Exception e) {
			String errString = "Exception occured in getTreatmentMapByUid:  "
					+ e.getMessage();
			e.printStackTrace();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return returnMap;
	}
	
	public Map<Long, LabReportSummaryVO> getLabMapByUid(
			Collection<Object> docColl, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		Map<Long, LabReportSummaryVO> returnMap = new HashMap<Long, LabReportSummaryVO>();
		try {
			Collection<LabReportSummaryVO> labs = parseLabSummaryFromCDADoc(
					docColl, nbsSecurityObj);

			for (LabReportSummaryVO labSummaryVO : labs) {
				returnMap.put(labSummaryVO.getObservationUid(),
						labSummaryVO);
			}
		} catch (Exception e) {
			String errString = "Exception occured in getLabMapByUid:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return returnMap;
	}
	
	public Map<Long, MorbReportSummaryVO> getMorbMapByUid(
			Collection<Object> docColl, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		Map<Long, MorbReportSummaryVO> returnMap = new HashMap<Long, MorbReportSummaryVO>();
		try {
			Collection<MorbReportSummaryVO> morbs = parseMorbSummaryFromCDADoc(
					docColl, nbsSecurityObj);

			for (MorbReportSummaryVO morbSummaryVO : morbs) {
				returnMap.put(morbSummaryVO.getObservationUid(),
						morbSummaryVO);
			}
		} catch (Exception e) {
			String errString = "Exception occured in getMorbMapByUid:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return returnMap;
	}
	
	public Map<String, TreatmentSummaryVO> getTreatmentMapByLocalId(
			Collection<Object> docColl, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Map<String, TreatmentSummaryVO> returnMap = new HashMap<String, TreatmentSummaryVO>();
		try {
			Collection<TreatmentSummaryVO> treatments = parseTreatmentSummaryFromCDADoc(
					docColl, nbsSecurityObj);

			for (TreatmentSummaryVO treatmentSummaryVO : treatments) {
				returnMap.put(treatmentSummaryVO.getLocalId(),
						treatmentSummaryVO);
			}
		} catch (Exception e) {
			String errString = "Exception occured in getTreatmentMapByLocalId:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return returnMap;
	}
	
	public Map<String, LabReportSummaryVO> getLabMapByLocalId(
			Collection<Object> docColl, Long investigationUID, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Map<String, LabReportSummaryVO> returnMap = new HashMap<String, LabReportSummaryVO>();
		Map<Object, Object>  associatedLabUids = null;
		try {
			Collection<LabReportSummaryVO> labs = parseLabSummaryFromCDADoc(
					docColl, nbsSecurityObj);
			if(labs!=null && labs.size()>0){
					//get the associations
					ObservationSummaryDAOImpl dao = new ObservationSummaryDAOImpl();
					associatedLabUids = dao.findObservationAssociationWithPHC(investigationUID, NEDSSConstants.LABRESULT_CODE);
			}
			for (LabReportSummaryVO lab : labs) {
				//set the Investigation association flag
				if(associatedLabUids!=null && associatedLabUids.size()>0 && lab.getObservationUid()!=null && associatedLabUids.containsKey(lab.getObservationUid().toString()))
					lab.setItAssociated(true);
				returnMap.put(lab.getLocalId(),lab);
			}
		} catch (Exception e) {
			String errString = "Exception occured in getLabMapByLocalId:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return returnMap;
	}
	
	
	public Map<String, MorbReportSummaryVO> getMorbMapByLocalId(
			Collection<Object> docColl, Long investigationUID, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Map<String, MorbReportSummaryVO> returnMap = new HashMap<String, MorbReportSummaryVO>();
		Map<Object, Object>  associatedMorbUids = null;
		try {
			Collection<MorbReportSummaryVO> morbs = parseMorbSummaryFromCDADoc(
					docColl, nbsSecurityObj);
			if(morbs!=null && morbs.size()>0){
					//get the associations
					ObservationSummaryDAOImpl dao = new ObservationSummaryDAOImpl();
					associatedMorbUids = dao.findObservationAssociationWithPHC(investigationUID, NEDSSConstants.MORBIDITY_CODE);
			}
			for (MorbReportSummaryVO morb : morbs) {
				//set the Investigation association flag
				if(associatedMorbUids!=null && associatedMorbUids.size()>0 && morb.getObservationUid()!=null && associatedMorbUids.containsKey(morb.getObservationUid().toString()))
					morb.setItAssociated(true);
				returnMap.put(morb.getLocalId(),morb);
			}
		} catch (Exception e) {
			String errString = "Exception occured in getMorbMapByLocalId:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return returnMap;
	}
	
	public ArrayList<POCDMT000040SubstanceAdministration> extractTretmentsFromDoc(
			NBSDocumentVO nbsDocVO) throws NEDSSSystemException{
		ArrayList<POCDMT000040SubstanceAdministration> treatments = new ArrayList<POCDMT000040SubstanceAdministration>();
		try {
			clinicalDocumentRoot = ClinicalDocumentDocument1.Factory
					.parse(nbsDocVO.getNbsDocumentDT().getPayLoadTxt());
			clinicalDocument = clinicalDocumentRoot.getClinicalDocument();
			POCDMT000040Component2 component = clinicalDocument.getComponent();
			POCDMT000040StructuredBody structuredBody = component
					.getStructuredBody();
			POCDMT000040Component3[] componentArray = structuredBody
					.getComponentArray();
			if (componentArray != null && componentArray.length > 0) {
				for (POCDMT000040Component3 component3 : componentArray) {
					POCDMT000040Section section = component3.getSection();
					if(section!=null){
					CE code = section.getCode();
					POCDMT000040Entry[] entries = section.getEntryArray();
					if (code != null && code.getCode() != null
							&& code.getCode().equals(EdxCDAConstants.TREATMENT_INFORMATION_CD)) {
						if (entries != null && entries.length > 0) {
							for (POCDMT000040Entry entry : entries) {
									POCDMT000040SubstanceAdministration substanceAdministration = entry
											.getSubstanceAdministration();
									if (substanceAdministration != null)
										treatments
												.add(substanceAdministration);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			String errString = "CdaMessageParser constructor failed parsing CDA message:  "
					+ e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
		return treatments;
	}
	
	@SuppressWarnings("unchecked")
	private void fillTreatmentSummaryVO(Collection<TreatmentSummaryVO> trtColl,
			ArrayList<POCDMT000040SubstanceAdministration> treatments,
			NBSDocumentVO doc, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		try{
		for (POCDMT000040SubstanceAdministration substanceAdministration : treatments) {
			TreatmentSummaryVO summaryVO = new TreatmentSummaryVO();
			summaryVO.setCreateDate(doc.getNbsDocumentDT().getAddTime());
			summaryVO.setNbsDocumentUid(doc.getNbsDocumentDT()
					.getNbsDocumentUid());
			if (substanceAdministration != null
					&& substanceAdministration.getIdArray() != null
					&& substanceAdministration.getIdArray().length > 0)
				summaryVO.setLocalId(substanceAdministration.getIdArray(0)
						.getExtension());
			POCDMT000040LabeledDrug mm = substanceAdministration
					.getConsumable().getManufacturedProduct()
					.getManufacturedLabeledDrug();
			if (mm.getCode() != null) {
				summaryVO.setCustomTreatmentNameCode(mm.getCode()
						.getDisplayName());
				summaryVO.setTreatmentNameCode(mm.getCode().getDisplayName());
				if((mm.getCode().getCode()!=null && mm.getCode().getCode().equalsIgnoreCase(NEDSSConstants.OTHER)) || mm.getCode().getNullFlavor()!=null){
					summaryVO.setTreatmentNameCode(mm.getName().xmlText());
					summaryVO.setCustomTreatmentNameCode(mm.getName().xmlText());
				}
			if (substanceAdministration.getEffectiveTimeArray() != null
					&& substanceAdministration.getEffectiveTimeArray().length > 0) {
				if(substanceAdministration.getEffectiveTimeArray(0) instanceof IVLTS)
					summaryVO.setActivityFromTime(CDAXMLTypeToNBSObject.parseDateAsTimestamp(((IVLTS)substanceAdministration.getEffectiveTimeArray(0)).getLow()));
			}
			// set the treatment_uid as the act_uid created for treatment
			// records with in document during import process
			if (doc.getEDXEventProcessDTMap() != null
					&& doc.getEDXEventProcessDTMap()
							.get(summaryVO.getLocalId()) != null)
				summaryVO
						.setTreatmentUid(((EDXEventProcessDT) (doc
								.getEDXEventProcessDTMap().get(summaryVO
								.getLocalId()))).getNbsEventUid());
			else
				summaryVO.setTreatmentUid(new Long(-1));
			RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
			summaryVO.setAssociationMap(rsvo.getAssociatedInvList(
					summaryVO.getTreatmentUid(), nbsSecurityObj, NEDSSConstants.TREATMENT_ACT_TYPE_CD));
			if (summaryVO.getAssociationMap() == null)
				summaryVO.setAssociationMap(new HashMap<Object, Object>());
			summaryVO.getAssociationMap().putAll(
					rsvo.getAssociatedDocumentMapBysourceEventId(
							summaryVO.getLocalId(), nbsSecurityObj,
							NEDSSConstants.TREATMENT_ACT_TYPE_CD));

				POCDMT000040Participant2[] participant = substanceAdministration
						.getParticipantArray();
				if(participant!=null &&participant.length>0){
				for(POCDMT000040Participant2 entity: participant){
							if (entity.getParticipantRole() != null
									&& entity.getParticipantRole().getIdArray() != null
									&& entity.getParticipantRole().getIdArray().length > 0
									&& entity.getParticipantRole()
											.getIdArray(0)
											.getAssigningAuthorityName() != null
									&& "LR_PSN".equals(entity
											.getParticipantRole().getIdArray(0)
											.getAssigningAuthorityName())) {
								summaryVO
										.setProviderFirstName(getPerformerNameInfo(entity));
							}
						}
			}
			if (trtColl == null)
				trtColl = new ArrayList<TreatmentSummaryVO>();
			trtColl.add(summaryVO);
		}
		}
		} catch (Exception ex) {
			String error = "Exception in fillTreatmentSummaryVO "+ex.getMessage();
			logger.error(error);
			ex.printStackTrace();
			throw new NEDSSSystemException(error, ex);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void fillLabSummaryVO(Collection<LabReportSummaryVO> labColl,
			NBSDocumentVO doc, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		try{
			clinicalDocumentRoot = ClinicalDocumentDocument1.Factory
					.parse(doc.getNbsDocumentDT().getPayLoadTxt());
			clinicalDocument = clinicalDocumentRoot.getClinicalDocument();
			XmlObject[] sections = clinicalDocument.selectPath(EdxCDAConstants.CDA_NAMESPACE 
					+ EdxCDAConstants.CDA_STRUCTURED_XML_SECTION );
			for (int i = 0; i < sections.length; i++) {
				POCDMT000040Section section = (POCDMT000040Section) sections[i];
				if (section.getCode() != null
						&& section.getCode().getCode() != null
						&& section.getCode().getCode()
								.equals(EdxCDAConstants.LAB_INFORMATION_CD)) {
					LabReportSummaryVO labSummaryVO = new LabReportSummaryVO();
					labSummaryVO.setElectronicInd(NEDSSConstants.ELECTRONIC_IND_ELR);
					labSummaryVO.setProgramArea(doc.getNbsDocumentDT().getProgAreaCd());
					if(section.getId()!=null && section.getId().getExtension()!=null)
					labSummaryVO.setLocalId(section.getId().getExtension());
					labSummaryVO.setDateReceived(doc.getNbsDocumentDT().getAddTime());
					labSummaryVO.setLabFromDoc(true);
					labSummaryVO.setUid(doc.getNbsDocumentDT().getNbsDocumentUid());
					if(section.getText()!=null)
						labSummaryVO.setResultedTestString(section.getText().xmlText());
					if (section.getEntryArray() != null
							&& section.getEntryArray().length > 0) {
						for (POCDMT000040Entry entry : section.getEntryArray()) {
							if (entry.getOrganizer() != null
									&& entry.getOrganizer().getIdArray() != null
									&& entry.getOrganizer().getIdArray().length > 0
									&& entry.getOrganizer().getIdArray(0)
											.getRoot() != null
									&& entry.getOrganizer()
											.getIdArray(0)
											.getRoot()
											.equals(EdxCDAConstants.ORDER_IDENTIFIER)) {
								if(entry.getOrganizer().getComponentArray()!=null && entry.getOrganizer().getComponentArray().length>0){
									for(POCDMT000040Component4 component: entry.getOrganizer().getComponentArray()){
										if(component.getProcedure()!=null && component.getProcedure().getEffectiveTime()!=null)
											labSummaryVO.setDateCollected(CDAXMLTypeToNBSObject.parseDateAsTimestamp(component.getProcedure().getEffectiveTime()));
									}
								}
								if(entry.getOrganizer().getPerformerArray()!=null && entry.getOrganizer().getPerformerArray().length>0){
									for(POCDMT000040Performer2 performer: entry.getOrganizer().getPerformerArray()){
										if(performer.getAssignedEntity().getAssignedPerson()!=null){
											setOrderingProviderForLab(performer,labSummaryVO);
										}
									}
								}
								if(entry.getOrganizer().getAuthorArray()!=null && entry.getOrganizer().getAuthorArray().length>0){
									POCDMT000040Author author = entry.getOrganizer().getAuthorArray(0);
									setReportingFacilityForLab(author,labSummaryVO);
								}
							}
						}
					}
					// set the observation_uid as the act_uid created for lab reports
					// with in document during import process
					if (doc.getEDXEventProcessDTMap() != null
							&& doc.getEDXEventProcessDTMap()
									.get(labSummaryVO.getLocalId()) != null)
						labSummaryVO.setObservationUid(((EDXEventProcessDT) (doc
										.getEDXEventProcessDTMap().get(labSummaryVO
										.getLocalId()))).getNbsEventUid());
					else
						labSummaryVO.setObservationUid(new Long(-1));
					RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
					//Check for Investigation associations
					labSummaryVO.setAssociationsMap(rsvo.getAssociatedInvList(
							labSummaryVO.getObservationUid(), nbsSecurityObj, NEDSSConstants.OBSERVATION_CLASS_CODE));

					if (labSummaryVO.getAssociationsMap() == null)
						labSummaryVO.setAssociationsMap(new HashMap<Object, Object>());
					labSummaryVO.getAssociationsMap().putAll(
							rsvo.getAssociatedDocumentMapBysourceEventId(
									labSummaryVO.getLocalId(), nbsSecurityObj,
									NEDSSConstants.LABRESULT_CODE));
					labColl.add(labSummaryVO);
				}
			}
		} catch (Exception ex) {
			String error = "Exception in fillLabSummaryVO "+ex.getMessage();
			logger.error(error);
			ex.printStackTrace();
			throw new NEDSSSystemException(error, ex);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void fillMorbSummaryVO(Collection<MorbReportSummaryVO> morbColl,
			NBSDocumentVO doc, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		try {
			clinicalDocumentRoot = ClinicalDocumentDocument1.Factory.parse(doc
					.getNbsDocumentDT().getPayLoadTxt());
			clinicalDocument = clinicalDocumentRoot.getClinicalDocument();
			XmlObject[] sections = clinicalDocument
					.selectPath(EdxCDAConstants.CDA_NAMESPACE
							+ EdxCDAConstants.CDA_STRUCTURED_XML_SECTION);
			for (int i = 0; i < sections.length; i++) {
				POCDMT000040Section section = (POCDMT000040Section) sections[i];
				if (section.getCode() != null
						&& section.getCode().getCode() != null
						&& section.getCode().getCode()
								.equals(EdxCDAConstants.MORBIDITY_REPORT_CD)) {
					MorbReportSummaryVO morbSummaryVO = new MorbReportSummaryVO();
					morbSummaryVO
							.setElectronicInd(NEDSSConstants.ELECTRONIC_IND_ELR);
					morbSummaryVO.setProgramArea(doc.getNbsDocumentDT()
							.getProgAreaCd());
					if (section.getId() != null
							&& section.getId().getExtension() != null)
						morbSummaryVO
								.setLocalId(section.getId().getExtension());
					morbSummaryVO.setDateReceived(doc.getNbsDocumentDT()
							.getAddTime());
					morbSummaryVO.setMorbFromDoc(true);
					morbSummaryVO.setUid(doc.getNbsDocumentDT()
							.getNbsDocumentUid());
					if (section.getEntryArray() != null
							&& section.getEntryArray().length > 0) {
						for (POCDMT000040Entry entry : section.getEntryArray()) {
							if (entry.getObservation() != null
									&& entry.getObservation().getCode() != null
									&& entry.getObservation().getCode()
											.getCode() != null
									&& entry.getObservation().getCode()
											.getCode().equals("MRB101")) {
								if (entry.getObservation().getValueArray() != null
										&& entry.getObservation()
												.getValueArray().length > 0)
									morbSummaryVO
											.setReportDate(CDAXMLTypeToNBSObject
													.parseDateAsTimestamp(((TS) entry
															.getObservation()
															.getValueArray(0))));
							} else if (entry.getObservation() != null
									&& entry.getObservation().getCode() != null
									&& entry.getObservation().getCode()
											.getCode() != null
									&& entry.getObservation().getCode()
											.getCode().equals("MRB121")) {
								if (entry.getObservation().getValueArray() != null
										&& entry.getObservation()
												.getValueArray().length > 0){
									String conditionCd = ((CE) entry
											.getObservation().getValueArray(0))
											.getCode();
									morbSummaryVO.setCondition(conditionCd);
								Coded conditionCode = new Coded(conditionCd,
										"v_Condition_code", "PHC_TYPE");
								morbSummaryVO.setConditionDescTxt(conditionCode.getCodeDescription());
								}
							}else if (entry.getObservation() != null
									&& entry.getObservation().getCode() != null
									&& entry.getObservation().getCode()
											.getCode() != null
									&& entry.getObservation().getCode()
											.getCode().equals("MRB100")) {
								if (entry.getObservation().getValueArray() != null
										&& entry.getObservation()
												.getValueArray().length > 0){
									String reportType = ((CE) entry
											.getObservation().getValueArray(0))
											.getCode();
									morbSummaryVO.setReportType(reportType);
									Coded coded = new Coded(reportType,
											"code_value_general", "MORB_RPT_TYPE");
									morbSummaryVO.setReportTypeDescTxt(coded.getCodeDescription());
								}
							}else if (entry.getObservation() != null
									&& entry.getObservation().getCode() != null
									&& entry.getObservation().getCode()
											.getCode() != null
									&& entry.getObservation().getCode()
											.getCode().equals("INV247")) {
								if (entry.getObservation().getValueArray() != null
										&& entry.getObservation()
												.getValueArray().length > 0){
									String providerId = ((II) entry
											.getObservation().getValueArray(0)).getExtension();
									morbSummaryVO.setProviderFirstName(getProviderInfoByID(providerId,sections));
								}
							}
						}
					}
					// set the observation_uid as the act_uid created for morb
					// reports
					// with in document during import process
					if (doc.getEDXEventProcessDTMap() != null
							&& doc.getEDXEventProcessDTMap().get(
									morbSummaryVO.getLocalId()) != null)
						morbSummaryVO
								.setObservationUid(((EDXEventProcessDT) (doc
										.getEDXEventProcessDTMap()
										.get(morbSummaryVO.getLocalId())))
										.getNbsEventUid());
					else
						morbSummaryVO.setObservationUid(new Long(-1));
					RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
					// Check for Investigation associations
					morbSummaryVO.setAssociationsMap(rsvo.getAssociatedInvList(
							morbSummaryVO.getObservationUid(), nbsSecurityObj,
							NEDSSConstants.OBSERVATION_CLASS_CODE));

					if (morbSummaryVO.getAssociationsMap() == null)
						morbSummaryVO
								.setAssociationsMap(new HashMap<Object, Object>());
					morbSummaryVO.getAssociationsMap().putAll(
							rsvo.getAssociatedDocumentMapBysourceEventId(
									morbSummaryVO.getLocalId(), nbsSecurityObj,
									NEDSSConstants.MORBIDITY_CODE));
					morbColl.add(morbSummaryVO);
				}
			}
		} catch (Exception ex) {
			String error = "Exception in fillMorbSummaryVO " + ex.getMessage();
			logger.error(error);
			ex.printStackTrace();
			throw new NEDSSSystemException(error, ex);
		}
	}
	
	private String getProviderInfoByID(String providerId, XmlObject[] sections)
			throws NEDSSSystemException {
		try {
			for (int i = 0; i < sections.length; i++) {
				POCDMT000040Section section = (POCDMT000040Section) sections[i];
				if (section.getCode() != null
						&& section.getCode().getCode() != null
						&& section.getCode().getCode()
								.equals(EdxCDAConstants.INTERESTED_PARTY_CD)) {
					for (POCDMT000040Entry entry : section.getEntryArray()) {
						if (entry.getAct() != null
								&& entry.getAct().getParticipantArray() != null
								&& entry.getAct().getParticipantArray(0)
										.getParticipantRole() != null
								&& entry.getAct().getParticipantArray(0)
										.getParticipantRole().getIdArray() != null
								&& entry.getAct().getParticipantArray(0)
										.getParticipantRole().getIdArray(0)
										.getExtension() != null
								&& entry.getAct().getParticipantArray(0)
										.getParticipantRole().getIdArray(0)
										.getExtension().equals(providerId))
							return getPerformerNameInfo(entry.getAct()
									.getParticipantArray(0));
					}
				}
			}
		} catch (Exception ex) {
			String error = "Exception in getProviderInfoByID "
					+ ex.getMessage();
			logger.error(error);
			ex.printStackTrace();
			throw new NEDSSSystemException(error, ex);
		}
		return "";
	}
	
	private String getPerformerNameInfo(POCDMT000040Participant2 participant)
			throws NEDSSSystemException {
		String perName = "";
		PN[] name = null;
		try {
			if (participant.getParticipantRole() != null
					&& participant.getParticipantRole().getPlayingEntity() != null) {
				name = participant.getParticipantRole().getPlayingEntity()
						.getNameArray();
				if (name != null && name.length > 0) {
					String prefix = name[0].getPrefixArray(0) != null ? name[0]
							.getPrefixArray(0).toString()+" " : "";
					String fName = name[0].getGivenArray() != null ? name[0]
							.getGivenArray(0).toString()+" " : "";
					String lName = name[0].getFamilyArray() != null ? name[0]
							.getFamilyArray(0).toString()+" " : "";
					String suffix = name[0].getSuffixArray(0) != null ? name[0]
							.getSuffixArray(0).toString() : "";
					perName = prefix+fName+lName+suffix;
					return perName;
				}
			}
		} catch (IndexOutOfBoundsException ioouEx) {
			// for organization name
			if (name != null && name.length > 0)
				perName = name[0].newCursor().getTextValue();
		} catch (Exception ex) {
			logger.error("Exception in getPerformerNameInfo");
			throw new NEDSSSystemException("Exception in getPerformerNameInfo",
					ex);
		}
		return perName;
	}
	
	private void setOrderingProviderForLab(POCDMT000040Performer2 performer,
			LabReportSummaryVO labSummaryVO) throws NEDSSSystemException {
		PN[] name = null;
		try {
			if (performer.getAssignedEntity() != null
					&& performer.getAssignedEntity().getAssignedPerson() != null) {
				name = performer.getAssignedEntity().getAssignedPerson()
						.getNameArray();
				if (name != null && name.length > 0) {
					labSummaryVO
							.setProviderFirstName(name[0].getGivenArray() != null ? name[0]
									.getGivenArray(0).toString() : "");
					labSummaryVO
							.setProviderLastName(name[0].getFamilyArray() != null ? name[0]
									.getFamilyArray(0).toString() : "");
					labSummaryVO
							.setProviderPrefix(name[0].getPrefixArray() != null ? name[0]
									.getPrefixArray(0).toString() : "");
					labSummaryVO
							.setProviderSuffix(name[0].getSuffixArray() != null ? name[0]
									.getSuffixArray(0).toString() : "");
				}
			}
		} catch (IndexOutOfBoundsException ioouEx) {
			// for organization name
			if (name != null && name.length > 0)
				labSummaryVO.setProviderFirstName(name[0].newCursor()
						.getTextValue());
		} catch (Exception ex) {
			String errorTxt = "Exception in setOrderingProviderForLab: "
					+ ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}
	
	private void setReportingFacilityForLab(POCDMT000040Author author,
			LabReportSummaryVO labSummaryVO) throws NEDSSSystemException {
		ON[] name = null;
		try {
			if (author.getAssignedAuthor() != null
					&& author.getAssignedAuthor().getRepresentedOrganization() != null) {
				name = author.getAssignedAuthor().getRepresentedOrganization()
						.getNameArray();
				if (name != null && name.length > 0) {
					labSummaryVO.setReportingFacility(name[0].newCursor()
							.getTextValue() != null ? name[0].newCursor()
							.getTextValue() : "");
				}
			}
		} catch (IndexOutOfBoundsException ioouEx) {
			// for organization name
			if (name != null && name.length > 0)
				labSummaryVO.setProviderFirstName(name[0].newCursor()
						.getTextValue());
		} catch (Exception ex) {
			String errorTxt = "Exception in setReportingFacilityForLab: "
					+ ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSSystemException(errorTxt, ex);
		}
	}
	
	private NBSDocumentVO getNBSDocument(Long nbsDocUid,
			NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		NBSDocumentVO docVO = null;
		try {
			NedssUtils nedssUtils = new NedssUtils();
			NbsDocument document = null;
			Object object = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
			logger.debug("NBSDocument lookup = " + object.toString());
			NbsDocumentHome dochome = (NbsDocumentHome) PortableRemoteObject
					.narrow(object, NbsDocumentHome.class);
			logger.debug("Found NbsDocumentHome: " + dochome);
			document = dochome.create();
			docVO = document.getNBSDocument(nbsDocUid, nbsSecurityObj);
		} catch (Exception ex) {
			String errString = "Exception while retreiving document for document Uid :"+nbsDocUid+ " " + ex.getMessage();
			ex.printStackTrace();
			logger.error(errString);
			throw new NEDSSSystemException(errString, ex);
		}
		return docVO;
	}
	
	public void parseEventId(NBSDocumentDT nbsDocumentDT,
			POCDMT000040ClinicalDocument1 clinicalDocument)
			throws NEDSSSystemException {
		ArrayList<Object> treatmentIdList = new ArrayList<Object>();
		ArrayList<Object> labIdList = new ArrayList<Object>();
		ArrayList<Object> caeIdList = new ArrayList<Object>();
		ArrayList<Object> contactRecordList = new ArrayList<Object>();
		ArrayList<Object> morbIdList = new ArrayList<Object>();
		try {
			XmlObject[] treatmentchildren = clinicalDocument.selectPath(EdxCDAConstants.CDA_NAMESPACE 
					+ EdxCDAConstants.CDA_STRUCTURED_XML_ENRTY_SA );
			for (int i = 0; i < treatmentchildren.length; i++) {
				POCDMT000040SubstanceAdministration substanceAdministration = (POCDMT000040SubstanceAdministration) treatmentchildren[i];
				if (substanceAdministration.getIdArray() != null
						&& substanceAdministration.getIdArray().length > 0) {
					String idString = substanceAdministration.getIdArray(0)
							.getExtension();
					if (idString != null)
						treatmentIdList.add(idString);
				}
			}
			nbsDocumentDT.getEventIdMap().put(
					NEDSSConstants.TREATMENT_ACT_TYPE_CD, treatmentIdList);
			//Extract Lab Ids and Case Ids
			XmlObject[] sections = clinicalDocument.selectPath(EdxCDAConstants.CDA_NAMESPACE 
					+ EdxCDAConstants.CDA_STRUCTURED_XML_SECTION );
				
			for (int i = 0; i < sections.length; i++) {
				POCDMT000040Section section = (POCDMT000040Section) sections[i];
				if (section.getCode() != null
						&& section.getCode().getCode() != null
						&& section.getCode().getCode()
								.equals(EdxCDAConstants.LAB_INFORMATION_CD)
						&& section.getId() != null
						&& section.getId().getExtension() != null) {
					String idString = section.getId().getExtension();
					if (idString != null)
						labIdList.add(idString);
				}
				if (section.getCode() != null
						&& section.getCode().getCode() != null
						&& section.getCode().getCode()
								.equals(EdxCDAConstants.EPI_INFO_CD)
						&& section.getId() != null
						&& section.getId().getExtension() != null) {
					String idString = section.getId().getExtension();
					if (idString != null)
						caeIdList.add(idString);
				}
				if (section.getCode() != null
						&& section.getCode().getCode() != null
						&& section.getCode().getCode()
								.equals(EdxCDAConstants.EXPOSURE_INFO_CD)
						&& section.getId() != null
						&& section.getId().getExtension() != null) {
					String idString = section.getId().getExtension();
					if (idString != null)
						contactRecordList.add(idString);
				}
				if (section.getCode() != null
						&& section.getCode().getCode() != null
						&& section.getCode().getCode()
								.equals(EdxCDAConstants.MORBIDITY_REPORT_CD)
						&& section.getId() != null
						&& section.getId().getExtension() != null) {
					String idString = section.getId().getExtension();
					if (idString != null)
						morbIdList.add(idString);
				}
			}
			nbsDocumentDT.getEventIdMap().put(
					NEDSSConstants.LABRESULT_CODE, labIdList);
			nbsDocumentDT.getEventIdMap().put(
					NEDSSConstants.CLASS_CD_CASE, caeIdList);
			nbsDocumentDT.getEventIdMap().put(
					NEDSSConstants.CLASS_CD_CONTACT, contactRecordList);
			nbsDocumentDT.getEventIdMap().put(
					NEDSSConstants.MORBIDITY_CODE, morbIdList);
		} catch (Exception e) {
			String errString = "CdaMessageParser: parseEventId failed ectracting EventIds:  "
					+ e.getMessage();
			e.printStackTrace();
			logger.error(errString);
			throw new NEDSSSystemException(errString, e);
		}
	}
}
