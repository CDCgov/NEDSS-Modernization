package gov.cdc.nedss.report.javaRepot.util;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.report.javaRepot.dao.ReportPa3DAO;
import gov.cdc.nedss.report.javaRepot.dt.CountIndexDT;
import gov.cdc.nedss.report.javaRepot.vo.Pa3VO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Pradeep Kumar Sharma
 *
 */
public class Pa3Util {
	static final LogUtils logger = new LogUtils(Pa3Util.class.getName());

	//numberOfCasesColl
	private String totalNumberOfCases="Total Number of Cases";
	private String totalNumberOfPartnersInitiated=	"Total No. Partners Init’d:";
	private String totalNumberOfSocialContactsInitiated="Total No. Social Contacts Init’d:";
	private String totalNumberOfAssoicatesInitiated="Total No. Associates Init'd:";
	
	private String contactIndex="Contact Index:";
	private String clusterIndex="Cluster Index:";
	
	private String numberOfCasesWithInternetFollowup="No./Pct. Cases w/Internet Follow-up";
	private String totalNumberOfPartners="Total No. Partners:";
	private String totalNumberOfSocialContacts="Total No. Social Contacts:";
	private String totalNumberOfAssociates="Total No. Associates:";
	
	private String ipsContactIndex="IPS Contact Index:";
	private String ipsClusterIndex="IPS Cluster Index:";
	
	
	//ipsColl
	private String totalNumberOfIpsPartners="Total No. IPS Partners:";
	private String totalNumberOfIpsSocialContacts="Total No. IPS Social Contacts:";
	private String totalNumberOfIpsAssociates="Total No. IPS Associates:";
	
	
	//internetOcByContType
	private String outcomeBySexualContact="Sexual Contact";
	private String outcomeBySocialContact="Social Contact";
	private String outcomeByAssociate="Associate";
	
	private String associateList=" 'A1 - Associate 1', 'A2 - Associate 2','A3 - Associate 3'";
	private String cohortList=" 'C1- Cohort'";
	private String partnerList="'P1 - Partner, Sex','P2 - Partner, Needle-Sharing','P3 - Partner, Both'";
	private String socialList="'S1 - Social Contact 1','S2 - Social Contact 2','S3 - Social Contact 3'";
	
	
	public Pa3VO processRequest(String fromConfirmationDate,String toConfirmationDate, String[] diagnosisArray,  NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		ArrayList list = new ArrayList();
		Pa3VO pa3VO =  new Pa3VO();
		DecimalFormat formatter = new DecimalFormat("0.00");
		ReportPa3DAO reportPa3DAO =  new ReportPa3DAO();		
		StringBuffer diagnosisBuffer =new StringBuffer();
		if(diagnosisArray!=null && diagnosisArray.length>0){
			 for(int i =0; i<diagnosisArray.length; i++){
				 if(diagnosisArray[i].equals(null)){
					 continue;
				 }
			    	if(i==0){
						diagnosisBuffer.append("'");
					}
			    	diagnosisBuffer.append(diagnosisArray[i]);
					if((i+1)<diagnosisArray.length)
						diagnosisBuffer.append("','");
					
				}
				diagnosisBuffer.append("'");
		}else{
			logger.error("Passsed epiLinkId:", diagnosisBuffer);
			logger.error("Passsed diagnosisArray is null, Please check!!!", diagnosisArray);
		}
		Timestamp fromConfirmTime = StringUtils.stringToStrutsTimestamp(fromConfirmationDate);
		Timestamp toConfirmTime= StringUtils.stringToStrutsTimestamp(toConfirmationDate);
		//numberOfCasesColl
		CountIndexDT countIndexDT1 =  new CountIndexDT();
		countIndexDT1.setLabel(totalNumberOfCases);
		int counta =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(), null, ReportPa3DAO.QueryA, false,false,nbsSecurityObj);
		countIndexDT1.setCount1(counta+"");
		pa3VO.getNumberOfCasesColl().add(countIndexDT1);
		
