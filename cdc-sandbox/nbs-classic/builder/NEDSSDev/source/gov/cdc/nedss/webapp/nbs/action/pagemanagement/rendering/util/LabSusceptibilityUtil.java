package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedModDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.dt.ObservationInterpDT;
import gov.cdc.nedss.act.observation.dt.ObservationReasonDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ObservationUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations.util.CaseCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageSubForm;
/**
 * This class has been created for Susceptibilities within the Resulted Test. This handles Add, Edit, Delete of Susceptibilities
 * @Component : lab report
 * @author Pradeep Kumar Sharma
 * @since 2018
 * @version 6.0
 *
 */
public class LabSusceptibilityUtil {
	static final LogUtils logger = new LogUtils(LabSusceptibilityUtil.class.getName());
	private ObservationVO rootObservationVO = null;
	private LabResultProxyVO labResultProxyVO = null;
	private Map<Long, Object> susceptibilityMap = null;
	private Map<Long, Object> susAndTrackIsolateVOMap  = null;
	//private Map<Long, Object> susAndTrackIsolateARSourceMap  = null;
	private Map<Long, Object> aRMap = null;
	private static ArrayList<Long> updatedObservationUidList = new ArrayList<Long>();
	private static Map<String,String> questionToLegacySusceptabilityMap;
	private static Map<String,String> legacyQuestionSusceptabilityMap;
	private static String labId;

	public static String[] SUSCEPTIBILITY_TEST_OBSERVATION= {"NBS455", "NBS_LAB121_1", "NBS_LAB293_1", "NBS405", 
						"NBS367", "NBS378", "NBS375", "NBS376", "NBS369", "NBS372", "NBS373", "NBS374", "NBS365", "NBS377", 
						"NBS458", "NBS_LAB110"};
	
	
	/**
	 * Constructor to reset values and create default values for variables
	 * @param rootObservationVO
	 * @param labResultProxyVO
	 * @param consolidatedMap
	 */
	public LabSusceptibilityUtil(String labId, ObservationVO rootObservationVO, LabResultProxyVO labResultProxyVO,
			Map<String, Map<Long, Object>> consolidatedMap){
		updatedObservationUidList =new ArrayList<Long>();
		this.labId = labId;
		this.rootObservationVO =rootObservationVO;
		this.labResultProxyVO = labResultProxyVO;
		this.susceptibilityMap= ((Map<Long, Object>)consolidatedMap.get(PageConstants.SUSCEPTABILITY_TEST));
		if(susceptibilityMap==null)
			susceptibilityMap = new HashMap<Long, Object>();
		this.susAndTrackIsolateVOMap= ((Map<Long, Object>)consolidatedMap.get(PageConstants.ISOLATE_AND_SUSCEPTABILITY_TEST_VO));
		if(susAndTrackIsolateVOMap==null)
			  susAndTrackIsolateVOMap  = new HashMap<Long, Object>();
		this.aRMap = consolidatedMap.get(PageConstants.AR_MAP_FOR_LAB);
	}
	public String getLabId() {
		return labId;
	}
	public void setLabId(String labId) {
		this.labId = labId;
	}
	/**
	 *Default map for legacy and new lab question identifiers
	 */
	public static void legacySusceptabilityMap(){
		questionToLegacySusceptabilityMap =  new HashMap<String, String>();
		questionToLegacySusceptabilityMap.put("LAB121", "NBS_LAB121_1");
		questionToLegacySusceptabilityMap.put("LAB293", "NBS_LAB293_1");
		questionToLegacySusceptabilityMap.put("LAB217", "NBS405");
		questionToLegacySusceptabilityMap.put("LAB280", "NBS367");
		questionToLegacySusceptabilityMap.put("LAB118", "NBS378");
		questionToLegacySusceptabilityMap.put("LAB104", "NBS375");
		questionToLegacySusceptabilityMap.put("LAB207", "NBS376");
		questionToLegacySusceptabilityMap.put("LAB114", "NBS369");
		questionToLegacySusceptabilityMap.put("LAB115", "NBS372");
		questionToLegacySusceptabilityMap.put("LAB119", "NBS373");
		questionToLegacySusceptabilityMap.put("LAB120", "NBS374");
		questionToLegacySusceptabilityMap.put("LAB208", "NBS365");
		questionToLegacySusceptabilityMap.put("LAB279", "NBS377");
		questionToLegacySusceptabilityMap.put("LAB110", "NBS_LAB110");
		
		legacyQuestionSusceptabilityMap =  new HashMap<String, String>();
		legacyQuestionSusceptabilityMap = 
				questionToLegacySusceptabilityMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
	}
		
