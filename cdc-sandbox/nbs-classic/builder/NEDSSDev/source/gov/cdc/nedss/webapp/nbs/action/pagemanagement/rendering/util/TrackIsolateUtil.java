package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.events.investigations.util.CaseCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.form.page.PageSubForm;
/**
 * This class has been created for TrackIsolate within the Resulted Test. This handles Add, Edit, Delete of TrackIsolate
 * @Component : lab report
 * @author Pradeep Kumar Sharma
 * @since 2019
 * @version 6.0
 *
 */ 
public class TrackIsolateUtil {
	static final LogUtils logger = new LogUtils(TrackIsolateUtil.class.getName());
	private ObservationVO rootObservationVO = null;
	private LabResultProxyVO labResultProxyVO = null;
	private static Map<Long, Object> trackIsolateVOMap  = null;
	private Map<Long, Object> track329Map  = null;
	private Map<Long, Object> aRMap = null;
	private static ArrayList<Long> updatedObservationUidList = new ArrayList<Long>();
	private static Map<String,String> legacyToNewIsolateMap;
	private static Map<String,String> newToLegacyQuestionIsolateMap;
	Map<String, Object> trackIsolateObservationMap  = new HashMap<String, Object>();
	

	public static String[] TRACK_ISOLATE_TEST_OBSERVATION= {"NBS_LAB336", "NBS_LAB334", "NBS_LAB351", "NBS_LAB362", "NBS_LAB357", "NBS_LAB361", 
			"NBS_LAB356", "NBS_LAB355", "NBS_LAB338", "NBS_LAB331", "NBS_LAB363", "NBS_LAB346", "NBS_LAB345", "NBS_LAB350", "NBS_LAB349", 
			"NBS_LAB360", "NBS_LAB354", "NBS_LAB337", "NBS_LAB339", "NBS_LAB341", "NBS_LAB343", "NBS_LAB332", "NBS_LAB333", "NBS_LAB347", 
			"NBS_LAB353", "NBS_LAB359", "NBS_LAB352", "NBS_LAB340", "NBS_LAB342", "NBS_LAB344", "NBS_LAB335", "NBS_LAB348", "NBS_LAB358" };
	
	
	/**
	 * Constructor to reset values and create default values for variables
	 * @param rootObservationVO
	 * @param labResultProxyVO
	 * @param consolidatedMap
	 */
	public TrackIsolateUtil(ObservationVO rootObservationVO, LabResultProxyVO labResultProxyVO,
			Map<String, Map<Long, Object>> consolidatedMap){
		updatedObservationUidList =new ArrayList<Long>();
		this.rootObservationVO =rootObservationVO;
		this.labResultProxyVO = labResultProxyVO;
		
		trackIsolateVOMap= ((Map<Long, Object>)consolidatedMap.get(PageConstants.TRACK_ISOLATE_TEST));
		if(trackIsolateVOMap==null)
			trackIsolateVOMap  = new HashMap<Long, Object>();
		track329Map= ((Map<Long, Object>)consolidatedMap.get(PageConstants.TRACK_329_VO_TEST));
		if(track329Map==null)
			track329Map  = new HashMap<Long, Object>();
		aRMap = consolidatedMap.get(PageConstants.AR_MAP_FOR_LAB);
		
		
	}

