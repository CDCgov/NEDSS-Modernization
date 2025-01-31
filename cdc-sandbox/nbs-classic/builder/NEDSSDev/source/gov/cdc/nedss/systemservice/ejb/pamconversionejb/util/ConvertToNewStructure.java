package gov.cdc.nedss.systemservice.ejb.pamconversionejb.util;

import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationHistoryManager;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationRootDAOImpl;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.ConfirmationMethodDAOImpl;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.ConfirmationMethodHistDAOImpl;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseHistDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ActRelationshipHistDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dao.StateDefinedFieldDataDAOImpl;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.nbsactentity.dao.NbsActEntityDAO;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.pam.dao.NbsCaseAnswerDAO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionLegacyDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.LegacyBlockDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionErrorDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMappingDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Name: ConvertToNewStructure.java Description: DAO Object for Pam answers.
 * Copyright: Copyright (c) 2008 
 * Company: Computer Sciences Corporation
 * 
 * @author Pradeep Sharma
 * @updated : 2010 by Pradeep Sharma
 */

public class ConvertToNewStructure extends DAOBase {
	static final LogUtils logger = new LogUtils((ConvertToNewStructure.class).getName());
	public static final String PublicHealthCaseDT = "PublicHealthCaseDT";
	public static final String ERROR = "ERROR";
	public static final String nbsCaseAnswerDTColl = "nbsCaseAnswerDTColl";
	public static final String batchEntrynbsCaseAnswerDTColl = "batchEntrynbsCaseAnswerDTColl";

