package gov.cdc.nedss.report.javaRepot.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dao.ReportPatientDAO;
import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.report.javaRepot.dt.ReportPlaceDT;
import gov.cdc.nedss.report.javaRepot.vo.CR4VO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Util class for Chalk report 5
 * @author Pradeep Kuamr Sharma
 *
 */
public class CR5Util {

	Map<Long, Object> namedOnlyMap = new HashMap<Long, Object>();
	Map<Long, Object> namedBackByOnlyMap = new HashMap<Long, Object>();
	Map<Long, Object> namedAndNamedBackByMap = new HashMap<Long, Object>();

	Map<Long, Object> partnerRelationshipMap = new HashMap<Long, Object>();
	Map<Long, Object> socialAssociatesMap = new HashMap<Long, Object>();
	static final LogUtils logger = new LogUtils(CR3Util.class.getName());
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public Collection<Object> processRequest(String fromConfirmationDate,String toConfirmationDate, String[] hangoutArray, String[] diseaseArray,NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		ReportPatientDAO reportPatientDAO = new ReportPatientDAO();
		StringBuffer hangoutValues =new StringBuffer();
		StringBuffer diseaseValues =new StringBuffer();
		if(hangoutArray!=null && hangoutArray.length>0){
			for(int i =0; i<hangoutArray.length; i++){
				hangoutValues.append(hangoutArray[i]);
				if((i+1)<hangoutArray.length)
					hangoutValues.append(",");
			}
		}else{
			logger.error("Passsed fromConfirmaDate:", fromConfirmationDate);
			logger.error("Passsed toConfirmationDate:", toConfirmationDate);
			logger.error("Passsed hangoutArray is null, Please check!!!", hangoutArray);
			//return null;
		}
		if(diseaseArray!=null && diseaseArray.length>0){
			for(int i =0; i<diseaseArray.length; i++){
				diseaseValues.append(diseaseArray[i]);
				if((i+1)<diseaseArray.length)
					diseaseValues.append(",");
			}
		}else{
			logger.error("Passsed fromConfirmaDate:", fromConfirmationDate);
			logger.error("Passsed toConfirmationDate:", toConfirmationDate);
			logger.error("Passsed diseaseArray is null, Please check!!!", diseaseArray);
			//return null;
		}

		Collection<Object> returnVal = new ArrayList<Object>();
		try {
			
			Timestamp fromConfirmTime = StringUtils.stringToStrutsTimestamp(fromConfirmationDate);
			Timestamp toConfirmTime= StringUtils.stringToStrutsTimestamp(toConfirmationDate);
			Collection<Object>  hangoutLocationCollection=reportPatientDAO.getAssociatedHangoutsWithinDateRange(fromConfirmTime, toConfirmTime, hangoutValues.toString(), diseaseValues.toString(), nbsSecurityObj);
			
			Iterator<Object> iterator = hangoutLocationCollection.iterator();
			while(iterator.hasNext()){
				CR4VO cr4VO =new CR4VO();
				ReportPlaceDT reportPlaceDT = (ReportPlaceDT)iterator.next();
				cr4VO.setReportPlaceDT(reportPlaceDT);
				
				Collection coll = reportPatientDAO.getAssociatedHangoutsCasesWithinDateRange(fromConfirmTime, toConfirmTime,reportPlaceDT.getPlaceKey(), nbsSecurityObj);
				
				if(coll !=null){
					Integer number200 = 0;
					Integer number300 = 0;
					Integer number700 = 0;
					Integer number710 = 0;
					Integer number720 = 0;
					Integer number730 = 0;
					Integer number740 = 0;
					Integer number745 = 0;
					Integer number900 = 0;
					Integer number950 = 0;
					Integer numberCohorts = 0;
					Map map = new HashMap();
							
					Iterator iterator2 =  coll.iterator();
					while(iterator2.hasNext()){
						ReportPatientDT reportPatientDT =  (ReportPatientDT)iterator2.next();
						if((map.get(reportPatientDT.getInvestigationKey()))!=null){
							continue;
						}
						map.put(reportPatientDT.getInvestigationKey(),  reportPatientDT);
						if(reportPatientDT.getDx().equals("200")){
							number200 = number200+1;
						}else if(reportPatientDT.getDx().equals("300")){
							number300 = number300+1;
						}else if(reportPatientDT.getDx().equals("700")){
							number700 = number700+1;
						}else if(reportPatientDT.getDx().equals("710")){
							number710 = number710+1;
						}else if(reportPatientDT.getDx().equals("720")){
							number720= number720+1;
						}else if(reportPatientDT.getDx().equals("730")){
							number730 = number730+1;
						}else if(reportPatientDT.getDx().equals("740")){
							number740 = number740+1;
						}else if(reportPatientDT.getDx().equals("745")){
							number745 = number745+1;
						}else if(reportPatientDT.getDx().equals("900")){
							number900 = number900+1;
						}else if(reportPatientDT.getDx().equals("950")){
							number950 = number950+1;
						}
						cr4VO.setCount200(number200+"");
						cr4VO.setCount300(number300+"");
						cr4VO.setCount710(number710+"");
						cr4VO.setCount720(number720+"");
						cr4VO.setCount730(number730+"");
						cr4VO.setCount740(number740+"");
						cr4VO.setCount745(number745+"");
						cr4VO.setCount900(number900+"");
						cr4VO.setCount950(number950+"");
						if(reportPatientDT.getReferralBasis()!=null && reportPatientDT.getReferralBasis().trim()!=""
								&& reportPatientDT.getReferralBasis().startsWith("C1") ){
							numberCohorts=numberCohorts+1;
							
						}
						if(!Arrays.asList(diseaseArray).contains("200")){
							cr4VO.setCount200("x");
						}else if(!Arrays.asList(diseaseArray).contains("300")){
							cr4VO.setCount300("x");
						}else if(!Arrays.asList(diseaseArray).contains("710")){
							cr4VO.setCount710("x");
						}else if(!Arrays.asList(diseaseArray).contains("720")){
							cr4VO.setCount720("x");
						}else if(!Arrays.asList(diseaseArray).contains("730")){
							cr4VO.setCount730("x");
						}else if(!Arrays.asList(diseaseArray).contains("745")){
							cr4VO.setCount745("x");
						}else if(!Arrays.asList(diseaseArray).contains("900")){
							cr4VO.setCount900("x");
						}else if(!Arrays.asList(diseaseArray).contains("950")){
							cr4VO.setCount950("x");
						}

						cr4VO.setCohortCount(numberCohorts+"");
					}
				}
				returnVal.add(cr4VO);
			}

		} catch (NEDSSSystemException ex) {
			logger.fatal("Exception in processLinkId:  ERROR = "
					+ ex);
			throw new NEDSSAppException(ex.toString());
		} catch (Exception e) {
			logger.error("Passsed fromConfirmaDate:", fromConfirmationDate);
			logger.error("Passsed toConfirmationDate:", toConfirmationDate);
			logger.error("Passsed hangoutArray is null, Please check!!!", hangoutArray);
			throw new NEDSSAppException(e.getMessage());
		}
		return returnVal;
	}

}
