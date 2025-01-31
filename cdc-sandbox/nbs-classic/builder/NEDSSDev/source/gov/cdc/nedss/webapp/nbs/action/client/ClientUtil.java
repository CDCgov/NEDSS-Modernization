package gov.cdc.nedss.webapp.nbs.action.client;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;


import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;












import org.apache.log4j.Logger;


public class ClientUtil {
	private static final Logger logger = Logger.getLogger(ClientUtil.class);
	public static ClientVO setPatientInformation(String actionMode, PersonVO personVO, ClientVO clientVO, HttpServletRequest request, String formCd) {
	  try {
		Map<Object,Object> answerMap = new HashMap<Object,Object>();
		//set Demographic Info
		setPatientDemographicInfoToAnswers(actionMode, personVO, answerMap, formCd);
		//set SSN Info
		setSSNInfoToAnswers(personVO, answerMap, formCd);
		//Set PatientName Info
		setPatientNameInfoToAnswers(personVO, answerMap, formCd);
		//Set Locator Info
		setLocatorInfoToAnswers(personVO, answerMap, formCd);

		//set Race Info to ** ClientVO
		setRaceInfoToClientVO(personVO, clientVO,formCd);
		
		//setPatientName Information
		convertPatientNameAndAliaName(personVO, clientVO);
		
		PersonUtil.setCurrentAgeToRequest(request,personVO.getThePersonDT());
		clientVO.getAnswerMap().putAll(answerMap);
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.setPatientInformation encountered.." + ex.getMessage());
		  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
		  ex.printStackTrace();
	  }
		return clientVO;
		
	}

	/**
	 * setPatientInformation2: used from right investigation on compare/merge investigations screen
	 * @param actionMode
	 * @param personVO
	 * @param clientVO
	 * @param request
	 * @param formCd
	 * @return
	 */
	public static ClientVO setPatientInformation2(String actionMode, PersonVO personVO, ClientVO clientVO, HttpServletRequest request, String formCd) {
		  try {
			Map<Object,Object> answerMap = new HashMap<Object,Object>();
			//set Demographic Info
			setPatientDemographicInfoToAnswers(actionMode, personVO, answerMap, formCd);
			//set SSN Info
			setSSNInfoToAnswers(personVO, answerMap, formCd);
			//Set PatientName Info
			setPatientNameInfoToAnswers(personVO, answerMap, formCd);
			//Set Locator Info
			setLocatorInfoToAnswers(personVO, answerMap, formCd);

			//set Race Info to ** ClientVO
			setRaceInfoToClientVO(personVO, clientVO,formCd);
			PersonUtil.setCurrentAgeToRequest2(request,personVO.getThePersonDT());
			clientVO.getAnswerMap().putAll(answerMap);
		  } catch (Exception ex) {
			  logger.error("Exception in ClientUtil.setPatientInformation encountered.." + ex.getMessage());
			  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
			  ex.printStackTrace();
		  }
			return clientVO;
			
		}
	
	public static void setPersonIdDetails(PersonVO vo, BaseForm form)
			throws Exception {
		Collection<Object> personIdColl = vo.getTheEntityIdDTCollection();

		try {
			ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
			Map<Object, ArrayList<BatchEntry>> batchMaplist = new HashMap<Object, ArrayList<BatchEntry>>();
			String hivPermissions = (String)form.getSecurityMap().get(NEDSSConstants.HAS_HIV_PERMISSIONS);
			if (personIdColl != null && personIdColl.size() > 0) {
				NedssUtils nUtil = new NedssUtils();
				nUtil.sortObjectByColumn("getAsOfDate", personIdColl, false);
				HashMap<Object, Object> formMap = new HashMap<Object, Object>();
				formMap = (HashMap<Object, Object>) form.getFormFieldMap();
				CachedDropDownValues cddv = new CachedDropDownValues();
				Iterator<Object> personPersonIdsIte = personIdColl.iterator();
				while (personPersonIdsIte.hasNext()) {
					EntityIdDT ids = (EntityIdDT) personPersonIdsIte.next();
					if(ids.getTypeCd()!=null && ids.getTypeCd().equals("SS"))
						continue;
					if(ids.getTypeCd()!=null && ids.getTypeCd().equals("PSID") && (hivPermissions==null || hivPermissions.equals("F")))
						continue;
					if (ids.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)
							&& ids.getRecordStatusCd() != null
							&& ids.getRecordStatusCd().equals(
									NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						BatchEntry dt = new BatchEntry();
						HashMap<String, String> map = new HashMap<String, String>();
						String val = ids.getTypeCd() == null ? "" : ids
								.getTypeCd()
								+ "$$"
								+ (cddv.getDescForCode("EI_TYPE_PAT",
										ids.getTypeCd()) ==null?ids.getTypeCd():cddv.getDescForCode("EI_TYPE_PAT",
												ids.getTypeCd()));

							if (ids.getTypeCd() != null
									&& ids.getTypeCd().equalsIgnoreCase(NEDSSConstants.OTHER)
									&& ids.getTypeDescTxt() != null
									&& ids.getTypeDescTxt().trim().length() > 0)
								val = val + ":" + ids.getTypeDescTxt();

						map.put(PageConstants.PATIENT_ENTITY_ID_TYPE, val);

						map.put(PageConstants.PATIENT_ENTITY_ID_ASSIGNING_AUTHORITY,
								ids.getAssigningAuthorityCd() == null ? ""
										: ids.getAssigningAuthorityCd()
												+ "$$"
												+ (cddv.getDescForCode("EI_AUTH_PAT",
														ids.getAssigningAuthorityCd())==null?ids.getAssigningAuthorityCd():cddv.getDescForCode("EI_AUTH_PAT",
																ids.getAssigningAuthorityCd())));
						map.put(PageConstants.PATIENT_ENTITY_ID_VALUE,
								ids.getRootExtensionTxt() == null ? "" : ids
										.getRootExtensionTxt());
						map.put(PageConstants.PATIENT_ENTITY_ID_AS_OF, StringUtils.formatDate(ids.getAsOfDate()));
						dt.setAnswerMap(map);
						//this field will be compared for updates
						dt.setLocalId(ids.getEntityUid().longValue()+""+ids.getEntityIdSeq());
						dt.setId(PageForm.getNextId());
						dt.setSubsecNm(PageConstants.ENTITY_ID_SUBSECTION);
						alist.add(dt);
				  }
				}
			}
			batchMaplist.put(PageConstants.ENTITY_ID_SUBSECTION, alist);
			if (form.getBatchEntryMap() == null)
				form.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
			form.getBatchEntryMap().putAll(batchMaplist);
		} catch (Exception ex) {
			throw new Exception(
					"Exception while converting entity IDs to batch for patient revision: "
							+ ex.getMessage());
		}
	}
	