	@SuppressWarnings("unchecked")
	public Map<Object,Object> convertToNewStructure(
			InvestigationProxyVO investigationProxyVO,
			Map<Object, Object> questionIdMap,
			PamConversionDAO pamConversionDAO, 
			NBSSecurityObj nbsSecurityObj)
			throws NEDSSAppException, CloneNotSupportedException, IOException,
			ClassNotFoundException {
		logger.debug("\n in convertToNewStructure for: " );
		logger.debug("phc_uid=" + investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getUid().toString() +"\n");
		Map<Object,Object> map = new HashMap<Object,Object>();
		InvestigationProxyVO investigationProxyVOOriginal = new InvestigationProxyVO();
		Map<Object,Object> batchEntryMap = new HashMap<Object,Object>();
		Map<Object,Object> obsToObsbatchEntryMap = new HashMap<Object,Object>();
		
		try {
			investigationProxyVOOriginal = (InvestigationProxyVO) investigationProxyVO
			.deepCopy();
		} catch (CloneNotSupportedException e1) {
			logger.error("The investigationProxyVO object canoot be casted"
					+ e1.toString());
			e1.printStackTrace();
		} catch (IOException e1) {
			logger.error("The investigationProxyVO object canoot be casted"
					+ e1.toString());
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			logger.error("The investigationProxyVO object canoot be casted"
					+ e1.toString());
			e1.printStackTrace();
		}
		if (investigationProxyVO.getPublicHealthCaseVO()
				.getThePublicHealthCaseDT() != null) {
			if (investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId() != null)
				logger.info("\n >>>>>>>>>> Conversion Performance- starting conversion processing for " +investigationProxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT().getLocalId() +"  at " +LocalDateTime.now());
			Map<Object,Object> pamDTMap = new HashMap<Object,Object>();
			Collection<Object>  nbsAnswerDTColl = new ArrayList<Object> ();
			Collection<Object>  nbsErrorDTColl = new ArrayList<Object> ();
			Map<Object, Object> nbsSNMCaseAnswerDTMap = new HashMap<Object, Object>();
			PersonVO updatePatientVO = null;
			PersonVO patientVO = getThePatient(investigationProxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT().getPublicHealthCaseUid(), nbsSecurityObj);
			logger.info("\n >>>>>>>>>> Conversion Performance- LDF processing  at " +LocalDateTime.now());
			
			
			if (investigationProxyVO.getTheStateDefinedFieldDataDTCollection() != null) {
				//For NBSCentral 9370 also add the Patient LDFs to the Investigation LDFs (12-15-2016)
				if (patientVO.getTheStateDefinedFieldDataDTCollection() != null && !patientVO.getTheStateDefinedFieldDataDTCollection().isEmpty())
					investigationProxyVO.getTheStateDefinedFieldDataDTCollection().addAll(patientVO.getTheStateDefinedFieldDataDTCollection());
				Iterator<Object> iter = investigationProxyVO
				.getTheStateDefinedFieldDataDTCollection().iterator();
				while (iter.hasNext()) {
					StateDefinedFieldDataDT stateDefinedFieldDataDT = (StateDefinedFieldDataDT) iter
					.next();
					Map<Object,Object> returnLDFMap = new HashMap<Object,Object>();
					try {
						returnLDFMap = convertLdfToPamAnswer(
								investigationProxyVO, stateDefinedFieldDataDT,nbsSNMCaseAnswerDTMap,questionIdMap, pamConversionDAO);
						if (returnLDFMap.get(ERROR) != null) {
							Collection<?>  coll = (ArrayList<?>) returnLDFMap
							.get(ERROR);
							if (coll != null && coll.size() > 0) {
								map.put(ERROR, coll);
								return map;
							}
						}
					} catch (NEDSSAppException e) {
						logger.error("Error thrown at convertLdfToPamAnswer "
								+ e.toString(), e);
						e.printStackTrace();
						throw new NEDSSAppException(e.toString(), e);
					}
					PublicHealthCaseDT publicHealthCaseDT = null;
					if (returnLDFMap.get(PublicHealthCaseDT) != null) {
						publicHealthCaseDT = (PublicHealthCaseDT) returnLDFMap
						.get(PublicHealthCaseDT);
						investigationProxyVO.getPublicHealthCaseVO()
						.setThePublicHealthCaseDT(publicHealthCaseDT);
					}
					if (returnLDFMap.get("nbsCaseAnswerDTColl") != null) {
						Collection<?>  nbsCaseAnswerDTColl = (ArrayList<?> ) returnLDFMap
						.get("nbsCaseAnswerDTColl");
						nbsAnswerDTColl.addAll(nbsCaseAnswerDTColl);
					}
				}
			}
			//Convert Transmission Mode if mapping exists
			try {
				Map<Object,Object> returnMap = convertTransissionModetoNBSCaseAnswer(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT(), nbsAnswerDTColl);
				if (returnMap.get(ERROR) != null) {
					Collection<?>  coll = (ArrayList<?>) returnMap
					.get(ERROR);
					if (coll != null && coll.size() > 0) {
						map.put(ERROR, coll);
						return map;
					}
				}
			} catch (NEDSSAppException e) {
				logger.error("Error thrown at convertLdfToPamAnswer "
						+ e.toString(), e);
				e.printStackTrace();
				throw new NEDSSAppException(e.toString(), e);
			}
			
			
			
			logger.info("\n >>>>>>>>>> Conversion Performance- Participant processing  at " +LocalDateTime.now());
			if (investigationProxyVO.getTheParticipationDTCollection() != null) {
				Iterator<Object> iter = investigationProxyVO
				.getTheParticipationDTCollection().iterator();
				Collection<Object>  coll = new ArrayList<Object> ();
				while (iter.hasNext()) {
					ParticipationDT participationDT = (ParticipationDT) iter
					.next();
					if (!participationDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.PHC_INVESTIGATOR)) {
						coll.add(participationDT);
						continue;
					}
					Map<Object,Object> returnLDFMap = setParticipationToAnswer(
							investigationProxyVO, participationDT);
					PublicHealthCaseDT publicHealthCaseDT = null;
					if (returnLDFMap.get(PublicHealthCaseDT) != null) {
						publicHealthCaseDT = (PublicHealthCaseDT) returnLDFMap
						.get(PublicHealthCaseDT);
						investigationProxyVO.getPublicHealthCaseVO()
						.setThePublicHealthCaseDT(publicHealthCaseDT);
					}
					if (returnLDFMap.get("nbsCaseAnswerDTColl") != null) {
						Collection<?>  nbsCaseAnswerDTColl = (ArrayList<?> ) returnLDFMap
						.get("nbsCaseAnswerDTColl");
						nbsAnswerDTColl.addAll(nbsCaseAnswerDTColl);
					}
					if (returnLDFMap.get("participationDT") != null) {
						ParticipationDT returnedparticipationDT = (ParticipationDT) returnLDFMap
						.get("participationDT");
						coll.add(returnedparticipationDT);
					}
				}
			}
			logger.info("\n >>>>>>>>>> Conversion Performance- Batch Observation processing  at " +LocalDateTime.now());
			Collection<Object> parentBatchObs=new ArrayList<Object>();
			int batchEntryGroupId= 0;
			Map batchEntryGroup= new HashMap();
			if (investigationProxyVO.getTheObservationVOCollection() != null) {
				Iterator<ObservationVO> iter = investigationProxyVO.getTheObservationVOCollection().iterator();
				while(iter.hasNext()){
					
					ObservationVO observationVO= (ObservationVO)iter.next();
					if(observationVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.ITEM_TO_ROW)){
						Long targetActUid=observationVO.getTheObservationDT().getObservationUid();
						if(batchEntryGroup.get(observationVO.getTheObservationDT().getCtrlCdDisplayForm())==null){
							batchEntryGroup.put(observationVO.getTheObservationDT().getCtrlCdDisplayForm(), new Integer(1));
							batchEntryGroupId=1;
						}else{
							batchEntryGroupId=(Integer)batchEntryGroup.get(observationVO.getTheObservationDT().getCtrlCdDisplayForm());
							++batchEntryGroupId;
							batchEntryGroup.put(observationVO.getTheObservationDT().getCtrlCdDisplayForm(), new Integer(batchEntryGroupId));
						}
						if(investigationProxyVO.getTheActRelationshipDTCollection()!=null){
							Iterator<Object> iterator = investigationProxyVO.getTheActRelationshipDTCollection().iterator();
							while(iterator.hasNext()){
								ActRelationshipDT actDT=(ActRelationshipDT)iterator.next();
								if(observationVO.getTheObservationDT().getObservationUid().compareTo(actDT.getTargetActUid())==0){
									Collection<Object> batchCollection = null;
									if(obsToObsbatchEntryMap.get(observationVO.getTheObservationDT().getCtrlCdDisplayForm()+""+batchEntryGroupId)==null){
										batchCollection= new ArrayList<Object>();
										LegacyBlockDT legacyBlockDT = new LegacyBlockDT();
										legacyBlockDT.setGroupSeqNbr(batchEntryGroupId);
										legacyBlockDT.setSourceActUid(actDT.getSourceActUid());
										legacyBlockDT.setTargetActUid(targetActUid);
										batchCollection.add(legacyBlockDT);
										obsToObsbatchEntryMap.put(observationVO.getTheObservationDT().getCtrlCdDisplayForm()+""+batchEntryGroupId, batchCollection);
									}else{
										batchCollection=(Collection<Object>)obsToObsbatchEntryMap.get(observationVO.getTheObservationDT().getCtrlCdDisplayForm()+""+batchEntryGroupId);
										LegacyBlockDT legacyBlockDT = new LegacyBlockDT();
										legacyBlockDT.setGroupSeqNbr(batchEntryGroupId);
										legacyBlockDT.setSourceActUid(actDT.getSourceActUid());
										legacyBlockDT.setTargetActUid(targetActUid);
										batchCollection.add(legacyBlockDT);
										obsToObsbatchEntryMap.put(observationVO.getTheObservationDT().getCtrlCdDisplayForm()+""+batchEntryGroupId, batchCollection);
									}
								}
								else{
									//do nothing!!!
								}
								
							}
						}
					}
				}
			}
			if(!obsToObsbatchEntryMap.isEmpty()){
				Set<Object> set = obsToObsbatchEntryMap.keySet();
				Iterator<Object> setIterator=set.iterator();
				while(setIterator.hasNext()){
					String cdDisplayForm=(String)setIterator.next();
					Collection<Object> coll = (Collection<Object>)obsToObsbatchEntryMap.get(cdDisplayForm);
					if(coll!=null){
						Iterator<Object> collIterator= coll.iterator();
						while(collIterator.hasNext()){
							LegacyBlockDT legacyBlockDT = (LegacyBlockDT)collIterator.next();
							batchEntryMap.put(legacyBlockDT.getSourceActUid(), legacyBlockDT);
						}
					}
				}
			}
			logger.info("\n >>>>>>>>>> >>>>>>>>>> Conversion Performance- Observation VO processing  at " +LocalDateTime.now());
			if (investigationProxyVO.getTheObservationVOCollection() != null) {
				//preprocess units
				preprocessObsVoForUnits(investigationProxyVO.getTheObservationVOCollection());
				// convertTheObservationObjects
				Iterator<ObservationVO> it = investigationProxyVO
				.getTheObservationVOCollection().iterator();
				if (investigationProxyVO.getTheObservationVOCollection() != null && !investigationProxyVO.getTheObservationVOCollection().isEmpty())
					logger.info("\n >>>>>>>>>> Conversion Performance- starting processing collection of " + investigationProxyVO.getTheObservationVOCollection().size() + " convertObsToPamCaseAnswer() at " +LocalDateTime.now());
				while (it.hasNext()) {
					ObservationVO observationVO = (ObservationVO) it.next();
					Map<Object,Object> returnObsMap = convertObsToPamCaseAnswer(patientVO, observationVO,
							investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT(), batchEntryMap, nbsSNMCaseAnswerDTMap);

					if (returnObsMap.get(ERROR) != null) {
						Collection<?>  coll = (ArrayList<?> ) returnObsMap.get(ERROR);
						if (coll != null && coll.size() > 0) {
							map.put(ERROR, coll);
							return map;
						}
					}
					if (returnObsMap.get(PublicHealthCaseDT) != null) {
						PublicHealthCaseDT publicHealthCaseDT = (PublicHealthCaseDT) returnObsMap
						.get(PublicHealthCaseDT);
						investigationProxyVO.getPublicHealthCaseVO()
						.setThePublicHealthCaseDT(publicHealthCaseDT);
					}
					if (returnObsMap.get("nbsCaseAnswerDTColl") != null) {
						Collection<?>  nbsCaseAnswerDTColl = (ArrayList<?> ) returnObsMap
						.get("nbsCaseAnswerDTColl");
						if (nbsCaseAnswerDTColl != null
								&& nbsCaseAnswerDTColl.size() > 0)
							nbsAnswerDTColl.addAll(nbsCaseAnswerDTColl);
					}
					if (returnObsMap.get("PatientVO") != null) {
						updatePatientVO = (PersonVO) returnObsMap
						.get("PatientVO");
					}
				}
				logger.info("\n >>>>>>>>>> Conversion Performance- completed processing collection of convertObsToPamCaseAnswer() at " +LocalDateTime.now());
			}
			logger.info("\n >>>>>>>>>> Conversion Performance- Structured Numeric and Multi-Sel processing  at " +LocalDateTime.now());
			if(!nbsSNMCaseAnswerDTMap.isEmpty()){
				logger.debug("\n calling convert to Structured Numeric or Multi Select structure.." );
				Collection<Object> returnList= this.convertToSNMStructure(nbsSNMCaseAnswerDTMap);
				nbsAnswerDTColl.addAll(returnList);
			}
			PublicHealthCaseDT 	publicHealthCaseDT =cleanPublicHealthCaseDT(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
			investigationProxyVO.getPublicHealthCaseVO().setThePublicHealthCaseDT(publicHealthCaseDT);
			/*Note: Starting with release 2.0.2 the confirmation methods and PHC values are retained and not migrated to NMB_CASE_ANSWERS */

			logger.info("\n >>>>>>>>>> Conversion Performance- History processing  at " +LocalDateTime.now());
			PublicHealthCaseHistDAOImpl publicHealthCaseHistDAOImpl = new PublicHealthCaseHistDAOImpl();
			investigationProxyVO.getPublicHealthCaseVO()
			.getThePublicHealthCaseDT().setRecordStatusCd("OPEN");
			logger.debug("\n storing original investigation into history.. " );
			publicHealthCaseHistDAOImpl.store(investigationProxyVOOriginal
					.getPublicHealthCaseVO().getThePublicHealthCaseDT());
			ActRelationshipHistDAOImpl ActRelationshipHistDAOImpl = new ActRelationshipHistDAOImpl();
			ActRelationshipHistDAOImpl.store(investigationProxyVOOriginal
					.getTheActRelationshipDTCollection());
			logger.info("\n >>>>>>>>>> Conversion Performance- Store data processing  at " +LocalDateTime.now());
			PublicHealthCaseDT publicHealthCaseDT2 = investigationProxyVO
			.getPublicHealthCaseVO().getThePublicHealthCaseDT();
			Integer versionCtrlNbr = new Integer(investigationProxyVO
					.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.getVersionCtrlNbr().intValue() + 1);
			PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
			publicHealthCaseVO.setThePublicHealthCaseDT(publicHealthCaseDT2);
			publicHealthCaseDT2.setVersionCtrlNbr(versionCtrlNbr);
			java.util.Date dateTime = new java.util.Date();
			Timestamp lastChgTime = new Timestamp(dateTime.getTime());

			map.put("investigationProxyVO", investigationProxyVO);
			publicHealthCaseDT2.setLastChgTime(lastChgTime);
			publicHealthCaseDT2.setRecordStatusTime(lastChgTime);
			publicHealthCaseDT2.setLastChgUserId(new Long(nbsSecurityObj
					.getEntryID()));
			investigationProxyVO.getPublicHealthCaseVO()
			.getThePublicHealthCaseDT().setItDirty(true);
			PublicHealthCaseDAOImpl publicHealthCaseDAOImpl = new PublicHealthCaseDAOImpl();
			logger.debug("\n storing updated public_health_case... " );
			try {
				publicHealthCaseDAOImpl.store(publicHealthCaseVO
						.getThePublicHealthCaseDT());
			} catch (NEDSSSystemException e) {
				logger.error("Error thrown at convertToNewStructure "
						+ e.toString());
				e.printStackTrace();
				throw new NEDSSSystemException(e.toString(), e);
			} catch (NEDSSConcurrentDataException e) {
				logger.error("Error thrown at convertToNewStructure "
						+ e.toString(), e);
				throw new NEDSSSystemException(e.toString(), e);
			}

			NbsCaseAnswerDAO nbsAnswerDAO = new NbsCaseAnswerDAO();
			Collection<Object> dedupAnswerColl = removeDuplicateAnswers(nbsAnswerDTColl);
			pamDTMap.put("PAMANSWERCOLL", dedupAnswerColl);

			investigationProxyVO.getPublicHealthCaseVO()
			.getThePublicHealthCaseDT().setRecordStatusCd("OPEN");
			nbsAnswerDAO.storePamAnswerDTCollection(dedupAnswerColl,
					investigationProxyVO.getPublicHealthCaseVO());
			PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
			pamConversionLegacyDAO
			.updatePublicHealthCaseDT(investigationProxyVO
					.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT());
			if (updatePatientVO != null)
				pamConversionLegacyDAO
				.setPerson(updatePatientVO, nbsSecurityObj);
			// setActRelationshipLogDel(investigationProxyVO.getTheActRelationshipDTCollection());
			ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
			if (investigationProxyVO.getTheActRelationshipDTCollection() != null) {
				Iterator<Object> iterAct = investigationProxyVO
				.getTheActRelationshipDTCollection().iterator();
				while (iterAct.hasNext()) {
					ActRelationshipDT actRelationshipdt = (ActRelationshipDT) iterAct
					.next();
					actRelationshipDAOImpl.remove(actRelationshipdt);
				}
			}
			Collection<Object>  nbsActEntityDTColl = new ArrayList<Object> ();
			if (investigationProxyVO.getTheParticipationDTCollection() != null) {
				logger.debug("\n storing act_entities.act_entities..");
				Iterator<Object> iterator = investigationProxyVO
				.getTheParticipationDTCollection().iterator();
				while (iterator.hasNext()) {
					ParticipationDT partDT = (ParticipationDT) iterator.next();
					NbsActEntityDT nbsActEntityDT = new NbsActEntityDT();

					nbsActEntityDT.setActUid(partDT.getActUid());
					nbsActEntityDT.setAddTime(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getAddTime());
					nbsActEntityDT.setAddUserId(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getAddUserId());
					nbsActEntityDT.setEntityUid(partDT.getSubjectEntityUid());
					nbsActEntityDT.setEntityVersionCtrlNbr(new Integer(1));
					nbsActEntityDT.setLastChgTime(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getLastChgTime());
					nbsActEntityDT.setLastChgUserId(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getLastChgUserId());
					nbsActEntityDT.setRecordStatusCd(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getRecordStatusCd());
					nbsActEntityDT.setRecordStatusTime(partDT
							.getRecordStatusTime());
					nbsActEntityDT.setTypeCd(partDT.getTypeCd());
					nbsActEntityDTColl.add(nbsActEntityDT);

				}
				NbsActEntityDAO nbsCaseEntityDAO = new NbsActEntityDAO();
				nbsCaseEntityDAO.storeActEntityDTCollection(
						nbsActEntityDTColl, investigationProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT());
			}

			ConfirmationMethodDAOImpl confirmationMethodDAOImpl = new ConfirmationMethodDAOImpl();
			Collection<Object>  confirmMethodColl =investigationProxyVO.getPublicHealthCaseVO().getTheConfirmationMethodDTCollection();
			confirmationMethodDAOImpl.store(confirmMethodColl);
			/*Deprecated Rel 2.0.2 confirmationMethodDAOImpl.remove(investigationProxyVOOriginal
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getPublicHealthCaseUid());
			 */
			if (investigationProxyVOOriginal.getPublicHealthCaseVO()
					.getTheConfirmationMethodDTCollection() != null) {
				logger.debug("\n updating confirmation_method_hist... " );
				ConfirmationMethodHistDAOImpl confirmationMethodHistDAOImpl = new ConfirmationMethodHistDAOImpl(
						investigationProxyVOOriginal
						.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT()
						.getVersionCtrlNbr().intValue());

				confirmationMethodHistDAOImpl
				.store(investigationProxyVOOriginal
						.getPublicHealthCaseVO()
						.getTheConfirmationMethodDTCollection());
			}

			ParticipationHistDAOImpl participationHistDAOImpl = new ParticipationHistDAOImpl();
			participationHistDAOImpl.store(investigationProxyVOOriginal
					.getTheParticipationDTCollection());

			ParticipationDAOImpl participationDAOImpl = new ParticipationDAOImpl();
			if (investigationProxyVO.getTheParticipationDTCollection() != null) {
				logger.debug("\n updating participations.. " );
				Iterator<Object> it = investigationProxyVO
				.getTheParticipationDTCollection().iterator();
				while (it.hasNext()) {
					ParticipationDT root = (ParticipationDT) it.next();
					root.setItDirty(true);
					root.setItNew(false);
					// root.setRecordStatusCd(newPublicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusCd());
					root.setLastChgTime(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getLastChgTime());
					root.setLastChgUserId(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getLastChgUserId());
					participationDAOImpl.store(root);
				}
			}
			logger.info("\n >>>>>>>>>> Conversion Performance- Log Del old data  at " +LocalDateTime.now());
			ObservationRootDAOImpl observationRootDAOImpl = new ObservationRootDAOImpl();
			ObservationHistoryManager observationHistoryManager = new ObservationHistoryManager();
			if (investigationProxyVO.getTheObservationVOCollection() != null) {
				logger.debug("\n logically deleting observations.." );
				Iterator<ObservationVO> it = investigationProxyVOOriginal
				.getTheObservationVOCollection().iterator();
				while (it.hasNext()) {
					ObservationVO obsVO = (ObservationVO) it.next();
					ObservationVO observationVO = new ObservationVO();
					obsVO.setItDirty(true);
					obsVO.setItNew(false);
					ObservationDT obsDT = obsVO.getTheObservationDT();
					obsDT.setItDirty(true);
					obsDT.setItNew(false);
					obsDT.setRecordStatusCd(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getRecordStatusCd());
					obsDT.setLastChgTime(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getLastChgTime());
					obsDT.setLastChgUserId(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getLastChgUserId());
					obsDT.setRecordStatusCd("LOG_DEL");
					obsDT.setVersionCtrlNbr(new Integer(obsDT
							.getVersionCtrlNbr().intValue() + 1));
					obsDT.setRecordStatusCd(investigationProxyVO
							.getPublicHealthCaseVO().getThePublicHealthCaseDT()
							.getRecordStatusCd());
					obsDT.setLastChgTime(publicHealthCaseDT2.getLastChgTime());
					obsDT.setRecordStatusTime(publicHealthCaseDT2
							.getRecordStatusTime());
					observationVO.setTheObservationDT(obsDT);
					ObservationVO newObservationVO = (ObservationVO) observationVO
					.deepCopy();
					observationVO.getTheObservationDT().setRecordStatusCd(
							"LOG_DEL");
					try {
						observationRootDAOImpl.store(observationVO);
					} catch (NEDSSSystemException e) {
						logger
						.error("NEDSSSystemException thrown at convertToNewStructure "
								+ e.toString());
						throw new NEDSSSystemException(e.toString(), e);
					} catch (NEDSSConcurrentDataException e) {
						logger
						.error("NEDSSConcurrentDataException thrown at convertToNewStructure "
								+ e.toString());
						throw new NEDSSConcurrentDataException(e.toString(), e);
					}
					observationHistoryManager.store(newObservationVO);
				}
			}

			StateDefinedFieldDataDAOImpl StateDefinedFieldDataDAOImpl = new StateDefinedFieldDataDAOImpl();
			StateDefinedFieldDataDAOImpl
			.removeLDFForPamConversion(investigationProxyVO
					.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT()
					.getPublicHealthCaseUid());
			map.put(ERROR, nbsErrorDTColl);
			map.put("investigationProxyVO", investigationProxyVO);
			if (investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId() != null)
				logger.info("\n >>>>>>>>>> Conversion Performance- Completed processing for " +investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId() + " at " +LocalDateTime.now());
			return map;
		} else {
			throw new NEDSSAppException("The publicHealthCaseDT object is null");

		}
	}



	public void removeMetadata(String conditionCd,Timestamp recordStatusTime ) {
		PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
		//pamConversionLegacyDAO.removeMetaDataByConditionCd(conditionCd, recordStatusTime);
	}
	public void updateConditionCode(String conditionCd,WaTemplateDT waTemplateDT, Timestamp recordStatusTime ) {
		PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
		pamConversionLegacyDAO.updateConditionCode(conditionCd, waTemplateDT,recordStatusTime);
	}

	private Map<Object,Object> convertLdfToPamAnswer(
			
			InvestigationProxyVO investigationProxyVO,
			StateDefinedFieldDataDT stateDefinedFieldDataDT,
			Map nbsSNMCaseAnswerDTMap, 
			Map<Object, Object> questionIdMap, 
			PamConversionDAO pamConversionDAO)
			throws NEDSSAppException {
		String conditionCode=investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		Collection<Object>  nbsCaseAnswerDTColl= new ArrayList<Object> ();
		Collection<Object>  nbsConversionErrorDTColl = new ArrayList<Object> ();
		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		int i = 0;
		if(stateDefinedFieldDataDT.getFieldSize()!=null && stateDefinedFieldDataDT.getFieldSize().trim().length()>0 && stateDefinedFieldDataDT.getDataType()!=null && stateDefinedFieldDataDT.getDataType().equalsIgnoreCase("CV")){
			i=1;	
		}

		if (stateDefinedFieldDataDT.getCodeSetNm() != null) {

			if (stateDefinedFieldDataDT.getLdfValue().indexOf("|") > 0) {
				StringTokenizer token = new StringTokenizer(
						stateDefinedFieldDataDT.getLdfValue(), "|", false);
				int n = 0;
				int j=0;
				while (token.hasMoreElements()) {
					String ldfTokenValue = token.nextElement().toString();
					System.out.println("" + ++n + ": " + ldfTokenValue);
					String key2 = stateDefinedFieldDataDT.getLdfUid()
					.toString().trim();
					if (questionIdMap.get(key2) != null) {
						Iterator ite = ((Collection)questionIdMap.get(key2)).iterator();
						while(ite.hasNext()){
							NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT)ite.next();

						if (nbsConversionMapperrDT.getToDbLocation().indexOf(
								"PUBLIC_HEALTH_CASE") != -1
								|| nbsConversionMapperrDT.getToDbLocation()
								.indexOf("public_health_case") != -1) {
							PublicHealthCaseDT publicHealthCaseDT = setPublicHealthCaseDTCoded(
									investigationProxyVO
									.getPublicHealthCaseVO()
									.getThePublicHealthCaseDT(),
									stateDefinedFieldDataDT.getLdfValue(),
									nbsConversionMapperrDT);
							returnMap.put(PublicHealthCaseDT,
									publicHealthCaseDT);
						} else {
							NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();

							if (pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
											.getToQuestionId(), conditionCode) == null) {
								NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
										nbsConversionMapperrDT
										.getConditionCdGroupId(),
										nbsConversionMapperrDT
										.getNbsConversionMappingUid(),
										nbsConversionMapperrDT
										.getFromQuestionId(),
										nbsConversionMapperrDT
										.getToQuestionId());
								nbsConversionErrorDTColl
								.add(nbsConversionErrorDT);
								returnMap.put(ERROR,
										nbsConversionErrorDTColl);
								return returnMap;
							} else {
								nbsCaseAnswerDT.setAnswerTxt(ldfTokenValue);
								NbsUiMetadataDT nbsUiMetadataDT = pamConversionDAO
								.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
										.getToQuestionId(), conditionCode);
								nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDT.getBlockIdNbr());
								nbsCaseAnswerDT.setType(nbsConversionMapperrDT.getToDataType());
								nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDT.getOtherInd());
								Long componentUid=nbsUiMetadataDT.getNbsUiComponentUid();
								if(componentUid.compareTo(new Long(1007)) == 0){
									j=0;
								}else if(componentUid.compareTo(new Long(1013)) == 0){
									j=j+1;
								}
								nbsCaseAnswerDT = setStandardNBSCaseAnswerVals(
										investigationProxyVO
										.getPublicHealthCaseVO()
										.getThePublicHealthCaseDT(),
										nbsCaseAnswerDT, j,nbsUiMetadataDT );
								if((nbsConversionMapperrDT.getUnitInd()!=null && nbsConversionMapperrDT.getUnitInd().trim().equalsIgnoreCase("T"))||
									(nbsConversionMapperrDT.getUnitTypeCd()!=null && nbsConversionMapperrDT.getUnitTypeCd().trim().length()>0)||
										(nbsConversionMapperrDT.getOtherInd()!=null && nbsConversionMapperrDT.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE))){
									Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDT.getToQuestionId());
									if(collection==null){
										collection = new ArrayList();
									}
									nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDT.getToQuestionId());
									collection.add(nbsCaseAnswerDT);
									nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDT.getToQuestionId(), collection);
								}
								else
									nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
							}
						}
					}
						} else {
						String key = stateDefinedFieldDataDT.getLdfUid()
						.toString().trim()
						+ ldfTokenValue.trim();

						if (questionIdMap.get(key) != null) {
							Iterator ite =  ((Collection)questionIdMap.get(key)).iterator();
							while(ite.hasNext()){
								NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT)ite.next();
							if (nbsConversionMapperrDT.getToDbLocation()
									.indexOf("PUBLIC_HEALTH_CASE") != -1
									|| nbsConversionMapperrDT.getToDbLocation()
									.indexOf("public_health_case") != -1) {
								PublicHealthCaseDT publicHealthCaseDT = setPublicHealthCaseDTCoded(
										investigationProxyVO
										.getPublicHealthCaseVO()
										.getThePublicHealthCaseDT(),
										stateDefinedFieldDataDT.getLdfValue(),
										nbsConversionMapperrDT);
								returnMap.put(PublicHealthCaseDT,
										publicHealthCaseDT);
							} else {

								if (pamConversionDAO
										.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
												.getToQuestionId(), conditionCode) == null) {
									NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
											nbsConversionMapperrDT
											.getConditionCdGroupId(),
											nbsConversionMapperrDT
											.getNbsConversionMappingUid(),
											nbsConversionMapperrDT
											.getFromQuestionId(),
											nbsConversionMapperrDT
											.getToQuestionId());
									nbsConversionErrorDTColl
									.add(nbsConversionErrorDT);
									returnMap.put(ERROR,
											nbsConversionErrorDTColl);
									return returnMap;
								} else {
									NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
									nbsCaseAnswerDT
									.setAnswerTxt(nbsConversionMapperrDT
											.getToCode());
									NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
											.getToQuestionId(), conditionCode);

									nbsCaseAnswerDT.setType(nbsConversionMapperrDT.getToDataType());
									nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDT.getOtherInd());
									nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDT.getBlockIdNbr());
									Long componentUid=nbsUiMetadataDT.getNbsUiComponentUid();
									
									if(componentUid.compareTo(new Long(1007)) == 0){
										j=0;
									}else if(componentUid.compareTo(new Long(1013)) == 0){
										j=j+1;
									}
									nbsCaseAnswerDT = setStandardNBSCaseAnswerVals(
											investigationProxyVO
											.getPublicHealthCaseVO()
											.getThePublicHealthCaseDT(),
											nbsCaseAnswerDT, j, nbsUiMetadataDT);
									if((nbsConversionMapperrDT.getUnitInd()!=null && nbsConversionMapperrDT.getUnitInd().trim().equalsIgnoreCase("T"))||
										(nbsConversionMapperrDT.getUnitTypeCd()!=null && nbsConversionMapperrDT.getUnitTypeCd().trim().length()>0)||
											(nbsConversionMapperrDT.getOtherInd()!=null && nbsConversionMapperrDT.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE))){
										Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDT.getToQuestionId());
										if(collection==null){
											collection = new ArrayList();
										}
										nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDT.getToQuestionId());

										collection.add(nbsCaseAnswerDT);
										nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDT.getToQuestionId(), collection);
									}
									else
										nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
								}
							}
						}
					}
				}
				}
			} else {
				String key = stateDefinedFieldDataDT.getLdfUid().toString()
				.trim()
				+ stateDefinedFieldDataDT.getLdfValue().trim();
				String key2 = stateDefinedFieldDataDT.getLdfUid().toString()
				.trim();
				int j=0;
				if (questionIdMap.get(key) != null) {
					Iterator ite = ((Collection)questionIdMap.get(key)).iterator();
					while(ite.hasNext()){
						NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT)ite.next();

					if (nbsConversionMapperrDT.getToDbLocation().indexOf(
							"PUBLIC_HEALTH_CASE") != -1
							|| nbsConversionMapperrDT.getToDbLocation()
							.indexOf("public_health_case") != -1) {
						PublicHealthCaseDT publicHealthCaseDT = setPublicHealthCaseDTCoded(
								investigationProxyVO.getPublicHealthCaseVO()
								.getThePublicHealthCaseDT(),
								stateDefinedFieldDataDT.getLdfValue(),
								nbsConversionMapperrDT);
						returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
					} else {
						NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
						nbsCaseAnswerDT.setAnswerTxt(nbsConversionMapperrDT
								.getToCode());

						if (pamConversionDAO
								.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
										.getToQuestionId(), conditionCode) == null) {
							NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
									nbsConversionMapperrDT
									.getConditionCdGroupId(),
									nbsConversionMapperrDT
									.getNbsConversionMappingUid(),
									nbsConversionMapperrDT
									.getFromQuestionId(),
									nbsConversionMapperrDT
									.getToQuestionId());
							nbsConversionErrorDTColl
							.add(nbsConversionErrorDT);
							returnMap.put(ERROR, nbsConversionErrorDTColl);
							return returnMap;
						} else {
							NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
							.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
									.getToQuestionId(), conditionCode);
							nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDT.getBlockIdNbr());
							Long componentUid=nbsUiMetadataDT.getNbsUiComponentUid();
							
							if(componentUid.compareTo(new Long(1007)) == 0){
								j=0;
							}else if(componentUid.compareTo(new Long(1013)) == 0){
								j=j+1;
							}
							nbsCaseAnswerDT = setStandardNBSCaseAnswerVals(
									investigationProxyVO
									.getPublicHealthCaseVO()
									.getThePublicHealthCaseDT(),
									nbsCaseAnswerDT,j,nbsUiMetadataDT);
							if((nbsConversionMapperrDT.getUnitInd()!=null && nbsConversionMapperrDT.getUnitInd().trim().equalsIgnoreCase("T"))||
								(nbsConversionMapperrDT.getUnitTypeCd()!=null && nbsConversionMapperrDT.getUnitTypeCd().trim().length()>0)||
									(nbsConversionMapperrDT.getOtherInd()!=null && nbsConversionMapperrDT.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE))){
								Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDT.getToQuestionId());
								if(collection==null){
									collection = new ArrayList();
								}
								nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDT.getOtherInd());
								nbsCaseAnswerDT.setType(nbsConversionMapperrDT.getToDataType());
								nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDT.getToQuestionId());
								collection.add(nbsCaseAnswerDT);
								nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDT.getToQuestionId(), collection);
							}
							else
								nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
						}
					}

				} 
				} if (questionIdMap.get(key2) != null) {
					Iterator ite = ((Collection)questionIdMap.get(key2)).iterator();
					while(ite.hasNext()){
						NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT)ite.next();
					if (nbsConversionMapperrDT.getToDbLocation().indexOf(
							"PUBLIC_HEALTH_CASE") != -1
							|| nbsConversionMapperrDT.getToDbLocation()
							.indexOf("public_health_case") != -1) {
						PublicHealthCaseDT publicHealthCaseDT = setPublicHealthCaseDTCoded(
								investigationProxyVO.getPublicHealthCaseVO()
								.getThePublicHealthCaseDT(),
								stateDefinedFieldDataDT.getLdfValue(),
								nbsConversionMapperrDT);
						returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
					} else {
						NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
						nbsCaseAnswerDT.setAnswerTxt(stateDefinedFieldDataDT
								.getLdfValue());

						if (pamConversionDAO
								.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
										.getToQuestionId(), conditionCode) == null) {
							NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
									nbsConversionMapperrDT
									.getConditionCdGroupId(),
									nbsConversionMapperrDT
									.getNbsConversionMappingUid(),
									nbsConversionMapperrDT
									.getFromQuestionId(),
									nbsConversionMapperrDT
									.getToQuestionId());
							nbsConversionErrorDTColl
							.add(nbsConversionErrorDT);
							returnMap.put(ERROR, nbsConversionErrorDTColl);
							return returnMap;
						} else {
							NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
							.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
									.getToQuestionId(), conditionCode);
							nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDT.getBlockIdNbr());
							nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDT.getOtherInd());
							Long componentUid=nbsUiMetadataDT.getNbsUiComponentUid();
							if(componentUid.compareTo(new Long(1007)) == 0){
								j=0;
							}else if(componentUid.compareTo(new Long(1013)) == 0){
								j=j+1;
							}
							nbsCaseAnswerDT = setStandardNBSCaseAnswerVals(
									investigationProxyVO
									.getPublicHealthCaseVO()
									.getThePublicHealthCaseDT(),
									nbsCaseAnswerDT,j,  nbsUiMetadataDT);
							if((nbsConversionMapperrDT.getUnitInd()!=null && nbsConversionMapperrDT.getUnitInd().trim().equalsIgnoreCase("T"))||
								(nbsConversionMapperrDT.getUnitTypeCd()!=null && nbsConversionMapperrDT.getUnitTypeCd().trim().length()>0)||
									(nbsConversionMapperrDT.getOtherInd()!=null && nbsConversionMapperrDT.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE))){
								Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDT.getToQuestionId());
								if(collection==null){
									collection = new ArrayList();
								}
								nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDT.getToQuestionId());
								nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDT.getOtherInd());
								nbsCaseAnswerDT.setType(nbsConversionMapperrDT.getToDataType());
								collection.add(nbsCaseAnswerDT);
								nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDT.getToQuestionId(), collection);
							}
							else
								nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
						}
					}

				}
			}
		} 
		}else {

			if (questionIdMap.get(stateDefinedFieldDataDT.getLdfUid()
					.toString().trim()) == null)
				return new HashMap<Object,Object>();
			Iterator ite = ((Collection)questionIdMap.get(stateDefinedFieldDataDT.getLdfUid().toString().trim())).iterator();
			while(ite.hasNext()){
				NBSConversionMappingDT nbsConversionMapperrDT = (NBSConversionMappingDT)ite.next();

			if (nbsConversionMapperrDT.getToDbLocation().indexOf(
					"PUBLIC_HEALTH_CASE") != -1
					|| nbsConversionMapperrDT.getToDbLocation().indexOf(
					"public_health_case") != -1) {
				PublicHealthCaseDT publicHealthCaseDT = setPublicHealthCaseDTCoded(
						investigationProxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT(),
						stateDefinedFieldDataDT.getLdfValue(),
						nbsConversionMapperrDT);
				returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
			} else {
				if (pamConversionDAO
						.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
								.getToQuestionId(), conditionCode) == null) {

					NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
							nbsConversionMapperrDT
							.getConditionCdGroupId(),
							nbsConversionMapperrDT
							.getNbsConversionMappingUid(),
							nbsConversionMapperrDT.getFromQuestionId(),
							nbsConversionMapperrDT.getToQuestionId());
					nbsConversionErrorDTColl.add(nbsConversionErrorDT);
					returnMap.put(ERROR, nbsConversionErrorDTColl);
					return returnMap;
				} else {
					NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
					nbsCaseAnswerDT.setAnswerTxt(stateDefinedFieldDataDT
							.getLdfValue());
					NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
					.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT
							.getToQuestionId(), conditionCode);
					nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDT.getBlockIdNbr());
					Long componentUid=nbsUiMetadataDT.getNbsUiComponentUid();
					int j=0;
					if(componentUid.compareTo(new Long(1007)) == 0){
						j=0;
					}else if(componentUid.compareTo(new Long(1013)) == 0){
						j=1;
					}
					nbsCaseAnswerDT = setStandardNBSCaseAnswerVals(
							investigationProxyVO.getPublicHealthCaseVO()
							.getThePublicHealthCaseDT(),
							nbsCaseAnswerDT,j, nbsUiMetadataDT);
					if((nbsConversionMapperrDT.getUnitInd()!=null && nbsConversionMapperrDT.getUnitInd().trim().equalsIgnoreCase("T"))||
							(nbsConversionMapperrDT.getUnitTypeCd()!=null && nbsConversionMapperrDT.getUnitTypeCd().trim().length()>0)||
							(nbsConversionMapperrDT.getOtherInd()!=null && nbsConversionMapperrDT.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE))){
						Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDT.getToQuestionId());
						if(collection==null){
							collection = new ArrayList();
						}
						nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDT.getOtherInd());
						nbsCaseAnswerDT.setType(nbsConversionMapperrDT.getToDataType());
						nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDT.getToQuestionId());
						collection.add(nbsCaseAnswerDT);
						nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDT.getToQuestionId(), collection);
					}
					else
						nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
				}
			}
		}
		}
		returnMap.put(ERROR, nbsConversionErrorDTColl);
		/*if(!nbsSNMCaseAnswerDTMap.isEmpty()){
			Collection<Object> returnList= this.convertToSNMStructure(nbsSNMCaseAnswerDTMap);
			nbsCaseAnswerDTColl.addAll(returnList);
		}*/
		returnMap.put("nbsCaseAnswerDTColl", nbsCaseAnswerDTColl);

		return returnMap;
	}

	/*DEPRECATED: in Release 2.0.2 the confirmation methods are retained and not migrated to NMB_CASE_ANSWERS
	 * private void convertObsToPHC(PublicHealthCaseDT publicHealthCaseDT,
	 * boolean isPrerun){
	 *  }
	 */

	@SuppressWarnings("unused")
	private Map<Object,Object> convertConfirmationMethodToPamAnswer(
			InvestigationProxyVO investigationProxyVO)
			throws NEDSSAppException {
		String conditionCode=investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		ArrayList<Object> nbsConversionErrorDTColl = new ArrayList<Object> ();
		ArrayList<Object> nbsCaseAnswerDTColl = new ArrayList<Object> ();
		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		int i = 1;
		PamConversionDAO pamConversionDAO = new PamConversionDAO();
		if (investigationProxyVO.getPublicHealthCaseVO()
				.getTheConfirmationMethodDTCollection() != null
				&& investigationProxyVO.getPublicHealthCaseVO()
				.getTheConfirmationMethodDTCollection().size() > 0) {
			Iterator<Object> it = investigationProxyVO.getPublicHealthCaseVO()
			.getTheConfirmationMethodDTCollection().iterator();
			HashMap<Object,Object> questionIdMap = (HashMap<Object,Object>) pamConversionDAO
			.getCachedQuestionIdMap(conditionCode);
			NBSConversionMappingDT nbsConversionMapperrDT161 = null;
			NBSConversionMappingDT nbsConversionMapperrDT161Coded = null;
			NBSConversionMappingDT nbsConversionMapperrDT162 = null;

			String toQuestionId = null;
			String fromQuestionId = "";
			Long mappingUid= null;
			Long mappingGroupCd = null;
			boolean hasTimeAnswerDTCreated = false;
			while (it.hasNext()) {
				boolean coded = false;
				NbsUiMetadataDT nbsnbsUiMetadataDT = null;
				ConfirmationMethodDT confirmationMethodDT = (ConfirmationMethodDT) it
				.next();
				if (questionIdMap.get("INV161") != null && ((Collection)questionIdMap.get("INV161")).size()>0
						&& confirmationMethodDT.getConfirmationMethodCd() != null)
					nbsConversionMapperrDT161 = (NBSConversionMappingDT) ((Collection)questionIdMap
					.get("INV161")).iterator().next();
				if (questionIdMap.get("INV162") != null && ((Collection)questionIdMap.get("INV161")).size()>0)
					nbsConversionMapperrDT162 = (NBSConversionMappingDT) ((Collection)questionIdMap
					.get("INV162")).iterator().next();
				String cd = confirmationMethodDT.getConfirmationMethodCd();
				Timestamp methodTime = confirmationMethodDT
				.getConfirmationMethodTime();

				if (cd != null) {
					try {
						if (confirmationMethodDT.getConfirmationMethodCd() != null
								&& questionIdMap.get("INV161"
										+ confirmationMethodDT
										.getConfirmationMethodCd()
										.trim()) != null)
							nbsConversionMapperrDT161Coded = (NBSConversionMappingDT) ((Collection)questionIdMap
							.get("INV161"
									+ confirmationMethodDT
									.getConfirmationMethodCd()
									.trim())).iterator().next();

						if (nbsConversionMapperrDT161 == null
								&& nbsConversionMapperrDT162 == null
								&& nbsConversionMapperrDT161Coded == null)
							continue;

						if (nbsConversionMapperrDT161 != null
								|| nbsConversionMapperrDT161Coded != null) {
							coded = true;
						}

						if (coded && !confirmationMethodDT.getConfirmationMethodCd().equalsIgnoreCase("NA")) {
							if (nbsConversionMapperrDT161 != null
									&& pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT161
											.getToQuestionId(), conditionCode) != null) {
								nbsnbsUiMetadataDT = pamConversionDAO
								.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT161
										.getToQuestionId(), conditionCode);
								toQuestionId = nbsConversionMapperrDT161
								.getToQuestionId();
								fromQuestionId = nbsConversionMapperrDT161
								.getFromQuestionId();
								mappingUid = nbsConversionMapperrDT161
								.getNbsConversionMappingUid();
								mappingGroupCd=nbsConversionMapperrDT161.getConditionCdGroupId();
							}
							if (nbsConversionMapperrDT161Coded != null
									&& pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT161Coded
											.getToQuestionId(), conditionCode) != null) {
								nbsnbsUiMetadataDT = pamConversionDAO
								.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT161Coded
										.getToQuestionId(), conditionCode);
								toQuestionId = nbsConversionMapperrDT161Coded
								.getToQuestionId();
								fromQuestionId = nbsConversionMapperrDT161Coded
								.getFromQuestionId();
								mappingUid = nbsConversionMapperrDT161Coded.getNbsConversionMappingUid();
								mappingGroupCd=nbsConversionMapperrDT161Coded.getConditionCdGroupId();
							}
							if (nbsnbsUiMetadataDT == null ) {
								NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(mappingGroupCd,
										mappingUid, fromQuestionId,
										toQuestionId);
								nbsConversionErrorDTColl
								.add(nbsConversionErrorDT);
							} else if (nbsnbsUiMetadataDT == null) {
								NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(mappingGroupCd,
										mappingUid, fromQuestionId,
										toQuestionId);
								nbsConversionErrorDTColl
								.add(nbsConversionErrorDT);
								returnMap.put(ERROR, nbsConversionErrorDTColl);
								return returnMap;
							} else {
								NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
								if (nbsConversionMapperrDT161Coded != null
										&& nbsConversionMapperrDT161Coded
										.getTranslationRequiredInd()
										.equalsIgnoreCase("Y")) {
									nbsCaseAnswerDT
									.setAnswerTxt(nbsConversionMapperrDT161Coded
											.getToCode());
									NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT161Coded
											.getToQuestionId(), conditionCode);

									nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDT161Coded.getBlockIdNbr());
									setStandardNBSCaseAnswerVals(
											investigationProxyVO
											.getPublicHealthCaseVO()
											.getThePublicHealthCaseDT(),
											nbsCaseAnswerDT, i, nbsUiMetadataDT);
								} else if (nbsConversionMapperrDT161 != null
										&& nbsConversionMapperrDT161
										.getTranslationRequiredInd()
										.equalsIgnoreCase("N")) {
									nbsCaseAnswerDT
									.setAnswerTxt(confirmationMethodDT
											.getConfirmationMethodCd());
									NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT161
											.getToQuestionId(), conditionCode);

									nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDT161.getBlockIdNbr());
									setStandardNBSCaseAnswerVals(
											investigationProxyVO
											.getPublicHealthCaseVO()
											.getThePublicHealthCaseDT(),
											nbsCaseAnswerDT,i, nbsUiMetadataDT);
								}
							}
						}
						if (nbsConversionMapperrDT162 != null
								&& methodTime != null
								&& hasTimeAnswerDTCreated == false) {
							hasTimeAnswerDTCreated = true;
							toQuestionId = nbsConversionMapperrDT162
							.getToQuestionId();
							fromQuestionId = nbsConversionMapperrDT162
							.getFromQuestionId();
							mappingUid = nbsConversionMapperrDT162
							.getNbsConversionMappingUid();
							mappingGroupCd=nbsConversionMapperrDT162.getConditionCdGroupId();
							nbsnbsUiMetadataDT = pamConversionDAO
							.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT162
									.getToQuestionId(), conditionCode);
							if (nbsnbsUiMetadataDT == null ) {
								NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(mappingGroupCd,
										mappingUid, fromQuestionId,
										toQuestionId);
								nbsConversionErrorDTColl
								.add(nbsConversionErrorDT);
							} else if (nbsnbsUiMetadataDT == null) {
								NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(mappingGroupCd,
										mappingUid, fromQuestionId,
										toQuestionId);
								nbsConversionErrorDTColl
								.add(nbsConversionErrorDT);
								returnMap.put(ERROR, nbsConversionErrorDTColl);
								return returnMap;
							}

							NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
							nbsCaseAnswerDT.setAnswerTxt(StringUtils
									.formatDate(confirmationMethodDT
											.getConfirmationMethodTime()));
							NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
							.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDT162
									.getToQuestionId(), conditionCode);
							setStandardNBSCaseAnswerVals(investigationProxyVO
									.getPublicHealthCaseVO()
									.getThePublicHealthCaseDT(),
									nbsCaseAnswerDT, i, nbsUiMetadataDT);
							nbsCaseAnswerDT.setSeqNbr(new Integer(0));
							nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
						}
					} catch (Exception ex) {
						NBSConversionErrorDT nBSConversionErrorDT = new NBSConversionErrorDT();
						nBSConversionErrorDT
						.setErrorCd(ConverterConstants.mappingError);
						nBSConversionErrorDT
						.setErrorMessageTxt("Mapping does not exsist for questionId"
								+ fromQuestionId
								+ " For Code"
								+ confirmationMethodDT
								.getConfirmationMethodCd()
								+ " For confirmation Code "
								+ confirmationMethodDT
								.getConfirmationMethodCd());
						nbsConversionErrorDTColl.add(nBSConversionErrorDT);
						returnMap.put(ERROR, nbsConversionErrorDTColl);
						return returnMap;
					}

				}
			}

		}

		returnMap.put(ERROR, nbsConversionErrorDTColl);
		returnMap.put("nbsCaseAnswerDTColl", nbsCaseAnswerDTColl);
		return returnMap;
	}

	@SuppressWarnings("unused")
	private Map<Object,Object> convertObsToPamCaseAnswer(PersonVO patientVO, ObservationVO observationVO,
			PublicHealthCaseDT publicHealthCaseDT, Map batchEntryMap, Map nbsSNMCaseAnswerDTMap) {
		logger.debug("\n in convertObsToPamCaseAnswer for: " );
		logger.debug("\n observation_uid= " + observationVO.getTheObservationDT().getObservationUid().toString() + "\n");
	
		String conditionCode=publicHealthCaseDT.getCd();
		HashMap<Object,Object> returnMap = new HashMap<Object,Object>();
		Collection<Object>  nbsCaseAnswerDTColl = new ArrayList<Object> ();
		Collection<Object>  nbsConversionErrorDTColl = new ArrayList<Object> ();
		PamConversionDAO pamConversionDAO = new PamConversionDAO();
		HashMap<Object,Object> questionIdMap = (HashMap<Object,Object>) pamConversionDAO
		.getCachedQuestionIdMap(conditionCode);
		Collection nbsConversionMapperrDTWOTranslationList =  new ArrayList();
		Collection nbsConversionMapperrDTWithranslationList =  new ArrayList();
		if (questionIdMap.get(observationVO.getTheObservationDT().getCd()) != null) {
			nbsConversionMapperrDTWOTranslationList = (Collection) questionIdMap
			.get(observationVO.getTheObservationDT().getCd());
		}

		if (questionIdMap != null) {
			Collection<Object>  observationCodedDTColl = observationVO
			.getTheObsValueCodedDTCollection();
			if (observationCodedDTColl != null) {
				Iterator<Object> it = observationCodedDTColl.iterator();
				int i = 0;
				while (it.hasNext()) {
					ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) it
					.next();
					try {
						if (questionIdMap.get(observationVO
								.getTheObservationDT().getCd()
								+ obsValueCodedDT.getCode().trim()) != null)
							nbsConversionMapperrDTWithranslationList = (Collection) questionIdMap
							.get(observationVO.getTheObservationDT()
									.getCd()
									+ obsValueCodedDT.getCode().trim());

						if ((nbsConversionMapperrDTWOTranslationList == null || nbsConversionMapperrDTWOTranslationList.size()==0)
								&& (nbsConversionMapperrDTWithranslationList == null || nbsConversionMapperrDTWithranslationList.size()==0))
							continue;

						if ((nbsConversionMapperrDTWOTranslationList != null && nbsConversionMapperrDTWOTranslationList.size()>0)
								|| (nbsConversionMapperrDTWithranslationList != null && nbsConversionMapperrDTWithranslationList.size()>0)) {
							NbsUiMetadataDT nbsUiMetadata = null;
							String toQuestionId = null;
							String fromQuestionId = null;
							Long mappingUid = null;
							Long mappingGroupCd= null;

							if (nbsConversionMapperrDTWOTranslationList != null && nbsConversionMapperrDTWOTranslationList.size()>0) {
								Iterator ite = nbsConversionMapperrDTWOTranslationList.iterator();
								while(ite.hasNext()){
									NBSConversionMappingDT nbsConversionMapperrDTWOTranslation = (NBSConversionMappingDT)ite.next();
								if (pamConversionDAO
										.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
												.getToQuestionId(), conditionCode) != null)
									nbsUiMetadata = pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
											.getToQuestionId(), conditionCode);
								if (nbsUiMetadata != null && nbsUiMetadata.getNbsUiComponentUid() != null && 
										nbsUiMetadata.getNbsUiComponentUid().longValue() == NEDSSConstants.MULTI_SELECT_CD)
										i = i+1; //multiselects seq nbr starts with 1
								toQuestionId = nbsConversionMapperrDTWOTranslation
								.getToQuestionId();
								fromQuestionId = nbsConversionMapperrDTWOTranslation
								.getFromQuestionId();
								mappingUid = nbsConversionMapperrDTWOTranslation
								.getNbsConversionMappingUid();
								}
							} else if (nbsConversionMapperrDTWithranslationList != null) {
								Iterator ite = nbsConversionMapperrDTWithranslationList.iterator();
								while(ite.hasNext()){
									NBSConversionMappingDT nbsConversionMapperrDTWithranslation = (NBSConversionMappingDT)ite.next();
								if (pamConversionDAO
										.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWithranslation
												.getToQuestionId(), conditionCode) != null)
									nbsUiMetadata = pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWithranslation
											.getToQuestionId(), conditionCode);
								if (nbsUiMetadata != null && nbsUiMetadata.getNbsUiComponentUid() != null && 
										nbsUiMetadata.getNbsUiComponentUid().longValue() == NEDSSConstants.MULTI_SELECT_CD)
										i = i+1; //multiselects seq nbr starts with 1
								toQuestionId = nbsConversionMapperrDTWithranslation
								.getToQuestionId();
								fromQuestionId = nbsConversionMapperrDTWithranslation
								.getFromQuestionId();
								mappingUid = nbsConversionMapperrDTWithranslation
								.getNbsConversionMappingUid();
								mappingGroupCd=nbsConversionMapperrDTWithranslation.getConditionCdGroupId();
								}
							} else {
								NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(mappingGroupCd,
										mappingUid, fromQuestionId,
										toQuestionId);
								nbsConversionErrorDTColl
								.add(nbsConversionErrorDT);
								returnMap.put(ERROR,
										nbsConversionErrorDTColl);
								return returnMap;
							}
							boolean conversionDone=false;
							if (nbsConversionMapperrDTWOTranslationList != null &&  nbsConversionMapperrDTWOTranslationList.size()>0) {
								Iterator ite = nbsConversionMapperrDTWOTranslationList.iterator();
								while (ite.hasNext()) {
									NBSConversionMappingDT nbsConversionMapperrDTWOTranslation = (NBSConversionMappingDT) ite.next();

									if (nbsConversionMapperrDTWOTranslation
											.getToDbLocation().indexOf(
													"PUBLIC_HEALTH_CASE") != -1
											|| nbsConversionMapperrDTWOTranslation
													.getToDbLocation()
													.indexOf(
															"public_health_case") != -1) {
										publicHealthCaseDT = setPublicHealthCaseDTCoded(
												publicHealthCaseDT,
												obsValueCodedDT.getCode(),
												nbsConversionMapperrDTWOTranslation);
										returnMap.put(PublicHealthCaseDT,
												publicHealthCaseDT);
										conversionDone=true;
									}
								}
							} if (!conversionDone && nbsConversionMapperrDTWithranslationList != null && nbsConversionMapperrDTWithranslationList.size()>0) {
								Iterator ite = nbsConversionMapperrDTWithranslationList.iterator();
								while (ite.hasNext()) {
									NBSConversionMappingDT nbsConversionMapperrDTWithTranslation = (NBSConversionMappingDT) ite.next();

									if (nbsConversionMapperrDTWithTranslation
											.getToDbLocation().indexOf(
													"PUBLIC_HEALTH_CASE") != -1
											|| nbsConversionMapperrDTWithTranslation
													.getToDbLocation()
													.indexOf(
															"public_health_case") != -1) {
										publicHealthCaseDT = setPublicHealthCaseDTCoded(
												publicHealthCaseDT,
												obsValueCodedDT.getCode(),
												nbsConversionMapperrDTWithTranslation);
										returnMap.put(PublicHealthCaseDT,
												publicHealthCaseDT);
										conversionDone=true;
									}
								}
							} if (!conversionDone && nbsConversionMapperrDTWithranslationList != null && nbsConversionMapperrDTWithranslationList.size()>0) {
								Iterator ite = nbsConversionMapperrDTWithranslationList.iterator();
								while (ite.hasNext()) {
									NBSConversionMappingDT nbsConversionMapperrDTWithTranslation = (NBSConversionMappingDT) ite.next();
									if (nbsConversionMapperrDTWithTranslation
											.getToDbLocation().indexOf(
													"POSTAL_LOCATOR") != -1
											|| nbsConversionMapperrDTWithTranslation
													.getToDbLocation().indexOf(
															"postal_locator") != -1) {
										Boolean patientUpdated = setEntityLocatorParticipationDTCoded(
												patientVO,
												nbsConversionMapperrDTWithTranslation);
										if (patientUpdated)
											returnMap.put("PatientVO",
													patientVO);
										conversionDone=true;
									}
								}
							} if(!conversionDone) {
								
								if (nbsConversionMapperrDTWithranslationList != null && nbsConversionMapperrDTWithranslationList.size()>0) {
									Iterator ite = nbsConversionMapperrDTWithranslationList.iterator();
									while (ite.hasNext()) {
									NBSConversionMappingDT nbsConversionMapperrDTWithTranslation = (NBSConversionMappingDT) ite.next();
									NbsUiMetadataDT nbsUiMetadataDT  = null;
									NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
									nbsCaseAnswerDT
									.setAnswerTxt(nbsConversionMapperrDTWithTranslation
											.getToCode());
									nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDTWithTranslation.getBlockIdNbr());
									nbsUiMetadataDT = pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWithTranslation
											.getToQuestionId(), conditionCode);
									setStandardNBSCaseAnswerVals(
											publicHealthCaseDT, nbsCaseAnswerDT, i, nbsUiMetadataDT);
									if((batchEntryMap!=null && (batchEntryMap.get(obsValueCodedDT.getObservationUid())!=null))|| nbsConversionMapperrDTWithTranslation.getOtherInd()!=null && nbsConversionMapperrDTWithTranslation.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE)){
										Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDTWithTranslation.getToQuestionId());
										if(collection==null){
											collection = new ArrayList();
										}
										LegacyBlockDT legacyBlockDT= (LegacyBlockDT)batchEntryMap.get(observationVO.getTheObservationDT().getObservationUid());
										if(legacyBlockDT!=null)
											nbsCaseAnswerDT.setAnswerGroupSeqNbr(legacyBlockDT.getGroupSeqNbr());
										nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDTWithTranslation.getOtherInd());
										nbsCaseAnswerDT.setType(nbsConversionMapperrDTWithTranslation.getToDataType());
										collection.add(nbsCaseAnswerDT);
										nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDTWithTranslation.getToQuestionId());
										nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDTWithTranslation.getToQuestionId(), collection);
									}
									else
										nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
								}
								}
								if (nbsConversionMapperrDTWOTranslationList != null && nbsConversionMapperrDTWOTranslationList.size()>0) {
									Iterator ite = nbsConversionMapperrDTWOTranslationList.iterator();
									while (ite.hasNext()) {
									NBSConversionMappingDT nbsConversionMapperrDTWOTranslation = (NBSConversionMappingDT) ite.next();
									NbsUiMetadataDT nbsUiMetadataDT  = null;
									NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
									nbsCaseAnswerDT
									.setAnswerTxt(obsValueCodedDT
											.getCode());
									nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDTWOTranslation.getBlockIdNbr());
									nbsUiMetadataDT = pamConversionDAO
									.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
											.getToQuestionId(), conditionCode);
									setStandardNBSCaseAnswerVals(
											publicHealthCaseDT, nbsCaseAnswerDT, i, nbsUiMetadataDT);
									if((batchEntryMap!=null && (batchEntryMap.get(obsValueCodedDT.getObservationUid())!=null)) || nbsConversionMapperrDTWOTranslation.getOtherInd()!=null && nbsConversionMapperrDTWOTranslation.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE)){
										Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDTWOTranslation.getToQuestionId());
										if(collection==null){
											collection = new ArrayList();
										}
										collection.add(nbsCaseAnswerDT);
										LegacyBlockDT legacyBlockDT= (LegacyBlockDT)batchEntryMap.get(observationVO.getTheObservationDT().getObservationUid());
										if(legacyBlockDT!=null)
											nbsCaseAnswerDT.setAnswerGroupSeqNbr(legacyBlockDT.getGroupSeqNbr());
										nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDTWOTranslation.getOtherInd());
										nbsCaseAnswerDT.setType(nbsConversionMapperrDTWOTranslation.getToDataType());
										nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDTWOTranslation.getToQuestionId());
										nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDTWOTranslation.getToQuestionId(), collection);
								
									}
									else
										nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);

									if(nbsCaseAnswerDT.getAnswerGroupSeqNbr()==null || nbsCaseAnswerDT.getAnswerGroupSeqNbr()<1)
										nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDTWOTranslation.getBlockIdNbr());
								}
								}
								//Next line is redundant..
								//A coded question (if present) can set another question on the legacy page
								//Added for HEP246 to set the Y on VAC126
								if (questionIdMap.get(observationVO.getTheObservationDT().getCd()+"fixed") != null) {
									Collection nbsConversionMapperrDTFixedList = (Collection) questionIdMap.get(observationVO.getTheObservationDT().getCd()+"fixed");
									if(nbsConversionMapperrDTFixedList!=null && nbsConversionMapperrDTFixedList.size()>0){
										Iterator ite = nbsConversionMapperrDTFixedList.iterator();
										while(ite.hasNext()){
											NBSConversionMappingDT nbsConversionMapperFixedField = (NBSConversionMappingDT)ite.next();
											NbsCaseAnswerDT nbsFixedAnswerDT = new NbsCaseAnswerDT();
											nbsFixedAnswerDT.setAnswerTxt(nbsConversionMapperFixedField.getToCode());
											NbsUiMetadataDT fixedUiMetadataDT  = pamConversionDAO
													.getNBSUiMetadtaForIdentifier(nbsConversionMapperFixedField
															.getToQuestionId(), conditionCode);
											setStandardNBSCaseAnswerVals(publicHealthCaseDT,
													nbsFixedAnswerDT, 0, fixedUiMetadataDT);
											nbsCaseAnswerDTColl.add(nbsFixedAnswerDT);
										}
									}
								}
							}
						}
					} catch (Exception ex) {
						logger.debug("\n exception in convertObsToPamCaseAnswer - mapping not found... ");
						NBSConversionErrorDT nBSConversionErrorDT = new NBSConversionErrorDT();
						nBSConversionErrorDT
						.setErrorCd(ConverterConstants.mappingError);
						nBSConversionErrorDT
						.setErrorMessageTxt("Mapping does not exsist for questionId"
								+ questionIdMap.get(obsValueCodedDT
										.getCode())
										+ " For Code"
										+ obsValueCodedDT.getCode()
										+ " For observationUid "
										+ obsValueCodedDT.getObservationUid());
						nbsConversionErrorDTColl.add(nBSConversionErrorDT);
						returnMap.put(ERROR, nbsConversionErrorDTColl);
						return returnMap;
					}

				}
			}
		}
		if (nbsConversionMapperrDTWOTranslationList == null || nbsConversionMapperrDTWOTranslationList.size()==0) {
			//gt - fixed bug was just return empty returnmap
			if (nbsCaseAnswerDTColl != null && nbsCaseAnswerDTColl.size() > 0)
				returnMap.put("nbsCaseAnswerDTColl", nbsCaseAnswerDTColl);
			if (nbsSNMCaseAnswerDTMap != null && nbsSNMCaseAnswerDTMap.size() > 0)
				returnMap.put("nbsSNMCaseAnswerDTMap", nbsSNMCaseAnswerDTMap);
			//if no mapping found, put warning in log
			if (nbsConversionMapperrDTWithranslationList == null || nbsConversionMapperrDTWithranslationList.size()==0) {
				String theCode = observationVO.getTheObservationDT().getCd();
				if (theCode != null) {
					logger.warn("Warning: No mapping exists for legacy question identifier " +theCode);
				}
			}
			
			return returnMap;
		}
		if (observationVO.getTheObsValueTxtDTCollection() != null
				&& observationVO.getTheObsValueTxtDTCollection().size() > 1) {
			Collection<Object>  observationValueTxtDTColl = observationVO
			.getTheObsValueTxtDTCollection();
			NBSConversionErrorDT nBSConversionErrorDT = new NBSConversionErrorDT();
			nBSConversionErrorDT.setErrorCd(ConverterConstants.mappingError);
			nBSConversionErrorDT.setErrorMessageTxt("For ObservationUid "
					+ observationVO.getTheObservationDT().getObservationUid()
					+ " There exisits " + observationValueTxtDTColl.size()
					+ "ObsValueTxtDTs");
			nbsConversionErrorDTColl.add(nBSConversionErrorDT);
			returnMap.put(ERROR, nbsConversionErrorDTColl);
			return returnMap;
		}
		if (observationVO.getTheObsValueTxtDTCollection() != null) {
			Iterator<Object> it = observationVO.getTheObsValueTxtDTCollection()
			.iterator();
			while (it.hasNext()) {
				ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) it.next();
				Iterator ite = nbsConversionMapperrDTWOTranslationList.iterator();
				while (ite.hasNext()) {
				NBSConversionMappingDT nbsConversionMapperrDTWOTranslation = (NBSConversionMappingDT) ite.next();
				if (pamConversionDAO
						.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
								.getToQuestionId(), conditionCode) == null) {
					NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
							nbsConversionMapperrDTWOTranslation
							.getConditionCdGroupId(),
							nbsConversionMapperrDTWOTranslation
							.getNbsConversionMappingUid(),
							nbsConversionMapperrDTWOTranslation
							.getFromQuestionId(),
							nbsConversionMapperrDTWOTranslation
							.getToQuestionId());
					nbsConversionErrorDTColl.add(nbsConversionErrorDT);
					returnMap.put(ERROR, nbsConversionErrorDTColl);
					return returnMap;
				}

				
				if (nbsConversionMapperrDTWOTranslation.getToDbLocation() != null
						&& (nbsConversionMapperrDTWOTranslation
						.getToDbLocation().indexOf("PUBLIC_HEALTH_CASE") != -1 || 
						nbsConversionMapperrDTWOTranslation.getToDbLocation()
						.indexOf("public_health_case") != -1)) {
					publicHealthCaseDT = setPublicHealthCaseDTText(
							publicHealthCaseDT, obsValueTxtDT,
							nbsConversionMapperrDTWOTranslation);
					returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
				} else {
					NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
					nbsCaseAnswerDT.setAnswerTxt(obsValueTxtDT.getValueTxt());

					NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
					.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
							.getToQuestionId(), conditionCode);

					nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDTWOTranslation.getBlockIdNbr());
					setStandardNBSCaseAnswerVals(publicHealthCaseDT,
							nbsCaseAnswerDT,0, nbsUiMetadataDT);
					if((batchEntryMap!=null && (batchEntryMap.get(obsValueTxtDT.getObservationUid())!=null)) || nbsConversionMapperrDTWOTranslation.getOtherInd()!=null && nbsConversionMapperrDTWOTranslation.getOtherInd().equalsIgnoreCase(NEDSSConstants.TRUE)){
						Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDTWOTranslation.getToQuestionId());
						if(collection==null){
							collection = new ArrayList();
						}
						nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDTWOTranslation.getOtherInd());
						nbsCaseAnswerDT.setType(nbsConversionMapperrDTWOTranslation.getToDataType());
						LegacyBlockDT legacyBlockDT= (LegacyBlockDT)batchEntryMap.get(observationVO.getTheObservationDT().getObservationUid());
						if(legacyBlockDT!=null)
							nbsCaseAnswerDT.setAnswerGroupSeqNbr(legacyBlockDT.getGroupSeqNbr());
						collection.add(nbsCaseAnswerDT);
						nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDTWOTranslation.getToQuestionId());
						nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDTWOTranslation.getToQuestionId(), collection);
					}
					else
						nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
				}
			}
		}
		}

		Collection<Object>  observationDateDTColl = observationVO
		.getTheObsValueDateDTCollection();
		if (observationVO.getTheObsValueDateDTCollection() != null
				&& observationVO.getTheObsValueDateDTCollection().size() > 1) {
			NBSConversionErrorDT nBSConversionErrorDT = new NBSConversionErrorDT();
			nBSConversionErrorDT.setErrorMessageTxt("For ObservationUid "
					+ observationVO.getTheObservationDT().getObservationUid()
					+ " There exisits " + observationDateDTColl.size()
					+ "ObsDateDTs");
			returnMap.put(ERROR, nBSConversionErrorDT);
			logger.debug("\n leaving convertObsToPamCaseAnswer()" );
			return returnMap;
		}

		if (observationVO.getTheObsValueDateDTCollection() != null) {
			Iterator<Object> it = observationDateDTColl.iterator();
			while (it.hasNext()) {
				ObsValueDateDT obsValueDateDT = (ObsValueDateDT) it.next();
				Iterator ite = nbsConversionMapperrDTWOTranslationList.iterator();
				while (ite.hasNext()) {
				NBSConversionMappingDT nbsConversionMapperrDTWOTranslation = (NBSConversionMappingDT) ite.next();
				if (nbsConversionMapperrDTWOTranslation.getToDbLocation() != null
						&& (nbsConversionMapperrDTWOTranslation
						.getToDbLocation()
						.indexOf("PUBLIC_HEALTH_CASE") != -1 || nbsConversionMapperrDTWOTranslation
						.getToDbLocation()
						.indexOf("public_health_case") != -1 )) {
					publicHealthCaseDT = setPublicHealthCaseDTDate(
							publicHealthCaseDT, obsValueDateDT.getFromTime(),
							nbsConversionMapperrDTWOTranslation);
					returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
				} else {
					NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
					nbsCaseAnswerDT.setAnswerTxt(StringUtils
							.formatDate(obsValueDateDT.getFromTime()));

					NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
					.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
							.getToQuestionId(), conditionCode);
					nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDTWOTranslation.getBlockIdNbr());
					setStandardNBSCaseAnswerVals(publicHealthCaseDT,
							nbsCaseAnswerDT, 0,nbsUiMetadataDT);
					if(batchEntryMap!=null && (batchEntryMap.get(obsValueDateDT.getObservationUid())!=null)){
						Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDTWOTranslation.getToQuestionId());
						if(collection==null){
							collection = new ArrayList();
						}
						LegacyBlockDT legacyBlockDT= (LegacyBlockDT)batchEntryMap.get(observationVO.getTheObservationDT().getObservationUid());
						nbsCaseAnswerDT.setAnswerGroupSeqNbr(legacyBlockDT.getGroupSeqNbr());
						nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDTWOTranslation.getOtherInd());
						nbsCaseAnswerDT.setType(nbsConversionMapperrDTWOTranslation.getToDataType());
						collection.add(nbsCaseAnswerDT);
						nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDTWOTranslation.getToQuestionId());
						nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDTWOTranslation.getToQuestionId(), collection);
					}
					else
						nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
					//The presence of a date field can set another fixed case answer field
					//added for Hep Perinatel VAC120
					if (questionIdMap.get(observationVO.getTheObservationDT().getCd()+"fixed") != null) {
						Collection nbsConversionMapperrDTFixedList = (Collection) questionIdMap.get(observationVO.getTheObservationDT().getCd()+"fixed");
						if(nbsConversionMapperrDTFixedList!=null && nbsConversionMapperrDTFixedList.size()>0){
							Iterator ite1 = nbsConversionMapperrDTFixedList.iterator();
							while(ite1.hasNext()){
								NBSConversionMappingDT nbsConversionMapperFixedValue = (NBSConversionMappingDT)ite1.next();
								NbsCaseAnswerDT nbsFixedAnswerDT = new NbsCaseAnswerDT();
								nbsFixedAnswerDT.setAnswerTxt(nbsConversionMapperFixedValue.getToCode());
								NbsUiMetadataDT fixedUiMetadataDT  = pamConversionDAO
										.getNBSUiMetadtaForIdentifier(nbsConversionMapperFixedValue
												.getToQuestionId(), conditionCode);
								nbsFixedAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperFixedValue.getBlockIdNbr());
								setStandardNBSCaseAnswerVals(publicHealthCaseDT,
										nbsFixedAnswerDT, 0, fixedUiMetadataDT);
								nbsCaseAnswerDTColl.add(nbsFixedAnswerDT);
							}
						}
					}
				}
			}
		}
		}

		Collection<Object>  observationNumericDTColl = observationVO
		.getTheObsValueNumericDTCollection();
		if (observationVO.getTheObsValueNumericDTCollection() != null
				&& observationVO.getTheObsValueNumericDTCollection().size() > 1) {
			NBSConversionErrorDT nBSConversionErrorDT = new NBSConversionErrorDT();
			nBSConversionErrorDT.setErrorMessageTxt("For ObservationUid "
					+ observationVO.getTheObservationDT().getObservationUid()
					+ " There exisits " + observationNumericDTColl.size()
					+ "ObsNumericDTs");
			nbsConversionErrorDTColl.add(nBSConversionErrorDT);
			returnMap.put(ERROR, nbsConversionErrorDTColl);
			return returnMap;
		}
		if (observationVO.getTheObsValueNumericDTCollection() != null) {
			Iterator<Object> it = observationNumericDTColl.iterator();
			while (it.hasNext()) {
				
				ObsValueNumericDT obsNumericDT = (ObsValueNumericDT) it.next();
				Iterator ite = nbsConversionMapperrDTWOTranslationList.iterator();
				while (ite.hasNext()) {
				NBSConversionMappingDT nbsConversionMapperrDTWOTranslation = (NBSConversionMappingDT) ite.next();
				if (pamConversionDAO
						.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
								.getToQuestionId(), conditionCode) == null) {
					NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
							nbsConversionMapperrDTWOTranslation
							.getConditionCdGroupId(),
							nbsConversionMapperrDTWOTranslation
							.getNbsConversionMappingUid(),
							nbsConversionMapperrDTWOTranslation
							.getFromQuestionId(),
							nbsConversionMapperrDTWOTranslation
							.getToQuestionId());
					nbsConversionErrorDTColl.add(nbsConversionErrorDT);
					returnMap.put(ERROR, nbsConversionErrorDTColl);
					return returnMap;
				}
				if (nbsConversionMapperrDTWOTranslation.getToDbLocation() != null
						&& (nbsConversionMapperrDTWOTranslation
						.getToDbLocation()
						.indexOf("PUBLIC_HEALTH_CASE") != -1 ||
						nbsConversionMapperrDTWOTranslation
						.getToDbLocation()
						.indexOf("public_health_case") != -1)) {
					publicHealthCaseDT = setPublicHealthCaseDTNumeric(
							publicHealthCaseDT, obsNumericDT,
							nbsConversionMapperrDTWOTranslation);
					returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
				} else {
					NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
					String StringDecimal= obsNumericDT.getNumericValue1()+"";
					String StringDecimal2="";
					if(StringDecimal.indexOf(".0")>0)
						StringDecimal2= StringDecimal.substring(0, StringDecimal.indexOf(".0"));
					if(StringDecimal2.equalsIgnoreCase(obsNumericDT.getNumericValue1().intValue()+""))
						nbsCaseAnswerDT.setAnswerTxt(obsNumericDT.getNumericValue1().intValue()+"");
					else
						nbsCaseAnswerDT.setAnswerTxt(obsNumericDT.getNumericValue1().toString());
						
					NbsUiMetadataDT nbsUiMetadataDT  = pamConversionDAO
					.getNBSUiMetadtaForIdentifier(nbsConversionMapperrDTWOTranslation
							.getToQuestionId(), conditionCode);
					nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMapperrDTWOTranslation.getBlockIdNbr());
					setStandardNBSCaseAnswerVals(publicHealthCaseDT,
							nbsCaseAnswerDT, 0, nbsUiMetadataDT);
					if(batchEntryMap!=null && (batchEntryMap.get(obsNumericDT.getObservationUid())!=null)){
						Collection<Object> collection = (Collection<Object>)nbsSNMCaseAnswerDTMap.get(nbsConversionMapperrDTWOTranslation.getToQuestionId());
						if(collection==null){
							collection = new ArrayList();
						}
						LegacyBlockDT legacyBlockDT= (LegacyBlockDT)batchEntryMap.get(observationVO.getTheObservationDT().getObservationUid());
						nbsCaseAnswerDT.setAnswerGroupSeqNbr(legacyBlockDT.getGroupSeqNbr());
						nbsCaseAnswerDT.setOtherType(nbsConversionMapperrDTWOTranslation.getOtherInd());
						nbsCaseAnswerDT.setType(nbsConversionMapperrDTWOTranslation.getToDataType());
						collection.add(nbsCaseAnswerDT);
						nbsCaseAnswerDT.setQuestionIdentifier(nbsConversionMapperrDTWOTranslation.getToQuestionId());
						nbsSNMCaseAnswerDTMap.put(nbsConversionMapperrDTWOTranslation.getToQuestionId(), collection);
					}
					else
						nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);

				}
			}
		}
		}
		returnMap.put(ERROR, nbsConversionErrorDTColl);
		returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
		if (nbsCaseAnswerDTColl != null && nbsCaseAnswerDTColl.size() > 0)
			returnMap.put("nbsCaseAnswerDTColl", nbsCaseAnswerDTColl);
		if (nbsSNMCaseAnswerDTMap != null && nbsSNMCaseAnswerDTMap.size() > 0)
			returnMap.put("nbsSNMCaseAnswerDTMap", nbsSNMCaseAnswerDTMap);

		logger.debug("\n leaving convertObsToPamCaseAnswer()" );
		
		return returnMap;
	}


	private NbsCaseAnswerDT setStandardNBSCaseAnswerVals(
			PublicHealthCaseDT publicHealthCaseDT,
			NbsCaseAnswerDT nbsCaseAnswerDT, int i, NbsUiMetadataDT nbsUiMetadataDT) {

		nbsCaseAnswerDT.setActUid(publicHealthCaseDT.getPublicHealthCaseUid());
		nbsCaseAnswerDT.setAddTime(publicHealthCaseDT.getAddTime());
		nbsCaseAnswerDT.setLastChgTime(publicHealthCaseDT.getLastChgTime());
		nbsCaseAnswerDT.setAddUserId(publicHealthCaseDT.getAddUserId());
		nbsCaseAnswerDT.setLastChgUserId(publicHealthCaseDT.getLastChgUserId());
		nbsCaseAnswerDT.setLastChgUserId(publicHealthCaseDT.getLastChgUserId());
		nbsCaseAnswerDT.setRecordStatusCd("OPEN");
		if (nbsCaseAnswerDT.getSeqNbr() != null
				&& nbsCaseAnswerDT.getSeqNbr().intValue() < 0)
			nbsCaseAnswerDT.setSeqNbr(new Integer(0));
		nbsCaseAnswerDT.setRecordStatusTime(publicHealthCaseDT
				.getRecordStatusTime());

		nbsCaseAnswerDT.setNbsQuestionUid(nbsUiMetadataDT
				.getNbsQuestionUid());
		nbsCaseAnswerDT
		.setNbsQuestionVersionCtrlNbr(nbsUiMetadataDT
				.getVersionCtrlNbr());
		if((nbsUiMetadataDT.getUnitTypeCd()!=null) && (nbsUiMetadataDT.getUnitTypeCd().trim().length()>0)){
			String answerTxt=nbsCaseAnswerDT.getAnswerTxt();
			nbsCaseAnswerDT.setAnswerTxt(answerTxt);
		}
		nbsCaseAnswerDT.setSeqNbr(new Integer(i));
		nbsCaseAnswerDT.setItNew(true);


		if (nbsCaseAnswerDT.getNbsQuestionUid() == null) {
			logger.error("There is no question identifier");
		}
		return nbsCaseAnswerDT;
	}
	

	private PublicHealthCaseDT setPublicHealthCaseDTime(
			PublicHealthCaseDT publicHealthCaseDT, String location,
			ObsValueCodedDT obsValueCodedDT) {
		if (location.equalsIgnoreCase("investigator_assigned_time")) {
		}
		if (location.equalsIgnoreCase("hospitalized_admin_time")) {
		}
		if (location.equalsIgnoreCase("hospitalized_discharge_time")) {
		}
		return publicHealthCaseDT;
	}

	private PublicHealthCaseDT setPublicHealthCaseDTCoded(
			PublicHealthCaseDT publicHealthCaseDT, String cd,
			NBSConversionMappingDT nbsConversionMapperrDT) {
		logger.debug("\n in setPublicHealthCaseDTCoded()" );
		String toDBLocation = nbsConversionMapperrDT.getToDbLocation();
		if (toDBLocation != null)
			toDBLocation = toDBLocation.toUpperCase();

		if (toDBLocation.indexOf("HOSPITALIZED_IND_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setHospitalizedIndCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setHospitalizedIndCd(cd);

		} else if (toDBLocation.indexOf("PREGNANT_IND_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setPregnantIndCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setPregnantIndCd(cd);
		} else if (toDBLocation.indexOf("DAY_CARE_IND_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setDayCareIndCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setDayCareIndCd(cd);
		} else if (toDBLocation.indexOf("FOOD_HANDLER_IND_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setFoodHandlerIndCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setFoodHandlerIndCd(cd);
		} else if (toDBLocation.indexOf("IMPORTED_COUNTRY_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setImportedCountryCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setImportedCountryCd(cd);
		} else if (toDBLocation.indexOf("IMPORTED_STATE_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setImportedStateCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setImportedStateCd(cd);
		} else if (toDBLocation.indexOf("IMPORTED_COUNTY_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setImportedCountyCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setImportedCountyCd(cd);
		}else if (toDBLocation.indexOf("OUTCOME_CD") > 0) {
			if (nbsConversionMapperrDT != null
					&& nbsConversionMapperrDT.getTranslationRequiredInd()
					.equalsIgnoreCase("Y"))
				publicHealthCaseDT.setOutcomeCd(nbsConversionMapperrDT
						.getToCode());
			else
				publicHealthCaseDT.setOutcomeCd(cd);
		}
		
		logger.debug("\n leaving setPublicHealthCaseDTCoded()" );
		return publicHealthCaseDT;
	}

	private PublicHealthCaseDT setPublicHealthCaseDTText(
			PublicHealthCaseDT publicHealthCaseDT, ObsValueTxtDT obsValueTxtDT,
			NBSConversionMappingDT nbsConversionMapperrDT) {
		logger.debug("\n in setPublicHealthCaseDTText()");
		String toDBLocation = nbsConversionMapperrDT.getToDbLocation();
		if (toDBLocation != null)
			toDBLocation = toDBLocation.toUpperCase();
		if (toDBLocation.indexOf("IMPORTED_CITY_DESC_TXT") > 0) {
			publicHealthCaseDT.setImportedCityDescTxt(obsValueTxtDT
					.getValueTxt());
		}
		return publicHealthCaseDT;
	}

	private PublicHealthCaseDT setPublicHealthCaseDTNumeric(
			PublicHealthCaseDT publicHealthCaseDT,
			ObsValueNumericDT obsValueNumericDT,
			NBSConversionMappingDT nbsConversionMapperrDT) {
		logger.debug("\n in setPublicHealthCaseDTNumeric()" );
		String toDBLocation = nbsConversionMapperrDT.getToDbLocation();
		if (toDBLocation != null)
			toDBLocation = toDBLocation.toUpperCase();
		if (toDBLocation.indexOf("HOSPITALIZED_DURATION_AMT") > 0) {
			publicHealthCaseDT.setHospitalizedDurationAmt(obsValueNumericDT
					.getNumericValue1());
		}
		return publicHealthCaseDT;
	}

	private PublicHealthCaseDT setPublicHealthCaseDTDate(
			PublicHealthCaseDT publicHealthCaseDT, Timestamp time,
			NBSConversionMappingDT nbsConversionMapperrDT) {
		logger.debug("\n in setPublicHealthCaseDTDate()" );
		String toDBLocation = nbsConversionMapperrDT.getToDbLocation();
		if (toDBLocation != null)
			toDBLocation = toDBLocation.toUpperCase();
		if (toDBLocation.indexOf("INVESTIGATOR_ASSIGNED_TIME") > 0) {
			publicHealthCaseDT.setInvestigatorAssignedTime(time);
		}
		if (toDBLocation.indexOf("HOSPITALIZED_ADMIN_TIME") > 0) {
			publicHealthCaseDT.setHospitalizedAdminTime(time);
		}
		if (toDBLocation.indexOf("HOSPITALIZED_DISCHARGE_TIME") > 0) {
			publicHealthCaseDT.setHospitalizedDischargeTime(time);
		}
		return publicHealthCaseDT;
	}


	public HashMap<Object,Object> setParticipationToAnswer(
			InvestigationProxyVO investigationProxyVO,
			ParticipationDT participationDT) {
		logger.debug("in setParticipationToAnswer()\n");
		String conditionCode=investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		logger.debug("for participation " + participationDT.getTypeCd() +"\n");
		HashMap<Object,Object> returnMap = new HashMap<Object,Object>();
		Collection<Object>  nbsConversionErrorDTColl = new ArrayList<Object> ();
		Collection<Object>  nbsCaseAnswerDTColl = new ArrayList<Object> ();
		PamConversionDAO pamConversionDAO = new PamConversionDAO();
		HashMap<Object,Object> questionIdMap = (HashMap<Object,Object>) pamConversionDAO
		.getCachedQuestionIdMap(conditionCode);
		NBSConversionMappingDT nbsConversionMappingDT = null;
		if (questionIdMap.get("INV110") != null && ((Collection)questionIdMap.get("INV110")).size()>0.) {
			nbsConversionMappingDT = (NBSConversionMappingDT) ((Collection)questionIdMap
			.get("INV110")).iterator().next();
		}
		if (nbsConversionMappingDT == null)
			return returnMap;
		NbsUiMetadataDT nbsUiMetadataDT = pamConversionDAO
		.getNBSUiMetadtaForIdentifier(nbsConversionMappingDT
				.getToQuestionId(), conditionCode);
		if (nbsUiMetadataDT == null) {
			NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
					nbsConversionMappingDT.getConditionCdGroupId(),
					nbsConversionMappingDT.getNbsConversionMappingUid(),
					nbsConversionMappingDT.getFromQuestionId(),
					nbsConversionMappingDT.getToQuestionId());
			nbsConversionErrorDTColl.add(nbsConversionErrorDT);
			returnMap.put(ERROR, nbsConversionErrorDTColl);
			return returnMap;
		}

		if (nbsConversionMappingDT.getToDbLocation().indexOf(
				"PUBLIC_HEALTH_CASE") != -1
				|| nbsConversionMappingDT.getToDbLocation().indexOf(
				"public_health_case") != -1) {
			PublicHealthCaseDT publicHealthCaseDT = setPublicHealthCaseDTDate(
					investigationProxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT(), participationDT
					.getFromTime(), nbsConversionMappingDT);
			returnMap.put(PublicHealthCaseDT, publicHealthCaseDT);
		} else {

			NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
			nbsCaseAnswerDT.setAnswerTxt(StringUtils.formatDate(participationDT
					.getFromTime()));
			nbsCaseAnswerDT.setNbsQuestionUid(nbsUiMetadataDT.getNbsQuestionUid());
			nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(nbsUiMetadataDT
					.getVersionCtrlNbr());
			nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMappingDT.getBlockIdNbr());
			setStandardNBSCaseAnswerVals(investigationProxyVO
					.getPublicHealthCaseVO().getThePublicHealthCaseDT(),
					nbsCaseAnswerDT, 0,nbsUiMetadataDT );
			nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
		}
		if (nbsCaseAnswerDTColl != null && nbsCaseAnswerDTColl.size() > 0)
			returnMap.put("nbsCaseAnswerDTColl", nbsCaseAnswerDTColl);
		returnMap.put("participationDT", participationDT);
		participationDT.setFromTime(null);

		return returnMap;
	}

	private NBSConversionErrorDT createNBSConversionErrorDT(
			Long  conditionCdGroupId,Long nbsConversionMappingUid, String fromQuestionId,
			String toQuestionId) {
		logger.debug("in createNBSConversionErrorDT()\n");
		NBSConversionErrorDT nbsConversionErrorDT = new NBSConversionErrorDT();
		nbsConversionErrorDT.setErrorCd("Mapping Error");
		nbsConversionErrorDT.setConditionCdGroupId(conditionCdGroupId);
		nbsConversionErrorDT.setNbsConversionMappingUid(nbsConversionMappingUid);
		nbsConversionErrorDT
		.setErrorMessageTxt("Please check NBS_conversion_mapping table. For [NBS_conversion_mapping].[from_question_id]: "
				+ fromQuestionId
				+ " [NBS_conversion_mapping].[to_question_id]: "
				+ toQuestionId
				+ " Does not Exist in NBS_Question table");
		return nbsConversionErrorDT;

	}

	public Object deepCopy() throws CloneNotSupportedException, IOException,
	ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object deepCopy = ois.readObject();

		return deepCopy;
	}

	public PublicHealthCaseDT cleanPublicHealthCaseDT(PublicHealthCaseDT publicHealthCaseDT){
		if(publicHealthCaseDT.getPatAgeAtOnset()==null ||( publicHealthCaseDT.getPatAgeAtOnset()!=null && publicHealthCaseDT.getPatAgeAtOnset().equals(""))){
			publicHealthCaseDT.setPatAgeAtOnsetUnitCd(null);
		}
		if(publicHealthCaseDT.getEffectiveDurationAmt()==null ||( publicHealthCaseDT.getEffectiveDurationAmt()!=null && publicHealthCaseDT.getEffectiveDurationAmt().equals(""))){
			publicHealthCaseDT.setEffectiveDurationUnitCd(null);
		}
		return publicHealthCaseDT;
	}

	public WaTemplateDT getWaTemplateByConditionCd(String cdToBeTranslated) {
		logger.debug("in getWaTemplateByConditionCd()\n");
		PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
		WaTemplateDT waTemplateDT = pamConversionLegacyDAO.getWaTemplateByConditionCd(cdToBeTranslated);
		return waTemplateDT;
	}
	@SuppressWarnings("deprecation")
	private Collection<Object> convertToSNMStructure(Map<Object, Object> map) throws NEDSSAppException{
		logger.debug("in convertToSNMStructure()\n");
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		try {
			if(map!=null && map.keySet().size()>0){
				Set<Object> keys=map.keySet();
				Iterator<Object> it = keys.iterator();
				while(it.hasNext()){
					try { 
						String key = (String)it.next();
						Long nbsQuestionUid = new Long(0);
						Collection<Object> coll= (Collection<Object>)map.get(key);
						Iterator<Object> innerIterator = coll.iterator();
						while(innerIterator.hasNext()){
							NbsCaseAnswerDT nbsCaseAnswerDT= (NbsCaseAnswerDT)innerIterator.next();
							String questionId = nbsCaseAnswerDT.getQuestionIdentifier().toString();
							if(PamConversionDAO.getCachedSNMQuestionIdMap(questionId)!=null){
							try {
								Collection<Object> collection = (Collection<Object>)PamConversionDAO.getCachedSNMQuestionIdMap(questionId);
								Iterator<Object> snmCollIterator = collection.iterator();
								NBSConversionMappingDT nbsConversionMapperrDT = null;
								while(snmCollIterator.hasNext()){
									NBSConversionMappingDT nbsConversionMapperrDT1 = (NBSConversionMappingDT)snmCollIterator.next();
									if(nbsConversionMapperrDT1.getToDataType().equalsIgnoreCase(nbsCaseAnswerDT.getType())){
										nbsConversionMapperrDT=nbsConversionMapperrDT1;
										break;
									}
								}
								if(nbsConversionMapperrDT.getToDataType().equalsIgnoreCase(NEDSSConstants.CODED)){
									nbsCaseAnswerDT.setCode(nbsCaseAnswerDT.getAnswerTxt());
								}else if(nbsConversionMapperrDT.getToDataType().equalsIgnoreCase("NUMERIC") &&
									((nbsConversionMapperrDT.getUnitTypeCd()!=null && nbsConversionMapperrDT.getUnitTypeCd().equalsIgnoreCase(NEDSSConstants.CODED))||
									(nbsConversionMapperrDT.getUnitInd()!=null && nbsConversionMapperrDT.getUnitInd().equalsIgnoreCase(NEDSSConstants.TRUE)))){
										nbsCaseAnswerDT.setValue(nbsCaseAnswerDT.getAnswerTxt());
								}
								else if(nbsConversionMapperrDT.getUnitTypeCd()!=null && nbsConversionMapperrDT.getUnitTypeCd().equalsIgnoreCase(NEDSSConstants.LITERAL)){
									nbsCaseAnswerDT.setValue(nbsCaseAnswerDT.getAnswerTxt()+"^"+nbsConversionMapperrDT.getUnitValue());
								}
								else if(nbsConversionMapperrDT.getOtherInd()!=null && nbsConversionMapperrDT.getOtherInd().equalsIgnoreCase("T")){
									nbsCaseAnswerDT.setValue(nbsCaseAnswerDT.getAnswerTxt());
								}
								else
									nbsCaseAnswerDT.setValue(nbsCaseAnswerDT.getAnswerTxt());
							} catch (Exception e) {
								logger.error("convertToNewStructure Error thrown for key! Please check data key!"+key);
								throw new NEDSSAppException("convertToNewStructure Error thrown for keys! Please check!"+key, e);
							}
							nbsQuestionUid=nbsCaseAnswerDT.getNbsQuestionUid();
							if(returnMap.get(nbsQuestionUid+"Key"+nbsCaseAnswerDT.getAnswerGroupSeqNbr())==null){
								if(nbsCaseAnswerDT.getValue()!=null && nbsCaseAnswerDT.getValue().trim().length()>0)
									nbsCaseAnswerDT.setAnswerTxt(nbsCaseAnswerDT.getValue());
								else if(nbsCaseAnswerDT.getCode()!=null && nbsCaseAnswerDT.getCode().trim().length()>0)
									nbsCaseAnswerDT.setAnswerTxt(nbsCaseAnswerDT.getCode());
								returnMap.put(nbsQuestionUid +"Key"+nbsCaseAnswerDT.getAnswerGroupSeqNbr(), nbsCaseAnswerDT);
							}
							else
							{
								String value="";
								String code="";
								NbsCaseAnswerDT nbsCaseAnswerDT1=(NbsCaseAnswerDT) returnMap.get(nbsQuestionUid+"Key"+nbsCaseAnswerDT.getAnswerGroupSeqNbr());
								if(nbsCaseAnswerDT1.getValue()!=null && !nbsCaseAnswerDT1.getValue().trim().equalsIgnoreCase("")){
									value=nbsCaseAnswerDT1.getValue();
								}
								else if(nbsCaseAnswerDT.getValue()!=null && !nbsCaseAnswerDT.getValue().trim().equalsIgnoreCase("")){
									value=nbsCaseAnswerDT.getValue();
								}
								if(nbsCaseAnswerDT1.getCode()!=null && !nbsCaseAnswerDT1.getCode().trim().equalsIgnoreCase("")){
									code=nbsCaseAnswerDT1.getCode();
								}
								else if(nbsCaseAnswerDT.getCode()!=null && !nbsCaseAnswerDT.getCode().trim().equalsIgnoreCase("")){
									code=nbsCaseAnswerDT.getCode();
								}
								boolean skipAddition = false;

								if(code.trim().length()>0 && value.trim().length()>0 ){
									if (nbsCaseAnswerDT1.getOtherType()!=null && nbsCaseAnswerDT1.getOtherType().equalsIgnoreCase("T") 
											&& nbsCaseAnswerDT1.getOtherType()!=null && nbsCaseAnswerDT1.getOtherType().equalsIgnoreCase("T")){
										if(code.trim().equalsIgnoreCase(NEDSSConstants.OTHER))
											nbsCaseAnswerDT.setAnswerTxt(code+"^"+value);
										else{
											skipAddition= true;
										}
									}
									else if(code.trim().equalsIgnoreCase(NEDSSConstants.OTHER))
									nbsCaseAnswerDT.setAnswerTxt(code+"^"+value);
									else
										nbsCaseAnswerDT.setAnswerTxt(value+"^"+code);
											
								}
								else if(code.trim().length()>0 )
									nbsCaseAnswerDT.setAnswerTxt(code);
								else if(value.trim().length()>0 )
									nbsCaseAnswerDT.setAnswerTxt(value);
								if(!skipAddition){
									returnMap.put(nbsQuestionUid+"Key"+nbsCaseAnswerDT.getAnswerGroupSeqNbr(), nbsCaseAnswerDT);
								}
							}
							
						}
						}

					}catch (Exception e) {
						logger.error("convertToNewStructure Error thrown for keys! Please check!"+keys);
						throw new NEDSSAppException("convertToNewStructure Error thrown for keys! Please check!"+keys, e);
					}
				}
			}
		} catch (Exception e) {
			logger.error("convertToNewStructure Error thrown! Please check!");
			throw new NEDSSAppException("convertToNewStructure Error thrown for keys! Please check!"+e.getMessage(), e);

		}
		logger.debug("leaving convertToSNMStructure()\n");
		return returnMap.values();
	}
	/**
	 * Find the person that is the Subject of PHC
	 * @param publicHealthCaseUid
	 * @return personVO for the subject of the investigation
	 */
	private PersonVO getThePatient(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj) {
		PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
		PersonVO patientVO = pamConversionLegacyDAO.getPatientVO(publicHealthCaseUid, nbsSecurityObj);
		return patientVO;
	}	
	private Boolean setEntityLocatorParticipationDTCoded(
			PersonVO patientVO, 
			NBSConversionMappingDT nbsConversionMapDT) {
		logger.debug("in setEntityLocatorParticipationDTCoded\n");
		if (nbsConversionMapDT.getFromQuestionId() != null && nbsConversionMapDT.getFromQuestionId().equals("HEP255"))
				return(addPersonBirthTypeLocator(nbsConversionMapDT.getToCode(), patientVO));
		return false;
	}
	
	/**
	 * Add a birth type locator to the subject record.
	 * @param birthCountryCd
	 * @param personVO
	 * @return
	 */
	public static boolean addPersonBirthTypeLocator(String birthCountryCd, PersonVO personVO) {
		
		if (birthCountryCd == null || birthCountryCd.trim().isEmpty())
			return false;
		logger.debug("Adding birth country ELP to Person");
		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
		elp.setItNew(true);
		elp.setItDirty(false);
		elp.setAddTime(new Timestamp(new Date().getTime()));
		elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setRecordStatusTime(personVO.getThePersonDT().getRecordStatusTime());
		elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		elp.setStatusTime(personVO.getThePersonDT().getRecordStatusTime());
		elp.setClassCd(NEDSSConstants.POSTAL) ;
		elp.setUseCd(NEDSSConstants.BIRTHPLACE);
		elp.setCd(NEDSSConstants.BIRTHCD);
        elp.setLastChgUserId(personVO.getThePersonDT().getAddUserId());
        elp.setLastChgTime(personVO.getThePersonDT().getLastChgTime());
		elp.setEntityUid(personVO.getThePersonDT().getPersonUid());
		elp.setAsOfDate(personVO.getThePersonDT().getLastChgTime());

		PostalLocatorDT pl = new PostalLocatorDT();
		pl.setItNew(true);
		pl.setItDirty(false);
		pl.setAddTime(new Timestamp(new Date().getTime()));
		pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
		pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		pl.setCntryCd(birthCountryCd.trim());
		pl.setAddUserId(personVO.getThePersonDT().getAddUserId());
		elp.setThePostalLocatorDT(pl);
		if (personVO.getTheEntityLocatorParticipationDTCollection() == null) {
			personVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
		}
		personVO.getTheEntityLocatorParticipationDTCollection().add(elp);
		personVO.setItDirty(true);
		
		return true;
	}
	/**
	 * HEP185 Incarceration period if over 6 months goes to INV641 which is only in months
	 * If the units is Years, adjust. If the units is hours or minutes or days. drop HEP186 
	 * @param theObservationVOCollection
	 */
	private void preprocessObsVoForUnits(
			Collection<ObservationVO> theObservationVOCollection) {
		logger.debug("Begin preprocessObsVoForUnits()");
		Iterator it = theObservationVOCollection.iterator();
		ObservationVO hep185Obs = null;
		ObservationVO hep186Obs = null;
		boolean deleteHep185 = false;
		while (it.hasNext()) {
			ObservationVO thisObs = (ObservationVO)it.next();
			if (thisObs.getTheObservationDT() != null) {
				String theCd = thisObs.getTheObservationDT().getCd();
				if (theCd.equals("HEP185"))
					hep185Obs = thisObs;
				else if (theCd.equals("HEP186"))
					hep186Obs = thisObs;
			}
	    }
		
		if (hep186Obs == null || hep185Obs == null) 
			return;
		
		if (hep186Obs != null && hep185Obs != null) {
			Collection<Object> obsValueCodedDTColl  = hep186Obs.getTheObsValueCodedDTCollection();
			if (obsValueCodedDTColl != null) {
				Iterator<Object> it2 = obsValueCodedDTColl.iterator();
				if (it2.hasNext()) {
					ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) it2.next();
					String theUnitsCd = obsValueCodedDT.getCode();
					if (theUnitsCd != null && theUnitsCd.equals("Y") ) { //if months everything ok else adjust value...
						if (hep185Obs.getTheObsValueNumericDTCollection() != null) {
							Iterator<Object> it3 = hep185Obs.getTheObsValueNumericDTCollection().iterator();
							while (it3.hasNext()) {
								ObsValueNumericDT obsNumericDT = (ObsValueNumericDT) it3.next();
								BigDecimal nbrMonths = obsNumericDT.getNumericValue1();
								if (theUnitsCd.equals("Y")) { //if years - multiply by 12
									try {
										obsNumericDT.setNumericValue1(nbrMonths.multiply(new BigDecimal(12)));
										logger.debug("Incarceration number of months adjusted to "+ obsNumericDT.getNumericValue1() );
									} catch (Exception ex){ 
										deleteHep185 = true;
									}
								}
							}
						}
					} else if (theUnitsCd != null && !theUnitsCd.equals("M"))
						deleteHep185 = true;
				}
			}
		}
		if (deleteHep185) {
			Iterator itDel = theObservationVOCollection.iterator();
			while (itDel.hasNext()) {
				ObservationVO thisObs = (ObservationVO)itDel.next();
				if (thisObs.getTheObservationDT() != null) {
					String theCd = thisObs.getTheObservationDT().getCd();
					if (theCd.equals("HEP185")) {
						itDel.remove(); //remove the underlying collection
						break;
					}
				}
			}
		}
		logger.debug("leaving preprocessObsVoForUnits()");
		return;
	}	
	
	private Collection<Object> removeDuplicateAnswers(
			Collection<Object> nbsAnswerDTColl) {
		Map<Object, Object> deduplicateMap = new HashMap<Object, Object>();
		if (nbsAnswerDTColl != null && nbsAnswerDTColl.size() > 0) {
			Iterator ite = nbsAnswerDTColl.iterator();
			while (ite.hasNext()) {
				NbsCaseAnswerDT nbsCaseAnswerDT = (NbsCaseAnswerDT) ite.next();
				String key = String.valueOf(nbsCaseAnswerDT.getNbsQuestionUid())
						+ nbsCaseAnswerDT.getSeqNbr()
						+ nbsCaseAnswerDT.getAnswerGroupSeqNbr();
				deduplicateMap.put(key, nbsCaseAnswerDT);
			}
		}
		return deduplicateMap.values();
	}
	
	private HashMap<Object, Object> convertTransissionModetoNBSCaseAnswer(
			PublicHealthCaseDT PhcDT, Collection<Object> nbsAnswerDTColl)
			throws NEDSSAppException {
		HashMap<Object, Object> returnMap = new HashMap<Object, Object>();
		Collection<Object> nbsConversionErrorDTColl = new ArrayList<Object>();
		Collection<Object> nbsCaseAnswerDTColl = new ArrayList<Object>();
		PamConversionDAO pamConversionDAO = new PamConversionDAO();
		HashMap<Object, Object> PHCMap = (HashMap<Object, Object>) pamConversionDAO
				.getCachedPHCLocationMap();
		NBSConversionMappingDT nbsConversionMappingDT = null;
		String key = "PUBLIC_HEALTH_CASE.TRANSMISSION_MODE_CD"
				+ PhcDT.getTransmissionModeCd();
		
		if (PHCMap.get(key) != null
				&& ((Collection) PHCMap.get(key)).size() > 0) {
			nbsConversionMappingDT = (NBSConversionMappingDT) ((Collection) PHCMap
					.get(key)).iterator().next();
			if (nbsConversionMappingDT == null)
				return returnMap;
			NbsUiMetadataDT nbsUiMetadataDT = pamConversionDAO
					.getNBSUiMetadtaForIdentifier(
							nbsConversionMappingDT.getToQuestionId(),							
							PhcDT.getCd());
			if (nbsUiMetadataDT == null) {
				NBSConversionErrorDT nbsConversionErrorDT = createNBSConversionErrorDT(
						nbsConversionMappingDT.getConditionCdGroupId(),
						nbsConversionMappingDT.getNbsConversionMappingUid(),
						nbsConversionMappingDT.getFromQuestionId(),
						nbsConversionMappingDT.getToQuestionId());
				nbsConversionErrorDTColl.add(nbsConversionErrorDT);
				returnMap.put(ERROR, nbsConversionErrorDTColl);
				return returnMap;
			}
			NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
			if (nbsConversionMappingDT.getToCode() == null)
				nbsCaseAnswerDT.setAnswerTxt(PhcDT.getTransmissionModeCd());
			else
				nbsCaseAnswerDT
						.setAnswerTxt(nbsConversionMappingDT.getToCode());
			nbsCaseAnswerDT.setNbsQuestionUid(nbsUiMetadataDT
					.getNbsQuestionUid());
			nbsCaseAnswerDT.setNbsQuestionVersionCtrlNbr(nbsUiMetadataDT
					.getVersionCtrlNbr());
			nbsCaseAnswerDT.setAnswerGroupSeqNbr(nbsConversionMappingDT
					.getBlockIdNbr());
			setStandardNBSCaseAnswerVals(PhcDT, nbsCaseAnswerDT, 0,
					nbsUiMetadataDT);
			nbsCaseAnswerDTColl.add(nbsCaseAnswerDT);
			if (nbsCaseAnswerDTColl != null && nbsCaseAnswerDTColl.size() > 0)
				nbsAnswerDTColl.add(nbsCaseAnswerDT);
			else {
				nbsAnswerDTColl = new ArrayList();
				nbsAnswerDTColl.add(nbsCaseAnswerDT);
			}
			PhcDT.setTransmissionModeCd(null);
			PhcDT.setTransmissionModeDescTxt(null);
		}

		return returnMap;
	}
	
}