		CountIndexDT countIndexDT2 =  new CountIndexDT();
		countIndexDT2.setLabel(totalNumberOfPartnersInitiated);
		int countb =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  partnerList, ReportPa3DAO.QueryB, false,false,nbsSecurityObj);
		countIndexDT2.setCount1(countb+"");
		pa3VO.getNumberOfCasesColl().add(countIndexDT2);

		
		CountIndexDT countIndexDT3 =  new CountIndexDT();
		countIndexDT3.setLabel(totalNumberOfSocialContactsInitiated);
		int countc =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  socialList, ReportPa3DAO.QueryB, false,false,nbsSecurityObj);
		countIndexDT3.setCount1(countc+"");
		pa3VO.getNumberOfCasesColl().add(countIndexDT3);


		CountIndexDT countIndexDT4 =  new CountIndexDT();
		countIndexDT4.setLabel(totalNumberOfAssoicatesInitiated);
		int countd =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  associateList, ReportPa3DAO.QueryB, false,false,nbsSecurityObj);
		countIndexDT4.setCount1(countd+"");
		pa3VO.getNumberOfCasesColl().add(countIndexDT4); 

		CountIndexDT countIndexDT5 =  new CountIndexDT();
		countIndexDT5.setLabel(contactIndex);
		double e =0.0;
		if(counta>0){
			e = new Double(countb)/new Double(counta);
		}
		countIndexDT5.setCount1(formatter.format(e));
		pa3VO.getNumberOfCasesColl().add(countIndexDT5);

		CountIndexDT countIndexDT6 =  new CountIndexDT();
		countIndexDT6.setLabel(clusterIndex);
		double f =0.0;
		if(counta>0){
			f = new Double((countc+ countd))/new Double(counta);
		}
		countIndexDT6.setCount1(formatter.format(f));
		pa3VO.getNumberOfCasesColl().add(countIndexDT6);

		CountIndexDT countIndexDT7 =  new CountIndexDT();
		countIndexDT7.setLabel(numberOfCasesWithInternetFollowup);
		int countg =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  null, ReportPa3DAO.QueryG, true,false,nbsSecurityObj);
		countIndexDT7.setCount1(countg+"");
		pa3VO.getNumberOfCasesColl().add(countIndexDT7);

		CountIndexDT countIndexDT8 =  new CountIndexDT();
		countIndexDT8.setLabel(totalNumberOfPartners);
		int counth =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(), partnerList, ReportPa3DAO.QueryB, true, false,nbsSecurityObj);
		countIndexDT8.setCount1(counth+"");

		pa3VO.getNumberOfCasesColl().add(countIndexDT8);

		CountIndexDT countIndexDT9 =  new CountIndexDT();
		countIndexDT9.setLabel(totalNumberOfSocialContacts);
		int counti =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  socialList, ReportPa3DAO.QueryB, true, false,nbsSecurityObj);
		countIndexDT9.setCount1(counti+"");
		pa3VO.getNumberOfCasesColl().add(countIndexDT9);

		CountIndexDT countIndexDT10 =  new CountIndexDT();
		countIndexDT10.setLabel(totalNumberOfAssociates);
		int countj =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(), associateList, ReportPa3DAO.QueryB, true, false,nbsSecurityObj);
		countIndexDT10.setCount1(countj+"");
		pa3VO.getNumberOfCasesColl().add(countIndexDT10);

		CountIndexDT countIndexDT11 =  new CountIndexDT();
		countIndexDT11.setLabel(ipsContactIndex);
		double k =0.0;
		if(countg>0){
			k = new Double(counth)/new Double(countg);
		}
		countIndexDT11.setCount1(formatter.format(k));

		pa3VO.getNumberOfCasesColl().add(countIndexDT11);

		CountIndexDT countIndexDT12 =  new CountIndexDT();
		countIndexDT12.setLabel(ipsClusterIndex);
		double l =0.0;
		if(countg>0){
			l = new Double((counti+countj))/new Double(countg);
		}
		countIndexDT12.setCount1(formatter.format(l));
		pa3VO.getNumberOfCasesColl().add(countIndexDT12);

		
		//ipsColl
		CountIndexDT countIndexDT13 =  new CountIndexDT();
		countIndexDT13.setLabel(totalNumberOfIpsPartners);
		int countl =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(), partnerList, ReportPa3DAO.QueryB, true, true,nbsSecurityObj);
		countIndexDT13.setCount1(countl+"");
		pa3VO.getIpsColl().add(countIndexDT13);

		CountIndexDT countIndexDT14 =  new CountIndexDT();
		countIndexDT14.setLabel(totalNumberOfIpsSocialContacts);
		int countm =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  socialList, ReportPa3DAO.QueryB, true, true,nbsSecurityObj);
		countIndexDT14.setCount1(countm+"");
		pa3VO.getIpsColl().add(countIndexDT14);

		CountIndexDT countIndexDT15 =  new CountIndexDT();
		countIndexDT15.setLabel(totalNumberOfIpsAssociates);
		int countn =reportPa3DAO.getCountCases(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  associateList, ReportPa3DAO.QueryB, true, true,nbsSecurityObj);
		countIndexDT15.setCount1(countn+"");
		pa3VO.getIpsColl().add(countIndexDT15);


		
		//internetOcByContType
		CountIndexDT countIndexDT16=reportPa3DAO.getCountCasesByIntFollup(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  partnerList, ReportPa3DAO.distinctCount,nbsSecurityObj);
		countIndexDT16.setLabel(outcomeBySexualContact);
		pa3VO.getInternetOcByContType().add(countIndexDT16);

		CountIndexDT countIndexDT17=reportPa3DAO.getCountCasesByIntFollup(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  socialList,ReportPa3DAO.distinctCount, nbsSecurityObj);
		countIndexDT17.setLabel(outcomeBySocialContact);
		pa3VO.getInternetOcByContType().add(countIndexDT17);

		CountIndexDT countIndexDT18=reportPa3DAO.getCountCasesByIntFollup(fromConfirmTime, toConfirmTime, diagnosisBuffer.toString(),  associateList,ReportPa3DAO.distinctCount, nbsSecurityObj);
		countIndexDT18.setLabel(outcomeByAssociate);
		pa3VO.getInternetOcByContType().add(countIndexDT18);

		return pa3VO;
	}
	
	

}