	public static void setPersonIdDetails2(PersonVO vo, BaseForm form)
			throws Exception {
		Collection<Object> personIdColl = vo.getTheEntityIdDTCollection();

		try {
			ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
			Map<Object, ArrayList<BatchEntry>> batchMaplist = new HashMap<Object, ArrayList<BatchEntry>>();
			if (personIdColl != null && personIdColl.size() > 0) {
				NedssUtils nUtil = new NedssUtils();
				nUtil.sortObjectByColumn("getAsOfDate", personIdColl, false);
				HashMap<Object, Object> formMap = new HashMap<Object, Object>();
				formMap = (HashMap<Object, Object>) form.getFormFieldMap();
				CachedDropDownValues cddv = new CachedDropDownValues();
				Iterator<Object> personPersonIdsIte = personIdColl.iterator();
				while (personPersonIdsIte.hasNext()) {
					EntityIdDT ids = (EntityIdDT) personPersonIdsIte.next();
					if (ids.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)
							&& ids.getRecordStatusCd() != null
							&& ids.getRecordStatusCd().equals(
									NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						BatchEntry dt = new BatchEntry();
						HashMap<String, String> map = new HashMap<String, String>();
						// map.put("idDate",
						// (StringUtils.formatDate(ids.getAsOfDate())).toString());
						FormField ff = (FormField) formMap
								.get(PageConstants.PATIENT_ENTITY_ID_TYPE);
						if(ff!=null){
						String val = ids.getTypeCd() == null ? "" : ids
								.getTypeCd()
								+ "$$"
								+ (cddv.getDescForCode("EI_TYPE_PAT",
										ids.getTypeCd()) ==null?"":cddv.getDescForCode("EI_TYPE_PAT",
												ids.getTypeCd()));


						if (ids.getTypeCd() != null
								&& ids.getTypeCd().equalsIgnoreCase(NEDSSConstants.OTHER)
								&& ids.getTypeDescTxt() != null
								&& ids.getTypeDescTxt().trim().length() > 0)
							val = val + ":" + ids.getTypeDescTxt();

						map.put(PageConstants.PATIENT_ENTITY_ID_TYPE, val);
						FormField ff1 = (FormField) formMap
								.get(PageConstants.PATIENT_ENTITY_ID_ASSIGNING_AUTHORITY);
						if(ff1!=null){
						map.put(PageConstants.PATIENT_ENTITY_ID_ASSIGNING_AUTHORITY,
								ids.getAssigningAuthorityCd() == null ? ""
										: ids.getAssigningAuthorityCd()
												+ "$$"
												+ (cddv.getDescForCode("EI_AUTH_PAT",
														ids.getAssigningAuthorityCd())==null?"":cddv.getDescForCode("EI_AUTH_PAT",
																ids.getAssigningAuthorityCd())));
						}
						map.put(PageConstants.PATIENT_ENTITY_ID_VALUE,
								ids.getRootExtensionTxt() == null ? "" : ids
										.getRootExtensionTxt());
						map.put(PageConstants.PATIENT_ENTITY_ID_AS_OF, StringUtils.formatDate(ids.getAsOfDate()));
						dt.setAnswerMap(map);
						//this field will be compared for updates
						dt.setLocalId(ids.getEntityUid().longValue()+""+ids.getEntityIdSeq());
						dt.setId(PageForm.getNextId());
						dt.setSubsecNm(PageConstants.ENTITY_ID_SUBSECTION+"_2");
						alist.add(dt);
					}
				  }
				}
			}
			batchMaplist.put(PageConstants.ENTITY_ID_SUBSECTION+"_2", alist);
			if (form.getBatchEntryMap2() == null)
				form.setBatchEntryMap2(new HashMap<Object, ArrayList<BatchEntry>>());
			form.getBatchEntryMap2().putAll(batchMaplist);
		} catch (Exception ex) {
			throw new Exception(
					"Exception while converting entity IDs to batch for patient revision: "
							+ ex.getMessage());
		}
	}

