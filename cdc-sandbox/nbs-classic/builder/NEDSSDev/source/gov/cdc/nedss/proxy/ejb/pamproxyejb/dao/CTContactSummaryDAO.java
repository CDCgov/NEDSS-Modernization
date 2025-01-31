package gov.cdc.nedss.proxy.ejb.pamproxyejb.dao;

import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.ejb.dao.PersonNameDAOImpl;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CTContactSummaryDAO  extends DAOBase  {
	private static final LogUtils logger = new LogUtils(CTContactSummaryDAO.class.getName());

	@SuppressWarnings("unchecked")
	private  Collection<Object> getContactNamedByPatientDTColl(String sql) throws NEDSSSystemException{
		CTContactSummaryDT  cTContactSummaryDT  = new CTContactSummaryDT();
		ArrayList<Object>  cTContactNameByPatientSummDTColl  = new ArrayList<Object> ();
		ArrayList<Object>  returnCTContactNameByPatientSummDTColl  = new ArrayList<Object> ();
		try
		{
			cTContactNameByPatientSummDTColl  = (ArrayList<Object> )preparedStmtMethod(cTContactSummaryDT, cTContactNameByPatientSummDTColl, sql, NEDSSConstants.SELECT);
			Iterator<Object> it = cTContactNameByPatientSummDTColl.iterator();
			while(it.hasNext()){
				CTContactSummaryDT cTContactSumyDT = (CTContactSummaryDT)it.next();
				cTContactSumyDT.setContactNamedByPatient(true);
				Long contactEntityUid = cTContactSumyDT.getContactEntityUid();
				PersonNameDAOImpl personNameDAO =new PersonNameDAOImpl();
				Collection personNameColl =personNameDAO.load(contactEntityUid);
				//add the contact summary dt
				returnCTContactNameByPatientSummDTColl.add(cTContactSumyDT);
				Collection contactNameColl =personNameDAO.load(contactEntityUid);
				if(contactNameColl != null && contactNameColl.size() > 0) {
					Iterator iter = contactNameColl.iterator();
					while(iter.hasNext()){
						PersonNameDT personNameDT = (PersonNameDT)iter.next();
						if(personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)){
							String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
							String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
							String personName = lastName + ", " + firstName;
							cTContactSumyDT.setName(personName);
							cTContactSumyDT.setContactName(personName);
							break;
						}
					} //name iter
				}//name coll not null
				//Other Infected Person is seldom present 
				Long otherEntityUid = cTContactSumyDT.getThirdPartyEntityUid();
				if (otherEntityUid != null) {
					Collection<Object> ctOtherNameColl = personNameDAO.load(otherEntityUid);
					if(ctOtherNameColl != null && ctOtherNameColl.size() > 0) {
						Iterator<Object> otherIter = ctOtherNameColl.iterator();
						while(otherIter.hasNext()){
							PersonNameDT personNameDT = (PersonNameDT)otherIter.next();
							if (personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
								String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
								String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
								String personName =lastName + ", " + firstName;
								cTContactSumyDT.setOtherInfectedPatientName(personName);
								break;
							}
						}
					}
				}
				//Business rule with convoluted logic, if contact Processing Decision is RSC or SR and the contiact's investigation disposition
				// is A, the disposition of the Contact Record will be Z. 
				//If the disposition on the Contact’s existing investigation is C, the disposition of the Contact Record will be E.
				if (cTContactSumyDT.getContactProcessingDecisionCd() != null && 
						cTContactSumyDT.getDispositionCd() != null &&
						(cTContactSumyDT.getContactProcessingDecisionCd().equals(CTConstants.RecordSearchClosure) 
								|| cTContactSumyDT.getContactProcessingDecisionCd().equals(CTConstants.SecondaryReferral))) {
					 if (cTContactSumyDT.getDispositionCd().equals("A")) //preventative treatment
						 cTContactSumyDT.setDispositionCd("Z"); //prev preventative treated
					 else if (cTContactSumyDT.getDispositionCd().equals("C")) //infected brought to treat
						 cTContactSumyDT.setDispositionCd("E"); //prev treated
				}
			} //while
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getCTContactSummaryDTCollection:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return returnCTContactNameByPatientSummDTColl;
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Object> getPatientNamedAsContactSummDTColl(String sql, boolean otherInfected, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		CTContactSummaryDT  ctContactSummaryDT  = new CTContactSummaryDT();
		ArrayList<Object>  ctNameByPatientSummDTColl  = new ArrayList<Object> ();
		ArrayList<Object>  returnCTNameByPatientSummDTColl  = new ArrayList<Object> ();
		try
		{
			ctNameByPatientSummDTColl  = (ArrayList<Object> )preparedStmtMethod(ctContactSummaryDT, ctNameByPatientSummDTColl, sql, NEDSSConstants.SELECT);
			Iterator<Object> it = ctNameByPatientSummDTColl.iterator();
			RetrieveSummaryVO retrieveSummaryVo = new RetrieveSummaryVO();
			while(it.hasNext()){
				CTContactSummaryDT cTContactSumyDT = (CTContactSummaryDT)it.next();
				cTContactSumyDT.setContactNamedByPatient(false);
				cTContactSumyDT.setPatientNamedByContact(true);
				cTContactSumyDT.setOtherNamedByPatient(otherInfected);
				
				cTContactSumyDT.setAssociatedMap(retrieveSummaryVo
						.getAssociatedDocumentList(
								cTContactSumyDT.getCtContactUid(),
								nbsSecurityObj,
								NEDSSConstants.CLASS_CD_CONTACT,
								NEDSSConstants.ACT_CLASS_CD_FOR_DOC));
				//go ahead and add the summary dt into the collection
				returnCTNameByPatientSummDTColl.add(cTContactSumyDT);
				
				//get the subject name
				Long contactSubjectEntityUid = cTContactSumyDT.getSubjectEntityUid();
				PersonNameDAOImpl personNameDAO =new PersonNameDAOImpl();  
				Collection<Object> subjectNameColl =personNameDAO.load(contactSubjectEntityUid);
				if(subjectNameColl != null && subjectNameColl.size() > 0) {
					Iterator<Object> iter = subjectNameColl.iterator();
					while(iter.hasNext()){
					PersonNameDT personNameDT = (PersonNameDT)iter.next();
						if(personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)){
							String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
							String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
							String personName =lastName + ", " + firstName;
							cTContactSumyDT.setNamedBy(personName);
							cTContactSumyDT.setSubjectName(personName);
							break;
						}
					}
				}
				
				//get the Contact Name 
				Long contactEntityUid = cTContactSumyDT.getContactEntityUid();
				if (contactEntityUid != null) {
					Collection<Object> contactNameColl = personNameDAO.load(contactEntityUid);
					if(contactNameColl != null && contactNameColl.size() > 0) {
						Iterator<Object> ctIter = contactNameColl.iterator();
						while(ctIter.hasNext()){
							PersonNameDT personNameDT = (PersonNameDT)ctIter.next();
							if (personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
								String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
								String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
								String personName =lastName + ", " + firstName;
								cTContactSumyDT.setContactName(personName);
								break;
							}
						}
					}
				} //contact Entity not null
				//Other Infected Person is seldom present 
				Long otherEntityUid = cTContactSumyDT.getThirdPartyEntityUid();
				if (otherEntityUid != null) {
					Collection<Object> ctOtherNameColl = personNameDAO.load(otherEntityUid);
					if(ctOtherNameColl != null && ctOtherNameColl.size() > 0) {
						Iterator<Object> otherIter = ctOtherNameColl.iterator();
						while(otherIter.hasNext()){
							PersonNameDT personNameDT = (PersonNameDT)otherIter.next();
							if (personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
								String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
								String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
								String personName =lastName + ", " + firstName;
								cTContactSumyDT.setOtherInfectedPatientName(personName);
								break;
							}
						}
					}
				} //other entity
				//Setting the disposition to the source patient's disposition for the section 'patient named by contacts'
				cTContactSumyDT.setDispositionCd(cTContactSumyDT.getSourceDispositionCd());
				
 			} //has next
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getPatientNamedAsContactSummDTColl:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return returnCTNameByPatientSummDTColl;
	}
	
	private final String SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION_FOR_MANAGE1 = "select subjectPHC.cd \"subjectPhcCd\","
		+" ct_contact.named_On_Date \"namedOnDate\", ct_contact.CT_Contact_uid \"ctContactUid\",ct_contact.local_Id \"localId\", ct_contact.subject_Entity_Uid \"subjectEntityUid\", "
		+" ct_contact.contact_Entity_Uid \"contactEntityUid\", CT_CONTACT.prog_area_cd \"progAreaCd\", CT_CONTACT.priority_cd \"priorityCd\", ct_contact.disposition_cd \"dispositionCd\", "
		+" ct_contact.named_during_interview_uid \"namedDuringInterviewUid\", ct_contact.contact_referral_basis_cd \"contactReferralBasisCd\", ct_contact.processing_decision_cd \"contactProcessingDecisionCd\", "   
		+" ct_contact.third_party_entity_uid \"thirdPartyEntityUid\", ct_contact.third_party_entity_phc_uid \"thirdPartyEntityPhcUid\", "			
		+" contactPHC.public_health_case_uid \"contactEntityPhcUid\", contactPHC.local_id \"contactPhcLocalId\"," 
		+" subjectPHC.public_health_case_uid \"subjectEntityPhcUid\", subjectPHC.local_id \"subjectPhcLocalId\","
		+" subjectPerson.person_parent_uid \"subjectMprUid\", CT_CONTACT.relationship_cd \"relationshipCd\", subjectPerson.age_reported \"ageReported\", "
		+" subjectPerson.age_reported_unit_cd \"ageReportedUnitCd\", subjectPerson.curr_sex_cd \"currSexCd\", subjectPerson.birth_time \"birthTime\" "
		+" from ct_contact with (nolock) "
        +" left outer join public_health_case contactPHC with (nolock) on ct_contact.CONTACT_ENTITY_PHC_UID=contactPHC.PUBLIC_HEALTH_CASE_UID" 
        +" inner join public_health_case currentPHC with (nolock) on currentPHC.PUBLIC_HEALTH_CASE_UID = ?"
        +" inner join public_health_case subjectPHC with (nolock) on ( ct_contact.SUBJECT_ENTITY_PHC_UID=subjectPHC.PUBLIC_HEALTH_CASE_UID ";
        private final String SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION_FOR_MANAGE3 = " ) inner join person contactPerson with (nolock) on ct_contact.CONTACT_ENTITY_UID=contactPerson.person_uid"
        +" inner join person subjectPerson with (nolock) on CT_CONTACT.SUBJECT_ENTITY_UID=subjectPerson.person_uid where ct_contact.record_status_cd='ACTIVE' and contactPerson.person_parent_uid=? and (contact_entity_phc_uid is null or contact_entity_phc_uid = ?) and subjectPHC.cd = currentPHC.cd ";        
		
		        		
	private final String SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION_FOR_MANAGE2=" order by ct_contact.named_On_Date";
	
	public Collection<Object> getMPRPatientNamedAsContactForConditionAndPHCSummDTColl(Long publicHealthCaseUID, Long mprUid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
  	    ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		int resultCount = 0;
		int i = 1;
		ArrayList<Object> mprCTContactNameByPatientSummDTColl  = new ArrayList<Object>();
		Collection<Object> returnMprCTContactNameByPatientSummDTColl  = new ArrayList<Object>();
		try
		{
			String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
					NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
			logger.debug("CTContactSummaryDAO. getMPRPatientNamedAsContactSummDTColl mprUid = " + mprUid +
					" - dataAccessWhereClause = " + dataAccessWhereClause);
			if (dataAccessWhereClause == null) {
				dataAccessWhereClause = "";
			}
			else {
				dataAccessWhereClause = " AND " + dataAccessWhereClause;
				dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "CT_CONTACT.program_jurisdiction_oid");
			dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "CT_CONTACT.shared_ind_cd");
			}
			
			String dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(
					NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			logger.debug("CTContactSummaryDAO. getMPRPatientNamedAsContactSummDTColl mprUid = " + mprUid +
					" - dataAccessWhereClause1 = " + dataAccessWhereClause1);
			if (dataAccessWhereClause1 == null) {
				dataAccessWhereClause1 = "";
			}
			else {
				dataAccessWhereClause1 = " AND " + dataAccessWhereClause1;
				dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("program_jurisdiction_oid", "subjectPHC.program_jurisdiction_oid");
			dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("shared_ind", "subjectPHC.shared_ind");
			}
	
	
	
			String sql = SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION_FOR_MANAGE1+ dataAccessWhereClause1 +SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION_FOR_MANAGE3+
			dataAccessWhereClause+SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION_FOR_MANAGE2;


		
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(sql);
			preparedStmt.setLong(i++, publicHealthCaseUID.longValue()); // 1
			preparedStmt.setLong(i++, mprUid.longValue());              // 2
			preparedStmt.setLong(i++, publicHealthCaseUID.longValue()); // 3
		    resultSet = preparedStmt.executeQuery();		    
		    resultSetMetaData = resultSet.getMetaData();
		    
		    mprCTContactNameByPatientSummDTColl = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet, 
		    		                                                                                  resultSetMetaData, 
		    		                                                                                  CTContactSummaryDT.class, 
		    		                                                                                  mprCTContactNameByPatientSummDTColl);

			Iterator<Object> it = mprCTContactNameByPatientSummDTColl.iterator();
			while(it.hasNext()){
				CTContactSummaryDT ctContactSummaryDT = (CTContactSummaryDT)it.next();
				ctContactSummaryDT.setContactNamedByPatient(false);
				ctContactSummaryDT.setPatientNamedByContact(true);
				ctContactSummaryDT.setOtherNamedByPatient(false);
				//go ahead and add the DT into the return collection
				returnMprCTContactNameByPatientSummDTColl.add(ctContactSummaryDT);
				//get subject name
				Long contactSubjectEntityUid = ctContactSummaryDT.getSubjectEntityUid();
				PersonNameDAOImpl personNameDAO = new PersonNameDAOImpl();
				Collection<Object> subjectNameColl = personNameDAO.load(contactSubjectEntityUid);
				if(subjectNameColl != null && subjectNameColl.size() > 0) {
					Iterator<Object> iter = subjectNameColl.iterator();
					while(iter.hasNext()){
						PersonNameDT personNameDT = (PersonNameDT)iter.next();
						if (personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
							String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
							String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
							String personName =lastName + ", " + firstName;
							ctContactSummaryDT.setNamedBy(personName);
							ctContactSummaryDT.setSubjectName(personName);
							break;
						}
					}
				} 
				//Contact Name is always there..
				Long contactEntityUid = ctContactSummaryDT.getContactEntityUid();				
				Collection<Object> contactNameColl = personNameDAO.load(contactEntityUid);
				if(contactNameColl != null && contactNameColl.size() > 0) {
					Iterator<Object> ctIter = contactNameColl.iterator();
					while(ctIter.hasNext()){
						PersonNameDT personNameDT = (PersonNameDT)ctIter.next();
						if (personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
							String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
							String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
							String personName =lastName + ", " + firstName;
							ctContactSummaryDT.setName(personName);
							ctContactSummaryDT.setContactName(personName);
							break;
						}
					}
				} //contact name
				//Other Infected Person is seldom present 
				Long otherEntityUid = ctContactSummaryDT.getThirdPartyEntityUid();
				if (otherEntityUid != null) {
					Collection<Object> ctOtherNameColl = personNameDAO.load(otherEntityUid);
					if(ctOtherNameColl != null && ctOtherNameColl.size() > 0) {
						Iterator<Object> otherIter = ctOtherNameColl.iterator();
						while(otherIter.hasNext()){
							PersonNameDT personNameDT = (PersonNameDT)otherIter.next();
							if (personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
								String lastName = (personNameDT.getLastNm()==null)?"No Last":personNameDT.getLastNm();
								String firstName = (personNameDT.getFirstNm()==null)?"No First":personNameDT.getFirstNm();
								String personName =lastName + ", " + firstName;
								ctContactSummaryDT.setOtherInfectedPatientName(personName);
								break;
							}
						}
					}
				} //other entity
				
			}//has next
		}
		catch (Exception ex) {
			logger.fatal("Exception in getMPRPatientNamedAsContactForConditionAndPHCSummDTColl:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnMprCTContactNameByPatientSummDTColl;
	}

	private final String SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION="select DISTINCT contact.local_id \"contactPhcLocalId\", subject.cd \"subjectPhcCd\","
		+" ct_contact.named_On_Date \"namedOnDate\",ct_contact.add_time \"createDate\", ct_contact.CT_Contact_uid \"ctContactUid\",ct_contact.local_Id \"localId\", ct_contact.subject_Entity_Uid \"subjectEntityUid\", "
		+" ct_contact.contact_Entity_Uid \"contactEntityUid\", CT_CONTACT.prog_area_cd \"progAreaCd\", CT_CONTACT.priority_cd \"priorityCd\", ct_contact.disposition_cd \"dispositionCd\", "
		+" ct_contact.named_during_interview_uid \"namedDuringInterviewUid\", ct_contact.contact_referral_basis_cd \"contactReferralBasisCd\", ct_contact.processing_decision_cd \"contactProcessingDecisionCd\", "   
		+" ct_contact.third_party_entity_uid \"thirdPartyEntityUid\", ct_contact.third_party_entity_phc_uid \"thirdPartyEntityPhcUid\", "
		+" contact.public_health_case_uid contactEntityPhcUid, " 
		+" subject.public_health_case_uid \"subjectEntityPhcUid\", subject.local_id \"subjectPhcLocalId\","
		+" subjectCM.FLD_FOLL_UP_DISPO \"sourceDispositionCd\", subject.cd \"sourceConditionCd\", subjectrevision.curr_sex_cd \"sourceCurrentSexCd\", subjectCM.PAT_INTV_STATUS_CD \"sourceInterviewStatusCd\","		
		+" contactpat.person_parent_uid \"subjectMprUid\", CT_CONTACT.relationship_cd \"relationshipCd\" , subjectrevision.person_parent_uid  \"subjectMprUid\""
		+" from ct_contact with (nolock) "
		+" left outer join public_health_case subject with (nolock) on ct_contact.SUBJECT_ENTITY_PHC_UID=subject.PUBLIC_HEALTH_CASE_UID "
		+" left outer join case_management subjectCM with (nolock) on ct_contact.SUBJECT_ENTITY_PHC_UID=subjectCM.PUBLIC_HEALTH_CASE_UID "
		+" left outer join public_health_case contact with (nolock) on ct_contact.CONTACT_ENTITY_PHC_UID=contact.PUBLIC_HEALTH_CASE_UID "
		+" inner join person contactpat with (nolock) on ct_contact.CONTACT_ENTITY_UID=contactpat.person_uid "
		+" inner join person subjectrevision with (nolock) on ct_contact.subject_entity_uid=subjectrevision.person_uid "
		+" inner join person contactrevision with (nolock) on contactrevision.person_parent_uid=contactpat.person_parent_uid where   ct_contact.record_status_cd='ACTIVE' and contactpat.person_parent_uid=";
	private final String SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION2=" order by subject.cd";
	public Collection<Object> getMPRPatientNamedAsContactSummDTColl(Long mprUid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		
		try{
			String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
			        NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			    logger.debug("CTContactSummaryDAO. getMPRPatientNamedAsContactSummDTColl mprUid = " + mprUid +
			                 " - dataAccessWhereClause = " + dataAccessWhereClause);
			    if (dataAccessWhereClause == null) {
			      dataAccessWhereClause = "";
			    }
			    else {
			      dataAccessWhereClause = " AND " + dataAccessWhereClause;
			      dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "subject.program_jurisdiction_oid");
		      dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "subject.shared_ind");
			}
			 String dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(
			    		NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
				    logger.debug("CTContactSummaryDAO. getMPRPatientNamedAsContactSummDTColl mprUid = " + mprUid +
				                 " - dataAccessWhereClause1 = " + dataAccessWhereClause1);
				    if (dataAccessWhereClause1 == null) {
				      dataAccessWhereClause1 = "";
				    }
				    else {
				      dataAccessWhereClause1 = " AND " + dataAccessWhereClause1;
				      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("program_jurisdiction_oid", "CT_CONTACT.program_jurisdiction_oid");
			      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("shared_ind", "CT_CONTACT.shared_ind_cd");
				}	    
			  
			Collection<Object>  MPRcTContactNameByPatientSummDTColl  = new ArrayList<Object> ();
	
			String sql = SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION+mprUid +
						 dataAccessWhereClause+dataAccessWhereClause1+SELECT_MPRPAT_NAMED_BY_CONTACT_COLLECTION2;
	
			MPRcTContactNameByPatientSummDTColl = getPatientNamedAsContactSummDTColl(sql, false, nbsSecurityObj);
			return MPRcTContactNameByPatientSummDTColl;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	private final String SELECT_MPRPAT_OTHER_NAMED_BY_CONTACT_COLLECTION="select DISTINCT contact.local_id \"contactPhcLocalId\", subject.cd \"subjectPhcCd\","
			+" ct_contact.named_On_Date \"namedOnDate\",ct_contact.add_time \"createDate\", ct_contact.CT_Contact_uid \"ctContactUid\",ct_contact.local_Id \"localId\", ct_contact.subject_Entity_Uid \"subjectEntityUid\", "
			+" ct_contact.contact_Entity_Uid \"contactEntityUid\", CT_CONTACT.prog_area_cd \"progAreaCd\", CT_CONTACT.priority_cd \"priorityCd\", ct_contact.disposition_cd \"dispositionCd\", "
			+" ct_contact.named_during_interview_uid \"namedDuringInterviewUid\", ct_contact.contact_referral_basis_cd \"contactReferralBasisCd\", ct_contact.processing_decision_cd \"contactProcessingDecisionCd\", "   
			+" ct_contact.third_party_entity_uid \"thirdPartyEntityUid\", ct_contact.third_party_entity_phc_uid \"thirdPartyEntityPhcUid\", "
			+" contact.public_health_case_uid contactEntityPhcUid, " 
			+" subject.public_health_case_uid \"subjectEntityPhcUid\", subject.local_id \"subjectPhcLocalId\","
			+" subjectCM.FLD_FOLL_UP_DISPO \"sourceDispositionCd\", subject.cd \"sourceConditionCd\", subjectrevision.curr_sex_cd \"sourceCurrentSexCd\", subjectCM.PAT_INTV_STATUS_CD \"sourceInterviewStatusCd\","		
			+" otherpat.person_parent_uid \"subjectMprUid\", CT_CONTACT.relationship_cd \"relationshipCd\" , subjectrevision.person_parent_uid  \"subjectMprUid\""
			+" from ct_contact with (nolock) "
			+" left outer join public_health_case subject with (nolock)  on ct_contact.SUBJECT_ENTITY_PHC_UID=subject.PUBLIC_HEALTH_CASE_UID "
			+" left outer join case_management subjectCM with (nolock)  on ct_contact.SUBJECT_ENTITY_PHC_UID=subjectCM.PUBLIC_HEALTH_CASE_UID "
			+" left outer join public_health_case contact with (nolock)  on ct_contact.third_party_entity_phc_uid=contact.PUBLIC_HEALTH_CASE_UID "
			+" inner join person otherpat with (nolock)  on ct_contact.THIRD_PARTY_ENTITY_UID=otherpat.person_uid "
			+" inner join person subjectrevision with (nolock)  on ct_contact.subject_entity_uid=subjectrevision.person_uid "
			+" inner join person contactrevision with (nolock)  on contactrevision.person_parent_uid=otherpat.person_parent_uid where   ct_contact.record_status_cd='ACTIVE' and otherpat.person_parent_uid=";
		private final String SELECT_MPRPAT_OTHER_NAMED_BY_CONTACT_COLLECTION2=" order by subject.cd";
		public Collection<Object> getMPRPatientOtherNamedAsContactSummDTColl(Long mprUid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
			
			try{
				String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
				        NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
				    logger.debug("CTContactSummaryDAO. getMPRPatientOtherNamedAsContactSummDTColl mprUid = " + mprUid +
				                 " - dataAccessWhereClause = " + dataAccessWhereClause);
				    if (dataAccessWhereClause == null) {
				      dataAccessWhereClause = "";
				    }
				    else {
				      dataAccessWhereClause = " AND " + dataAccessWhereClause;
				      dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "subject.program_jurisdiction_oid");
			      dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "subject.shared_ind");
				    }
				 String dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(
				    		NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
					    logger.debug("CTContactSummaryDAO. getMPRPatientOtherNamedAsContactSummDTColl mprUid = " + mprUid +
					                 " - dataAccessWhereClause1 = " + dataAccessWhereClause1);
					    if (dataAccessWhereClause1 == null) {
					      dataAccessWhereClause1 = "";
					    }
					    else {
					      dataAccessWhereClause1 = " AND " + dataAccessWhereClause1;
					      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("program_jurisdiction_oid", "CT_CONTACT.program_jurisdiction_oid");
				      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("shared_ind", "CT_CONTACT.shared_ind_cd");
					    }	    
				  
				Collection<Object>  MPRcTContactOtherNameByPatientSummDTColl  = new ArrayList<Object> ();
	
				String sql = SELECT_MPRPAT_OTHER_NAMED_BY_CONTACT_COLLECTION+mprUid +
							 dataAccessWhereClause+dataAccessWhereClause1+SELECT_MPRPAT_OTHER_NAMED_BY_CONTACT_COLLECTION2;
	
				MPRcTContactOtherNameByPatientSummDTColl = getPatientNamedAsContactSummDTColl(sql, true, nbsSecurityObj);
				return MPRcTContactOtherNameByPatientSummDTColl;
			}catch(Exception ex){
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}	

	private final String SELECT_MPR_CONTACT_NAMED_BY_PATIENT_COLLECTION1 ="select subject.cd \"subjectPhcCd\","
		+" ct_contact.add_time \"createDate\", ct_contact.named_On_Date \"namedOnDate\", ct_contact.CT_Contact_uid \"ctContactUid\",ct_contact.local_Id \"localId\", ct_contact.subject_Entity_Uid \"subjectEntityUid\", " 
        +" ct_contact.contact_Entity_Uid \"contactEntityUid\", CT_CONTACT.prog_area_cd \"progAreaCd\", CT_CONTACT.priority_cd \"priorityCd\", ct_contact.disposition_cd \"dispositionCd\", "
		+" ct_contact.named_during_interview_uid \"namedDuringInterviewUid\", ct_contact.contact_referral_basis_cd \"contactReferralBasisCd\", ct_contact.processing_decision_cd \"contactProcessingDecisionCd\", " 
		+" ct_contact.third_party_entity_uid \"thirdPartyEntityUid\", ct_contact.third_party_entity_phc_uid \"thirdPartyEntityPhcUid\", "        
        +" contact.public_health_case_uid \"contactEntityPhcUid\", contact.cd \"contactPhcCd\", contact.local_id \"contactPhcLocalId\", "
        +" subject.public_health_case_uid \"subjectEntityPhcUid\", subject.local_id \"subjectPhcLocalId\", "
		+" subjectCM.FLD_FOLL_UP_DISPO \"sourceDispositionCd\", subject.cd \"sourceConditionCd\", subjectpat.curr_sex_cd \"sourceCurrentSexCd\", subjectCM.PAT_INTV_STATUS_CD \"sourceInterviewStatusCd\","	
        +" subjectpat.person_parent_uid \"subjectMprUid\", ixs.interview_date \"interviewDate\", contactrevision.person_parent_uid \"contactMprUid\""
        +" from ct_contact with (nolock) "
        +" left outer join public_health_case subject with (nolock)  on ct_contact.SUBJECT_ENTITY_PHC_UID=subject.PUBLIC_HEALTH_CASE_UID "
        +" left outer join interview ixs with (nolock) on ct_contact.named_during_interview_uid=ixs.interview_uid "
        +" left outer join case_management subjectCM with (nolock) on ct_contact.SUBJECT_ENTITY_PHC_UID=subjectCM.PUBLIC_HEALTH_CASE_UID "
        +" left outer join public_health_case contact with (nolock) on (ct_contact.CONTACT_ENTITY_PHC_UID=contact.PUBLIC_HEALTH_CASE_UID ";
        
	private final String SELECT_MPR_CONTACT_NAMED_BY_PATIENT_COLLECTION3 = " )inner join person with (nolock) on ct_contact.CONTACT_ENTITY_UID=person.person_uid "
        +" inner join person contactrevision with (nolock) on contactrevision.person_uid= ct_contact.CONTACT_ENTITY_UID "
        +" inner join person subjectpat with (nolock) on CT_CONTACT.SUBJECT_ENTITY_UID=subjectpat.person_uid "
        +" where ct_contact.record_status_cd='ACTIVE' and subjectpat.person_parent_uid =";
		private final String SELECT_MPR_CONTACT_NAMED_BY_PATIENT_COLLECTION2=" order by subject.cd";
		private final String SELECT_MPR_CONTACT_NAMED_BY_PATIENT_COLLECTION_FOR_MANAGE2=" order by ct_contact.named_On_Date";
		
		public Collection<Object> getMPRContactNamedByPatientSummDTColl(Long mprUid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
			try{
				String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
				logger.debug("CTContactSummaryDAO. getMPRContactNamedByPatientSummDTColl mprUid = " + mprUid +
						" - dataAccessWhereClause = " + dataAccessWhereClause);
				
				if (dataAccessWhereClause == null) {
					dataAccessWhereClause = "";
				}
				else {
					dataAccessWhereClause = " AND " + dataAccessWhereClause;
					dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "CT_CONTACT.program_jurisdiction_oid");
				dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "CT_CONTACT.shared_ind_cd");
				}
				
				String dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
				logger.debug("CTContactSummaryDAO. getMPRContactNamedByPatientSummDTColl mprUid = " + mprUid +
						" - dataAccessWhereClause1 = " + dataAccessWhereClause1);
				
				if (dataAccessWhereClause1 == null) {
					dataAccessWhereClause1 = "";
				}
				else {
					dataAccessWhereClause1 = " AND " + dataAccessWhereClause1;
					dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("program_jurisdiction_oid", "contact.program_jurisdiction_oid");
				dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("shared_ind", "contact.shared_ind");
				}
				
				
				Collection<Object>  MPRcTContactNameByPatientSummDTColl  = new ArrayList<Object> ();
				String sql = SELECT_MPR_CONTACT_NAMED_BY_PATIENT_COLLECTION1+dataAccessWhereClause1+SELECT_MPR_CONTACT_NAMED_BY_PATIENT_COLLECTION3+ mprUid + dataAccessWhereClause +
		             	  SELECT_MPR_CONTACT_NAMED_BY_PATIENT_COLLECTION2;
				
				MPRcTContactNameByPatientSummDTColl = getContactNamedByPatientDTColl(sql);
				
				return MPRcTContactNameByPatientSummDTColl;
			}catch(Exception ex){
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}

	
	
	private final String SELECT_PHCPAT_NAMED_BY_CONTACT_COLLECTION="select ct_contact.named_On_Date \"namedOnDate\", ct_contact.CT_Contact_uid \"ctContactUid\", ct_contact.local_Id \"localId\", "
	+" ct_contact.subject_Entity_Uid \"subjectEntityUid\", ct_contact.contact_Entity_Uid \"contactEntityUid\", CT_CONTACT.priority_cd \"priorityCd\", ct_contact.disposition_cd \"dispositionCd\", "  
	+" ct_contact.prog_area_cd \"progAreaCd\", ct_contact.named_during_interview_uid \"namedDuringInterviewUid\", ct_contact.contact_referral_basis_cd \"contactReferralBasisCd\", "
	+" ct_contact.third_party_entity_uid \"thirdPartyEntityUid\", ct_contact.third_party_entity_phc_uid \"thirdPartyEntityPhcUid\", ct_contact.processing_decision_cd \"contactProcessingDecisionCd\", "   
	+" subjectCM.FLD_FOLL_UP_DISPO \"sourceDispositionCd\", subject.cd \"sourceConditionCd\", subjectperson.curr_sex_cd \"sourceCurrentSexCd\", subjectCM.PAT_INTV_STATUS_CD \"sourceInterviewStatusCd\","	
	+" ct_contact.SUBJECT_ENTITY_PHC_UID \"subjectEntityPhcUid\", ixs.interview_date \"interviewDate\",  ct_contact.add_time \"createDate\", subject.local_id \"subjectPhcLocalId\", person.person_parent_uid \"contactMprUid\" , subject.cd \"subjectPhcCd\", subjectperson.person_parent_uid  \"subjectMprUid\" 	"          
	+" from ct_contact with (nolock) " 
	+"  left outer join public_health_case subject with (nolock) on (ct_contact.SUBJECT_ENTITY_PHC_UID=subject.public_health_case_uid) " 
	+"  left outer join case_management cm with (nolock) on (ct_contact.CONTACT_ENTITY_PHC_UID=cm.public_health_case_uid) "
	+"  left outer join case_management subjectCM with (nolock) on (ct_contact.SUBJECT_ENTITY_PHC_UID=subjectCM.public_health_case_uid) "
	+"  left outer join interview ixs with (nolock) on ct_contact.named_during_interview_uid=ixs.interview_uid "
	+" 	inner join person with (nolock) on ct_contact.CONTACT_ENTITY_UID=person.person_uid "
	+" 	inner join person subjectperson with (nolock) on ct_contact.SUBJECT_ENTITY_UID=subjectperson.person_uid "
	+" 	where  ct_contact.record_status_cd='ACTIVE' and CONTACT_ENTITY_PHC_UID =";
	public Collection<Object> getPHCPatientNamedAsContactSummDTColl(Long publicHealthCaseUID, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		try{
			String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			logger.debug("CTContactSummaryDAO. getPHCPatientNamedAsContactSummDTColl publicHealthCaseUID = " + publicHealthCaseUID +
			                 " - dataAccessWhereClause = " + dataAccessWhereClause);
			if (dataAccessWhereClause == null) {
			     dataAccessWhereClause = "";
			}
			else {
			 dataAccessWhereClause = " AND " + dataAccessWhereClause;
			 dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "subject.program_jurisdiction_oid");
		 dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "subject.shared_ind");
			}
			String dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(
			    		NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
				    logger.debug("CTContactSummaryDAO. getPHCPatientNamedAsContactSummDTColl publicHealthCaseUID = " + publicHealthCaseUID +
				                 " - dataAccessWhereClause1 = " + dataAccessWhereClause1);
				    if (dataAccessWhereClause1 == null) {
				      dataAccessWhereClause1 = "";
				    }
				    else {
				      dataAccessWhereClause1 = " AND " + dataAccessWhereClause1;
				      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("program_jurisdiction_oid", "CT_CONTACT.program_jurisdiction_oid");
			      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("shared_ind", "CT_CONTACT.shared_ind_cd");
				}	 
			Collection<Object>  PHCcTContactNameByPatientSummDTColl  = new ArrayList<Object> ();
			String sql  =SELECT_PHCPAT_NAMED_BY_CONTACT_COLLECTION+publicHealthCaseUID+dataAccessWhereClause+dataAccessWhereClause1;
			PHCcTContactNameByPatientSummDTColl = getPatientNamedAsContactSummDTColl(sql, false, nbsSecurityObj);
			return PHCcTContactNameByPatientSummDTColl;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private final String SELECT_PHCPAT_OTHER_NAMED_BY_CONTACT_COLLECTION="select ct_contact.named_On_Date \"namedOnDate\", ct_contact.CT_Contact_uid \"ctContactUid\", ct_contact.local_Id \"localId\", "
			+" ct_contact.subject_Entity_Uid \"subjectEntityUid\", ct_contact.contact_Entity_Uid \"contactEntityUid\", CT_CONTACT.priority_cd \"priorityCd\", ct_contact.disposition_cd \"dispositionCd\", "  
			+" ct_contact.prog_area_cd \"progAreaCd\", ct_contact.named_during_interview_uid \"namedDuringInterviewUid\", ct_contact.contact_referral_basis_cd \"contactReferralBasisCd\", "
			+" subjectCM.FLD_FOLL_UP_DISPO \"sourceDispositionCd\", subject.cd \"sourceConditionCd\", subjectperson.curr_sex_cd \"sourceCurrentSexCd\", subjectCM.PAT_INTV_STATUS_CD \"sourceInterviewStatusCd\","
			+" ct_contact.third_party_entity_uid \"thirdPartyEntityUid\", ct_contact.third_party_entity_phc_uid \"thirdPartyEntityPhcUid\", ct_contact.processing_decision_cd \"contactProcessingDecisionCd\", "   
			+" ct_contact.SUBJECT_ENTITY_PHC_UID \"subjectEntityPhcUid\",  ixs.interview_date \"interviewDate\",  ct_contact.add_time \"createDate\", subject.local_id \"subjectPhcLocalId\", person.person_parent_uid \"contactMprUid\" , subject.cd \"subjectPhcCd\", subjectperson.person_parent_uid  \"subjectMprUid\" 	"          
			+" from ct_contact with (nolock) " 
			+"  left outer join public_health_case subject  with (nolock) on (ct_contact.SUBJECT_ENTITY_PHC_UID=subject.public_health_case_uid) " 
			+"  left outer join case_management cm  with (nolock) on (ct_contact.THIRD_PARTY_ENTITY_PHC_UID=cm.public_health_case_uid) "
			+"  left outer join case_management subjectCM  with (nolock) on (ct_contact.SUBJECT_ENTITY_PHC_UID=subjectCM.public_health_case_uid) "
			+"  left outer join interview ixs with (nolock) on ct_contact.named_during_interview_uid=ixs.interview_uid "
			+" 	inner join person with (nolock) on ct_contact.THIRD_PARTY_ENTITY_UID=person.person_uid "
			+" 	inner join person subjectperson with (nolock) on ct_contact.SUBJECT_ENTITY_UID=subjectperson.person_uid "
			+" 	where  ct_contact.record_status_cd='ACTIVE' and THIRD_PARTY_ENTITY_PHC_UID =";
			public Collection<Object> getPHCPatientOtherNamedAsContactSummDTColl(Long publicHealthCaseUID, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
				try{
					String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
					logger.debug("CTContactSummaryDAO. getPHCPatientOtherNamedAsContactSummDTColl publicHealthCaseUID = " + publicHealthCaseUID +
					                 " - dataAccessWhereClause = " + dataAccessWhereClause);
					if (dataAccessWhereClause == null) {
					     dataAccessWhereClause = "";
					}
					else {
					 dataAccessWhereClause = " AND " + dataAccessWhereClause;
					 dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "subject.program_jurisdiction_oid");
				 dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "subject.shared_ind");
					}
					String dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(
					    		NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
						    logger.debug("CTContactSummaryDAO. getPHCOtherPatientNamedAsContactSummDTColl publicHealthCaseUID = " + publicHealthCaseUID +
						                 " - dataAccessWhereClause1 = " + dataAccessWhereClause1);
						    if (dataAccessWhereClause1 == null) {
						      dataAccessWhereClause1 = "";
						    }
						    else {
						      dataAccessWhereClause1 = " AND " + dataAccessWhereClause1;
						      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("program_jurisdiction_oid", "CT_CONTACT.program_jurisdiction_oid");
					      dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("shared_ind", "CT_CONTACT.shared_ind_cd");
						}	 
					Collection<Object>  PHCcTContactNameByPatientSummDTColl  = new ArrayList<Object> ();
					String sql  =SELECT_PHCPAT_OTHER_NAMED_BY_CONTACT_COLLECTION+publicHealthCaseUID+dataAccessWhereClause+dataAccessWhereClause1;
					PHCcTContactNameByPatientSummDTColl = getPatientNamedAsContactSummDTColl(sql, true, nbsSecurityObj);
					return PHCcTContactNameByPatientSummDTColl;
				}catch(Exception ex){
					logger.fatal("Exception  = "+ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString());
				}
			 }
			
	private final String SELECT_PHCPAT_NAMED_BY_PATIENT_COLLECTION1="select subject.cd \"subjectPhcCd\", " 
		+"  ct_contact.subject_entity_phc_uid \"subjectEntityPhcUid\", subject.local_id \"subjectPhcLocalId\","			
	    +"  ct_contact.named_On_Date \"namedOnDate\", ct_contact.CT_Contact_uid \"ctContactUid\",ct_contact.local_Id \"localId\", "
		+" 	ct_contact.subject_Entity_Uid \"subjectEntityUid\", ct_contact.contact_Entity_Uid \"contactEntityUid\", CT_CONTACT.priority_cd \"priorityCd\", ct_contact.disposition_cd \"dispositionCd\", "
		+"  ct_contact.prog_area_cd \"progAreaCd\", ct_contact.named_during_interview_uid \"namedDuringInterviewUid\", cm.fld_foll_up_dispo \"invDispositionCd\", ct_contact.contact_referral_basis_cd \"contactReferralBasisCd\", "
		+"  ct_contact.third_party_entity_uid \"thirdPartyEntityUid\", ct_contact.third_party_entity_phc_uid \"thirdPartyEntityPhcUid\", ct_contact.processing_decision_cd \"contactProcessingDecisionCd\", "
		+"  subjectCM.FLD_FOLL_UP_DISPO \"sourceDispositionCd\", subject.cd \"sourceConditionCd\", subjectperson.curr_sex_cd \"sourceCurrentSexCd\", subjectCM.PAT_INTV_STATUS_CD \"sourceInterviewStatusCd\","
		+" 	 contact.public_health_case_uid \"contactEntityPhcUid\", contact.local_id \"contactPhcLocalId\", person.person_parent_uid \"subjectMprUid\" ,  ixs.interview_date \"interviewDate\", ct_contact.add_time \"createDate\", personcontact.person_parent_uid \"contactMprUid\" "
		+"   from ct_contact with (nolock) "
		+"  left outer join public_health_case subject with (nolock) on (ct_contact.SUBJECT_ENTITY_PHC_UID=subject.public_health_case_uid) " 
		+"  left outer join case_management cm with (nolock) on (ct_contact.CONTACT_ENTITY_PHC_UID=cm.public_health_case_uid)"
		+"  left outer join case_management subjectCM with (nolock) on (ct_contact.SUBJECT_ENTITY_PHC_UID=subjectCM.public_health_case_uid) "
		+"  left outer join interview ixs with (nolock) on ct_contact.named_during_interview_uid=ixs.interview_uid "
		+" 	left outer join public_health_case contact with (nolock) on (ct_contact.CONTACT_ENTITY_PHC_UID=contact.PUBLIC_HEALTH_CASE_UID ";
		private final String SELECT_PHCPAT_NAMED_BY_PATIENT_COLLECTION3= " ) inner join person with (nolock)  on ct_contact.SUBJECT_ENTITY_UID=person.person_uid  " 
		+" inner join person subjectperson with (nolock)  on ct_contact.SUBJECT_ENTITY_UID=subjectperson.person_uid "
		+" inner join person personcontact with (nolock) on ct_contact.CONTACT_ENTITY_UID=personcontact.person_uid "
		+" where  ct_contact.record_status_cd='ACTIVE' and Subject_Entity_Phc_Uid =";
	public Collection<Object> getPHCContactNamedByPatientSummDTColl(Long publicHealthCaseUID, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		try{
			String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW);
			logger.debug("CTContactSummaryDAO. getPHCContactNamedByPatientSummDTColl publicHealthCaseUID = " + publicHealthCaseUID +
			                 " - dataAccessWhereClause = " + dataAccessWhereClause);
			if (dataAccessWhereClause == null) {
			     dataAccessWhereClause = "";
			}
			else {
			 dataAccessWhereClause = " AND " + dataAccessWhereClause;
			 dataAccessWhereClause = dataAccessWhereClause.replaceAll("program_jurisdiction_oid", "CT_CONTACT.program_jurisdiction_oid");
		 dataAccessWhereClause = dataAccessWhereClause.replaceAll("shared_ind", "CT_CONTACT.shared_ind_cd");
			}
			
			String dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			logger.debug("CTContactSummaryDAO. getPHCContactNamedByPatientSummDTColl publicHealthCaseUID = " + publicHealthCaseUID +
			                 " - dataAccessWhereClause1 = " + dataAccessWhereClause1);
			if (dataAccessWhereClause1 == null) {
			     dataAccessWhereClause1 = "";
			}
			else {
			 dataAccessWhereClause1 = " AND " + dataAccessWhereClause1;
			 dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("program_jurisdiction_oid", "contact.program_jurisdiction_oid");
		 dataAccessWhereClause1 = dataAccessWhereClause1.replaceAll("shared_ind", "contact.shared_ind");
			}
			Collection<Object>  PHCcTContactNameByPatientSummDTColl  = new ArrayList<Object> ();
			String sql  =SELECT_PHCPAT_NAMED_BY_PATIENT_COLLECTION1+dataAccessWhereClause1+SELECT_PHCPAT_NAMED_BY_PATIENT_COLLECTION3+publicHealthCaseUID+dataAccessWhereClause;
			PHCcTContactNameByPatientSummDTColl = getContactNamedByPatientDTColl(sql);
			return PHCcTContactNameByPatientSummDTColl;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	public Collection<Object> getContactListForFile(Long mprUid,NBSSecurityObj nbsSecurityObj){
		Collection<Object> coll = new ArrayList<Object>();
		try{
			coll.addAll(getMPRContactNamedByPatientSummDTColl(mprUid, nbsSecurityObj));
			coll.addAll(getMPRPatientNamedAsContactSummDTColl(mprUid, nbsSecurityObj));
			coll.addAll(getMPRPatientOtherNamedAsContactSummDTColl(mprUid, nbsSecurityObj));
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return coll;
	}
	public Collection<Object> getContactListForInvestigation(Long publicHealthCaseUID, NBSSecurityObj nbsSecurityObj){
		Collection<Object> coll = new ArrayList<Object>();
		try{
			coll.addAll(getPHCContactNamedByPatientSummDTColl(publicHealthCaseUID, nbsSecurityObj));
			coll.addAll(getPHCPatientNamedAsContactSummDTColl(publicHealthCaseUID, nbsSecurityObj));
			coll.addAll(getPHCPatientOtherNamedAsContactSummDTColl(publicHealthCaseUID, nbsSecurityObj));	
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return coll;
	}
	
	public Collection<Object> getNamedAsContactSummaryByCondition(Long publicHealthCaseUID, Long mprUid, NBSSecurityObj nbsSecurityObj){
		Collection<Object> coll = new ArrayList<Object>();
		try{
			coll.addAll(getMPRPatientNamedAsContactForConditionAndPHCSummDTColl(publicHealthCaseUID, mprUid, nbsSecurityObj));
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return coll;
	}
	
	
}
