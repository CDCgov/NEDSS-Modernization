package gov.cdc.nedss.act.interview.ejb.dao;

import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class InterviewSummaryDAO extends DAOBase {
	
	private static final LogUtils logger = new LogUtils(InterviewSummaryDAO.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private final String SELECT_INTERVIEWS_FOR_INVESTIGATION_BASE = " select interview.interview_uid \"interviewUid\"," 
        +"patient.subject_entity_uid \"intervieweeMprUid\"," 
        +"investigator.subject_entity_uid \"interviewerUid\","
        +"investigatorName.first_nm \"interviewerFirstName\","
       	+"investigatorName.last_nm \"interviewerLastName\","
       	+"investigatorName.nm_suffix \"interviewerSuffix\", "     	
       	+"investigatorName.nm_degree \"interviewerDegree\","
       	+"qec.root_extension_txt \"interviewerQuickCd\","
       	+"patientName.first_nm \"intervieweeFirstName\","
       	+"patientName.middle_nm \"intervieweeMiddleName\","
       	+"patientName.last_nm \"intervieweeLastName\","
       	+"patientName.nm_suffix \"intervieweeSuffix\","
       	+"interview.interviewee_role_cd \"intervieweeRoleCd\","
       	+"interview.interview_date \"interviewDate\","
       	+"interview.interview_type_cd \"interviewTypeCd\","
       	+"interview.interview_status_cd \"interviewStatusCd\","
       	+"interview.interview_loc_cd \"interviewLocCd\","
       	+"interview.local_id \"localId\","
       	+"interview.record_status_cd \"recordStatusCd\","
        +"statusCdLookup.concept_nm \"interviewStatusDesc\","
        +"typeLookup.concept_nm \"interviewTypeDesc\"," 
        +"roleCdLookup.concept_nm \"intervieweeRoleDesc\","       	
       	+"locCdLookup.concept_nm \"interviewLocDesc\","
        +"ar.target_act_uid \"publicHealthCaseUid\","
        +"ar.add_reason_cd \"addReasonCd\" "
		+" from "+DataTables.INTERVIEW_TABLE+" interview"
		+" left join "+DataTables.PARTICIPATION_TABLE+"  patient "
		+"	on patient.act_uid=interview.interview_uid "
   		+"	and patient.type_cd='IntrvweeOfInterview'"
   		+" left  join "+DataTables.PERSON_NAME_TABLE+" patientName "
   		+"	on patient.subject_entity_uid=patientName.person_uid and patientName.person_name_seq=1 "   			
   		+" left  join "+DataTables.PARTICIPATION_TABLE+" investigator "
   		+"	on investigator.act_uid=interview.interview_uid "
   		+"	and investigator.type_cd='IntrvwerOfInterview' "
   		+" left  join "+DataTables.PERSON_NAME_TABLE+" investigatorName "
   		+"	on investigator.subject_entity_uid=investigatorName.person_uid "
   		+" left  join "+DataTables.ACT_RELATIONSHIP+" ar "
   		+"	on ar.source_act_uid= interview.interview_uid "
		+" left  join "+DataTables.ENTITY_ID_TABLE+" QEC "
		+"	on investigator.subject_entity_uid=QEC.entity_uid "
		+"    and QEC.type_cd = 'QEC' " ;
   		private final String SELECT_INTERVIEWS_FOR_INVESTIGATION_CODE_LOOKUP_SQL =	
   		" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" locCdLookup"
   		+"    on interview.interview_loc_cd=locCdLookup.code"
   		+"    and locCdLookup.code_set_nm = 'NBS_INTVW_LOC'"
   		+" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" roleCdLookup"
   		+"    on interview.interviewee_role_cd=roleCdLookup.code"
   		+"    and roleCdLookup.code_set_nm = 'NBS_INTVWEE_ROLE' " 
   		+" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" statusCdLookup"
   		+"    on interview.interview_status_cd=statusCdLookup.code"
   		+"    and statusCdLookup.code_set_nm = 'NBS_INTVW_STATUS'"
   		+" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" typeLookup"
   		+"    on interview.interview_type_cd=typeLookup.code"
   		+"    and typeLookup.code_set_nm = 'NBS_INTERVIEW_TYPE'";   
   		private final String SELECT_STDHIV_INTERVIEWS_FOR_INVESTIGATION_CODE_LOOKUP_SQL =	
   		" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" locCdLookup"
   		+"    on interview.interview_loc_cd=locCdLookup.code"
   		+"    and locCdLookup.code_set_nm = 'NBS_INTVW_LOC_STDHIV'"
   		+" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" roleCdLookup"
   		+"    on interview.interviewee_role_cd=roleCdLookup.code"
   		+"    and roleCdLookup.code_set_nm = 'NBS_INTVWEE_ROLE' " 
   		+" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" statusCdLookup"
   		+"    on interview.interview_status_cd=statusCdLookup.code"
   		+"    and statusCdLookup.code_set_nm = 'NBS_INTVW_STATUS'"
   		+" left join nbs_srte.."+DataTables.CODE_VALUE_GENERAL+" typeLookup"
   		+"    on interview.interview_type_cd=typeLookup.code"
   		+"    and typeLookup.code_set_nm = 'NBS_INTERVIEW_TYPE_STDHIV'";  
   		
   		private final String SELECT_INTERVIEWS_FOR_INVESTIGATION_CODE_LOOKUP_ORA =	
   		" left join nbs_srte."+DataTables.CODE_VALUE_GENERAL+" locCdLookup"
   		+"    on interview.interview_loc_cd=locCdLookup.code"
   		+"    and locCdLookup.code_set_nm = 'NBS_INTVW_LOC'"
   		+" left join nbs_srte."+DataTables.CODE_VALUE_GENERAL+" roleCdLookup"
   		+"    on interview.interviewee_role_cd=roleCdLookup.code"
   		+"    and roleCdLookup.code_set_nm = 'NBS_INTVWEE_ROLE' "  
   		+" left join nbs_srte."+DataTables.CODE_VALUE_GENERAL+" statusCdLookup"
   		+"    on interview.interview_status_cd=statusCdLookup.code"
   		+"    and statusCdLookup.code_set_nm = 'NBS_INTVW_STATUS'"
   		+" left join nbs_srte."+DataTables.CODE_VALUE_GENERAL+" typeLookup"
   		+"    on interview.interview_type_cd=typeLookup.code"
   		+"    and typeLookup.code_set_nm = 'NBS_INTERVIEW_TYPE'"; 
   		private final String SELECT_INTERVIEWS_FOR_INVESTIGATION_WHERE =  		
  		" where interview.record_status_cd='ACTIVE'"
  		+"	and ar.target_act_uid ="; 
   		private final String SELECT_INTERVIEWS_FOR_COINFECTION_WHERE =  		
  		" where interview.record_status_cd='ACTIVE'"
  		+"	and ar.target_act_uid in (select public_health_case_uid from public_health_case where coinfection_id="; 
   		private final String SELECT_INTERVIEWS_ORDER_BY =  " order by interview_date DESC";
   		private final String SELECT_COINFECTION_INTERVIEWS_ORDER_BY =  "') order by ar.target_act_uid ASC";	
  	  /**
  		* getInterviewListForInvestigation()
  		*  get TheInterviewSummaryDTCollection
  		* @param publicHealthCaseUID
  		* @param programArea - needed because codesets differ std and non-std
  		* @param NBSSecurityObj
  		 * @throws Exception 
  		*/	
	public Collection<Object> getInterviewListForInvestigation(Long publicHealthCaseUID, String programAreaCd, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		try{
			boolean isSTDProgramArea = PropertyUtil.isStdOrHivProgramArea(programAreaCd);
			
			Collection<Object>  interviewSummaryDTCollection  = new ArrayList<Object> ();
			String codeLookup = "";
		    	if (isSTDProgramArea)
		    		codeLookup = SELECT_STDHIV_INTERVIEWS_FOR_INVESTIGATION_CODE_LOOKUP_SQL;
		    	else
		    		codeLookup = SELECT_INTERVIEWS_FOR_INVESTIGATION_CODE_LOOKUP_SQL;
		    String sqlStr = SELECT_INTERVIEWS_FOR_INVESTIGATION_BASE
		    		+codeLookup + SELECT_INTERVIEWS_FOR_INVESTIGATION_WHERE + publicHealthCaseUID + SELECT_INTERVIEWS_ORDER_BY;
			interviewSummaryDTCollection = getInterviewList(sqlStr);
			return interviewSummaryDTCollection;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}		
	
	
	@SuppressWarnings("unchecked")
	private Collection<Object> getInterviewList(String sql) throws NEDSSSystemException{
		InterviewSummaryDT  interviewSummaryDT  = new InterviewSummaryDT();
		ArrayList<Object>  interviewSummaryDTCollection  = new ArrayList<Object> ();

		try
		{
			interviewSummaryDTCollection  = (ArrayList<Object> )preparedStmtMethod(interviewSummaryDT, interviewSummaryDTCollection, sql, NEDSSConstants.SELECT);
			Iterator<Object> it = interviewSummaryDTCollection.iterator();
			while(it.hasNext()){
				InterviewSummaryDT ixsSummaryDT = (InterviewSummaryDT)it.next();
						//patient name
				String subjectLastName = (ixsSummaryDT.getIntervieweeLastName()==null)?"No Last":ixsSummaryDT.getIntervieweeLastName();
				String subjectFirstName = (ixsSummaryDT.getIntervieweeFirstName()==null)?"No First":ixsSummaryDT.getIntervieweeFirstName();
				String subjectName =subjectLastName + ", " + subjectFirstName;
				ixsSummaryDT.setIntervieweeFullName(subjectName);
						//investigator name
				String investigatorLastName = (ixsSummaryDT.getInterviewerLastName()==null)?"No Last":ixsSummaryDT.getInterviewerLastName();
				String investigatoFirstName = (ixsSummaryDT.getInterviewerFirstName()==null)?"No First":ixsSummaryDT.getInterviewerFirstName();
				String investigatoName =investigatorLastName + ", " + investigatoFirstName;
				ixsSummaryDT.setInterviewerFullName(investigatoName);
			}
		}catch (Exception ex) {
			logger.fatal("Exception in getInterviewList:  ERROR = " + ex.getMessage(), ex);
			logger.info("SQL=" + sql);
			throw new NEDSSSystemException(ex.toString());
		}
		return interviewSummaryDTCollection;
	}
	
	/**
	  * getInterviewListForCoinfection()
	  *  get TheInterviewSummaryDTCollection
	  * @param coinfectionId
	  * @param programArea - needed because codesets differ std and non-std
	  * @param NBSSecurityObj
	  * @throws Exception 
	*/	
	public Collection<Object> getInterviewListForCoinfectionId(String coinfectionId, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		try{
			Collection<Object>  interviewSummaryDTCollection  = new ArrayList<Object> ();
			String codeLookup = "";
		    codeLookup = SELECT_STDHIV_INTERVIEWS_FOR_INVESTIGATION_CODE_LOOKUP_SQL;
		    String sqlStr = SELECT_INTERVIEWS_FOR_INVESTIGATION_BASE
		    		+codeLookup + SELECT_INTERVIEWS_FOR_COINFECTION_WHERE + "'" + coinfectionId + SELECT_COINFECTION_INTERVIEWS_ORDER_BY;
			interviewSummaryDTCollection = getInterviewList(sqlStr);
			return interviewSummaryDTCollection;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}		

}