	/**
	 * Sets the Patient Demographic Info to AnswersMap, a property of PamClientVO
	 * @param personVO
	 * @param answerMap
	 */
   private static void setPatientDemographicInfoToAnswers(String actionMode, PersonVO personVO, Map<Object,Object> answerMap, String formCd) {
	 try {
	   PersonDT dt = personVO.getThePersonDT();
	   //if(dt.getPersonParentUid()!=null)
		//   answerMap.put(PageConstants.PERSON_PARENT_UID, dt.getPersonParentUid().toString());
	   answerMap.put(PageConstants.PATIENT_LOCAL_ID, dt.getLocalId());
	   if(personVO.getDefaultJurisdictionCd()!=null)
		   answerMap.put(PageConstants.JURISDICTION,personVO.getDefaultJurisdictionCd());
	   answerMap.put(PamConstants.DOB, StringUtils.formatDate(dt.getBirthTime()));
	   if(actionMode != null && actionMode.equals(NEDSSConstants.CREATE_LOAD_ACTION))
		   answerMap.put(PageConstants.GEN_COMMENTS, "");
	   else
		   answerMap.put(PageConstants.GEN_COMMENTS, dt.getDescription());
	   String currentSex = dt.getCurrSexCd();
	   answerMap.put(PageConstants.CURR_SEX, currentSex);
	   answerMap.put(PageConstants.ADDITIONAL_GENDER,dt.getAdditionalGenderCd());
	   answerMap.put(PageConstants.SEX_UNKNOWN_REASON, dt.getSexUnkReasonCd());
	   answerMap.put(PageConstants.TRANSGENDER_INFORMATION,dt.getPreferredGenderCd());
	   answerMap.put(PageConstants.BIRTH_SEX, dt.getBirthGenderCd());
	   answerMap.put(PageConstants.BIRTH_COUNTRY, dt.getBirthCntryCd());
	   answerMap.put(PageConstants.IS_PAT_DECEASED,dt.getDeceasedIndCd());
	   answerMap.put(PageConstants.DECEASED_DATE, StringUtils.formatDate(dt.getDeceasedTime()));
	   answerMap.put(PageConstants.MAR_STAT, dt.getMaritalStatusCd());
	   //Ethnicity
	   answerMap.put(PageConstants.ETHNICITY, dt.getEthnicGroupInd());
	   answerMap.put(PageConstants.ETHNICITY_UNK_REASON, dt.getEthnicUnkReasonCd());
	 //eHARS ID
	   if(dt.getEharsId()!=null)
		   answerMap.put(PageConstants.EHARS_ID, dt.getEharsId());
	   answerMap.put(PageConstants.PRIMARY_LANGUAGE, dt.getPrimLangCd());
	   answerMap.put(PageConstants.SPEAKS_ENGLISH, dt.getSpeaksEnglishCd());
	   answerMap.put(PageConstants.PRIMARY_OCCUPATION, dt.getOccupationCd());
	   //deceased Ind
	   if(dt.getDeceasedTime()!=null && dt.getDeceasedIndCd()==null)
		   answerMap.put(PageConstants.IS_PAT_DECEASED, NEDSSConstants.YES);
	   
	   if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR)|| (formCd.equals(NBSConstantUtil.CONTACT_REC)))){
		   answerMap.put(PamConstants.REP_AGE, dt.getAgeReported());
		   answerMap.put(PamConstants.REP_AGE_UNITS, dt.getAgeReportedUnitCd());
		   //Load the asofdates (appears on Edit)
		   answerMap.put(PamConstants.DEM_DATA_AS_OF, StringUtils.formatDate(dt.getAsOfDateAdmin()));
		   answerMap.put(PamConstants.SEX_AND_BIRTH_INFORMATION_AS_OF, StringUtils.formatDate(dt.getAsOfDateSex()));
		   answerMap.put(PamConstants.MORTALITY_INFORMATION_AS_OF, StringUtils.formatDate(dt.getAsOfDateMorbidity()));
		   answerMap.put(PamConstants.MARITAL_STATUS_AS_OF, StringUtils.formatDate(dt.getAsOfDateGeneral()));
		   answerMap.put(PamConstants.ETHNICITY_AS_OF, StringUtils.formatDate(dt.getAsOfDateEthnicity()));
	   }else{
		   answerMap.put(PageConstants.REP_AGE, dt.getAgeReported());
		   answerMap.put(PageConstants.REP_AGE_UNITS, dt.getAgeReportedUnitCd());
		   //Load the asofdates (appears on Edit)
		   if (actionMode.equals(NEDSSConstants.CREATE_LOAD_ACTION)) {
			   //on create - default As Of's to current date
			   answerMap.put(PageConstants.DEM_DATA_AS_OF, StringUtils.formatDate(new Timestamp(new Date().getTime())));
		   } else {
			   answerMap.put(PageConstants.DEM_DATA_AS_OF, StringUtils.formatDate(dt.getAsOfDateAdmin()));
			   answerMap.put(PageConstants.SEX_AND_BIRTH_INFORMATION_AS_OF, StringUtils.formatDate(dt.getAsOfDateSex()));
			   answerMap.put(PageConstants.MORTALITY_INFORMATION_AS_OF, StringUtils.formatDate(dt.getAsOfDateMorbidity()));
			   answerMap.put(PageConstants.MARITAL_STATUS_AS_OF, StringUtils.formatDate(dt.getAsOfDateGeneral()));
			   answerMap.put(PageConstants.ETHNICITY_AS_OF, StringUtils.formatDate(dt.getAsOfDateEthnicity()));
		   }
		   
		   //Used to render lab header when created from Data Entry.
		   answerMap.put(PageConstants.EVENT_SUMMARY_PATIENT_ID, PersonUtil.getDisplayLocalID(dt.getLocalId()));
		   answerMap.put(PageConstants.EVENT_SUMMARY_ADDRESS, PageLoadUtil.extractAddressAsString(personVO));
		   String personAge = PersonUtil.displayAgeForPatientResults(StringUtils.formatDate(dt.getBirthTime())).toString().trim();
		   if(personAge!=null && personAge.length()==0)
			   personAge = "---";
		   answerMap.put(PageConstants.EVENT_SUMMARY_PERSON_AGE, personAge);
		   CachedDropDownValues cache = new CachedDropDownValues();
		   answerMap.put(PageConstants.EVENT_SUMMARY_SEX, cache.getDescForCode("SEX", dt.getCurrSexCd()));
		   answerMap.put(PageConstants.MULTIPLE_BIRTH_IND_DEM116_B, dt.getMultipleBirthInd());
		   if(dt.getBirthOrderNbr()!=null)
			   answerMap.put(PageConstants.BIRTH_ORDER_NBR_DEM117_B, dt.getBirthOrderNbr()+"");
		   
	   }
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.setPatientDemographicInfoToAnswers encountered.." + ex.getMessage());
		  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
		  ex.printStackTrace();
	  }
   }
   /**
    * Sets the Patient's SSN to AnswersMap, a property of PamClientVO
    * @param personVO
    * @param answerMap
    */
   private static void setSSNInfoToAnswers(PersonVO personVO, Map<Object,Object> answerMap, String formCd) {
      try {
	   Collection<Object>  ids = personVO.getTheEntityIdDTCollection();
	      if (ids != null) {
	    	 Iterator<Object>  iter = ids.iterator();
	          while (iter.hasNext()) {
	              EntityIdDT id = (EntityIdDT) iter.next();
	              if (id != null) {
	                 if (id.getAssigningAuthorityCd() != null &&
	                     id.getAssigningAuthorityCd().equals("SSA") && id.getTypeCd() != null &&
	                     id.getTypeCd().equals("SS")) {
	                	 if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR))){
		                	 answerMap.put(PamConstants.SSN_AS_OF, StringUtils.formatDate(id.getAsOfDate()));
		                	 answerMap.put(PamConstants.SSN, id.getRootExtensionTxt());
	                	 }else{
	                		 answerMap.put(PageConstants.SSN_AS_OF, StringUtils.formatDate(id.getAsOfDate()));
		                	 answerMap.put(PageConstants.SSN, id.getRootExtensionTxt());
	                	 }
	                 }
	              }
	          }
	      }
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.setSSNInfoToAnswers encountered..");
		  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
		  ex.printStackTrace();
	  }
	     
   }
   /**
    * Sets the Patient Name Info to AnswersMap, a property of PamClientVO
    * @param personVO
    * @param answerMap
    */
   private static void setPatientNameInfoToAnswers(PersonVO personVO, Map<Object,Object> answerMap, String formCd) {
      try {
	      Collection<Object>  names = personVO.getThePersonNameDTCollection();
	      if (names != null) {
	        Iterator<Object>  iter = names.iterator();
	         Timestamp mostRecentNameAOD = null;
	         while (iter.hasNext()) {
	            PersonNameDT name = (PersonNameDT) iter.next();
	            if (name != null) {
	               if (name != null && name.getNmUseCd() != null &&
	                   name.getNmUseCd().equals(NEDSSConstants.LEGAL) &&
	                   name.getStatusCd() != null &&
	                   name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
	                   name.getRecordStatusCd() != null &&
	                   name.getRecordStatusCd().
	                   equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {

	                 if (mostRecentNameAOD == null ||
	                     (name.getAsOfDate() != null &&
	                     !name.getAsOfDate().before(mostRecentNameAOD))) {
						   mostRecentNameAOD = name.getAsOfDate();
						   answerMap.put(PageConstants.LAST_NM, name.getLastNm());
						   answerMap.put(PageConstants.FIRST_NM, name.getFirstNm());
						   answerMap.put(PageConstants.MIDDLE_NM, name.getMiddleNm());
						   answerMap.put(PageConstants.SUFFIX,name.getNmSuffix());
						   if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR)|| (formCd.equals(NBSConstantUtil.CONTACT_REC)))){
							   answerMap.put(PamConstants.NAME_INFORMATION_AS_OF, StringUtils.formatDate(mostRecentNameAOD));
						   }else{
							   answerMap.put(PageConstants.NAME_INFORMATION_AS_OF, StringUtils.formatDate(mostRecentNameAOD));
							  
						   }
	                 }
	               }
	               //else if((formCd.equals(NBSConstantUtil.CONTACT_REC) || formCd.startsWith(NBSConstantUtil.ContactRecordFormPrefix)) && name != null && name.getNmUseCd() != null &&
	               else if(name != null && name.getNmUseCd() != null &&
		                   name.getNmUseCd().equals(NEDSSConstants.ALIAS_NAME) &&
		                   name.getStatusCd() != null &&
		                   name.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
		                   name.getRecordStatusCd() != null &&
		                   name.getRecordStatusCd().
		                   equals(NEDSSConstants.RECORD_STATUS_ACTIVE))
	               	{
	            	   answerMap.put(PamConstants.ALIAS_NICK_NAME,name.getFirstNm());
	            	   if (mostRecentNameAOD == null ||
	  	                     (name.getAsOfDate() != null &&
	  	                     !name.getAsOfDate().before(mostRecentNameAOD))) {
	  						   mostRecentNameAOD = name.getAsOfDate();
	  						 if(formCd != null && (formCd.equals(NBSConstantUtil.CONTACT_REC))){
		  						   answerMap.put(PamConstants.NAME_INFORMATION_AS_OF, StringUtils.formatDate(mostRecentNameAOD));
	  						 }else{
	  							 answerMap.put(PageConstants.NAME_INFORMATION_AS_OF, StringUtils.formatDate(mostRecentNameAOD));
	  						 }
	  	                 }
	               	} //alias
	               
	               //For Lab header
	               answerMap.put(PageConstants.EVENT_SUMMARY_FULL_NAME, extractNameAsString(name));
	      		}
	   		}
		}
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.setPatientNameInfoToAnswers() encountered.." + ex.getMessage());
		  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
		  ex.printStackTrace();
	  }
	    	      
	}

   private static String extractNameAsString (PersonNameDT name) throws NEDSSAppException{
	    try{
	    	String strFName = "";
            String strMName = "";
            String strLName = "";

            if (name.getFirstNm() != null)
				strFName = name.getFirstNm();
			if (name.getMiddleNm() != null)
				strMName = name.getMiddleNm();
			if (name.getLastNm() != null)
				strLName = name.getLastNm();

			String strPName = strFName +" "+strMName+ " "+strLName;
			if(null == strPName || strPName.equalsIgnoreCase("null")){
				strPName ="";
			}
			
			return strPName;
	    }catch (NEDSSSystemException ex) {
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
   }
   /**
    * Sets the TeleLocator, PostalLocator info to AnswersMap, a property of PamClientVO
    * @param personVO
    * @param answerMap
    */
   private static void setLocatorInfoToAnswers(PersonVO personVO, Map<Object,Object> answerMap, String formCd) {
	   try {
		if(personVO.getTheEntityLocatorParticipationDTCollection()!=null){

			Collection<Object>  entityCOllection = personVO.getTheEntityLocatorParticipationDTCollection();
			if(entityCOllection.size()>0){
			 NedssUtils nUtil = new NedssUtils();
        	 nUtil.sortObjectByColumn("getAsOfDate", entityCOllection, true);
			}
			Iterator<Object> it = entityCOllection.iterator();
			while(it.hasNext()){
				EntityLocatorParticipationDT edt = (EntityLocatorParticipationDT)it.next();

				if (edt.getClassCd() != null && edt.getStatusCd()!=null && edt.getStatusCd().equals(NEDSSConstants.A) && edt.getRecordStatusCd()!=null && edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)
						&& edt.getClassCd().equals(NEDSSConstants.TELE)	&& edt.getUseCd().equals(
								NEDSSConstants.WORK_PHONE)&& edt.getCd() != null
						&& !edt.getCd().trim().equals("")&& edt.getCd().equals(NEDSSConstants.PHONE)&& edt.getTheTeleLocatorDT()
								.getPhoneNbrTxt() != null)
                    {
                        TeleLocatorDT teleLocatorDT = edt.getTheTeleLocatorDT();
                        String offPhoneNbr = teleLocatorDT.getPhoneNbrTxt();
                        String offExt = teleLocatorDT.getExtensionTxt();
                        if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR) ||formCd.equals(NBSConstantUtil.CONTACT_REC) )){
	                        answerMap.put(PamConstants.W_PHONE, offPhoneNbr);
	                        answerMap.put(PamConstants.W_PHONE_EXT, offExt);
	        				answerMap.put(PamConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
                        }else
                        {
                        	answerMap.put(PageConstants.W_PHONE, offPhoneNbr);
	                        answerMap.put(PageConstants.W_PHONE_EXT, offExt);
	        				answerMap.put(PageConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
                        }
                    }
                    if (edt.getClassCd() != null && edt.getStatusCd()!=null && edt.getStatusCd().equals(NEDSSConstants.A) && edt.getRecordStatusCd()!=null && edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)
						&& edt.getClassCd().equals(NEDSSConstants.TELE)	&& edt.getUseCd().equals(
								NEDSSConstants.HOME)&& edt.getCd() != null
						&& !edt.getCd().trim().equals("")&& edt.getCd().equals(NEDSSConstants.PHONE)&& edt.getTheTeleLocatorDT()
								.getPhoneNbrTxt() != null)
                    {
                        TeleLocatorDT teleLocatorDT = edt.getTheTeleLocatorDT();
                        String homePhoneNbr = teleLocatorDT.getPhoneNbrTxt();
                        String homeExt = teleLocatorDT.getExtensionTxt();
                        if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR)||formCd.equals(NBSConstantUtil.CONTACT_REC))){
	                        answerMap.put(PamConstants.H_PHONE, homePhoneNbr);
	                        answerMap.put(PamConstants.H_PHONE_EXT, homeExt);
	        				answerMap.put(PamConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
                        }else
                        {
                        	answerMap.put(PageConstants.H_PHONE, homePhoneNbr);
	                        answerMap.put(PageConstants.H_PHONE_EXT, homeExt);
	        				answerMap.put(PageConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
                        }
                    }
                    if(!(formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR))){
	                    if (edt.getClassCd() != null && edt.getStatusCd()!=null && edt.getStatusCd().equals(NEDSSConstants.A) && edt.getRecordStatusCd()!=null && edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)
	    						&& edt.getClassCd().equals(NEDSSConstants.TELE)	&& edt.getUseCd().equals(
	    								NEDSSConstants.MOBILE)&& edt.getCd() != null
	    						&& !edt.getCd().trim().equals("")&& edt.getCd().equals(NEDSSConstants.CELL)&& edt.getTheTeleLocatorDT()
	    								.getPhoneNbrTxt() != null)
	                        {
	                    		
	                            TeleLocatorDT teleLocatorDT = edt.getTheTeleLocatorDT();
	                            String cell = teleLocatorDT.getPhoneNbrTxt();
	                            if(formCd != null && (formCd.equals(NBSConstantUtil.CONTACT_REC))){
		                            answerMap.put(PamConstants.CELL_PHONE, cell);
		            				answerMap.put(PamConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
	                    		}else{
	                    			answerMap.put(PageConstants.CELL_PHONE, cell);
		            				answerMap.put(PageConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
	                    		}
	                        }
                    }
                    if(!(formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR))){
	                    if (edt.getClassCd() != null && edt.getStatusCd()!=null && edt.getStatusCd().equals(NEDSSConstants.A) && edt.getRecordStatusCd()!=null && edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)
	    						&& edt.getClassCd().equals(NEDSSConstants.TELE)	&& edt.getUseCd().equals(
	    								NEDSSConstants.HOME)&& edt.getCd() != null
	    						&& !edt.getCd().trim().equals("")&& edt.getCd().equals(NEDSSConstants.NET)&& edt.getTheTeleLocatorDT()
	    								.getEmailAddress() != null)
	                        {
	                            TeleLocatorDT teleLocatorDT = edt.getTheTeleLocatorDT();
	                            String email = teleLocatorDT.getEmailAddress();
	                            if(formCd != null && (formCd.equals(NBSConstantUtil.CONTACT_REC))){
		                            answerMap.put(PamConstants.EMAIL, email);
		            				answerMap.put(PamConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
	                            }else{
	                            	answerMap.put(PageConstants.EMAIL, email);
		            				answerMap.put(PageConstants.TELEPHONE_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
	                            }
	                        }
                    }
                    if (edt.getClassCd() != null && edt.getStatusCd()!=null && edt.getStatusCd().equals(NEDSSConstants.A) && edt.getRecordStatusCd()!=null && edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)
    						&& edt.getClassCd().equals(NEDSSConstants.POSTAL)){
                        PostalLocatorDT postal = edt.getThePostalLocatorDT();
                        //this is for file summary tab
                        if (edt.getCd() != null && edt.getUseCd() != null &&
                        		edt.getCd().equals(NEDSSConstants.HOME) &&
                        		edt.getUseCd().equals(NEDSSConstants.HOME) &&
                        		edt.getStatusCd() != null &&
                        		edt.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                        		edt.getRecordStatusCd() != null &&
                        		edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                        	if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR)||formCd.equals(NBSConstantUtil.CONTACT_REC))){
	                        	answerMap.put(PamConstants.ADDRESS_1,postal.getStreetAddr1());
	                        	answerMap.put(PamConstants.ADDRESS_2,postal.getStreetAddr2());
	                        	answerMap.put(PamConstants.STATE,postal.getStateCd());
	                        	answerMap.put(PamConstants.CITY,postal.getCityDescTxt());
	                        	answerMap.put(PamConstants.COUNTY,postal.getCntyCd());
	                        	answerMap.put(PamConstants.ZIP,postal.getZipCd());
	                        	answerMap.put(PamConstants.COUNTRY,postal.getCntryCd());
	                        	answerMap.put(PamConstants.WITHIN_CITY_LIMITS, postal.getWithinCityLimitsInd());
	                        	answerMap.put(PamConstants.ADDRESS_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
                        	}else{
                        		answerMap.put(PageConstants.ADDRESS_1,postal.getStreetAddr1());
	                        	answerMap.put(PageConstants.ADDRESS_2,postal.getStreetAddr2());
	                        	answerMap.put(PageConstants.STATE,postal.getStateCd());
	                        	answerMap.put(PageConstants.CITY,postal.getCityDescTxt());
	                        	answerMap.put(PageConstants.COUNTY,postal.getCntyCd());
	                        	answerMap.put(PageConstants.CENSUS_TRACT,postal.getCensusTract());
	                        	answerMap.put(PageConstants.ZIP,postal.getZipCd());
	                        	answerMap.put(PageConstants.COUNTRY,postal.getCntryCd());
	                        	answerMap.put(PageConstants.WITHIN_CITY_LIMITS, postal.getWithinCityLimitsInd());
	                        	answerMap.put(PageConstants.ADDRESS_INFORMATION_AS_OF, StringUtils.formatDate(edt.getAsOfDate()));
                        	}
                       } else if (edt.getCd() != null && edt.getUseCd() != null &&
                       		edt.getCd().equals(NEDSSConstants.OFFICE_CD) &&
                       		edt.getUseCd().equals(NEDSSConstants.PRIMARY_BUSINESS) &&
                       		edt.getStatusCd() != null &&
                       		edt.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
                       		edt.getRecordStatusCd() != null &&
                       		edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                       		answerMap.put(PageConstants.ADDRESS_1+"_W",postal.getStreetAddr1());
	                        answerMap.put(PageConstants.ADDRESS_2+"_W",postal.getStreetAddr2());
	                        answerMap.put(PageConstants.STATE+"_W",postal.getStateCd());
	                        answerMap.put(PageConstants.CITY+"_W",postal.getCityDescTxt());
	                        answerMap.put(PageConstants.COUNTY+"_W",postal.getCntyCd());
	                        answerMap.put(PageConstants.CENSUS_TRACT+"_W",postal.getCensusTract());
	                        answerMap.put(PageConstants.ZIP+"_W",postal.getZipCd());
	                        answerMap.put(PageConstants.COUNTRY+"_W",postal.getCntryCd());
	                        answerMap.put(PageConstants.ADDRESS_COMMENTS+"_W",edt.getLocatorDescTxt());
	                        answerMap.put(PageConstants.ADDRESS_INFORMATION_AS_OF+"_W", StringUtils.formatDate(edt.getAsOfDate()));
                       	}
                    }
                    if(!(formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR))){
	                    if (edt.getClassCd() != null && edt.getClassCd().equals(NEDSSConstants.POSTAL)){
	                        PostalLocatorDT postal = edt.getThePostalLocatorDT();
	                        //this is for Birth Country in contactTracing
	                        if (edt.getCd() != null && edt.getUseCd() != null &&
	                    		edt.getCd().equals(NEDSSConstants.BIRTHCD) &&
	                    		edt.getUseCd().equals(NEDSSConstants.BIRTH) &&
	                    		edt.getStatusCd() != null &&
	                    		edt.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
	                    		edt.getRecordStatusCd() != null &&
	                    		edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
	                        	answerMap.put(PageConstants.BIRTH_COUNTRY,postal.getCntryCd());
	                       }
	                    }
                    }
            }
		}
	  } catch (Exception ex) {
			  logger.error("Exception in ClientUtil.setLocatorInfoToAnswers() encountered.." + ex.getMessage());
			  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
			  ex.printStackTrace();
	  }
	}

   /**
    * Sets the Patient Race information to clientVO
    * @param personVO
    * @param clientVO
    */
   public static void setRaceInfoToClientVO(PersonVO personVO, ClientVO clientVO, String formCd) {
	   try {
		if(personVO.getThePersonRaceDTCollection()!=null){

			ArrayList<Object> asc = new ArrayList<Object> ();
			ArrayList<Object> hsc = new ArrayList<Object> ();
			ArrayList<Object> whi = new ArrayList<Object> ();
			ArrayList<Object> blk = new ArrayList<Object> ();
			ArrayList<Object> aian = new ArrayList<Object> ();


			ArrayList<Object> raceColl = (ArrayList<Object> )personVO.getThePersonRaceDTCollection();
			Iterator<Object> ite = raceColl.iterator();
			while(ite.hasNext()){
				PersonRaceDT  raceDT= (PersonRaceDT)ite.next();
				if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR)|| (formCd.equals(NBSConstantUtil.CONTACT_REC)))){
					clientVO.setAnswer(PamConstants.RACE_INFORMATION_AS_OF, StringUtils.formatDate(raceDT.getAsOfDate()));
				}else{
					clientVO.setAnswer(PageConstants.RACE_INFORMATION_AS_OF, StringUtils.formatDate(raceDT.getAsOfDate()));
				}
				String recordStatusCd = raceDT.getRecordStatusCd();

				if( recordStatusCd != null  && recordStatusCd.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {

					if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.UNKNOWN))
						clientVO.setUnKnownRace(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.OTHER_RACE))
						clientVO.setOtherRace(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.NOT_ASKED))
						clientVO.setNotAsked(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.REFUSED_TO_ANSWER))
						clientVO.setRefusedToAnswer(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.AFRICAN_AMERICAN))
						clientVO.setAfricanAmericanRace(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE))
						clientVO.setAmericanIndianAlskanRace(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.WHITE))
						clientVO.setWhiteRace(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.ASIAN))
						clientVO.setAsianRace(1);
					else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER))
						clientVO.setHawaiianRace(1);
					else if(raceDT.getRaceCategoryCd().equalsIgnoreCase(NEDSSConstants.ASIAN)){
						asc.add(raceDT.getRaceCd());
					}
					else if(raceDT.getRaceCategoryCd().equalsIgnoreCase(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER)){
						hsc.add(raceDT.getRaceCd());
					}
					else if(raceDT.getRaceCategoryCd().equalsIgnoreCase(NEDSSConstants.WHITE)){
						whi.add(raceDT.getRaceCd());
					}
					else if(raceDT.getRaceCategoryCd().equalsIgnoreCase(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE)){
						aian.add(raceDT.getRaceCd());
					}
					else if(raceDT.getRaceCategoryCd().equalsIgnoreCase(NEDSSConstants.AFRICAN_AMERICAN)){
						blk.add(raceDT.getRaceCd());
					}
				}
			}
			String[] asianRaceArray = (String[])asc.toArray(new String[asc.size()]);
			String[] hawaiianOtherRaceArray = (String[])hsc.toArray(new String[hsc.size()]);
			String[] whiteRaceArray = (String[])whi.toArray(new String[whi.size()]);
			String[] africanAmericanRaceArray = (String[])blk.toArray(new String[blk.size()]);
			String[] americanIndianRaceArray = (String[])aian.toArray(new String[aian.size()]);

			
			if(formCd != null && (formCd.equals(NEDSSConstants.INV_FORM_RVCT) || formCd.equals(NEDSSConstants.INV_FORM_VAR)|| (formCd.equals(NBSConstantUtil.CONTACT_REC)))){
				clientVO.setAnswerArray(PamConstants.DETAILED_RACE_ASIAN, asianRaceArray);
				clientVO.setAnswerArray(PamConstants.DETAILED_RACE_HAWAII, hawaiianOtherRaceArray);
			}else{
				clientVO.setAnswerArray(PageConstants.DETAILED_RACE_ASIAN, asianRaceArray);
				clientVO.setAnswerArray(PageConstants.DETAILED_RACE_HAWAII, hawaiianOtherRaceArray);
				clientVO.setAnswerArray(PageConstants.DETAILED_RACE_WHITE, whiteRaceArray);
				clientVO.setAnswerArray(PageConstants.DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE, americanIndianRaceArray);
				clientVO.setAnswerArray(PageConstants.DETAILED_RACE_AFRICAN_AMERICAN, africanAmericanRaceArray);
			}
		}
		  } catch (Exception ex) {
			  logger.error("Exception in ClientUtil.setRaceInfoToClientVO() encountered.." + ex.getMessage());
			  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
			  ex.printStackTrace();
	  }
   }

   
   /**
    * Sets the Patient Race information to clientVO
    * @param personVO
    * @param clientVO
    */
   public static void setRaceInfoToClientVO(PersonVO personVO, ClientVO clientVO) {
	   try {
        if(personVO.getThePersonRaceDTCollection()!=null){

            ArrayList<Object> asc = new ArrayList<Object> ();


            ArrayList<Object> raceColl = (ArrayList<Object> )personVO.getThePersonRaceDTCollection();
            Iterator<Object> ite = raceColl.iterator();
            while(ite.hasNext()){
                PersonRaceDT  raceDT= (PersonRaceDT)ite.next();
                String recordStatusCd = raceDT.getRecordStatusCd();

                if( recordStatusCd != null  && recordStatusCd.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {

                    if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.UNKNOWN))
                        clientVO.setUnKnownRace(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.OTHER_RACE))
                        clientVO.setOtherRace(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.NOT_ASKED))
                        clientVO.setNotAsked(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.REFUSED_TO_ANSWER))
                        clientVO.setRefusedToAnswer(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.AFRICAN_AMERICAN))
                        clientVO.setAfricanAmericanRace(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE))
                        clientVO.setAmericanIndianAlskanRace(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.WHITE))
                        clientVO.setWhiteRace(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.ASIAN))
                        clientVO.setAsianRace(1);
                    else if(raceDT.getRaceCd().equalsIgnoreCase(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER))
                        clientVO.setHawaiianRace(1);
                    
                      asc.add(raceDT.getRaceCd()); 
                }
            }
            String[]  asianRaceArray = (String[])asc.toArray(new String[asc.size()]); 
            clientVO.setAnswerArray(PageConstants.RACE, asianRaceArray);  
        }
	  } catch (Exception ex) {
			  logger.error("Exception in ClientUtil.setRaceInfoToClintVO() encountered.." + ex.getMessage());
			  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
			  ex.printStackTrace();
	  }
   }
   
  
    private static String getPersonName(PersonVO personVO)
    {
    	PersonNameDT mostRecentNameDt = null;
      try {
    	Collection<Object> names = personVO.getThePersonNameDTCollection();
    	
        if (names != null)
        {
            Iterator<Object> iter = names.iterator();
            Timestamp mostRecentNameAOD = null;
            while (iter.hasNext())
            {
                PersonNameDT name = (PersonNameDT) iter.next();
                if (name != null)
                {
                    if (NEDSSConstants.LEGAL.equals(name.getNmUseCd())
                            && NEDSSConstants.STATUS_ACTIVE.equals(name.getStatusCd())
                            && NEDSSConstants.RECORD_STATUS_ACTIVE.equals(name.getRecordStatusCd()))
                    {

                        if (mostRecentNameAOD == null
                                || (name.getAsOfDate() != null && !name.getAsOfDate().before(mostRecentNameAOD)))
                        {
                            mostRecentNameAOD = name.getAsOfDate();
                            mostRecentNameDt = name;
                        }
                    }
                }
            }
        }
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.setPersonName() encountered.." + ex.getMessage());
		  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
		  ex.printStackTrace();
      }
        if (mostRecentNameDt != null)
            return mostRecentNameDt.getFullName();
        else
            return "";
    }
     
    private static void setMotherLocatorInfo(PersonVO personVO, ClientVO clientVO)
    {
      try {
        if (personVO.getTheEntityLocatorParticipationDTCollection() != null)
        {
            Collection<Object> entityCOllection = personVO.getTheEntityLocatorParticipationDTCollection();
            if (entityCOllection.size() > 0)
            {
                NedssUtils nUtil = new NedssUtils();
                nUtil.sortObjectByColumn("getAsOfDate", entityCOllection, true);
            }
            Iterator<Object> it = entityCOllection.iterator();
            while (it.hasNext())
            {
                EntityLocatorParticipationDT edt = (EntityLocatorParticipationDT) it.next();

                if (NEDSSConstants.POSTAL.equals(edt.getClassCd()))
                {
                    PostalLocatorDT postal = edt.getThePostalLocatorDT();
                    if (NEDSSConstants.HOME.equals(edt.getCd()) && NEDSSConstants.HOME.equals(edt.getUseCd())
                            && NEDSSConstants.STATUS_ACTIVE.equals(edt.getStatusCd())
                            && NEDSSConstants.RECORD_STATUS_ACTIVE.equals(edt.getRecordStatusCd()))
                    {
                        clientVO.setAnswer(PageConstants.STATE, postal.getStateCd());
                        clientVO.setAnswer("CS017",  postal.getStateCd() ) ; // State FIPS
                        clientVO.setAnswer(PageConstants.CITY, postal.getCityDescTxt());
                        clientVO.setAnswer(PageConstants.COUNTY, postal.getCntyCd());
                        clientVO.setAnswer("CS019", postal.getCntyCd()) ; // County FIPS. 
                        clientVO.setAnswer(PageConstants.ZIP, postal.getZipCd());
                        clientVO.setAnswer(PageConstants.COUNTRY, postal.getCntryCd());
                        clientVO.setAnswer("CS018", postal.getCntryCd()) ; // Country FIPS.
                    }
                }
            }
        }
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.setMotherLocatorInfo encountered.." + ex.getMessage());
		  logger.error("Person=" + personVO.getThePersonDT().getLocalId());
		  ex.printStackTrace();
      }      
    }
    
    public static String batchRecordsToJson(Map map)
    {
      StringBuffer sb = new StringBuffer();
      try {

        if (map != null)
        {
            Iterator mapIt = map.entrySet().iterator();
            String batchrec[][] = null;
            while (mapIt.hasNext())
            {
                Map.Entry mappairs = (Map.Entry) mapIt.next();
                batchrec = (String[][]) mappairs.getValue();
                for (int i = 0; i < batchrec.length; i++)
                {
                    if (batchrec[i][0] != null)
                    {
                        if (sb.length() > 0)
                            sb.append(",");
                        sb.append("{ssName:'" + mappairs.getKey().toString() + "',qid:'" + batchrec[i][0] + "',cid:'"
                                + batchrec[i][5] + "'} ");
                    }
                }
            }
        }
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.batchRecordsToJson encountered.." + ex.getMessage());
		  ex.printStackTrace();
      } 
        return sb.toString();
    }
   /*
    * This method is currently not used, it returns a string of the Race description.
    */
	public static void setRaceDescToClient(
			Collection<Object> personRaceDTCollection, String raceCodes,
			ClientVO clientVO, String raceElementIdentifier) {
		StringBuffer strBuf = new StringBuffer("");
		Iterator<Object> ite = personRaceDTCollection.iterator();
		while(ite.hasNext()){
			PersonRaceDT  raceDT= (PersonRaceDT)ite.next();
			if (raceDT.getRaceCd() != null && !raceDT.getRaceCd().isEmpty()) {
				String findRaceStart = raceCodes.substring(raceCodes.indexOf(raceDT.getRaceCd()));
	            String findRaceWk = findRaceStart.substring(findRaceStart.indexOf("$") + 1);
	            String raceDesc =  findRaceWk.substring(0, findRaceWk.indexOf("|"));
				if (raceDesc != null && !raceDesc.isEmpty()) {
					if (strBuf.length() != 0) 
						strBuf.append(", ");
					strBuf.append(raceDesc);
					} 
				}
			}
		clientVO.setAnswer(raceElementIdentifier, strBuf.toString());
		return;
	}
	   /*
	    * This method is used to set the race codes into answer array.
	    * Corresponding Page element should be a multi select or multi select no save(1028).
	    * Should be: codeSetNm="RACE_CALCULATED"
	    */
	public static void setRaceArrayToClient(Collection<Object> personRaceDTCollection, 
			ClientVO clientVO, 
			String raceElementIdentifier) {
	  try {
		ArrayList<String> raceList = new ArrayList<String>();
		Iterator<Object> ite = personRaceDTCollection.iterator();
		while(ite.hasNext()){
			PersonRaceDT  raceDT= (PersonRaceDT)ite.next();
			if (raceDT.getRaceCd() != null && !raceDT.getRaceCd().isEmpty()) {
				raceList.add(raceDT.getRaceCd());
			}
		}
		if (raceList.size() > 0) {
			String[] raceStrArr = new String[raceList.size()];
		    raceStrArr = raceList.toArray(raceStrArr);
		    clientVO.setAnswerArray(raceElementIdentifier, raceStrArr);
		}
	  } catch (Exception ex) {
		  logger.error("Exception in ClientUtil.setRaceArrayToClient encountered.." + ex.getMessage());
		  ex.printStackTrace();
      } 		
		return;
	}
	
	/**
	 * Changes to accomodate concatetnated Patient Information
	 * @param personVO
	 * @param clientVO
	 */
	 private static void convertPatientNameAndAliaName(PersonVO personVO, ClientVO clientVO)
	    {
	        ArrayList<Object> pNamelist = (ArrayList<Object>) personVO.getThePersonNameDTCollection();
	        Iterator<Object> pNameIt = pNamelist.iterator();
	        while (pNameIt.hasNext())
	        {
	            PersonNameDT pNameDT = (PersonNameDT) pNameIt.next();
	            if (pNameDT != null)
	            {
	                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME))
	                {
	                    /* Name Details if we are seeing ELR data */
	                    {
	                        StringBuffer personLegalDetails = new StringBuffer("");
	                        personLegalDetails.append(pNameDT.getNmPrefix() == null ? "" : pNameDT.getNmPrefix()); // 101
	                        personLegalDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // 104
	                        personLegalDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // 105
	                        personLegalDetails.append(pNameDT.getMiddleNm2() == null ? "" : " " + pNameDT.getMiddleNm2()); // 106
	                        personLegalDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // 102
	                        personLegalDetails.append(pNameDT.getLastNm2() == null ? "" : " " + pNameDT.getLastNm2()); // 103
	                        personLegalDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // 107
	                        personLegalDetails.append(pNameDT.getNmDegree() == null ? "" : ", " + pNameDT.getNmDegree()); // 108
	                       clientVO.setAnswer(PageConstants.legalName_NBS462,personLegalDetails.toString() );
	                    }
	                }
	                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.ALIAS_NAME))
	                {
	                    StringBuffer personAliasDetails = new StringBuffer("");
	                    personAliasDetails.append(pNameDT.getNmPrefix() == null ? "" : pNameDT.getNmPrefix()); // 101
	                    personAliasDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // 104
	                    personAliasDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // 105
	                    personAliasDetails.append(pNameDT.getMiddleNm2() == null ? "" : " " + pNameDT.getMiddleNm2()); // 106
	                    personAliasDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // 102
	                    personAliasDetails.append(pNameDT.getLastNm2() == null ? "" : " " + pNameDT.getLastNm2()); // 103
	                    personAliasDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // 107
	                    personAliasDetails.append(pNameDT.getNmDegree() == null ? "" : ", " + pNameDT.getNmDegree()); // 108
	                    clientVO.setAnswer(PageConstants.AliaslegalName_NBS463, personAliasDetails.toString());
	                }
	            }
	        }
	    }
	 
	 

}

