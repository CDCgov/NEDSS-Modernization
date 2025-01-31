package gov.cdc.nedss.report.javaRepot.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dao.ContactNamedDAO;
import gov.cdc.nedss.report.javaRepot.dao.ReportPatientDAO;
import gov.cdc.nedss.report.javaRepot.dt.ReportContactDT;
import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.report.javaRepot.vo.CR1VO;
import gov.cdc.nedss.report.javaRepot.vo.CR3VO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Util class for Chalk report 3
 * @author Pradeep Kuamr Sharma
 *
 */
public class CR3Util {

	Map<Long, Object> namedOnlyMap = new HashMap<Long, Object>();
	Map<Long, Object> namedBackByOnlyMap = new HashMap<Long, Object>();
	Map<Long, Object> namedAndNamedBackByMap = new HashMap<Long, Object>();

	Map<Long, Object> partnerRelationshipMap = new HashMap<Long, Object>();
	Map<Long, Object> socialAssociatesMap = new HashMap<Long, Object>();
	static final LogUtils logger = new LogUtils(CR3Util.class.getName());
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public Collection<Object> processLinkId(String epiLinkId,String[] diagnosisArray, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		ReportPatientDAO reportPatientDAO = new ReportPatientDAO();
		StringBuffer tempValues =new StringBuffer();
		if(diagnosisArray!=null && diagnosisArray.length>0){
			for(int i =0; i<diagnosisArray.length; i++){
				tempValues.append(diagnosisArray[i]);
				if((i+1)<diagnosisArray.length)
					tempValues.append(",");
			}
		}else{
			logger.error("Passsed epiLinkId:", epiLinkId);
			logger.error("Passsed diagnosisArray is null, Please check!!!", diagnosisArray);
			//return null;
		}

		Collection<Object> returnVal = new ArrayList<Object>();
		try {
			Collection<Object>  coll =reportPatientDAO.getPatientByEpiLinkIdAndDiagnosis(epiLinkId,tempValues.toString(), nbsSecurityObj);
			Iterator<Object> iterator = coll.iterator();
			while(iterator.hasNext()){
				CR3VO cr3VO =new CR3VO();
				ReportPatientDT reportPatientDT = (ReportPatientDT)iterator.next();
				cr3VO.setPatientDT(reportPatientDT);
				Long investigationKey= reportPatientDT.getInvestigationKey();
				ContactNamedDAO contactDAO= new ContactNamedDAO();
				Collection contacts = contactDAO.getContactBySubjectInvestigationKey(investigationKey, nbsSecurityObj);
//				if(contacts.size()>0){
//					Iterator it = contacts.iterator();
//					Map<Object, Object> map = new HashMap<Object, Object>();
//					while(it.hasNext()){
//						ReportContactDT contactDT = (ReportContactDT)it.next();
//						if(contactDT.getInterviewDate()!=null){
//							map.put(contactDT.getContactMprUid(), contactDT);
//						}else{
//							map.put(contactDT.getContactName(), contactDT);
//						}
//					}
//					Collection setCollection = map.values();
					cr3VO.setNamed(contacts);

//				}
				reportPatientDT.setContacts(contacts.size()+"");
				returnVal.add(cr3VO);
			}
		} catch (NEDSSSystemException ex) {
			logger.fatal("Exception in processLinkId:  ERROR = "
					+ ex);
			throw new NEDSSAppException(ex.toString());
		} catch (Exception e) {
			logger.error("The EpiLinkId is " + epiLinkId);
			logger.error("The diagnosisArray is " + diagnosisArray);
			logger.error("Exception thrown CR3Util, ", e);
			throw new NEDSSAppException(e.getMessage());
		}
		return returnVal;
	}

}
