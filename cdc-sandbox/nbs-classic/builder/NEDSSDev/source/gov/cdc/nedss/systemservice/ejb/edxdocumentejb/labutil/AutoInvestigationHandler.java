package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.dt.PrePopMappingDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAPHCProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleManageDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.RenderConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJBException;
/**
 * 
 * @author Pradeep Kumar Sharma
 *
 */
public class AutoInvestigationHandler {
	static final LogUtils logger = new LogUtils(AutoInvestigationHandler.class.getName());
	
	private PublicHealthCaseVO createPublicHealthCaseVO(ObservationVO observationVO, EdxLabInformationDT edxLabInformationDT,  NBSSecurityObj securityObj){
		PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
		
		phcVO.getThePublicHealthCaseDT().setLastChgTime(new java.sql.Timestamp(new Date().getTime()));
		
		phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(edxLabInformationDT.getNextUid()-1));
		//edxLabInformationDT.setNextUid(edxLabInformationDT.getNextUid());
		phcVO.getThePublicHealthCaseDT().setJurisdictionCd((observationVO.getTheObservationDT().getJurisdictionCd()));
		phcVO.getThePublicHealthCaseDT().setRptFormCmpltTime(observationVO.getTheObservationDT().getRptToStateTime());
		//phcVO.getThePublicHealthCaseDT().setCaseClassCd(EdxELRConstants.ELR_CONFIRMED_CD);
		
		phcVO.getThePublicHealthCaseDT().setAddTime(new Timestamp(new Date().getTime()));
		phcVO.getThePublicHealthCaseDT().setAddUserId(Long.valueOf(securityObj.getEntryID()));
		phcVO.getThePublicHealthCaseDT().setCaseTypeCd(EdxELRConstants.ELR_INDIVIDUAL);
		ProgramAreaVO programAreaVO= CachedDropDowns.getProgramAreaForCondition(edxLabInformationDT.getConditionCode());
		phcVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
		phcVO.getThePublicHealthCaseDT().setProgAreaCd(programAreaVO.getStateProgAreaCode());
		if(PropertyUtil.isStdOrHivProgramArea(programAreaVO.getStateProgAreaCode()))
			phcVO.getThePublicHealthCaseDT().setReferralBasisCd(NEDSSConstants.REFERRAL_BASIS_LAB);
		phcVO.getThePublicHealthCaseDT().setSharedInd(NEDSSConstants.TRUE);
		phcVO.getThePublicHealthCaseDT().setCdDescTxt(programAreaVO.getConditionShortNm());
		phcVO.getThePublicHealthCaseDT().setGroupCaseCnt(new Integer(1));
		phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(EdxELRConstants.ELR_OPEN_CD);
		phcVO.getThePublicHealthCaseDT().setActivityFromTime(
				StringUtils.stringToStrutsTimestamp(StringUtils
						.formatDate(new Timestamp((new Date()).getTime()))));
		Calendar now = Calendar.getInstance();
		String dateValue = (now.get(Calendar.MONTH)+1) +"/" + now.get(Calendar.DATE) +"/" + now.get(Calendar.YEAR);
		RulesEngineUtil reu = new RulesEngineUtil();
    	int[] weekAndYear = reu.CalcMMWR(dateValue);
		phcVO.getThePublicHealthCaseDT().setMmwrWeek(weekAndYear[0]+"");
		phcVO.getThePublicHealthCaseDT().setMmwrYear(weekAndYear[1]+"");
		phcVO.getThePublicHealthCaseDT().setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
		if (edxLabInformationDT.getConditionCode() != null) {
			phcVO.setCoinfectionCondition(CachedDropDowns.getConditionCoinfectionMap().containsKey(edxLabInformationDT.getConditionCode())? true:false);
			if (phcVO.isCoinfectionCondition()) {
				logger.debug("AutoInvestigationHandler.createPublicHealthCaseVO set flag to create an new coinfection id for the case");
				phcVO.getThePublicHealthCaseDT().setCoinfectionId(NEDSSConstants.COINFCTION_GROUP_ID_NEW_CODE);
			}
		}
		phcVO.getThePublicHealthCaseDT().setItNew(true);
		phcVO.getThePublicHealthCaseDT().setItDirty(false);
		phcVO.setItNew(true);
		phcVO.setItDirty(false);
		
