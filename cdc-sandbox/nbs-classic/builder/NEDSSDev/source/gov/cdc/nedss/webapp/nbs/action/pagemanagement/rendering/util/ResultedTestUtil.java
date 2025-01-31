package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.util.ArrayList;
import java.util.Collection; 
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

/**
 * This is a utility class to handle Resulted Test information on a Lab for both add, edit, and view 
 *
 * @author Pradeep Sharma
 *@version: NBS Release 6.0 
 *@since December 2018
 */
public class ResultedTestUtil {
	
	private ObservationVO rootObservationVO = null;
	private LabResultProxyVO labResultProxyVO = null;
	private Map<Long, Object> resultedTestMap = null;
	private Map<Long, Object> susAndTrackIsolateVOMap  = null;

	private Long temporaryUid = 0L;
	private Map<String, Map<Long, Object>> consolidatedMap=null;

	static final LogUtils logger = new LogUtils(PageCreateHelper.class.getName());
	private static Map<String,String> questionToLegacyResultedMap;
	private String labFormCode;
	private String labId;
	
	ResultedTestUtil(String labId, String labFormCode, ObservationVO rootObservationVO, LabResultProxyVO labResultProxyVO,
			Map<String, Map<Long, Object>> oldMap){
		this.labId = labId;
		this.labFormCode = labFormCode;
		this.rootObservationVO =rootObservationVO;
		this.labResultProxyVO = labResultProxyVO;
		this.resultedTestMap= oldMap.get(PageConstants.LAB_RESULTED_TEST);
		this.consolidatedMap= oldMap;
		this.susAndTrackIsolateVOMap= ((Map<Long, Object>)consolidatedMap.get(PageConstants.ISOLATE_AND_SUSCEPTABILITY_TEST_VO));
		if(susAndTrackIsolateVOMap==null)
			  susAndTrackIsolateVOMap  = new HashMap<Long, Object>();
		
	}
	public String getLabId() {
		return labId;
	}
	public void setLabId(String labId) {
		this.labId = labId;
	}
	public String getLabFormCode() {
		return labFormCode;
	}
	public void setLabFromCode(String labFormCode) {
		this.labFormCode = labFormCode;
	}
	public ObservationVO getRootObservationVO() {
		return rootObservationVO;
	}
	public void setRootObservationVO(ObservationVO rootObservationVO) {
		this.rootObservationVO = rootObservationVO;
	}
	public LabResultProxyVO getLabResultProxyVO() {
		return labResultProxyVO;
	}
	public void setLabResultProxyVO(LabResultProxyVO labResultProxyVO) {
		this.labResultProxyVO = labResultProxyVO;
	}
	public Map<Long, Object> getResultedTestMap() {
		return resultedTestMap;
	}
	public void setResultedTestMap(Map<Long, Object> resultedTestMap) {
		this.resultedTestMap = resultedTestMap;
	}
/*
	public Map<Long, Object> getSusceptibilityMap() {
		if (susceptibilityMap==null)
			susceptibilityMap= new HashMap<Long, Object>();
		return susceptibilityMap;
	}
	public void setSusceptibilityMap(Map<Long, Object> susceptibilityMap) {
		this.susceptibilityMap = susceptibilityMap;
	}
	public Map<Long, Object> getTrackIsolateMap() {
		return trackIsolateMap;
	}
	public void setTrackIsolateMap(Map<Long, Object> trackIsolateMap) {
		this.trackIsolateMap = trackIsolateMap;
	}
*/
	public static String[] RESULTED_TEST_OBSERVATION= {"NBS_LAB118", "NBS_LAB121", "LAB115", "NBS_LAB104","NBS_LAB278", "NBS_LAB119", "NBS_LAB220CodeId",
			"NBS_LAB120", "NBS_LAB217", "NBS_LAB207", "NBS_LAB208", "NBS_LAB220", "NBS_LAB279", "NBS_LAB280", 
			"NBS_LAB293", "NBS_LAB364", "NBS459"};