	/**
	 * This process creates the Susceptibility batch entry 
	 * @param tempUid
	 * @param currentKey
	 * @param resultedTestObservationVO
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 */
	public Long setRepeatingSuscBatchQuestion(Long tempUid, String currentKey, ObservationVO resultedTestObservationVO, 
			PageSubForm pageSubForm) throws NEDSSAppException{
		ObservationVO observation222VO=null;
		Collection<Object> obsActRelationshipColl = null;
		int lab220Counter = 0;
		boolean lab220valid = false;
		boolean deleteAllSuscInd= false;
		try {
			Map<Object, ArrayList<BatchEntry>> batchmap = pageSubForm.getBatchEntryMap();
			Collection<ArrayList<BatchEntry>>  blist = batchmap.values();
			
				Iterator<ArrayList<BatchEntry>> iterator = blist.iterator();
				while(iterator.hasNext()) {
					ArrayList<BatchEntry> list  = iterator.next();
					if(list !=null && list.size()>0) {
						
						Iterator<BatchEntry> batchEntryIterator = list.iterator();
						
						while(batchEntryIterator.hasNext()) {
							try {
								BatchEntry batchEntry= batchEntryIterator.next();
								ObservationVO observationVO = populateObservation(this.susAndTrackIsolateVOMap,batchEntry.getAnswerMap(), SUSCEPTIBILITY_TEST_OBSERVATION, pageSubForm.getPageFormCd());
								
								if(resultedTestObservationVO.getTheActRelationshipDTCollection()!=null && resultedTestObservationVO.getTheActRelationshipDTCollection().size()>0) {
										Collection<Object> arCollection =resultedTestObservationVO.getTheActRelationshipDTCollection();
										Iterator<Object> arIterator = arCollection.iterator();
										while(arIterator.hasNext()) {
											ActRelationshipDT ar222Lab=(ActRelationshipDT)arIterator.next();
											if(ar222Lab.getSourceClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
													ar222Lab.getTargetClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
													ar222Lab.getTypeCd().equals(NEDSSConstants.ACT109_TYP_CD)) {
												Long ar222LabUid = ar222Lab.getSourceActUid();
												if(susceptibilityMap.get(ar222LabUid)!=null) {
													ObservationVO obsVO =(ObservationVO)susceptibilityMap.get(ar222LabUid);
													if(obsVO.getTheObservationDT().getCd()!=null && obsVO.getTheObservationDT().getCd().equals(NEDSSConstants.LAB222)) {
														observation222VO = obsVO;
													}
													obsActRelationshipColl = observation222VO.getTheActRelationshipDTCollection();
												}
											}
										}
									}
									if(observationVO.getTheObservationDT().getObservationUid()==null) {
										observationVO.getTheObservationDT().setObservationUid(new Long(tempUid--));
										lab220valid = true;
									}

									if(observation222VO==null) {
										observation222VO = LabPageUtil.createObservationVO(tempUid--, NEDSSConstants.LAB222, NEDSSConstants.NOINFORMATIONGIVEN, null, null, null, null, NEDSSConstants.STATUS_ACTIVE, null, NEDSSConstants.R_ORDER);
										ObsValueCodedDT obsValueCodedDT = new ObsValueCodedDT();
										obsValueCodedDT.setCode("Y");
										observation222VO.getTheObservationDT().setCtrlCdDisplayForm(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
										obsValueCodedDT.setObservationUid(observation222VO.getTheObservationDT().getObservationUid());
										observation222VO.setTheObsValueCodedDTCollection(new ArrayList<Object>());
										observation222VO.getTheObsValueCodedDTCollection().add(obsValueCodedDT);
										labResultProxyVO.getTheObservationVOCollection().add(observation222VO);
										ActRelationshipDT actRelationship115DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation222VO.getTheObservationDT().getObservationUid(),
												NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
												rootObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
												NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115);
										if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
											labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
										}
										labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship115DT);
										
										ActRelationshipDT actRelationship109DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation222VO.getTheObservationDT().getObservationUid(),
												NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
												resultedTestObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
											NEDSSConstants.ACT109_TYP_CD, NEDSSConstants.TYPE_DESC_TXT_109);
										
										labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship109DT);
									}
									
									if(observationVO.getTheObservationDT().getObservationUid()<0) {
										ActRelationshipDT actRelationship110DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observationVO.getTheObservationDT().getObservationUid(),
										NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
										observation222VO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
										NEDSSConstants.TYPE_CD_110, NEDSSConstants.TYPE_DESC_TXT_110);
								
										if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
											labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
										}
										labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship110DT);
									}
								
								labResultProxyVO.getTheObservationVOCollection().add(observationVO);
							} catch (Exception exception) {
								logger.error(exception);
								logger.error("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion exception thrown for batchEntryIterator: "+exception);
								logger.error("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion exception getMessage thrown for batchEntryIterator: "+exception.getMessage());
								throw new NEDSSAppException("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion Error thrown for batchEntryIterator: "+ exception.getMessage());
						}
					}
				}else {
					deleteAllSuscInd = true;
				}
			}
				
					
			/**Update and Delete scenario*/
			if(susAndTrackIsolateVOMap.size()>0 && obsActRelationshipColl!=null && obsActRelationshipColl.size()>0 
					&& resultedTestObservationVO.getTheObservationDT().getObservationUid()>0 ) {
				try {
					Iterator<Object> it  = obsActRelationshipColl.iterator();
					while(it.hasNext()) {
						ActRelationshipDT actRelationshipDT= (ActRelationshipDT)it.next();
						if(!updatedObservationUidList.contains(actRelationshipDT.getSourceActUid())) {
							Long observationDeletedUid=actRelationshipDT.getSourceActUid();
							if(susAndTrackIsolateVOMap.get(actRelationshipDT.getSourceActUid())!=null) {
								//if(updatedUid.compareTo(observationDeleteUid)==0) {
								lab220Counter++;
								ActRelationshipDT ar222DT= (ActRelationshipDT)aRMap.get(observationDeletedUid);
								Long obs22Uid =ar222DT.getTargetActUid();
								if(observation222VO==null)
									observation222VO =(ObservationVO)susceptibilityMap.get(obs22Uid);
								ObservationVO obsSusVO =(ObservationVO)susAndTrackIsolateVOMap.get(observationDeletedUid);

								obsSusVO.setItDelete(true);
								obsSusVO.getTheObservationDT().setItDelete(true);
								labResultProxyVO.getTheObservationVOCollection().add(obsSusVO);


								/*
								ActRelationshipDT actRelationship115DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation222VO.getTheObservationDT().getObservationUid(),
										NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
										rootObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
										NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115);

								actRelationship115DT.setItDelete(true);
								actRelationship115DT.setItNew(false);
								if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
									labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
								}
								labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship115DT);
								*/
								ActRelationshipDT actRelationship110DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsSusVO.getTheObservationDT().getObservationUid(),
										NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
										observation222VO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
										NEDSSConstants.TYPE_CD_110, NEDSSConstants.TYPE_DESC_TXT_110);
								actRelationship110DT.setItDelete(true);
								actRelationship110DT.setItNew(false);
								if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
									labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
								}
								labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship110DT);

								ActRelationshipDT ar220Lab=null;
								if(!lab220valid && observation222VO.getTheActRelationshipDTCollection()!=null 
										&& (observation222VO.getTheActRelationshipDTCollection().size()==lab220Counter)) {
									Collection<Object> arCollection =resultedTestObservationVO.getTheActRelationshipDTCollection();
									Iterator<Object> arIterator = arCollection.iterator();
									while(arIterator.hasNext()) {
										ActRelationshipDT arDT=(ActRelationshipDT)arIterator.next();
										if(arDT.getSourceClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
												arDT.getTargetClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
												arDT.getTypeCd().equals(NEDSSConstants.ACT109_TYP_CD)) {
											//Long ar220LabUid = ar220Lab.getSourceActUid();
											if(obs22Uid.compareTo(arDT.getSourceActUid())==0) {
												ar220Lab=arDT; 
												ar220Lab.setItDelete(true);
												ar220Lab.setItDirty(false);
												if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
													labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
												}
												labResultProxyVO.getTheActRelationshipDTCollection().add(ar220Lab);

											}
										}

									}
								}
							}
						}
					}
				} catch (Exception exception) {
					logger.error(exception);
					logger.error("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion exception thrown for deleteSuscTest: "+exception);
					logger.error("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion exception getMessage thrown for deleteSuscTest: "+exception.getMessage());
					throw new NEDSSAppException("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion Error thrown for deleteSuscTest: "+ exception.getMessage());

				}
			}
			/**Delete All scenario*/
			else if(susAndTrackIsolateVOMap.size()>0 && deleteAllSuscInd  && resultedTestObservationVO.getTheObservationDT().getObservationUid()>0 ) {        
				deleteAll(resultedTestObservationVO);
			}
		} catch (Exception exception) {
			logger.error(exception);
			logger.error("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion exception thrown : "+exception);
			logger.error("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion exception getMessage thrown : "+exception.getMessage());
			throw new NEDSSAppException("LabSusceptibilityUtil.setRepeatingSuscBatchQuestion Error thrown : "+ exception.getMessage());
		}
		
		
		
		return tempUid;
	}

	public void deleteAll(ObservationVO resultedTestObservationVO) throws NEDSSAppException {
		ObservationVO observation222VO = null;
		Collection<Object> obsActRelationshipColl = null;
		int lab220Counter = 0;
		boolean lab220valid = false;
		//boolean deleteAllSuscInd= false;

		try {
			if(resultedTestObservationVO.getTheActRelationshipDTCollection()!=null && resultedTestObservationVO.getTheActRelationshipDTCollection().size()>0) {
				Collection<Object> arCollection =resultedTestObservationVO.getTheActRelationshipDTCollection();
				Iterator<Object> arIterator = arCollection.iterator();
				while(arIterator.hasNext()) {
					ActRelationshipDT ar222Lab=(ActRelationshipDT)arIterator.next();
					if(ar222Lab.getSourceClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
							ar222Lab.getTargetClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
							ar222Lab.getTypeCd().equals(NEDSSConstants.ACT109_TYP_CD)) {
						Long ar222LabUid = ar222Lab.getSourceActUid();
						if(susceptibilityMap.get(ar222LabUid)!=null) {
							ObservationVO obsVO =(ObservationVO)susceptibilityMap.get(ar222LabUid);
							if(obsVO.getTheObservationDT().getCd()!=null && obsVO.getTheObservationDT().getCd().equals(NEDSSConstants.LAB222)) {
								observation222VO = obsVO;
							}
							obsActRelationshipColl = observation222VO.getTheActRelationshipDTCollection();
						}
					}
				}
			}
			if(obsActRelationshipColl!=null) {
				Iterator<Object> it  = obsActRelationshipColl.iterator();
				while(it.hasNext()) {
					ActRelationshipDT actRelationshipDT= (ActRelationshipDT)it.next();
					if(!updatedObservationUidList.contains(actRelationshipDT.getSourceActUid())) {
						Long observationDeletedUid=actRelationshipDT.getSourceActUid();
						if(susAndTrackIsolateVOMap.get(actRelationshipDT.getSourceActUid())!=null) {
							//if(updatedUid.compareTo(observationDeleteUid)==0) {
							lab220Counter++;
							ActRelationshipDT ar222DT= (ActRelationshipDT)aRMap.get(observationDeletedUid);
							Long obs22Uid =ar222DT.getTargetActUid();
							if(observation222VO==null)
								observation222VO =(ObservationVO)susceptibilityMap.get(obs22Uid);
							ObservationVO obsSusVO =(ObservationVO)susAndTrackIsolateVOMap.get(observationDeletedUid);
	
							obsSusVO.setItDelete(true);
							obsSusVO.getTheObservationDT().setItDelete(true);
							labResultProxyVO.getTheObservationVOCollection().add(obsSusVO);
							ActRelationshipDT actRelationship115DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation222VO.getTheObservationDT().getObservationUid(),
									NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
									rootObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
									NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115);
	
							actRelationship115DT.setItDelete(true);
							actRelationship115DT.setItNew(false);
							if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
								labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
							}
							labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship115DT);
							ActRelationshipDT actRelationship110DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsSusVO.getTheObservationDT().getObservationUid(),
									NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
									observation222VO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
									NEDSSConstants.TYPE_CD_110, NEDSSConstants.TYPE_DESC_TXT_110);
							actRelationship110DT.setItDelete(true);
							actRelationship110DT.setItNew(false);
							labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship110DT);
	
							ActRelationshipDT ar220Lab=null;
							if(!lab220valid && observation222VO.getTheActRelationshipDTCollection()!=null 
									&& (observation222VO.getTheActRelationshipDTCollection().size()==lab220Counter)) {
								Collection<Object> arCollection =resultedTestObservationVO.getTheActRelationshipDTCollection();
								Iterator<Object> arIterator = arCollection.iterator();
								while(arIterator.hasNext()) {
									ActRelationshipDT arDT=(ActRelationshipDT)arIterator.next();
									if(arDT.getSourceClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
											arDT.getTargetClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
											arDT.getTypeCd().equals(NEDSSConstants.ACT109_TYP_CD)) {
										//Long ar220LabUid = ar220Lab.getSourceActUid();
										if(obs22Uid.compareTo(arDT.getSourceActUid())==0) {
											ar220Lab=arDT; 
											ar220Lab.setItDelete(true);
											ar220Lab.setItDirty(false);
											if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
												labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
											}
											labResultProxyVO.getTheActRelationshipDTCollection().add(ar220Lab);
	
										}
									}
	
								}
							}
						}
					}
				}
			}
		} catch (Exception exception) {
			logger.error(exception);
			logger.error("LabSusceptibilityUtil.deleteAll exception thrown for deleteAll: "+exception);
			logger.error("LabSusceptibilityUtil.deleteAll exception getMessage thrown for deleteAll: "+exception.getMessage());
			throw new NEDSSAppException("LabSusceptibilityUtil.deleteAll Error thrown for deleteAll: "+ exception.getMessage());

		}
	
	}
	/**
	 * 
	 * @param labResultProxyVO
	 * @param form
	 * @param typeCode
	 * @param typeCode Example could be RESULTED_TEST_OBSERVATION for resulted Test
	 * @return
	 * @throws NEDSSAppException
	 */
	public static ObservationVO populateObservation(Map<Long, Object> susAndTrackIsolateVOMap, Map<String, String> answerMap,  String[] typeCode, String labFormCode) 
			throws NEDSSAppException {
		ObservationVO observationVO= new ObservationVO(); 
		NbsQuestionMetadata nbsQuestionMetadata = null;
		String answerText="";
		String akey ="";
		String uid =answerMap.get(PageConstants.NBS458);
		Long aObservationUid=null;
/*1. new
2. Edit
3. Delete
*/
		try {
			ObservationDT observationDT=null;
			ObsValueCodedDT obsValueCodedDT =null;
			ObsValueDateDT obsValueDateDT = null;
			ObsValueNumericDT obsValueNumericDT = null;
			ObsValueTxtDT  obsValueLABNBS375TxtDT = null;
			ObsValueTxtDT  obsValueLAB208TxtDT = null;
			
			ObservationInterpDT observationInterpDT = null;
			ObservationReasonDT observationReasonDT = null;
			ObsValueCodedModDT obsValueCodedModDT = null;
			
			//New batch Entry Case
			if(uid==null || uid.trim().equals("")) {
				observationDT= new ObservationDT();
				observationDT.setItNew(true);
				observationVO.setItNew(true);
				observationDT.setItDirty(false);
				observationVO.setItDirty(false);
				
			}
			//Update Case----
			else {
				Long updatedObservationUid= new Long(uid);
				updatedObservationUidList.add(updatedObservationUid);
				observationVO = (ObservationVO)susAndTrackIsolateVOMap.get(updatedObservationUid);
				susAndTrackIsolateVOMap.remove(updatedObservationUid);
				observationVO.setItDirty(true);
				observationDT= observationVO.getTheObservationDT();
				aObservationUid = observationDT.getObservationUid();
				observationDT.setItDirty(false);
				if(observationVO.getTheObsValueCodedDTCollection()!=null && observationVO.getTheObsValueCodedDTCollection().size()>0) {
					obsValueCodedDT=(ObsValueCodedDT)LabPageUtil.getFirstElementOfCollection(observationVO.getTheObsValueCodedDTCollection());
					obsValueCodedDT.setItDirty(false);
				}
				if(observationVO.getTheObsValueDateDTCollection()!=null && observationVO.getTheObsValueDateDTCollection().size()>0) {
					obsValueDateDT = (ObsValueDateDT)LabPageUtil.getFirstElementOfCollection(observationVO.getTheObsValueDateDTCollection());
					obsValueDateDT.setItDirty(false);
				}
				if(observationVO.getTheObsValueNumericDTCollection()!=null && observationVO.getTheObsValueNumericDTCollection().size()>0) {
					obsValueNumericDT = (ObsValueNumericDT)LabPageUtil.getFirstElementOfCollection(observationVO.getTheObsValueNumericDTCollection());
					obsValueNumericDT.setItDirty(false);
				}
				
				if(observationVO.getTheObsValueTxtDTCollection()!=null && observationVO.getTheObsValueTxtDTCollection().size()>0) {
					Iterator<Object> iter = observationVO.getTheObsValueTxtDTCollection().iterator();
					ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT)iter.next();
					if(obsValueTxtDT.getTxtTypeCd()!=null && obsValueTxtDT.getTxtTypeCd().equals(NEDSSConstants.NO)){
						obsValueLABNBS375TxtDT =obsValueTxtDT;
						obsValueLABNBS375TxtDT.setItDirty(false);
					}else {
						obsValueLAB208TxtDT =obsValueTxtDT;
						obsValueLAB208TxtDT.setItDirty(false);
					}
				}
				if(observationVO.getTheObservationInterpDTCollection()!=null && observationVO.getTheObservationInterpDTCollection().size()>0) {
					observationInterpDT = (ObservationInterpDT)LabPageUtil.getFirstElementOfCollection(observationVO.getTheObservationInterpDTCollection());
					observationInterpDT.setItDirty(false);
				}
				if(observationVO.getTheObservationReasonDTCollection()!=null && observationVO.getTheObservationReasonDTCollection().size()>0) {
					observationReasonDT = (ObservationReasonDT)LabPageUtil.getFirstElementOfCollection(observationVO.getTheObservationReasonDTCollection());
					observationReasonDT.setItDirty(false);
				}
				if(observationVO.getTheObsValueCodedModDTCollection()!=null && observationVO.getTheObsValueCodedModDTCollection().size()>0) {
					obsValueCodedModDT = (ObsValueCodedModDT)LabPageUtil.getFirstElementOfCollection(observationVO.getTheObsValueCodedModDTCollection());
					obsValueCodedModDT.setItDirty(false);
				}
			}
			boolean organismIndicator= false;
			Map<Object,Object> questionMap =CaseCreateHelper.loadQuestions(labFormCode);	
			List<String> list= LabPageUtil.toList(typeCode);
			for(int i=0 ; i<list.size(); i++) {
				akey=list.get(i);
				if(akey.equals(PageConstants.NBS_LAB110)) {
					akey = PageConstants.NBS_LAB110CodeId;
					answerText =(String)answerMap.get(akey);
					nbsQuestionMetadata = (NbsQuestionMetadata)questionMap.get(PageConstants.NBS_LAB110);
				}else {
					answerText =(String)answerMap.get(akey);
					nbsQuestionMetadata = (NbsQuestionMetadata)questionMap.get(akey);
				}
				if(answerText ==null || answerText.equals("") || answerText.equals("$$") ) {
					answerText="";
				}

				if(nbsQuestionMetadata==null) {
					/**@TODO PKS: As of Dec 5 2018: this is a temporary workaround as the hidden variable are still not getting populated properly 
					 * 
					 */
					logger.error("nbsQuestionMetadata is null for the question_identifier"+ akey);
					continue;
				}
				if(nbsQuestionMetadata.getDataLocation()!=null && 
						(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB121)
						|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS373)
						|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS374)
						|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS365)
						|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB293_1)
						|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS405)
						|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS376)
				)){
					continue;
				}
				logger.debug("key here is " +akey);
				logger.debug("nbsQuestionMetadata.getDataLocation() is " +nbsQuestionMetadata.getDataLocation());
				if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBSERVATION_DB)) {
					
					if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB110)) {
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationDT, answerText, RenderConstants.OBSERVATION);
					}else if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS377)) {
						String code=getFirstPart(answerText);
						String description=getSecondPart(answerText);
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationDT, code, RenderConstants.OBSERVATION);
						observationDT.setMethodDescTxt(description);
					}else if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS376)  ) {
						if(answerText.equals("$$")) {
							continue;
						}
						String code=getFirstPart(answerText);
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationDT, code, RenderConstants.OBSERVATION);
						
					}else {
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationDT, answerText, RenderConstants.OBSERVATION);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_CODED_DB)) {
					if(obsValueCodedDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueCodedDT = new ObsValueCodedDT();
						obsValueCodedDT.setObservationUid(aObservationUid);
						obsValueCodedDT.setItNew(true);
					}
					if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS367)) {
						String code=getFirstPart(answerText);
						String description=getSecondPart(answerText);
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueCodedDT, code, RenderConstants.OBS_VALUE_CODED);
						if(obsValueCodedDT.getCode()==null || obsValueCodedDT.getCode().equals("")) {
							obsValueCodedDT.setCode("");
						}
						obsValueCodedDT.setDisplayName(description);
					}
					if(observationVO.getTheObsValueCodedDTCollection()==null ||observationVO.getTheObsValueCodedDTCollection().size()==0) {
						observationVO.setTheObsValueCodedDTCollection(new ArrayList<Object>());
						observationVO.getTheObsValueCodedDTCollection().add(obsValueCodedDT);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_DATE_DB)) {
					if(obsValueDateDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueDateDT = new ObsValueDateDT();
						obsValueDateDT.setObservationUid(aObservationUid);
						obsValueDateDT.setItNew(true);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueDateDT, answerText, RenderConstants.OBS_VALUE_DATE);
					if(observationVO.getTheObsValueDateDTCollection()==null) {
						observationVO.setTheObsValueDateDTCollection(new ArrayList<Object>());
						obsValueDateDT.setObsValueDateSeq(observationVO.getTheObsValueDateDTCollection().size());
						observationVO.getTheObsValueDateDTCollection().add(obsValueDateDT);
					}
					
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_NUMERIC_DB)) {
					if(obsValueNumericDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueNumericDT = new ObsValueNumericDT();
						obsValueNumericDT.setObservationUid(aObservationUid);
						obsValueNumericDT.setItNew(true);
					}
					
					if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS372)){
						answerText=getFirstPart(answerText);
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueNumericDT, answerText, RenderConstants.OBS_VALUE_NUMERIC);
					}else if( nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS369)){
						obsValueNumericDT.setNumericValue(answerText);
						obsValueNumericDT.setNumericScale1(null);
						obsValueNumericDT.setNumericScale2(null);
						ObservationUtil obsUtil = new ObservationUtil();
						obsValueNumericDT = obsUtil.parseNumericResult(obsValueNumericDT);
						if(obsValueNumericDT.getNumericValue1()!=null && obsValueNumericDT.getComparatorCd1()==null) {
							obsValueNumericDT.setComparatorCd1("=");
						}
					}
					if(observationVO.getTheObsValueNumericDTCollection()==null || observationVO.getTheObsValueNumericDTCollection().size()==0) {
						observationVO.setTheObsValueNumericDTCollection(new ArrayList<Object>());
						obsValueNumericDT.setObsValueNumericSeq(observationVO.getTheObsValueNumericDTCollection().size());
						observationVO.getTheObsValueNumericDTCollection().add(obsValueNumericDT);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_TXT_DB)
						&& nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS375)) {
					if(obsValueLABNBS375TxtDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueLABNBS375TxtDT = new ObsValueTxtDT();
						obsValueLABNBS375TxtDT.setObservationUid(aObservationUid);
						obsValueLABNBS375TxtDT.setItNew(true);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueLABNBS375TxtDT, answerText, RenderConstants.OBS_VALUE_TXT);
					if(observationVO.getTheObsValueTxtDTCollection()==null) {
						observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
						obsValueLABNBS375TxtDT.setTxtTypeCd(NEDSSConstants.NO);
						obsValueLABNBS375TxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size()+1);
						observationVO.getTheObsValueTxtDTCollection().add(obsValueLABNBS375TxtDT);
					}
				}/*else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_TXT)
						&& nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB208)) {
					if(obsValueLAB208TxtDT==null) {
						obsValueLAB208TxtDT = new ObsValueTxtDT();
						obsValueLAB208TxtDT.setItNew(true);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueLAB208TxtDT, answerText, RenderConstants.OBS_VALUE_TXT);
					
					if(observationVO.getTheObsValueTxtDTCollection()==null) {
						observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
					}
					obsValueLAB208TxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size()+1);
					observationVO.getTheObsValueTxtDTCollection().add(obsValueLAB208TxtDT);
				}*/
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBSERVATION_INTERP_DB)) {
					if(observationInterpDT==null) {
						if (answerText.equals("")){
							continue;
						}
						observationInterpDT = new ObservationInterpDT();
						observationInterpDT.setObservationUid(aObservationUid);
						observationInterpDT.setItNew(true);
					}
					String code=getFirstPart(answerText);
					String description=getSecondPart(answerText);
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationInterpDT, code, RenderConstants.OBSERVATION_INTERP);
					observationInterpDT.setInterpretationDescTxt(description);

					if(observationVO.getTheObservationInterpDTCollection()==null  ||observationVO.getTheObservationInterpDTCollection().size()==0) {
						observationVO.setTheObservationInterpDTCollection(new ArrayList<Object>());
						observationVO.getTheObservationInterpDTCollection().add(observationInterpDT);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBSERVATION_REASON)) {
					if(observationReasonDT==null) {
						if (answerText.equals("")){
							continue;
						}
						observationReasonDT = new ObservationReasonDT();
						observationReasonDT.setItNew(true);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationReasonDT, answerText, RenderConstants.OBSERVATION_REASON);
					if(observationVO.getTheObservationReasonDTCollection()==null) {
						observationVO.setTheObservationReasonDTCollection(new ArrayList<Object>());
						observationVO.getTheObservationReasonDTCollection().add(observationReasonDT);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation().contains(RenderConstants.OBS_VALUE_CODED_MOD)) {
					if(obsValueCodedModDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueCodedModDT = new ObsValueCodedModDT();
						obsValueCodedModDT.setObservationUid(aObservationUid);
						obsValueCodedModDT.setItNew(true);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueCodedModDT, answerText, RenderConstants.OBS_VALUE_CODED_MOD);
					if(observationVO.getTheObsValueCodedModDTCollection()==null) {
						observationVO.setTheObsValueCodedModDTCollection(new ArrayList<Object>());
						observationVO.getTheObsValueCodedModDTCollection().add(obsValueCodedModDT);
					}						
				}
				
				if(getQuestionToLegacyResultedMap().get(observationDT.getCd())!=null) {
					observationDT.setCd(getQuestionToLegacyResultedMap().get(observationDT.getCd()));
				}
				
				observationVO.setTheObservationDT(observationDT);
				LabCommonUtil.updateObsVOForHiddenVarible(observationVO, labId);
				if(observationDT.getStatusCd()==null) {
					observationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				}
				if(observationDT.getStatusTime()==null) {
					observationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
				}
			}
			 observationVO.getTheObservationDT().setAddReasonCd(NEDSSConstants.OBS_ADD_REASON_CD);
			 
			 //For Delete Logic
			 /*
			 if(observationUid!=null)
				 resultedtTestMap.remove(observationUid);
			 */
			 /**@TODO PKS: As of Dec 5 2018: this is a temporary workaround as the hidden variable are still not getting populated properly */			 
				 //observationVO.getTheObservationDT().setCd("TEST PKS"); 
				 //observationVO.getTheObservationDT().setCdDescTxt("TEST PKS Description"); 
				 
			/*Default variables for Result*/	 
				observationVO.getTheObservationDT().setCtrlCdDisplayForm(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
				observationVO.getTheObservationDT().setElectronicInd("N");
				observationVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.R_RESULT);
				
				 if(organismIndicator) {
					 observationVO.getTheObservationDT().setCtrlCdUserDefined1("Y");
				 }

		}
		catch (Exception e) {
			String errorText="LabSusceptibilityUtil.populateObservation :  key is " +akey+"\n";
			String errorText1="LabSusceptibilityUtil.populateObservation : Error processing transferBeanValueForDT for NbsQuestionMetadata:"+ nbsQuestionMetadata.toString()+"\n";
			String errorText2="LabSusceptibilityUtil.populateObservation : Error processing transferBeanValueForDT for User value:"+ answerText+"\n";
			logger.error(errorText +errorText2 + errorText2 +e.getMessage() );
			throw new NEDSSAppException(errorText + errorText1 +errorText2 +"\n"+ e.toString());
		}
		return observationVO;
	}
	
	/**
	 * Method to get questionToLegacySusceptabilityMap
	 * @return
	 */
	private static Map<String,String> getQuestionToLegacyResultedMap(){
		if(questionToLegacySusceptabilityMap==null){
			legacySusceptabilityMap();
		}
		return questionToLegacySusceptabilityMap;
	}
	
	/**
	 * Convenient method to get First part of the code from batch entry
	 * @param code
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String getFirstPart(String code) throws NEDSSAppException {
		String returnCode = "";
		try {
			if(code.indexOf("$$")>0) {
				returnCode= code.substring(0, code.indexOf("$$"));
			}else {
				returnCode =code;
			}
		} catch (Exception e) {
			throw new NEDSSAppException("LabSusceptibilityUtil.getFirstPart Error thrown for code: "+ code);
		}
		return returnCode;
	}
	
	/**
	 * Convenient method to get second part of the code from batch entry
	 * @param code
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String getSecondPart(String code) throws NEDSSAppException {
		String returnCode = "";
		try {
			if(code.indexOf("$$")>0) {
				returnCode = code.substring(code.indexOf("$$")+2, code.length());
			}else {
				returnCode =code;
			}
		} catch (Exception e) {
			throw new NEDSSAppException("LabSusceptibilityUtil.getSecondPart Error thrown for code: "+ code);
		}
		return returnCode;
	}

	/**
	 * Convenient method to combine code with Description for front end batch entry
	 * @param code
	 * @param codeSetName
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String combineCodeDescriptionForBatchEntry(String code, String codeSetName) throws NEDSSAppException {
		String codeWithDescription = "";
		try {
			String description =LabPageUtil.getDescTxt(codeSetName,code);
			
			codeWithDescription  = code + "$$" + description;
		} catch (NEDSSAppException e) {
			String error1 = "LabSusceptibilityUtil.combineCodeDescriptionForBatchEntry Error thrown for code:"+ code;
			String error2 = "\nLabSusceptibilityUtil.combineCodeDescriptionForBatchEntry Error thrown for codeSetName:"+ codeSetName;
			
			throw new NEDSSAppException(error1 + error2);
		}
		return codeWithDescription;
		
	}
	
	@SuppressWarnings("unchecked")
	public  void setSusceptabilityObservationForUI(boolean electronicIndicator, int resultedTestToSusOrTrackLinkKey, ObservationVO resultedTestVO, 
					HttpServletRequest request) throws NEDSSAppException {
		ObservationDT observationDT= null;
		ObservationVO observationVO = null;
		ArrayList<BatchEntry> list =  new ArrayList<BatchEntry>();
		Long susceptabilityUid = null;
		Map<Object, ArrayList<BatchEntry>> batchEntrymap = new HashMap<Object, ArrayList<BatchEntry>>();
		try {
			Collection<Object> coll=  resultedTestVO.getTheActRelationshipDTCollection();
			Iterator<Object> it = coll.iterator();
			while(it.hasNext()) {
				ActRelationshipDT actrelLAB222 = (ActRelationshipDT)it.next();
                    Long sourceActUidLAB222 = actrelLAB222.getSourceActUid();
                    if(susceptibilityMap!=null && susceptibilityMap.get(sourceActUidLAB222)!=null) {
                    ObservationVO dependentObsVO= (ObservationVO)susceptibilityMap.get(sourceActUidLAB222);
                    
                    Collection<Object> collection =dependentObsVO.getTheActRelationshipDTCollection();
                    if(collection.size()>0) {
                    	Iterator<Object> iterator =collection.iterator();
                    	while(iterator.hasNext()) {
                    		ActRelationshipDT arDT = (ActRelationshipDT)iterator.next();
                    		susceptabilityUid = arDT.getSourceActUid();
                    		observationVO = (ObservationVO)susAndTrackIsolateVOMap.get(susceptabilityUid);
        						BatchEntry batchEntry =  new BatchEntry();
        						HashMap<String, String> beMap = new HashMap<String, String>();
        		
        						observationDT =observationVO.getTheObservationDT();
        						//ctrlCdUserDefined1 =observationVO.getTheObservationDT().getCtrlCdUserDefined1();
            					
        						if(observationDT.getActivityToTime()!=null)
        							beMap.put(PageConstants.NBS405, observationDT.getActivityToTime().toString());
        						
        						beMap.put(PageConstants.NBS457, observationDT.getElectronicInd());
        						
        						beMap.put(PageConstants.NBS458, observationDT.getObservationUid().toString());
        	
        						if(electronicIndicator) {
        							String mask = LabCommonUtil.obsTestCodeMaskForELR(observationDT);
        							beMap.put(PageConstants.NBS_LAB293_1, mask);
        						}
        						if(electronicIndicator && observationDT.getCd()!=null) {
        							String mask = LabCommonUtil.observationMaskForELR(observationDT);
        							beMap.put(PageConstants.NEW_LAB110, mask);
        							
        						}else if(observationDT.getCd()!=null) {
        							beMap.put(PageConstants.NEW_LAB110+PageConstants.CODEID , observationDT.getCd());
    								beMap.put(PageConstants.NEW_LAB110+PageConstants.DESCRIPTIONID ,observationDT.getCdDescTxt() +"("+ observationDT.getCd()+")"); 
    								beMap.put(PageConstants.NEW_LAB110+PageConstants.DESCRIPTION ,observationDT.getCdDescTxt());
    								beMap.put(PageConstants.NEW_LAB110, observationDT.getCd() +"$$"+ observationDT.getCdDescTxt());
    							
        						}if(observationDT.getActivityFromTime()!=null)
        							beMap.put(PageConstants.NEW_NBS405, observationDT.getActivityFromTime().toString());
        						
        						if(observationDT.getMethodCd()==null) {
        							beMap.put(PageConstants.NBS377, "$$");
            					}else {
        							String codeWithDesciption = "";
        							if(electronicIndicator) {
	        							if(observationDT.getMethodDescTxt()!=null && observationDT.getMethodCd()!=null)
											codeWithDesciption=observationDT.getMethodDescTxt() +"("+observationDT.getMethodCd()+")";
	        							else if(observationDT.getMethodCd()!=null)
	        								codeWithDesciption =observationDT.getMethodCd();
	        							beMap.put(PageConstants.NBS377,codeWithDesciption);
        							}else {
	        							if(observationDT.getMethodDescTxt()!=null && observationDT.getMethodCd()!=null)
	        								codeWithDesciption = observationDT.getMethodCd() + "$$" + observationDT.getMethodDescTxt();
	        							else if(observationDT.getMethodCd()!=null)
	        								codeWithDesciption = combineCodeDescriptionForBatchEntry(observationDT.getMethodCd(), "OBS_METH_SUSC");
	        							beMap.put(PageConstants.NBS377,codeWithDesciption);
        							}
										//codeWithDesciption= codeWithDesciption +  "$$" +observationDT.getMethodCd();
        						
        						}
        						if(observationDT.getStatusCd()!=null) {
        							String codeWithDesciption = combineCodeDescriptionForBatchEntry(observationDT.getStatusCd(), "ACT_OBJ_ST");
        							beMap.put(PageConstants.NBS376, codeWithDesciption);
        						}
        						Collection<Object> obsValueTxtColl = observationVO.getTheObsValueTxtDTCollection();
        						StringBuffer testBuffer = new StringBuffer();
        						StringBuffer commentBuffer = new StringBuffer();
        						if(obsValueTxtColl!=null) {
        							Iterator<Object> iter = obsValueTxtColl.iterator();
        							while (iter.hasNext()) {
        								ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) iter.next();
        								if(obsValueTxtDT.getTxtTypeCd()!=null && obsValueTxtDT.getTxtTypeCd().equals("O")) {
        									if(testBuffer.length()>0)
        										testBuffer =  testBuffer.append("<br>").append(obsValueTxtDT.getValueTxt());
        									else
        										testBuffer =  testBuffer.append(obsValueTxtDT.getValueTxt());
        									beMap.put(PageConstants.NBS365, testBuffer.toString());							

        								}
        								else { 
        									if(commentBuffer.length()>0)
        										commentBuffer =  commentBuffer.append("<br>").append(obsValueTxtDT.getValueTxt());
        									else
        										commentBuffer =  commentBuffer.append(obsValueTxtDT.getValueTxt());
        									beMap.put(PageConstants.NBS375, commentBuffer.toString());							
        								}
        							}	
        						}
        						//beMap.put(PageConstants.NBS375, obsValueTxtDT.getValueTxt());
        						Collection<Object> obsValueCodedColl = observationVO.getTheObsValueCodedDTCollection();
        						if(obsValueCodedColl!=null) {
        							Iterator<Object> iter = obsValueCodedColl.iterator();
        							while (iter.hasNext()) {
        								ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) iter.next();
    									String codeWithDesciption = obsValueCodedDT.getCode()+"$$"+ obsValueCodedDT.getDisplayName();
        								if(!electronicIndicator && obsValueCodedDT.getCode()!=null){ 
        									if(obsValueCodedDT.getCode()!=null && obsValueCodedDT.getCode().equals("NI")){
        										codeWithDesciption ="";
        									}else {
        										codeWithDesciption = obsValueCodedDT.getCode()+"$$"+ obsValueCodedDT.getDisplayName();
        									}
        									beMap.put(PageConstants.NBS367, codeWithDesciption);
        								}else if(electronicIndicator)  {
        									if(obsValueCodedDT.getDisplayName()!=null) {
        										
        										String maskResult = LabCommonUtil.obsValueCodedMaskForELR(obsValueCodedDT);
             									
        										beMap.put(PageConstants.NBS367, maskResult);
        									}
         									String mask = LabCommonUtil.obsCodedTestMaskForELR(obsValueCodedDT);
         									beMap.put("NBS_LAB121_1", mask);
        								}
        							}
        						}
        						Collection<Object> obsValueNumericColl = observationVO.getTheObsValueNumericDTCollection();
        						if(obsValueNumericColl!=null) {
        							Iterator<Object> iter = obsValueNumericColl.iterator();
        							while (iter.hasNext()) {
        								ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT) iter.next();
        								if(obsValueNumericDT.getHighRange()!=null ) {
        									beMap.put(PageConstants.NBS373, obsValueNumericDT.getHighRange());
        								}
        								if(obsValueNumericDT.getLowRange()!=null ) {
        									beMap.put(PageConstants.NBS374, obsValueNumericDT.getLowRange());
        								}
        								if(obsValueNumericDT.getNumericUnitCd()!=null ) {
        									beMap.put(PageConstants.NBS372, obsValueNumericDT.getNumericUnitCd());
        								}
        								if(obsValueNumericDT!=null ) {
        									beMap.put(PageConstants.NBS369,  LabCommonUtil.getStringFromObsValueNumericDT(obsValueNumericDT));
        								}
        							}
        						}
        						Collection<Object> obsInterpColl = observationVO.getTheObservationInterpDTCollection();
        						beMap.put(PageConstants.NBS378, "$$");
        						if(obsInterpColl!=null) {
        							Iterator<Object> iter = obsInterpColl.iterator();
        							while (iter.hasNext()) {
        								ObservationInterpDT observationInterpDT = (ObservationInterpDT) iter.next();
        								if(observationInterpDT.getInterpretationCd()!=null) {
        									String codeWithDesciption = combineCodeDescriptionForBatchEntry(observationInterpDT.getInterpretationCd(), "OBS_INTRP");
        									
        									beMap.put(PageConstants.NBS378, codeWithDesciption);
        								}
        							}
        						}
        						beMap.put(NEDSSConstants.CURRENT_KEY, new Integer(resultedTestToSusOrTrackLinkKey).toString());
        						batchEntry.setAnswerMap(beMap);
        						//this field will be compared for updates
        						batchEntry.setLocalId(observationDT.getLocalId());
        						batchEntry.setId(PageForm.getNextId());
        						batchEntry.setSubsecNm("NBS_UI_2");
        						list.add(batchEntry);
        	    //    }
                    }
                    HashMap<String, Object> subFormHashMap =null;
                    PageSubForm pageSubForm = new PageSubForm();
                    batchEntrymap.put("NBS_UI_2", list);	
                	pageSubForm.setBatchEntryMap(batchEntrymap);
						
                    if(request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP)==null) {
                    	subFormHashMap = new HashMap<String, Object>();
                    }else {
                    	subFormHashMap = (HashMap<String, Object>) request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
                    }
                	subFormHashMap.put("NBS_LAB222Button_"+resultedTestToSusOrTrackLinkKey, pageSubForm); 
                	request.getSession().setAttribute(NEDSSConstants.SUBFORM_HASHMAP, subFormHashMap);

						
			 		/*if(subFormHashMap!=null && subFormHashMap.get("NBS_LAB222Button_"+resultedTestToSusOrTrackLinkKey) !=null) {
			 				PageSubForm pageSubForm = (PageSubForm)subFormHashMap.get("NBS_LAB222Button_"+resultedTestToSusOrTrackLinkKey);
			 				Map<Object, ArrayList<BatchEntry>> map = pageSubForm.getBatchEntryMap();
			 				String key =(String) LabPageUtil.getFirstElementOfCollection(map.keySet());
			 				ArrayList<BatchEntry> previouslist = map.get(key);
			 				previouslist.add(batchEntry);
			 			}
		 			}else {
		 				PageSubForm pageSubForm  = new PageSubForm();
		 				HashMap<String, Object> subFormHashMap = new HashMap<String, Object>();
		 				Map<Object, ArrayList<BatchEntry>> map = pageSubForm.getBatchEntryMap();
		 				list.add(batchEntry);
		 				map.put("NBS_UI_2", list);
		 				subFormHashMap.put("NBS_LAB222Button_"+resultedTestToSusOrTrackLinkKey, list);
		 			}*/
				}
			}
			}
		} catch (Exception e) {
			String error1="LabSusceptibilityUtil.setSusceptabilityObservationForUI Exception thrown for observationDT:"+ observationDT.toString()+"\n";
			logger.error(error1 +  e); 
    		String errorText="LabSusceptibilityUtil.setSusceptabilityObservationForUI Exception thrown for susceptibilityUid :"+ susceptabilityUid+"\n";
			logger.error(errorText +  e); 
    		logger.error("LabSusceptibilityUtil.setSusceptabilityObservationForUI Exception " +  e.getMessage()); 
    		throw new NEDSSAppException(errorText+errorText+ e.getMessage(), e);
		}

	}
	
	
}