	/**
	 *Default map for legacy and new lab question identifiers
	 */
	public static void legacyQuestionIsolateMap(){
		legacyToNewIsolateMap=  new HashMap<String, String>();
		legacyToNewIsolateMap.put("LAB336",  "NBS_LAB336");
		legacyToNewIsolateMap.put("LAB334",  "NBS_LAB334");
		legacyToNewIsolateMap.put("LAB351",  "NBS_LAB351");
		legacyToNewIsolateMap.put("LAB362",  "NBS_LAB362");
		legacyToNewIsolateMap.put("LAB357",  "NBS_LAB357");
		legacyToNewIsolateMap.put("LAB361",  "NBS_LAB361");
		legacyToNewIsolateMap.put("LAB356",  "NBS_LAB356");
		legacyToNewIsolateMap.put("LAB355",  "NBS_LAB355");
		legacyToNewIsolateMap.put("LAB338",  "NBS_LAB338");
		legacyToNewIsolateMap.put("LAB331",  "NBS_LAB331");
		legacyToNewIsolateMap.put("LAB363",  "NBS_LAB363");
		legacyToNewIsolateMap.put("LAB346",  "NBS_LAB346");
		legacyToNewIsolateMap.put("LAB345",  "NBS_LAB345");
		legacyToNewIsolateMap.put("LAB350",  "NBS_LAB350");
		legacyToNewIsolateMap.put("LAB349",  "NBS_LAB349");
		legacyToNewIsolateMap.put("LAB360",  "NBS_LAB360");
		legacyToNewIsolateMap.put("LAB354",  "NBS_LAB354");
		legacyToNewIsolateMap.put("LAB337",  "NBS_LAB337");
		legacyToNewIsolateMap.put("LAB339",  "NBS_LAB339");
		legacyToNewIsolateMap.put("LAB341",  "NBS_LAB341");
		legacyToNewIsolateMap.put("LAB343",  "NBS_LAB343");
		legacyToNewIsolateMap.put("LAB332",  "NBS_LAB332");
		legacyToNewIsolateMap.put("LAB333",  "NBS_LAB333");
		legacyToNewIsolateMap.put("LAB347",  "NBS_LAB347");
		legacyToNewIsolateMap.put("LAB353",  "NBS_LAB353");
		legacyToNewIsolateMap.put("LAB359",  "NBS_LAB359");
		legacyToNewIsolateMap.put("LAB352",  "NBS_LAB352");
		legacyToNewIsolateMap.put("LAB340",  "NBS_LAB340");
		legacyToNewIsolateMap.put("LAB342",  "NBS_LAB342");
		legacyToNewIsolateMap.put("LAB344",  "NBS_LAB344");
		legacyToNewIsolateMap.put("LAB335",  "NBS_LAB335");
		legacyToNewIsolateMap.put("LAB348",  "NBS_LAB348");
		legacyToNewIsolateMap.put("LAB358",  "NBS_LAB358");

		
		newToLegacyQuestionIsolateMap =  new HashMap<String, String>();
		newToLegacyQuestionIsolateMap = 
				legacyToNewIsolateMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
	}
		
	public static Map<String,String> getQuestionToLegacyMap(){
		if(newToLegacyQuestionIsolateMap==null){
			legacyQuestionIsolateMap();
		}
		return newToLegacyQuestionIsolateMap;
	}
	