	public static void legacyResultedMap(){
		questionToLegacyResultedMap =  new HashMap<String, String>();
		questionToLegacyResultedMap.put("LAB118", "NBS_LAB118");
		questionToLegacyResultedMap.put("LAB121", "NBS_LAB121");
		questionToLegacyResultedMap.put("LAB115", "LAB115");
		questionToLegacyResultedMap.put("LAB104", "NBS_LAB104");
		questionToLegacyResultedMap.put("LAB278", "NBS_LAB278");
		questionToLegacyResultedMap.put("LAB119", "NBS_LAB119");
		questionToLegacyResultedMap.put("LAB120", "NBS_LAB120");
		questionToLegacyResultedMap.put("LAB217", "NBS_LAB217");
		questionToLegacyResultedMap.put("LAB207", "NBS_LAB207");
		questionToLegacyResultedMap.put("LAB208", "NBS_LAB208");
		questionToLegacyResultedMap.put("LAB220", "NBS_LAB220");
		questionToLegacyResultedMap.put("LAB279", "NBS_LAB279");
		questionToLegacyResultedMap.put("LAB280", "NBS_LAB280");
		questionToLegacyResultedMap.put("LAB293", "NBS_LAB293");
		questionToLegacyResultedMap.put("LAB114", "NBS_LAB364");
		questionToLegacyResultedMap.put("NBS459", "NBS459");
		
	}

	
	/**
	 * Submit(add and edit) process
	 * @param form
	 * @throws NEDSSAppException
	 */
	public Long setRepeatingBatchQuestion(Long tempUid,  PageForm obsForm, HttpServletRequest request) throws NEDSSAppException{
		Map<Object, ArrayList<BatchEntry>> batchmap = obsForm.getBatchEntryMap();
		temporaryUid = tempUid;
		if(batchmap.get(PageConstants.RESULTED_TEST_BATCH_CONTAINER)!=null) {
			ArrayList<BatchEntry> blist = batchmap.get(PageConstants.RESULTED_TEST_BATCH_CONTAINER);
			Iterator<BatchEntry> it = blist.iterator();
			while(it.hasNext()){			
				BatchEntry matchEntry= (BatchEntry)it.next();
				Long resultedTestUid=new Long(temporaryUid--);
				HashMap<String, String> answerMap= (HashMap<String, String>)matchEntry.getAnswerMap();
				ObservationVO observationVO = populateObservation(resultedTestUid, answerMap, RESULTED_TEST_OBSERVATION,  request);
				
				if(observationVO.getTheObservationDT().isItNew()) {
					observationVO.setItNew(true);
					observationVO.setItDirty(false);
					observationVO.getTheObservationDT().setObservationUid(resultedTestUid);
					ActRelationshipDT actRelationship108DT  =LabPageUtil.genericActRelationShipCreate(
							NEDSSConstants.RECORD_STATUS_ACTIVE,
							observationVO.getTheObservationDT().getObservationUid(),
							NEDSSConstants.CLASS_CD_OBS,
							NEDSSConstants.STATUS_ACTIVE,
							rootObservationVO.getTheObservationDT().getObservationUid(),
							NEDSSConstants.CLASS_CD_OBS,
							NEDSSConstants.ACT108_TYP_CD,
							NEDSSConstants.HAS_COMPONENT);

					if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
						labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
					}
					labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship108DT);
				}
				labResultProxyVO.getTheObservationVOCollection().add(observationVO);
			}
		}
		/**Delete scenario*/
		if(resultedTestMap.size()>0) {
			Collection<Long> deleteResultedTest=resultedTestMap.keySet();
			Iterator<Long> it  = deleteResultedTest.iterator();
			while(it.hasNext()) {
				Long observationDeleteUid=(Long)it.next();
				ObservationVO observationVO= (ObservationVO)resultedTestMap.get(observationDeleteUid);
				observationVO.setItDelete(true);
				observationVO.getTheObservationDT().setItDelete(true);
				labResultProxyVO.getTheObservationVOCollection().add(observationVO);
				ActRelationshipDT actRelationship108DT  =LabPageUtil.genericActRelationShipCreate(
							NEDSSConstants.RECORD_STATUS_ACTIVE,
				            observationVO.getTheObservationDT().getObservationUid(),
				            NEDSSConstants.CLASS_CD_OBS,
				            NEDSSConstants.STATUS_ACTIVE,
				            rootObservationVO.getTheObservationDT().getObservationUid(),
				            NEDSSConstants.CLASS_CD_OBS,
				            NEDSSConstants.ACT108_TYP_CD,
				            NEDSSConstants.HAS_COMPONENT);
					actRelationship108DT.setItDelete(true);
					actRelationship108DT.setItNew(false);
					if(labResultProxyVO.getTheActRelationshipDTCollection()==null) {
						labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
					}
					labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship108DT);
					
					Collection<Object> coll = observationVO.getTheActRelationshipDTCollection();
					if(coll !=null && coll.size()>0) {
						Iterator<Object> iterator= coll.iterator();
						while(iterator.hasNext()) {
							ActRelationshipDT  arDT=(ActRelationshipDT)iterator.next();
							arDT.setItDelete(true);
							arDT.setItNew(false);
							labResultProxyVO.getTheActRelationshipDTCollection().add(arDT);
							
							
							ActRelationshipDT actRelationship115DT  =LabPageUtil.genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, arDT.getSourceActUid(),
									NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
									rootObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
									NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115);
							actRelationship115DT.setItDelete(true);
							actRelationship115DT.setItNew(false);
							labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationship115DT);
						}
					}
			}
		  }
		return tempUid;
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
	@SuppressWarnings("unchecked")
	private ObservationVO populateObservation(Long resultedTestUid, HashMap<String, String> answerMap,  String[] typeCode, HttpServletRequest request) 
			throws NEDSSAppException {
		ObservationVO observationVO= new ObservationVO(); 
		NbsQuestionMetadata nbsQuestionMetadata = null;
		String answerText="";
		String key ="";
		String resultedTestToSusOrTrackLinkKey ="";
		String uid =answerMap.get(PageConstants.NBS458);
		Long observationUid= null;
		try {
			ObservationDT observationDT=null;
			ObsValueCodedDT obsValueCodedDT =null;
			ObsValueDateDT obsValueDateDT = null;
			ObsValueNumericDT obsValueNumericDT = null;
			ObsValueTxtDT  obsValueLAB104TxtDT = null;
			ObsValueTxtDT  obsValueLAB208TxtDT = null;
			
			ObservationInterpDT observationInterpDT = null;
			ObservationReasonDT observationReasonDT = null;
			ObsValueCodedModDT obsValueCodedModDT = null;
			boolean isNewNumericDT= false;
			boolean isNewCodedDT= false;
			
			//New batch Entry Case
			if(uid==null || uid.trim().equals("")) {

				observationDT= new ObservationDT();
				observationDT.setItNew(true);
				observationVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD);
				observationVO.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LAB_CTRLCD_DISPLAY);

/*				obsValueCodedDT=new ObsValueCodedDT();
				obsValueCodedDT.setItNew(true);
				obsValueDateDT = new ObsValueDateDT();
				obsValueDateDT.setItNew(true);
				obsValueNumericDT = new ObsValueNumericDT();
				obsValueNumericDT.setItNew(true);
				obsValueLAB104TxtDT = new ObsValueTxtDT();
				obsValueLAB104TxtDT.setItNew(true);
				obsValueLAB208TxtDT = new ObsValueTxtDT();
				obsValueLAB208TxtDT.setItNew(true);
				observationInterpDT = new ObservationInterpDT();
				observationInterpDT.setItNew(true);
				observationReasonDT = new ObservationReasonDT();
				observationReasonDT.setItNew(true);
				obsValueCodedModDT = new ObsValueCodedModDT();
				obsValueCodedModDT.setItNew(true);
				observationVO.setItNew(true);
				*/
			}
			//Update Case----
			else {
				observationUid= new Long(uid);
				
				observationVO = (ObservationVO)resultedTestMap.get(observationUid);
				resultedTestMap.remove(observationUid);
				observationVO.setItDirty(true);
				observationDT= observationVO.getTheObservationDT();
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
					while(iter.hasNext()) {
						ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT)iter.next();
						if(obsValueTxtDT.getTxtTypeCd()!=null && obsValueTxtDT.getTxtTypeCd().equals(NEDSSConstants.NO)){
							obsValueLAB104TxtDT =obsValueTxtDT;
							obsValueLAB104TxtDT.setItDirty(false);
						}else {
							obsValueLAB208TxtDT =obsValueTxtDT;
							obsValueLAB208TxtDT.setItDirty(false);
						}
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
			Map<Object,Object> questionMap =CaseCreateHelper.loadQuestions(labFormCode);	
			List<String> list= LabPageUtil.toList(typeCode);
			for(int i=0 ; i<list.size(); i++) {
				key=list.get(i);
				answerText =(String)answerMap.get(key);
				nbsQuestionMetadata = (NbsQuestionMetadata)questionMap.get(key);
				if(nbsQuestionMetadata==null) {
					/**@TODO PKS: As of Dec 5 2018: this is a temporary workaround as the hidden variable are still not getting populated properly 
					 * 
					 */
					logger.error("nbsQuestionMetadata is null for the question_identifier"+ key);
					continue;
				}
				if(nbsQuestionMetadata.getDataLocation()!=null && 
						(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB121)
								|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB118)
								|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB293)
								|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB217)
								|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB279)
								)){
					continue;
				}
				else if(nbsQuestionMetadata.getQuestionIdentifier().equals(NEDSSConstants.CURRENT_KEY) && answerMap.get(NEDSSConstants.CURRENT_KEY)!=null)
				{
					resultedTestToSusOrTrackLinkKey=answerMap.get(NEDSSConstants.CURRENT_KEY).toString();
					continue;
				}
				
				if(answerText ==null || answerText.equals("") || answerText.equals("$$") ) {
					answerText ="";
				}
			
				
						
						
			
				logger.debug("key here is " +key);
				logger.debug("nbsQuestionMetadata.getDataLocation() is " +nbsQuestionMetadata.getDataLocation());
				if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBSERVATION_DB)) {
					
					if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB220)) {
						String code=getFirstPart(answerText);
						String description=getSecondPart(answerText);
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationDT, code, RenderConstants.OBSERVATION);
						observationDT.setCdDescTxt(description);
					}else if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB207)  ) {
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
						obsValueCodedDT.setItNew(true);
						isNewCodedDT =true;
					}else if(!isNewCodedDT && obsValueCodedDT!=null){
						obsValueCodedDT.setItDirty(true);
						obsValueCodedDT.setItNew(false);
					}
					if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB280)) {
						String code=getFirstPart(answerText);
						String description=getSecondPart(answerText);
						if(code.equals("")) {
							obsValueCodedDT.setCode("");
							obsValueCodedDT.setDisplayName("");
						}else {
							DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueCodedDT, code, RenderConstants.OBS_VALUE_CODED);
							obsValueCodedDT.setDisplayName(description);
						}
					}
					if(observationVO.getTheObsValueCodedDTCollection()==null) {
						observationVO.setTheObsValueCodedDTCollection(new ArrayList<Object>());
						observationVO.getTheObsValueCodedDTCollection().add(obsValueCodedDT);
						obsValueCodedDT.setObservationUid(observationUid);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_DATE_DB)) {
					if(obsValueDateDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueDateDT = new ObsValueDateDT();
						obsValueDateDT.setItNew(true);
						obsValueDateDT.setObservationUid(observationUid);
					}else {
						obsValueDateDT.setItDirty(true);
						obsValueDateDT.setItNew(false);
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
						obsValueNumericDT.setItNew(true);
						isNewNumericDT= true;
						obsValueNumericDT.setObservationUid(observationUid);
					}else if(!isNewNumericDT && obsValueNumericDT!=null){
						obsValueNumericDT.setItNew(false);
						obsValueNumericDT.setItDirty(true);
					}
					
					
					if(nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.LAB115) || nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB119)
							|| nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB120)){
						answerText=getFirstPart(answerText);
						DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueNumericDT, answerText, RenderConstants.OBS_VALUE_NUMERIC);
					}else if( nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB364)){
						obsValueNumericDT.setNumericValue(answerText);
						ObservationUtil obsUtil = new ObservationUtil();
						obsValueNumericDT.setNumericScale1(null);
						obsValueNumericDT.setNumericScale2(null);
						obsValueNumericDT = obsUtil.parseNumericResult(obsValueNumericDT);
						if(obsValueNumericDT.getNumericValue1()!=null && obsValueNumericDT.getComparatorCd1()==null) {
							obsValueNumericDT.setComparatorCd1("=");
						}

					}
					obsValueNumericDT.setObservationUid(observationUid);
					if(observationVO.getTheObsValueNumericDTCollection()==null || observationVO.getTheObsValueNumericDTCollection().size()==0) {
						observationVO.setTheObsValueNumericDTCollection(new ArrayList<Object>());
					obsValueNumericDT.setObsValueNumericSeq(observationVO.getTheObsValueNumericDTCollection().size());
					observationVO.getTheObsValueNumericDTCollection().add(obsValueNumericDT);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_TXT_DB)
						&& nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB104)) {
					if(obsValueLAB104TxtDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueLAB104TxtDT = new ObsValueTxtDT();
						obsValueLAB104TxtDT.setItNew(true);
						obsValueLAB104TxtDT.setTxtTypeCd(NEDSSConstants.NO);
						obsValueLAB104TxtDT.setObservationUid(observationUid);
					}else {
						obsValueLAB104TxtDT.setItDirty(true);
						obsValueLAB104TxtDT.setItNew(false);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueLAB104TxtDT, answerText, RenderConstants.OBS_VALUE_TXT);
					if(observationVO.getTheObsValueTxtDTCollection()==null) {
						observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
						obsValueLAB104TxtDT.setTxtTypeCd(NEDSSConstants.NO);
						obsValueLAB104TxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size()+1);
						observationVO.getTheObsValueTxtDTCollection().add(obsValueLAB104TxtDT);
					}else if(obsValueLAB104TxtDT.isItNew()) {
						obsValueLAB104TxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size()+1);
						observationVO.getTheObsValueTxtDTCollection().add(obsValueLAB104TxtDT);
					}
				}else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBS_VALUE_TXT)
						&& nbsQuestionMetadata.getQuestionIdentifier().equals(PageConstants.NBS_LAB208)) {
					if(obsValueLAB208TxtDT==null) {
						if (answerText.equals("")){
							continue;
						}
						obsValueLAB208TxtDT = new ObsValueTxtDT();
						obsValueLAB208TxtDT.setItNew(true);
						obsValueLAB208TxtDT.setObservationUid(observationUid);
					}else {
						obsValueLAB208TxtDT.setItDirty(true);
						obsValueLAB208TxtDT.setItNew(false);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueLAB208TxtDT, answerText, RenderConstants.OBS_VALUE_TXT);
					
					if(observationVO.getTheObsValueTxtDTCollection()==null) {
						observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
						obsValueLAB208TxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size()+1);
						observationVO.getTheObsValueTxtDTCollection().add(obsValueLAB208TxtDT);
					}else if(obsValueLAB208TxtDT.isItNew()) {
						obsValueLAB208TxtDT.setObsValueTxtSeq(observationVO.getTheObsValueTxtDTCollection().size()+1);
						observationVO.getTheObsValueTxtDTCollection().add(obsValueLAB208TxtDT);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBSERVATION_INTERP_DB)) {
					if(observationInterpDT==null) {
						if (answerText.equals("")){
							continue;
						}
						observationInterpDT = new ObservationInterpDT();
						observationInterpDT.setItNew(true);
						observationInterpDT.setObservationUid(observationUid);
					}else {
						observationInterpDT.setItDirty(true);
						observationInterpDT.setItNew(false);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationInterpDT, answerText, RenderConstants.OBSERVATION_INTERP);
					String temp = LabPageUtil.getDescTxt("OBS_INTRP", observationInterpDT.getInterpretationCd());
					if (temp != null) {
						observationInterpDT.setInterpretationDescTxt(temp);
					}

					if(observationVO.getTheObservationInterpDTCollection()==null) {
						observationVO.setTheObservationInterpDTCollection(new ArrayList<Object>());
						observationVO.getTheObservationInterpDTCollection().add(observationInterpDT);
					}
				}
				else if(nbsQuestionMetadata.getDataLocation()!=null && nbsQuestionMetadata.getDataLocation().trim().startsWith(RenderConstants.OBSERVATION_REASON_DB)) {
					if(observationReasonDT==null) {
						if (answerText.equals("")){
							continue;
						}
						observationReasonDT = new ObservationReasonDT();
						observationReasonDT.setItNew(true);
						observationReasonDT.setObservationUid(observationUid);
					}else {
						observationReasonDT.setItDirty(true);
						observationReasonDT.setItNew(false);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, observationReasonDT, answerText, RenderConstants.OBSERVATION_REASON_DB);
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
						obsValueCodedModDT.setItNew(true);
						obsValueCodedModDT.setObservationUid(observationUid);
					}else {
						obsValueCodedModDT.setItDirty(true);
						obsValueCodedModDT.setItNew(false);
					}
					DynamicBeanBinding.transferBeanValueForDT(nbsQuestionMetadata, obsValueCodedModDT, answerText, RenderConstants.OBS_VALUE_CODED_MOD_DB);
					if(observationVO.getTheObsValueCodedModDTCollection()==null) {
						observationVO.setTheObsValueCodedModDTCollection(new ArrayList<Object>());
						observationVO.getTheObsValueCodedModDTCollection().add(obsValueCodedModDT);
					}
				}
				
				if(getQuestionToLegacyResultedMap().get(observationDT.getCd())!=null) {
					observationDT.setCd(getQuestionToLegacyResultedMap().get(observationDT.getCd()));
				}
				observationVO.setTheObservationDT(observationDT);

			}
			observationVO.getTheObservationDT().setAddReasonCd(NEDSSConstants.OBS_ADD_REASON_CD);
			if(observationVO.getTheObservationDT().getCtrlCdDisplayForm()==null || observationVO.getTheObservationDT().getCtrlCdDisplayForm().trim().equals("")) {
				observationVO.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LAB_DISPALY_FORM);
			}
			if(observationVO.getTheObservationDT().getStatusTime()==null ) {
				observationVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
			}
			 
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
			 	observationVO.getTheObservationDT().setObsDomainCdSt1(EdxELRConstants.ELR_RESULT_CD);
				LabCommonUtil.updateObsVOForHiddenVarible(observationVO, labId);
				 
				 
				 if(observationVO.getTheObservationDT().getObservationUid()==null)
					 observationVO.getTheObservationDT().setObservationUid(resultedTestUid);
	 			if(request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP)!=null) {
					HashMap<String, Object> subFormHashMap = (HashMap<String, Object>) request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
		 			
		 			if(subFormHashMap!=null && subFormHashMap.get("NBS_LAB222Button_"+resultedTestToSusOrTrackLinkKey) !=null) {
		 				PageSubForm pageSuscSubForm = (PageSubForm)subFormHashMap.get("NBS_LAB222Button_"+resultedTestToSusOrTrackLinkKey);
		 				pageSuscSubForm.setPageFormCd(NEDSSConstants.SUS_LAB_SUSCEPTIBILITIES);
		 				LabSusceptibilityUtil labSusceptibilityUtil = new LabSusceptibilityUtil(labId, rootObservationVO, labResultProxyVO, consolidatedMap);
		 				temporaryUid =labSusceptibilityUtil.setRepeatingSuscBatchQuestion(temporaryUid, resultedTestToSusOrTrackLinkKey, observationVO, pageSuscSubForm);
		 				observationVO.getTheObservationDT().setCtrlCdUserDefined1("Y");
					}else if(observationVO.getTheActRelationshipDTCollection()!=null && observationVO.getTheActRelationshipDTCollection().size()>0) {
		 				LabSusceptibilityUtil labSusceptibilityUtil = new LabSusceptibilityUtil(labId, rootObservationVO, labResultProxyVO, consolidatedMap);
		 				labSusceptibilityUtil.deleteAll(observationVO);
					}
		 			if(subFormHashMap!=null && subFormHashMap.get("NBS_LAB329Button_"+resultedTestToSusOrTrackLinkKey) !=null) {
		 				PageSubForm pageTrackSubForm = (PageSubForm)subFormHashMap.get("NBS_LAB329Button_"+resultedTestToSusOrTrackLinkKey);
		 				pageTrackSubForm.setPageFormCd(NEDSSConstants.ISO_LAB_TRACK_ISOLATES);
		 				TrackIsolateUtil trackIsolateUtil = new TrackIsolateUtil(rootObservationVO, labResultProxyVO, consolidatedMap);
		 				temporaryUid =trackIsolateUtil.setTrackIsolateQuestion(temporaryUid, resultedTestToSusOrTrackLinkKey, observationVO, pageTrackSubForm);
		 				observationVO.getTheObservationDT().setCtrlCdUserDefined1("Y");
					}else if(observationVO.getTheActRelationshipDTCollection()!=null && observationVO.getTheActRelationshipDTCollection().size()>0) {
						TrackIsolateUtil trackIsolateUtil = new TrackIsolateUtil(rootObservationVO, labResultProxyVO, consolidatedMap);
						trackIsolateUtil.deleteAll(observationVO);
					}
		 			
	 			}
		}
		catch (Exception e) {
			String errorText="ResultedTestUtil.populateObservation :  key is " +key+"\n";
			String errorText1="ResultedTestUtil.populateObservation :  resultedTestToSusOrTrackLinkKey is " +resultedTestToSusOrTrackLinkKey+"\n";
			String errorText2="ResultedTestUtil.populateObservation : Error processing transferBeanValueForDT for NbsQuestionMetadata:"+ nbsQuestionMetadata.toString()+"\n";
			String errorText3="ResultedTestUtil.populateObservation : Error processing transferBeanValueForDT for User value:"+ answerText+"\n";
			logger.error(errorText +errorText2 + errorText2 +errorText3+e.getMessage() );
			throw new NEDSSAppException(errorText + errorText1 +errorText2 +"\n"+ e.toString());
		}
		return observationVO;
	}
	
	
	private static Map<String,String> getQuestionToLegacyResultedMap(){
		if(questionToLegacyResultedMap==null){
			legacyResultedMap();
		}
		return questionToLegacyResultedMap;
	}
	
	public  void setResultedTestObservationForUI(PageForm obsForm, HttpServletRequest request) throws NEDSSAppException {
		Long resultedTestUid =null;
		ObservationDT observationDT= null;
		ObservationVO observationVO = null;
		int resultedTestToSusOrTrackLinkKey = 1;
		ArrayList<BatchEntry> list =  new ArrayList<BatchEntry>();
		Map<Object, ArrayList<BatchEntry>> batchmap = new HashMap<Object, ArrayList<BatchEntry>>();
		obsForm.getBatchEntryMap();
		String ctrlCdUserDefined1 ="";
		boolean electronicIndicator = false;
		try {
			if(rootObservationVO.getTheObservationDT().getElectronicInd()!=null && rootObservationVO.getTheObservationDT().getElectronicInd().equals("Y")) {
				electronicIndicator= true;
			}

			Set<Long> resultedSet =  resultedTestMap.keySet();
			Iterator<Long> it = resultedSet.iterator();
			while(it.hasNext()) {
					resultedTestUid= (Long)it.next();
					observationVO = (ObservationVO)resultedTestMap.get(resultedTestUid);
					ctrlCdUserDefined1 =observationVO.getTheObservationDT().getCtrlCdUserDefined1();
					if(observationVO.getTheActRelationshipDTCollection()!=null) {
						Iterator<Object> iterator =observationVO.getTheActRelationshipDTCollection().iterator();
						while(iterator.hasNext()) {
							ActRelationshipDT actRelationshipDT=(ActRelationshipDT)iterator.next();
							if(actRelationshipDT.getTypeCd()!=null && actRelationshipDT.getTypeCd().equals(NEDSSConstants.REFERRAL_CLASS_CODE)) {
								ctrlCdUserDefined1="Y";
							}
						}
					}
					BatchEntry batchEntry =  new BatchEntry();
					HashMap<String, String> beMap = new HashMap<String, String>();
	
					observationDT =observationVO.getTheObservationDT();
					beMap.put(PageConstants.NBS457, observationDT.getElectronicInd());
					beMap.put(PageConstants.NBS458, observationDT.getObservationUid().toString());
					beMap.put(PageConstants.LAB_NBS_LAB293, observationDT.getCd());
					if(electronicIndicator) {
						String mask = LabCommonUtil.obsTestCodeMaskForELR(observationDT);
						beMap.put(PageConstants.LAB_NBS_LAB293, mask);
					}else {
						beMap.put(PageConstants.LAB_NBS_LAB293, observationDT.getCd());
					}
					if (observationDT.getElectronicInd()!=null && observationDT.getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR)) {
						beMap.put(PageConstants.LAB_NBS_LAB217, LabCommonUtil.getDateTImePerformingOrgVO(labResultProxyVO, rootObservationVO, observationVO));
						String mergedCodedValue= LabCommonUtil.observationMaskForELR(observationDT);
						beMap.put(PageConstants.NBS_LAB220, mergedCodedValue);
					}else {
						/**Pradeep : Search related question are special type of questions required special mapping*/
						beMap.put(PageConstants.NBS_LAB220+PageConstants.CODEID , observationDT.getCd());
						beMap.put(PageConstants.NBS_LAB220+PageConstants.DESCRIPTIONID ,observationDT.getCdDescTxt() +"("+ observationDT.getCd()+")"); 
						beMap.put(PageConstants.NBS_LAB220+PageConstants.DESCRIPTION ,observationDT.getCdDescTxt());
						beMap.put(PageConstants.NBS_LAB220, observationDT.getCd() +"$$"+ observationDT.getCdDescTxt());
					}
					 
					if(electronicIndicator)	{
						String description="";
						if(observationDT.getMethodDescTxt() !=null) {
							description=observationDT.getMethodDescTxt();
						}
						if(observationDT.getMethodCd() !=null) {
							description=description+ " ("+ observationDT.getMethodCd()+ ")";
						}
						beMap.put(PageConstants.NBS_LAB279,description);
					}
					if(observationDT.getStatusCd()!=null) {
						String codeWithDesciption = combineCodeDescriptionForBatchEntry(observationDT.getStatusCd(), "ACT_OBJ_ST");
						beMap.put(PageConstants.NBS_LAB207, codeWithDesciption);
					}
	
					Collection<Object> obsValueTxtColl = observationVO.getTheObsValueTxtDTCollection();
					boolean isLab208Answered = false;
					if(obsValueTxtColl!=null) {
						StringBuffer testBuffer = new StringBuffer();
						StringBuffer commentBuffer = new StringBuffer();
						Iterator<Object> iter = obsValueTxtColl.iterator();
						while (iter.hasNext()) {
							ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) iter.next();
							if(obsValueTxtDT.getValueTxt()!=null && obsValueTxtDT.getTxtTypeCd()!=null && obsValueTxtDT.getTxtTypeCd().equals(NEDSSConstants.NO)) {
								if(electronicIndicator) {
									if(commentBuffer.length()>0)
										commentBuffer =  commentBuffer.append("<br>").append(obsValueTxtDT.getValueTxt());
									else
										commentBuffer =  commentBuffer.append(obsValueTxtDT.getValueTxt());
									beMap.put(PageConstants.NBS_LAB104, commentBuffer.toString());
								}else {
									beMap.put(PageConstants.NBS_LAB104, obsValueTxtDT.getValueTxt());
								}
							}else {
								if(obsValueTxtDT.getValueTxt()!=null && obsValueTxtDT.getValueTxt().length()>0) {
									if(electronicIndicator) {
										if(testBuffer.length()>0)
											testBuffer =  testBuffer.append("<br>").append(obsValueTxtDT.getValueTxt());
										else
											testBuffer =  testBuffer.append(obsValueTxtDT.getValueTxt());
											
										beMap.put(PageConstants.NBS_LAB208, testBuffer.toString());							
									}else {
										beMap.put(PageConstants.NBS_LAB208, obsValueTxtDT.getValueTxt());
									}
								}
							}
						}
					}
					Collection<Object> obsValueCodedColl = observationVO.getTheObsValueCodedDTCollection();
					if(obsValueCodedColl!=null) {
						Iterator<Object> iter = obsValueCodedColl.iterator();
						while (iter.hasNext()) {
							ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT) iter.next();
							boolean checkIdCodedValue =  checkIfcodedResultValue(obsValueCodedDT.getCode());
							
							if(!electronicIndicator && obsValueCodedDT.getCode()!=null && !obsValueCodedDT.getCode().equals(NEDSSConstants.LAB_TESTCODE_NI)
									&& !checkIdCodedValue) {
								//String codeWithDesciption = obsValueCodedDT.getCode()+"$$"+ obsValueCodedDT.getDisplayName();
								//beMap.put(PageConstants.NBS_LAB278, codeWithDesciption);
								/**Pradeep : Search related question are special type of questions required special mapping*/
								beMap.put(PageConstants.NBS_LAB280+PageConstants.CODEID , obsValueCodedDT.getCode());
								beMap.put(PageConstants.NBS_LAB280+PageConstants.DESCRIPTIONID ,obsValueCodedDT.getDisplayName() +"("+ obsValueCodedDT.getCode()+")"); 
								beMap.put(PageConstants.NBS_LAB280+PageConstants.DESCRIPTION ,obsValueCodedDT.getDisplayName());
								beMap.put(PageConstants.NBS_LAB280, obsValueCodedDT.getCode() +"$$"+ obsValueCodedDT.getDisplayName());
							}else if(obsValueCodedDT.getCode()!=null && !obsValueCodedDT.getCode().equals(NEDSSConstants.LAB_TESTCODE_NI)
										&& (ctrlCdUserDefined1==null || ctrlCdUserDefined1.equals("N"))) {
								String maskedValue = "";
								if(electronicIndicator)  {
										maskedValue =  LabCommonUtil.obsValueCodedMaskForELR(obsValueCodedDT);
										maskedValue = obsValueCodedDT.getCode()+"$$"+ maskedValue;
								}else {
									maskedValue = obsValueCodedDT.getCode()+"$$"+ obsValueCodedDT.getDisplayName();
								}
									beMap.put(PageConstants.NBS_LAB280, maskedValue);
									

							} else if(electronicIndicator && obsValueCodedDT.getCode()!=null && !obsValueCodedDT.getCode().equals(NEDSSConstants.LAB_TESTCODE_NI)) {
								String maskedValue =  LabCommonUtil.obsValueCodedMaskForELR(obsValueCodedDT);
								maskedValue = obsValueCodedDT.getCode()+"$$"+ maskedValue;
								beMap.put(PageConstants.NBS_LAB280, maskedValue);

							}
							if(electronicIndicator)  {
								String maskedValue =  LabCommonUtil.obsValueCodedMaskForELR(obsValueCodedDT);
								obsValueCodedDT.setDisplayName(maskedValue);
								beMap.put(PageConstants.NBS_LAB121,  LabCommonUtil.obsCodedTestMaskForELR(obsValueCodedDT));

							}
						}
					}
					Collection<Object> obsValueNumericColl = observationVO.getTheObsValueNumericDTCollection();
					if(obsValueNumericColl!=null) {
						Iterator<Object> iter = obsValueNumericColl.iterator();
						while (iter.hasNext()) {
							ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT) iter.next();
							if(obsValueNumericDT.getHighRange()!=null ) {
								beMap.put(PageConstants.NBS_LAB120, obsValueNumericDT.getHighRange());
							}
							if(obsValueNumericDT.getLowRange()!=null ) {
								beMap.put(PageConstants.NBS_LAB119, obsValueNumericDT.getLowRange());
							}
							if(obsValueNumericDT.getNumericUnitCd()!=null ) {
								String codeWithDesciption = combineCodeDescriptionForBatchEntry(obsValueNumericDT.getNumericUnitCd(), "UNIT_ISO");
								beMap.put(PageConstants.LAB115, codeWithDesciption);
							}
							if(obsValueNumericDT!=null ) {
								beMap.put(PageConstants.NBS_LAB364, LabCommonUtil.getStringFromObsValueNumericDT(obsValueNumericDT));
							}
							
						}
					}
					Collection<Object> obsInterpColl = observationVO.getTheObservationInterpDTCollection();
					if(obsInterpColl!=null) {
						Iterator<Object> iter = obsInterpColl.iterator();
						while (iter.hasNext()) {
							ObservationInterpDT observationInterpDT = (ObservationInterpDT) iter.next();
							if(observationInterpDT.getInterpretationCd()!=null && electronicIndicator) {
								//String description = LabPageUtil.getDescTxt(observationInterpDT.getInterpretationCd(),"OBS_INTRP");
								beMap.put(PageConstants.NBS_LAB118, observationInterpDT.getInterpretationDescTxt());
						}
					}
					beMap.put(NEDSSConstants.CURRENT_KEY, Integer.toString(resultedTestToSusOrTrackLinkKey));
					
					batchEntry.setAnswerMap(beMap);
					//this field will be compared for updates
					batchEntry.setLocalId(observationDT.getLocalId());
					batchEntry.setId(PageForm.getNextId());
					batchEntry.setSubsecNm(PageConstants.RESULTED_TEST_BATCH_CONTAINER);
					list.add(batchEntry);	
				}
				if(susAndTrackIsolateVOMap!=null && susAndTrackIsolateVOMap.size()>0) {
					LabSusceptibilityUtil labSusceptibilityUtil = new LabSusceptibilityUtil(labId, rootObservationVO, labResultProxyVO, consolidatedMap);
					labSusceptibilityUtil.setSusceptabilityObservationForUI(electronicIndicator, resultedTestToSusOrTrackLinkKey,observationVO, request);
				//resultedTestToSusOrTrackLinkKey++;
				}
				TrackIsolateUtil trackIsolateUtil = new TrackIsolateUtil(rootObservationVO, labResultProxyVO, consolidatedMap);
				trackIsolateUtil.setTrackIsolateObservationForUI(resultedTestToSusOrTrackLinkKey,observationVO, request);
				resultedTestToSusOrTrackLinkKey++;
			}
			//obsForm.getBatchEntryMap().put(PageConstants.RESULTED_TEST_BATCH_CONTAINER, list);
			if (obsForm.getBatchEntryMap() == null)
				obsForm.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
			batchmap.put(PageConstants.RESULTED_TEST_BATCH_CONTAINER, list);
			obsForm.getBatchEntryMap().putAll(batchmap);
			

		} catch (Exception e) {
			String error1="ResultedTestUtil.setResultedTestObservationForUI Exception thrown for observationDT:"+ observationDT.toString()+"\n";
			logger.error(error1 +  e); 
    		String errorText="ResultedTestUtil.setResultedTestObservationForUI Exception thrown for ResultedTestUid :"+ resultedTestUid+"\n";
			logger.error(errorText +  e); 
    		logger.error("ResultedTestUtil.setResultedTestObservationForUI Exception " +  e.getMessage()); 
    		throw new NEDSSAppException(errorText+errorText+ e.getMessage(), e);
		}

	}
	
	public static String getFirstPart(String code) throws NEDSSAppException {
		String returnCode = "";
		try {
			if(code.indexOf("$$")>0) {
				returnCode= code.substring(0, code.indexOf("$$"));
			}else {
				returnCode =code;
			}
		} catch (Exception e) {
			throw new NEDSSAppException("ResultedTestUtil.getFirstPart Error thrown for code: "+ code);
		}
		return returnCode;
	}
		
	public static String getSecondPart(String code) throws NEDSSAppException {
		String returnCode = "";
		try {
			if(code.indexOf("$$")>0) {
				returnCode = code.substring(code.indexOf("$$")+2, code.length());
			}else {
				returnCode =code;
			}
		} catch (Exception e) {
			throw new NEDSSAppException("ResultedTestUtil.getSecondPart Error thrown for code: "+ code);
		}
		return returnCode;
	}

	/**
	 * 
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
			String error1 = "ResultedTestUtil.combineCodeDescriptionForBatchEntry Error thrown for code:"+ code;
			String error2 = "\nResultedTestUtil.combineCodeDescriptionForBatchEntry Error thrown for codeSetName:"+ codeSetName;
			
			throw new NEDSSAppException(error1 + error2);
		}
		return codeWithDescription;
		
	}
	
	/**
	 * Check for result if coded value
	 */
	private static boolean checkIfcodedResultValue(String codeValue) {
		List list = CachedDropDowns.getCodedValueForType("CODED_LAB_RESULT");
		if(list.contains(codeValue))
			return true;
		else 
			return false;
		
		
	}
	
}