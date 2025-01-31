package gov.cdc.nedss.report.javaRepot.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dao.ContactNamedDAO;
import gov.cdc.nedss.report.javaRepot.dao.ReportPatientDAO;
import gov.cdc.nedss.report.javaRepot.dao.TreatmentDAO;
import gov.cdc.nedss.report.javaRepot.dt.ReportContactDT;
import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.report.javaRepot.vo.CR1VO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Util class for Chalk report 2
 * @author Pradeep Kuamr Sharma
 *
 */
public class CR2Util {
	Map<Long, Object> namedOnlyMap = new HashMap<Long, Object>();
	Map<Long, Object> namedBackByOnlyMap = new HashMap<Long, Object>();
	Map<Long, Object> namedAndNamedBackByMap = new HashMap<Long, Object>();
	
	Map<Long, Object> partnerRelationshipMap = new HashMap<Long, Object>();
	Map<Long, Object> socialAssociatesMap = new HashMap<Long, Object>();
	static final LogUtils logger = new LogUtils(CR2Util.class.getName());
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public Collection<Object> processLinkId(String epiLinkId,NBSSecurityObj nbsSecurityObj){
		ReportPatientDAO reportPatientDAO = new ReportPatientDAO();
		Collection<Object> reurnVal = new ArrayList<Object>();
		try {
			Collection<Object>  coll =reportPatientDAO.getPatientByEpiLinkId(epiLinkId,nbsSecurityObj);
			if(coll!=null && coll.size()>0){
				processContactLinkId(epiLinkId,nbsSecurityObj);
				processSubjectLinkId(epiLinkId,nbsSecurityObj);
				Iterator<Object> iterator = coll.iterator();
				while(iterator.hasNext()){
					CR1VO cr1VO =new CR1VO();
					ReportPatientDT reportPatientDT = (ReportPatientDT)iterator.next();
					if(!reportPatientDT.getInterviewType().startsWith("Initial/Original")){
						reportPatientDT.setOriginalInterviewDate(null);
					}
					cr1VO.setPatientDT(reportPatientDT);
					Long investigationKey= reportPatientDT.getInvestigationKey();
					Collection signsAndSymptoms=reportPatientDAO.getSignsAndSymptomsInformation(epiLinkId, investigationKey, nbsSecurityObj);
					cr1VO.setSignsAndSymptoms(signsAndSymptoms);
					if(namedOnlyMap.containsKey(investigationKey)){
						Collection<Object> namedOnlyCollection = (Collection<Object>)namedOnlyMap.get(investigationKey);
						cr1VO.setNamedBackedBy(namedOnlyCollection);
					}
					
					if(namedBackByOnlyMap.containsKey(investigationKey)){
						Collection<Object> namedBackByCollection = (Collection<Object>)namedBackByOnlyMap.get(investigationKey);
						cr1VO.setNamedBackedBy(namedBackByCollection);
					}
					if(namedAndNamedBackByMap.containsKey(investigationKey)){
						Collection<Object> namedandNamedByCollection = (Collection<Object>)namedAndNamedBackByMap.get(investigationKey);
						cr1VO.setNamedByButNotBackedBy(namedandNamedByCollection);;
					}
					if(partnerRelationshipMap.containsKey(investigationKey)){
						Collection<Object> partnerRelationshipCollection = (Collection<Object>)partnerRelationshipMap.get(investigationKey);
						reportPatientDT.setContacts(partnerRelationshipCollection.size()+"");
					}else{
						reportPatientDT.setContacts(0+"");
					}
					if(socialAssociatesMap.containsKey(investigationKey)){
						Collection<Object> partnerRelationshipCollection = (Collection<Object>)socialAssociatesMap.get(investigationKey);
						reportPatientDT.setSocialANAssociateContacts(socialAssociatesMap.size()+"");
					}else{
						reportPatientDT.setSocialANAssociateContacts(0+"");
								
					}
					reurnVal.add(cr1VO);
				}
		}
		} catch (Exception e) {
			logger.error("The EpiLinkId asscoaied is "+epiLinkId);
			logger.error("Exception thrown CR1Util, ", e);
		}
		return reurnVal;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void processContactLinkId(String epiLinkId,NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		try {
			ContactNamedDAO contactNamedDAO = new ContactNamedDAO();
			Collection<Object>  coll =contactNamedDAO.getContactEntityLinkCollection(epiLinkId, nbsSecurityObj);
			Iterator<Object> iterator = coll.iterator();
			while(iterator.hasNext()){
				ReportContactDT reportContactDT = (ReportContactDT)iterator.next();
				if(reportContactDT.getSubjectEpiLinkId().equalsIgnoreCase(reportContactDT.getContactEpiLinkId())){
					
					
						
					if(namedAndNamedBackByMap.containsKey(reportContactDT.getSubjectInvestigationKey())){
						Collection<Object> conColl = (Collection)namedAndNamedBackByMap.get(reportContactDT.getSubjectInvestigationKey());
						conColl.add(reportContactDT);
						namedAndNamedBackByMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}else{
						Collection<ReportContactDT> conColl = new ArrayList();
						conColl.add(reportContactDT);
						namedAndNamedBackByMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}
				}else{
					if(namedBackByOnlyMap.containsKey(reportContactDT.getSubjectInvestigationKey())){
						Collection<Object> conColl = (Collection)namedBackByOnlyMap.get(reportContactDT.getSubjectInvestigationKey());
						conColl.add(reportContactDT);
						namedBackByOnlyMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}else{
						Collection<ReportContactDT> conColl = new ArrayList();
						conColl.add(reportContactDT);
						namedBackByOnlyMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}
				}
				
				
			}
		}  catch (NEDSSSystemException e) {
			logger.error("The EpiLinkId asscoaied is "+epiLinkId);
			logger.error("Exception thrown CR2Util, ", e);
			throw new NEDSSAppException(e.getMessage());
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void processSubjectLinkId(String epiLinkId,NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		try {
			ContactNamedDAO contactNamedDAO = new ContactNamedDAO();
			Collection<Object>  coll =contactNamedDAO.getSubjectEntityLinkCollection(epiLinkId,nbsSecurityObj);
			Map<Long, Object> map = new HashMap<Long, Object>();
			Iterator<Object> iterator = coll.iterator();
			while(iterator.hasNext()){
				ReportContactDT reportContactDT = (ReportContactDT)iterator.next();
				if(reportContactDT.getSubjectEpiLinkId().equalsIgnoreCase(reportContactDT.getContactEpiLinkId())){
					
					if(reportContactDT.getContactReferralBasis().startsWith("P")){
						if(partnerRelationshipMap.containsKey(reportContactDT.getSubjectInvestigationKey())){
							Collection<Object> conColl = (Collection)partnerRelationshipMap.get(reportContactDT.getSubjectInvestigationKey());
							conColl.add(reportContactDT);
							partnerRelationshipMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
							
						}else{
							Collection<ReportContactDT> conColl = new ArrayList();
							conColl.add(reportContactDT);
							partnerRelationshipMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
						}	
					}else if(reportContactDT.getContactReferralBasis().startsWith("A") || reportContactDT.getContactReferralBasis().startsWith("S") 
							|| reportContactDT.getContactReferralBasis().startsWith("C") ){
						if(socialAssociatesMap.containsKey(reportContactDT.getSubjectInvestigationKey())){
							Collection<Object> conColl = (Collection)socialAssociatesMap.get(reportContactDT.getSubjectInvestigationKey());
							conColl.add(reportContactDT);
							socialAssociatesMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
							
						}else{
							Collection<ReportContactDT> conColl = new ArrayList();
							conColl.add(reportContactDT);
							socialAssociatesMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
						}	
						
					}
					
					
					if(namedAndNamedBackByMap.containsKey(reportContactDT.getSubjectInvestigationKey())){
						Collection<Object> conColl = (Collection)namedAndNamedBackByMap.get(reportContactDT.getSubjectInvestigationKey());
						conColl.add(reportContactDT);
						namedAndNamedBackByMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}else{
						Collection<ReportContactDT> conColl = new ArrayList();
						conColl.add(reportContactDT);
						namedAndNamedBackByMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}
				}else{
					if(namedOnlyMap.containsKey(reportContactDT.getSubjectInvestigationKey())){
						Collection<Object> conColl = (Collection)namedOnlyMap.get(reportContactDT.getSubjectInvestigationKey());
						conColl.add(reportContactDT);
						namedOnlyMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}else{
						Collection<ReportContactDT> conColl = new ArrayList();
						conColl.add(reportContactDT);
						namedOnlyMap.put(reportContactDT.getSubjectInvestigationKey(), conColl);
					}
				}
			}
		}  catch (NEDSSSystemException e) {
			logger.error("The EpiLinkId asscoaied is "+epiLinkId);
			logger.error("Exception thrown CR2Util, ", e);
			throw new NEDSSAppException(e.getMessage());
		}
	}
	
	
	
}