	/**
	 * This process creates the Track Isolate 
	 * @param tempUid
	 * @param currentKey
	 * @param resultedTestObservationVO
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 */
	@SuppressWarnings("unchecked")
	public Long setTrackIsolateQuestion(Long tempUid, String currentKey, ObservationVO resultedTestVO, 
			PageSubForm pageSubForm) throws NEDSSAppException{
		ObservationVO observation329VO=null;
		Collection<Object> obsActRelationshipColl = null;
		Map<String, Object> trackIsolateObservation  = null;
		int lab220Counter = 0;
		try {
			trackIsolateObservationMap  = (Map<String, Object>)trackIsolateVOMap.get(resultedTestVO.getTheObservationDT().getObservationUid());
			Map<Object, Object> answerMap = pageSubForm.getPageClientVO().getAnswerMap();
			List<String> list= LabPageUtil.toList(TRACK_ISOLATE_TEST_OBSERVATION);
			Long lab329Uid= null;
			Set<Object> set = answerMap.keySet();
				Iterator<Object> iterator = set.iterator();
				while(iterator.hasNext()) {
					String newkey = (String)iterator.next();
					String key ="";
					if(newToLegacyQuestionIsolateMap.get(newkey)!=null) {
						key =newToLegacyQuestionIsolateMap.get(newkey);
					}else
						continue;
					String value = (String)answerMap.get(newkey);
					if(value ==null || value.equals("") || value.equals("$$") ) {
						value="";
					}
					if(value !=null) {
						ObservationVO observationVO = null;
						ObservationVO oldObservationVO=null;
						try {
							if(trackIsolateObservation==null && resultedTestVO.getTheActRelationshipDTCollection()!=null) {
								Collection<Object> coll = resultedTestVO.getTheActRelationshipDTCollection();
								Iterator<Object> it = coll.iterator();
								while(it.hasNext()) {
									ActRelationshipDT arDT =(ActRelationshipDT)it.next();
									if(arDT.getTypeCd().equals(NEDSSConstants.ACT109_TYP_CD)) {
										if(trackIsolateVOMap!=null && trackIsolateVOMap.get(arDT.getSourceActUid())!=null){
											trackIsolateObservation  = (Map<String, Object>)trackIsolateVOMap.get(arDT.getSourceActUid());
										}
									}
								}
							}
							if(trackIsolateObservation==null && value.equals(""))
								continue;
							if(trackIsolateObservation!=null && trackIsolateObservation.get(key)!=null) {
								oldObservationVO = (ObservationVO)trackIsolateObservation.get(key);
							}
							/*if(trackIsolateVOMap!=null && trackIsolateVOMap.get(resultedTestVO.getTheObservationDT().getObservationUid())!=null) {
								Map<String, Object> trackIsolateObservation  = (Map<String, Object>)trackIsolateVOMap.get(resultedTestVO.getTheObservationDT().getObservationUid());
								if(trackIsolateObservation.get(key)!=null) {
									oldObservationVO = (ObservationVO)trackIsolateObservation.get(key);
								}
							}*/
							if(oldObservationVO==null && value.equals(""))
								continue;
							observationVO = populateObservationVO(oldObservationVO, newkey, value, TRACK_ISOLATE_TEST_OBSERVATION, pageSubForm.getPageFormCd());
							if(observation329VO ==null && track329Map.size()>0 && resultedTestVO.getTheActRelationshipDTCollection()!=null && resultedTestVO.getTheActRelationshipDTCollection().size()>0) {
									Collection<Object> arCollection =resultedTestVO.getTheActRelationshipDTCollection();
									Iterator<Object> arIterator = arCollection.iterator();
									while(arIterator.hasNext()) {
										ActRelationshipDT ar329Lab=(ActRelationshipDT)arIterator.next();
										if(ar329Lab.getSourceClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
												ar329Lab.getTargetClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
												ar329Lab.getTypeCd().equals(NEDSSConstants.ACT109_TYP_CD)) {
											Long ar329LabUid = ar329Lab.getSourceActUid();
											observation329VO =(ObservationVO)track329Map.get(ar329LabUid);
											if(observation329VO!=null) {
												obsActRelationshipColl = observation329VO.getTheActRelationshipDTCollection();
												break;	 
											}
										}
									}
								}
								if(observationVO.getTheObservationDT().getObservationUid()==null)
									observationVO.getTheObservationDT().setObservationUid(new Long(tempUid--));

								if(observation329VO==null) {
									observation329VO = LabPageUtil.createObservationVO(tempUid--, NEDSSConstants.LAB_329, NEDSSConstants.LAB329CD_DESC_TXT, "2.16.840.1.114222.4.5.1", NEDSSConstants.NEDSS_BASE_SYSTEM, null, null, NEDSSConstants.STATUS_CD_D, NEDSSConstants.ACTIVE, NEDSSConstants.I_ORDER);
									
									labResultProxyVO.getTheObservationVOCollection().add(observation329VO);
									ActRelationshipDT actRelationship115DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation329VO.getTheObservationDT().getObservationUid(),
											NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
											rootObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
											NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115);
									if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
										labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
									}
									labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship115DT);
									
									ActRelationshipDT actRelationship109DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation329VO.getTheObservationDT().getObservationUid(),
											NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
											resultedTestVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
										NEDSSConstants.ACT109_TYP_CD, NEDSSConstants.TYPE_DESC_TXT_109);
									
									labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship109DT);
								}
								
								if(observationVO.getTheObservationDT().getObservationUid()<0) {
									ActRelationshipDT actRelationship110DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observationVO.getTheObservationDT().getObservationUid(),
									NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
									observation329VO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
									NEDSSConstants.TYPE_CD_110, NEDSSConstants.TYPE_DESC_TXT_110);
							