		try{
			boolean isSTDProgramArea = PropertyUtil.isStdOrHivProgramArea(phcVO.getThePublicHealthCaseDT().getProgAreaCd());
			if (isSTDProgramArea) {
				//gt-ND-4592 - STD_HIV_DATAMART Fails To Populate Investigations Created From An ELR
				// per Pradeep need an empty case mgt
				CaseManagementDT caseMgtDT = new CaseManagementDT();
				caseMgtDT.setPublicHealthCaseUid(phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
				//caseMgtDT.setItNew(true); //not currently used
				//caseMgtDT.setItDirty(false); //not currently used
				caseMgtDT.setCaseManagementDTPopulated(true);
				phcVO.setTheCaseManagementDT(caseMgtDT);
			}
		} catch(Exception ex){
				logger.error("Exception setting CaseManagementDT to PHC = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("Unexpected exception setting CaseManagementDT to PHC -->" +ex.toString());
		}
		
		return phcVO;
	} 
	public Object autoCreateInvestigation( ObservationVO observationVO, EdxLabInformationDT edxLabInformationDT, NBSSecurityObj securityObj) throws NEDSSAppException{
		PageActProxyVO pageActProxyVO = null;
		PamProxyVO pamProxyVO = null;
		PublicHealthCaseVO phcVO=createPublicHealthCaseVO(observationVO, edxLabInformationDT,  securityObj);
		
		Collection<Object> theActIdDTCollection = new ArrayList<Object>();
			ActIdDT actIDDT = new ActIdDT();
			actIDDT.setItNew(true);
			actIDDT.setActIdSeq(new Integer(1));
			actIDDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
			theActIdDTCollection.add(actIDDT);
			phcVO.setTheActIdDTCollection(theActIdDTCollection);
		if(edxLabInformationDT.getInvestigationType()!=null && edxLabInformationDT.getInvestigationType().equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)){
			ActIdDT actID1DT = new ActIdDT();
			actID1DT.setItNew(true);
			actID1DT.setActIdSeq(new Integer(2));
			actID1DT.setTypeCd("CITY");
			theActIdDTCollection.add(actID1DT);
			phcVO
					.setTheActIdDTCollection(theActIdDTCollection);
		}
		if (edxLabInformationDT.getInvestigationType()!=null && (edxLabInformationDT.getInvestigationType().equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)
				|| edxLabInformationDT.getInvestigationType().equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT))) {
			pamProxyVO = new PamProxyVO();
			pamProxyVO.setItNew(true);
			pamProxyVO.setItDirty(false);
			pamProxyVO.setPublicHealthCaseVO(phcVO);
		} else {
			pageActProxyVO = new PageActProxyVO();
			pageActProxyVO.setItNew(true);
			pageActProxyVO.setItDirty(false);
			pageActProxyVO.setPublicHealthCaseVO(phcVO);
			populateProxyFromPrePopMapping(pageActProxyVO, edxLabInformationDT);
		}
		try {
			Object obj=null;
			
			//Object obj = transferValuesTOActProxyVO(pageActProxyVO,pamProxyVO, personVOCollection, observationVO);
			if(pageActProxyVO!=null)	
				obj=pageActProxyVO;
			else
				obj=pamProxyVO;
			return obj;
		} catch (NEDSSSystemException e) {
			logger.error("AutoInvestigationHandler-autoCreateInvestigation NEDSSSystemException raised"+e);
			throw new NEDSSAppException("AutoInvestigationHandler-autoCreateInvestigation NEDSSSystemException raised"+e);
		} catch (ClassCastException e) {
			logger.error("AutoInvestigationHandler-autoCreateInvestigation ClassCastException raised"+e);
			throw new NEDSSAppException("AutoInvestigationHandler-autoCreateInvestigation ClassCastException raised"+e);
		}  catch (EJBException e) {
			logger.error("AutoInvestigationHandler-autoCreateInvestigation EJBException raised"+e);
			throw new NEDSSAppException("AutoInvestigationHandler-autoCreateInvestigation EJBException raised"+e);
		}
	
	}
	
	public Object transferValuesTOActProxyVO(PageActProxyVO pageActProxyVO,PamProxyVO pamActProxyVO, Collection<Object> personVOCollection, ObservationVO rootObservationVO, Collection<Object> entities, Map<Object, Object> questionIdentifierMap) throws NEDSSAppException{
		try {
			PersonVO patientVO =null;
			boolean isOrgAsReporterOfPHCPartDT=false;
			boolean isPhysicianOfPHCDT=false;
			Collection<Object> coll = rootObservationVO.getTheParticipationDTCollection();
			Collection<Object> partColl = new ArrayList<Object>();
			Collection<Object> nbsActEntityDTColl = new ArrayList<Object>();
			long personUid=-1;
			if(pageActProxyVO!=null)
				personUid=pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid()-1;
			else{
				personUid=pamActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid()-1;
			}
			if(personVOCollection!=null){
				Iterator<Object> it=personVOCollection.iterator();
				while (it.hasNext()){
					PersonVO personVO=(PersonVO)it.next();
					if(personVO.getThePersonDT().getCd().equals("PAT")){
						patientVO= personVO;
						patientVO.getThePersonDT().setPersonUid(new Long(personUid));
						Collection<Object> thePersonVOCollection = new ArrayList<Object>();
						thePersonVOCollection.add(patientVO);
						patientVO.setItNew(true);
						patientVO.setItDirty(false);
						patientVO.getThePersonDT().setItDirty(false);
						patientVO.getThePersonDT().setItNew(true);
						personVO.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
						personVO.getThePersonDT().setStatusTime(new Timestamp(new Date().getTime()));
						if(pageActProxyVO!=null)
							pageActProxyVO.setThePersonVOCollection(thePersonVOCollection);
						else{
							pamActProxyVO.setThePersonVOCollection(thePersonVOCollection);
						}
						break;
					}
				}
			}
			if(entities!=null && entities.size()>0){
				Iterator iterator = entities.iterator();
				while(iterator.hasNext()){
					EdxRuleManageDT edxRuleManageDT =(EdxRuleManageDT)iterator.next();
					ParticipationDT participationDT = new ParticipationDT();
					participationDT.setTypeCd(edxRuleManageDT.getParticipationTypeCode());
					participationDT.setSubjectEntityUid(edxRuleManageDT.getParticipationUid());
					participationDT.setSubjectClassCd(edxRuleManageDT.getParticipationClassCode());
					if(participationDT.getTypeCd().equals("OrgAsReporterOfPHC")){
						isOrgAsReporterOfPHCPartDT=true;
					}else if(participationDT.getTypeCd().equals("PhysicianOfPHC")){
						isPhysicianOfPHCDT=true;
					}
					
					createActEntityObject(participationDT,pageActProxyVO,pamActProxyVO,nbsActEntityDTColl,partColl);
				}
			}
			if(coll!=null){
				
				Iterator<Object> it=coll.iterator();
				while (it.hasNext()){
					ParticipationDT partDT = (ParticipationDT)it.next();
					boolean createActEntity=false;
					String typeCd =partDT.getTypeCd();
					if(typeCd.equalsIgnoreCase(EdxELRConstants.ELR_AUTHOR_CD)&& partDT.getSubjectClassCd().equals(EdxELRConstants.ELR_ORG) && !isOrgAsReporterOfPHCPartDT){
						createActEntity=true;
						partDT.setTypeCd("OrgAsReporterOfPHC");
					}
					if(typeCd.equalsIgnoreCase(EdxELRConstants.ELR_ORDER_CD)&& partDT.getSubjectClassCd().equals(EdxELRConstants.ELR_PERSON_CD) && !isPhysicianOfPHCDT ){
						createActEntity=true;
						partDT.setTypeCd("PhysicianOfPHC");
					}
					//gst- ND-4326 Physician not getting populated..
					if(typeCd.equalsIgnoreCase(EdxELRConstants.ELR_ORDERER_CD) && partDT.getSubjectClassCd().equals(EdxELRConstants.ELR_PERSON_CD) && !isPhysicianOfPHCDT ){
						createActEntity=true;
						partDT.setTypeCd("PhysicianOfPHC");
					}
					//Transfer the ordering facility over if it is on the PageBuilder page
					if(typeCd.equalsIgnoreCase(EdxELRConstants.ELR_ORDERER_CD)&& 
							partDT.getSubjectClassCd().equals(EdxELRConstants.ELR_ORG) &&
							partDT.getCd().equals(EdxELRConstants.ELR_OP_CD) &&
							questionIdentifierMap != null &&
							questionIdentifierMap.containsKey("NBS291")){
						createActEntity=true;
						partDT.setTypeCd("OrgAsClinicOfPHC");
					}					
					if(typeCd.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_SUBJECT_CD) ){
						createActEntity=true;
						partDT.setTypeCd("SubjOfPHC");
						partDT.setSubjectEntityUid(personUid);
					}
					if(createActEntity){
						createActEntityObject(partDT,pageActProxyVO,pamActProxyVO,nbsActEntityDTColl,partColl);
					}
					
				}
					
			}
			
			if(pageActProxyVO!=null){
				pageActProxyVO.setTheParticipationDTCollection(partColl);
				PamVO pamVO = null;
				if(pageActProxyVO.getPageVO()!=null)
					pamVO=pageActProxyVO.getPageVO();
				else
					pamVO = new PamVO();
				pamVO.setActEntityDTCollection(nbsActEntityDTColl);
				pageActProxyVO.setPageVO(pamVO);
				return pageActProxyVO;
			}
			else{
				pamActProxyVO.setTheParticipationDTCollection(partColl);
				PamVO pamVO = null;
				if(pamActProxyVO.getPamVO()!=null)
					pamVO=pamActProxyVO.getPamVO();
				else
					pamVO = new PamVO();
				pamVO.setActEntityDTCollection(nbsActEntityDTColl);
				pamActProxyVO.setPamVO(pamVO);
				return pamActProxyVO;
			}
				
		} catch (Exception e) {
			logger.error("AutoInvestigationHandler-transferValuesTOActProxyVO Exception raised"+e);
			throw new NEDSSAppException("AutoInvestigationHandler-transferValuesTOActProxyVO Exception raised"+e);
		}
	}
	
	
	private void createActEntityObject(ParticipationDT partDT, PageActProxyVO pageActProxyVO, PamProxyVO pamActProxyVO, Collection<Object> nbsActEntityDTColl, Collection<Object> partColl ){
		CachedDropDownValues srtc = new CachedDropDownValues();
		
		partDT.setActClassCd(NEDSSConstants.CLASS_CD_CASE);
		if(pageActProxyVO!=null)
			partDT.setActUid(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
		else
			partDT.setActUid(pamActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
		//partDT.setTypeCd(typeCd.trim());
		partDT.setTypeDescTxt(srtc.getDescForCode("PAR_TYPE", partDT.getTypeCd()));
		partDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		partDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		partDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		partDT.setItNew(true);
		partDT.setItDirty(false);
		partColl.add(partDT);
		
		NbsActEntityDT nbsActEntityDT = new NbsActEntityDT();

		if(pageActProxyVO!=null){
			nbsActEntityDT.setAddTime(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime());
			nbsActEntityDT.setLastChgTime(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime());
			nbsActEntityDT.setLastChgUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId());
			nbsActEntityDT.setRecordStatusCd(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getRecordStatusCd());
			nbsActEntityDT.setAddUserId(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
		}else{
			nbsActEntityDT.setAddTime(pamActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime());
			nbsActEntityDT.setLastChgTime(pamActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime());
			nbsActEntityDT.setLastChgUserId(pamActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgUserId());
			nbsActEntityDT.setRecordStatusCd(pamActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getRecordStatusCd());
			nbsActEntityDT.setAddUserId(pamActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId());
		}
		nbsActEntityDT.setActUid(partDT.getActUid());
		nbsActEntityDT.setEntityUid(partDT.getSubjectEntityUid());
		nbsActEntityDT.setEntityVersionCtrlNbr(new Integer(1));
		nbsActEntityDT.setRecordStatusTime(partDT.getRecordStatusTime());
		nbsActEntityDT.setTypeCd(partDT.getTypeCd());
		nbsActEntityDT.setItNew(true);
		nbsActEntityDT.setItDirty(false);
		nbsActEntityDTColl.add(nbsActEntityDT);
	
	}
	
	@SuppressWarnings("unchecked")
	public void populateProxyFromPrePopMapping(PageActProxyVO pageActProxyVO, EdxLabInformationDT edxLabInformationDT)
			throws NEDSSAppException {
		try {
			QuestionsCache.fillPrePopMap();
			TreeMap<Object, Object> fromPrePopMap = (TreeMap<Object, Object>) QuestionsCache.fromPrePopFormMapping
					.get(NEDSSConstants.LAB_FORM_CD);
			Collection<ObservationVO> obsCollection = edxLabInformationDT.getLabResultProxyVO()
					.getTheObservationVOCollection();
			TreeMap<Object, Object> prePopMap = new TreeMap<Object, Object>();
			ObservationVO obsVO = null;

			// Begin Dynamic Pre-pop mapping

			Iterator<ObservationVO> ite = obsCollection.iterator();
			while (ite.hasNext()) {
				ObservationVO obs = ite.next();
				if (obs.getTheObsValueNumericDTCollection() != null
						&& obs.getTheObsValueNumericDTCollection().size() > 0
						&& fromPrePopMap.containsKey(obs.getTheObservationDT().getCd())) {
					String value = obs.getObsValueNumericDT_s(0).getNumericUnitCd() == null
							? obs.getObsValueNumericDT_s(0).getNumericValue1() + ""
							: obs.getObsValueNumericDT_s(0).getNumericValue1() + "^"
									+ obs.getObsValueNumericDT_s(0).getNumericUnitCd();
					prePopMap.put(obs.getTheObservationDT().getCd(), value);
				} else if (obs.getTheObsValueDateDTCollection() != null
						&& obs.getTheObsValueDateDTCollection().size() > 0
						&& fromPrePopMap.containsKey(obs.getTheObservationDT().getCd())) {
					String value = StringUtils.formatDate(obs.getObsValueDateDT_s(0).getFromTime());
					prePopMap.put(obs.getTheObservationDT().getCd(), value);
				} else if (obs.getTheObsValueCodedDTCollection() != null
						&& obs.getTheObsValueCodedDTCollection().size() > 0) {
					String key = obs.getTheObservationDT().getCd() + "$" + obs.getObsValueCodedDT_s(0).getCode();
					if (fromPrePopMap.containsKey(key))
						prePopMap.put(key, obs.getObsValueCodedDT_s(0).getCode());
					else if (fromPrePopMap.containsKey(obs.getTheObservationDT().getCd()))
						prePopMap.put(obs.getTheObservationDT().getCd(), obs.getObsValueCodedDT_s(0).getCode());
				} else if (obs.getTheObsValueTxtDTCollection() != null && obs.getTheObsValueTxtDTCollection().size() > 0
						&& fromPrePopMap.containsKey(obs.getTheObservationDT().getCd())) {
					Iterator<Object> txtIte = obs.getTheObsValueTxtDTCollection().iterator();
					while (txtIte.hasNext()) {
						ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) txtIte.next();
						if (obsValueTxtDT.getTxtTypeCd() == null || obsValueTxtDT.getTxtTypeCd().trim().equals("")
								|| obsValueTxtDT.getTxtTypeCd().equalsIgnoreCase("O")) {
							prePopMap.put(obs.getTheObservationDT().getCd(), obsValueTxtDT.getValueTxt());
							break;
						}
					}
				} 
			}
			populateFromPrePopMapping(prePopMap, pageActProxyVO);

		} catch (Exception e) {
			logger.error("AutoInvestigationHandler-populateProxyFromPrePopMapping Exception raised" + e);
			throw new NEDSSAppException("AutoInvestigationHandler-populateProxyFromPrePopMapping Exception raised" + e);
		}
	}

	@SuppressWarnings("unchecked")
	public void populateFromPrePopMapping(TreeMap<Object, Object> prePopMap, PageActProxyVO pageActProxyVO)
			throws Exception {
		try {
			PublicHealthCaseDT phcDT = pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + phcDT.getProgAreaCd() + "')",
					phcDT.getCd());
			if (programAreaVO == null) // level 2 condition for Hepatitis Diagnosis
				programAreaVO = cdv.getProgramAreaCondition("('" + phcDT.getProgAreaCd() + "')", 2, phcDT.getCd());
			String investigationFormCd = programAreaVO.getInvestigationFormCd();

			Map<Object, Object> questionMap = (Map<Object, Object>) QuestionsCache.dmbMap.get(investigationFormCd);

			if (prePopMap == null || prePopMap.size() == 0)
				return;
			TreeMap<Object, Object> toPrePopMap = (TreeMap<Object, Object>) QuestionsCache
					.getToPrePopFormMapping(investigationFormCd);
			if (toPrePopMap != null && toPrePopMap.size() > 0) {
				Collection<Object> toPrePopColl = toPrePopMap.values();
				Map<Object, Object> answerMap = new HashMap<Object, Object>();
				if (toPrePopColl != null && toPrePopColl.size() > 0) {
					for (Object obj : toPrePopColl) {
						PrePopMappingDT toPrePopMappingDT = (PrePopMappingDT) obj;
						String mappingKey = toPrePopMappingDT.getFromAnswerCode() == null
								? toPrePopMappingDT.getFromQuestionIdentifier()
								: toPrePopMappingDT.getFromQuestionIdentifier() + "$"
										+ toPrePopMappingDT.getFromAnswerCode();
						if (prePopMap.containsKey(mappingKey)) {
							String value = null;
							String dataLocation = null;
							NbsQuestionMetadata quesMetadata = (NbsQuestionMetadata) questionMap
									.get(toPrePopMappingDT.getToQuestionIdentifier());
							if (quesMetadata != null)
								dataLocation = quesMetadata.getDataLocation();
							if (toPrePopMappingDT.getToDataType() != null
									&& toPrePopMappingDT.getToDataType().equals(NEDSSConstants.DATE_DATATYPE)) {
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
									SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
									String stringDate = (String) prePopMap.get(mappingKey);
									if (stringDate != null && stringDate.length() > 8)
										stringDate = stringDate.substring(0, 8);
									Date date = formatter.parse(stringDate);
									value = sdf.format(date);
								} catch (Exception ex) {
									logger.error("Could not convert to date from value :" + prePopMap.get(mappingKey));
								}
							}

							else if (toPrePopMappingDT.getToAnswerCode() != null)
								value = toPrePopMappingDT.getToAnswerCode();
							else
								value = (String) prePopMap.get(mappingKey);

							if (value != null && dataLocation != null
									&& dataLocation.startsWith(RenderConstants.PUBLIC_HEALTH_CASE)) {
								String columnName = dataLocation.substring(dataLocation.indexOf(".") + 1,
										dataLocation.length());
								DynamicBeanBinding.populateBean(phcDT, columnName, value);
							} else if (value != null && dataLocation != null
									&& dataLocation.endsWith(RenderConstants.ANSWER_TXT)) {
								NbsCaseAnswerDT caseAnswerDT = new NbsCaseAnswerDT();
								caseAnswerDT.setAnswerTxt(value);
								CDAPHCProcessor.setStandardNBSCaseAnswerVals(phcDT, caseAnswerDT);
								caseAnswerDT.setNbsQuestionUid(quesMetadata.getNbsQuestionUid());
								caseAnswerDT.setNbsQuestionVersionCtrlNbr(quesMetadata.getQuestionVersionNbr());
								caseAnswerDT.setSeqNbr(0);
								answerMap.put(quesMetadata.getNbsQuestionUid(), caseAnswerDT);
							}
						}
					}
				}
				pageActProxyVO.getPageVO().setPamAnswerDTMap(answerMap);
			}
			else 
				logger.debug("No pre-pop mapping for Code: "+investigationFormCd);
		} catch (Exception e) {
			logger.error("Error in populateFromPrePopMapping");
			logger.fatal("AutoInvestigationHandler.populateFromPrePopMapping Exception thrown:" + e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
	}
}