									if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
										labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
									}
									labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship110DT);
								}
							
							labResultProxyVO.getTheObservationVOCollection().add(observationVO);
						} catch (Exception exception) {
							logger.error(exception);
							logger.error("TrackIsolateUtil.setTrackIsolateQuestion exception thrown for Iterator: "+exception);
							logger.error("TrackIsolateUtil.setTrackIsolateQuestion exception getMessage thrown for Iterator: "+exception.getMessage());
							throw new NEDSSAppException("TrackIsolateUtil.setTrackIsolateQuestion Error thrown for Iterator: "+ exception.getMessage());
					}
				
				}
			}
				
					
			/**Delete scenario
			if(trackIsolateVOMap.size()>0 && obsActRelationshipColl!=null && obsActRelationshipColl.size()>0 
					&& resultedTestVO.getTheObservationDT().getObservationUid()>0 ) {
				
				deleteAll(resultedTestVO);
			}
			*/
		} catch (Exception exception) {
			logger.error(exception);
			logger.error("TrackIsolateUtil.setTrackIsolateQuestion exception thrown : "+exception);
			logger.error("TrackIsolateUtil.setTrackIsolateQuestion exception getMessage thrown : "+exception.getMessage());
			throw new NEDSSAppException("TrackIsolateUtil.setTrackIsolateQuestion Error thrown : "+ exception.getMessage());
		}
		return tempUid;
	}
	
	
	@SuppressWarnings("unchecked")
	public void deleteAll(ObservationVO resultedTestVO) throws NEDSSAppException {
		ObservationVO observation329VO=null;
		Collection<Object> obsActRelationshipColl = null;
		int lab220Counter = 0;
		ActRelationshipDT ar329Lab=null;
		boolean ifActLab115AccountedFor=false;
		try {
			if(track329Map.size()>0 && resultedTestVO.getTheActRelationshipDTCollection()!=null && resultedTestVO.getTheActRelationshipDTCollection().size()>0) {
				Collection<Object> arCollection =resultedTestVO.getTheActRelationshipDTCollection();
				Iterator<Object> arIterator = arCollection.iterator();
				while(arIterator.hasNext()) {
					ar329Lab =(ActRelationshipDT)arIterator.next();
					if(ar329Lab.getSourceClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
							ar329Lab.getTargetClassCd().equals(NEDSSConstants.CLASS_CD_OBS) &&
							ar329Lab.getTypeCd().equals(NEDSSConstants.ACT109_TYP_CD)) {
						Long ar329LabUid = ar329Lab.getSourceActUid();
						observation329VO =(ObservationVO)track329Map.get(ar329LabUid);
						if(observation329VO!=null)
							obsActRelationshipColl = observation329VO.getTheActRelationshipDTCollection();
					}
				}
			}
			if(obsActRelationshipColl==null) {
				logger.debug("TrackIsolateUtil.deleteAll:There is no track isolate case mapped to this resulted Test  for resultedt Test Uid:"+resultedTestVO.getTheObservationDT().toString());
				return;
			}
			Iterator<Object> it  = obsActRelationshipColl.iterator();
			if(it.hasNext()) {
				ActRelationshipDT actRelationshipDT= (ActRelationshipDT)it.next();
				if(!updatedObservationUidList.contains(actRelationshipDT.getSourceActUid())) {
					Long observationDeletedUid=actRelationshipDT.getSourceActUid();
					if(trackIsolateVOMap.get(actRelationshipDT.getTargetActUid())!=null) {
						//if(updatedUid.compareTo(observationDeleteUid)==0) {
						lab220Counter++;
						ActRelationshipDT ar329DT= (ActRelationshipDT)aRMap.get(observationDeletedUid);
						Long obs22Uid =ar329DT.getTargetActUid();
						if(observation329VO==null)
							observation329VO =(ObservationVO)track329Map.get(obs22Uid);
						
						HashMap<Long, Object> hashmap =(HashMap<Long, Object>)trackIsolateVOMap.get(actRelationshipDT.getTargetActUid());
						Collection<Object> coll = hashmap.values();
						if(coll!=null) {
							Iterator<Object> iterator = coll.iterator();
							while(iterator.hasNext()) {
								ObservationVO obsTrackVO =(ObservationVO)iterator.next();
		
								obsTrackVO.setItDelete(true);
								obsTrackVO.getTheObservationDT().setItDelete(true);
								labResultProxyVO.getTheObservationVOCollection().add(obsTrackVO);
		
		
								if(!ifActLab115AccountedFor) {
									ActRelationshipDT actRelationship115DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation329VO.getTheObservationDT().getObservationUid(),
											NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
											rootObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
											NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115);
			 
									actRelationship115DT.setItNew(false);
									actRelationship115DT.setItDelete(true);
									//actRelationship115DT.setItDirty(false);
									if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
										labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
									}
									ifActLab115AccountedFor=true;
									labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship115DT);
									/////////////////

									ActRelationshipDT actRelationship109DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, observation329VO.getTheObservationDT().getObservationUid(),
											NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
											resultedTestVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
											NEDSSConstants.ACT109_TYP_CD, NEDSSConstants.TYPE_DESC_TXT_109);
			
									actRelationship109DT.setItNew(false);
									actRelationship109DT.setItDelete(true);
									actRelationship109DT.setItDirty(false);
										if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
											labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
										}
										labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship109DT);

									}
									
								ActRelationshipDT actRelationship110DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsTrackVO.getTheObservationDT().getObservationUid(),
										NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
										observation329VO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
										NEDSSConstants.TYPE_CD_110, NEDSSConstants.TYPE_DESC_TXT_110);
								actRelationship110DT.setItNew(false);
								actRelationship110DT.setItDelete(true);
								actRelationship110DT.setItDirty(false);
								labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship110DT);
		
								
							}
						}
					}
				}
			}
		} catch (Exception exception) {
			logger.error(exception);
			logger.error("TrackIsolateUtil.deleteAll exception thrown for deleteAll: "+exception);
			throw new NEDSSAppException("TrackIsolateUtil.deleteAll Error thrown for deleteAll: ", exception);

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
	public static ObservationVO populateObservationVO(ObservationVO oldObservationVO, String obsCodekey, String value, String[] typeCode, String labFormCode) 
			throws NEDSSAppException {
		ObservationVO observationVO= new ObservationVO(); 
		NbsQuestionMetadata nbsQuestionMetadata = null;
		//String answerText="";
		//String key ="";
		Long aObservationUid=null;

		try {
			legacyQuestionIsolateMap();
			ObservationDT observationDT=null;
			ObsValueCodedDT obsValueCodedDT =null;
			ObsValueDateDT obsValueDateDT = null;
			ObsValueTxtDT  obsValueTxtDT = null;
			
			//New Observation Case
			if(oldObservationVO==null) {
				observationDT= new ObservationDT();
				observationDT.setItNew(true);
				observationVO.setItNew(true);
				observationDT.setItDirty(false);
				observationVO.setItDirty(false);
				
			}
			//Update Case----
			else {
				Long updatedObservationUid= oldObservationVO.getTheObservationDT().getObservationUid();
				observationVO=oldObservationVO;
				updatedObservationUidList.add(updatedObservationUid);
				trackIsolateVOMap.remove(updatedObservationUid);
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
				
				if(observationVO.getTheObsValueTxtDTCollection()!=null && observationVO.getTheObsValueTxtDTCollection().size()>0) {
					Iterator<Object> iter = observationVO.getTheObsValueTxtDTCollection().iterator();
					obsValueTxtDT = (ObsValueTxtDT)iter.next();
					if(obsValueTxtDT.getTxtTypeCd()!=null){
						obsValueTxtDT.setItDirty(false);
					}else {
						obsValueTxtDT.setItDirty(false);
					}
				}
			}
			Map<Object,Object> questionMap =CaseCreateHelper.loadQuestions(labFormCode);	

			nbsQuestionMetadata = (NbsQuestionMetadata)questionMap.get(obsCodekey);
			logger.debug("obsCodekey here is " +obsCodekey);
			logger.debug("nbsQuestionMetadata.getDataLocation() is " +nbsQuestionMetadata.getDataLocation());
			if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_CODED_DB)) {
				if(obsValueCodedDT!=null && value.equals("")) {
					obsValueCodedDT.setCode("");
					obsValueCodedDT.setDisplayName("");
				}
				else if(obsValueCodedDT==null) {
					obsValueCodedDT = new ObsValueCodedDT();
					obsValueCodedDT.setObservationUid(aObservationUid);
					obsValueCodedDT.setItNew(true);
				}
				if(!value.equals("")) {
					String code=LabSusceptibilityUtil.getFirstPart(value);
					String description=LabPageUtil.getDescTxt(nbsQuestionMetadata.getCodeSetNm(), value);
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueCodedDT, code, RenderConstants.OBS_VALUE_CODED);
					obsValueCodedDT.setDisplayName(description);
					if(observationVO.getTheObsValueCodedDTCollection()==null) {
						observationVO.setTheObsValueCodedDTCollection(new ArrayList<Object>());
						observationVO.getTheObsValueCodedDTCollection().add(obsValueCodedDT);
					}
				}
			}
			else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_DATE_DB)
					&& !(obsValueDateDT==null && value.equals(""))) {
				
				if(obsValueDateDT==null) {
					obsValueDateDT = new ObsValueDateDT();
					obsValueDateDT.setObservationUid(aObservationUid);
					obsValueDateDT.setItNew(true);
				}
				DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueDateDT, value, RenderConstants.OBS_VALUE_DATE);
				if(observationVO.getTheObsValueDateDTCollection()==null) {
					observationVO.setTheObsValueDateDTCollection(new ArrayList<Object>());
					obsValueDateDT.setObsValueDateSeq(observationVO.getTheObsValueDateDTCollection().size());
					observationVO.getTheObsValueDateDTCollection().add(obsValueDateDT);
				}
				
			}
			else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_TXT_DB)
					&& !(obsValueTxtDT==null && value.equals(""))) {
				if(obsValueTxtDT==null) {
					obsValueTxtDT = new ObsValueTxtDT();
					obsValueTxtDT.setObservationUid(aObservationUid);
					obsValueTxtDT.setItNew(true);
				}
				DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueTxtDT, value, RenderConstants.OBS_VALUE_TXT);
				if(observationVO.getTheObsValueTxtDTCollection()==null) {
					observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
					//obsValueTxtDT.setTxtTypeCd(NEDSSConstants.NO);
					obsValueTxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size()+1);
					observationVO.getTheObsValueTxtDTCollection().add(obsValueTxtDT);
				}
			}				
			if(newToLegacyQuestionIsolateMap.get(obsCodekey)!=null) {
				observationDT.setCd(newToLegacyQuestionIsolateMap.get(obsCodekey));
			}
			observationVO.setTheObservationDT(observationDT);
			
			observationVO.getTheObservationDT().setAddReasonCd(NEDSSConstants.OBS_ADD_REASON_CD);
			 
			/*Default variables for Result*/	
			observationVO.getTheObservationDT().setCdDescTxt(nbsQuestionMetadata.getQuestionLabel());
			observationVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.I_RESULT);
			observationVO.getTheObservationDT().setRecordStatusCd(NEDSSConstants.ACTIVE);
			if(observationVO.getTheObservationDT().getStatusTime()==null)
				observationVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
			
			observationVO.getTheObservationDT().setCdSystemCd(nbsQuestionMetadata.getQuestionOid());


		}
		catch (Exception e) {
			String errorText="TrackIsolateUtil.populateObservation :  key is " +obsCodekey+"\n";
			String errorText1="TrackIsolateUtil.populateObservation : Error processing transferBeanValueForDT for NbsQuestionMetadata:"+ nbsQuestionMetadata.toString()+"\n";
			String errorText2="TrackIsolateUtil.populateObservation : Error processing transferBeanValueForDT for User value:"+ value+"\n";
			logger.error(errorText +errorText2 + errorText2 +e.getMessage() );
			throw new NEDSSAppException(errorText + errorText1 +errorText2 +"\n"+ e.toString());
		}
		return observationVO;
	}
	
	
	@SuppressWarnings("unchecked")
	public  void setTrackIsolateObservationForUI(int resultedTestToSusOrTrackLinkKey, ObservationVO resultedTestVO, 
					HttpServletRequest request) throws NEDSSAppException {
		ObservationDT observationDT= null;
		ObservationVO observationVO = null;
		String observationCode= "";
		PageSubForm pageSubForm = new PageSubForm();
		Long trackObservationUid= null;
		if(track329Map.isEmpty()) {
			return;
		}
		else {
	        //Long susceptabilityUid = null;
			try {
				legacyQuestionIsolateMap();
				Collection<String> trackIsolateList= TrackIsolateUtil.getQuestionToLegacyMap().values();
;
	
				Collection<Object> coll=  resultedTestVO.getTheActRelationshipDTCollection();
				Iterator<Object> it = coll.iterator();
				while(it.hasNext()) {
					ActRelationshipDT actrelLAB329 = (ActRelationshipDT)it.next();
                    Long sourceActUidLAB329 = actrelLAB329.getSourceActUid();
                    Map<String, Object> trackIsolateObservation = null;
                    if(trackIsolateVOMap.get(sourceActUidLAB329)!=null) {
                    	trackIsolateObservation =(HashMap<String, Object>)trackIsolateVOMap.get(sourceActUidLAB329);
                    }
                    if(trackIsolateObservation!=null && trackIsolateObservation.size()>0) {
                    	Set set = trackIsolateObservation.keySet();
                    	Iterator<Object> iterator =set.iterator();
                    	while(iterator.hasNext()) {
                    		String key = (String)iterator.next();
                    		observationCode= legacyToNewIsolateMap.get(key); 
                    		observationVO = (ObservationVO)trackIsolateObservation.get(key);
        						observationDT =observationVO.getTheObservationDT();
        						if(observationDT.getCd()!=null && !trackIsolateList.contains(observationDT.getCd())) {
        							continue;
        						}else {
        							if(observationCode.equals(PageConstants.CODED_TR_LAB336) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB351) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB355) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB338) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB331) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB363) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB346) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB345) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB337) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB332) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB347) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB353) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB359) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB352) ||
        									observationCode.equals(PageConstants.CODED_TR_LAB358)) {
	        								if(observationVO.getTheObsValueCodedDTCollection()!=null && observationVO.getTheObsValueCodedDTCollection().size()>0) {
	        									ObsValueCodedDT obsValueCodedDT=  observationVO.getObsValueCodedDT_s(0);
	        									pageSubForm.getPageClientVO().setAnswer(observationCode, obsValueCodedDT.getCode());
	        								}
        							}else if (observationCode.equals(PageConstants.DATE_TR_LAB334) ||
        									observationCode.equals(PageConstants.DATE_TR_LAB362) ||
        									observationCode.equals(PageConstants.DATE_TR_LAB357) ||
        									observationCode.equals(PageConstants.DATE_TR_LAB361) ||
        									observationCode.equals(PageConstants.DATE_TR_LAB356) ||
        									observationCode.equals(PageConstants.DATE_TR_LAB350) ||
        									observationCode.equals(PageConstants.DATE_TR_LAB349)) {
	        								if(observationVO.getTheObsValueDateDTCollection()!=null && observationVO.getTheObsValueDateDTCollection().size()>0) {
	        									ObsValueDateDT obsValueDateDT=  observationVO.getObsValueDateDT_s(0);
	        									pageSubForm.getPageClientVO().setAnswer(observationCode, StringUtils.formatDate(obsValueDateDT.getFromTime()));
	        								}
        							}else if(observationCode.equals(PageConstants.TEXT_TR_LAB360)||
											observationCode.equals(PageConstants.TEXT_TR_LAB354)||
											observationCode.equals(PageConstants.TEXT_TR_LAB339)||
											observationCode.equals(PageConstants.TEXT_TR_LAB341)||
											observationCode.equals(PageConstants.TEXT_TR_LAB343)||
											observationCode.equals(PageConstants.TEXT_TR_LAB333)||
											observationCode.equals(PageConstants.TEXT_TR_LAB340)||
											observationCode.equals(PageConstants.TEXT_TR_LAB342)||
											observationCode.equals(PageConstants.TEXT_TR_LAB344)||
											observationCode.equals(PageConstants.TEXT_TR_LAB335)||
											observationCode.equals(PageConstants.TEXT_TR_LAB348)) {
        									if(observationVO.getTheObsValueTxtDTCollection()!=null && observationVO.getTheObsValueTxtDTCollection().size()>0) {
        										ObsValueTxtDT obsValueTxtDT=  observationVO.getObsValueTxtDT_s(0);
        										pageSubForm.getPageClientVO().setAnswer(observationCode, obsValueTxtDT.getValueTxt());
        									}
        								
        							}
        						}
                    	}
                    }
				}
				HashMap<String, Object> subFormHashMap = null;		
	 			if(request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP)!=null) {
					subFormHashMap = (HashMap<String, Object>) request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
	 			}else {	
	            	subFormHashMap = new HashMap<String, Object>();
	            }
	 			
	        		subFormHashMap.put("NBS_LAB329Button_"+resultedTestToSusOrTrackLinkKey, pageSubForm); 
	        		request.getSession().setAttribute(NEDSSConstants.SUBFORM_HASHMAP, subFormHashMap);
	 			
			}catch (Exception e) {
				String error1="TrackIsolateUtil.setTrackIsolateObservationForUI Exception thrown for observationDT:"+ observationDT.toString()+"\n";
				logger.error(error1 +  e); 
	    		String errorText="TrackIsolateUtil.setTrackIsolateObservationForUI Exception thrown for Uid :"+ trackObservationUid+"\n";
				logger.error(errorText +  e); 
	    		logger.error("TrackIsolateUtil.setTrackIsolateObservationForUI Exception " +  e.getMessage()); 
	    		throw new NEDSSAppException(errorText+errorText+ e.getMessage(), e);
			}
		}

	}
	
	
}
