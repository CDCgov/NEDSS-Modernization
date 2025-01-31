package gov.cdc.nedss.proxy.ejb.pamproxyejb.dao;


import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pam.dao.PamRootDAO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.AggregateSummaryResultVO;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class AggregateSummaryDataDAOImpl extends DAOBase {
	private static final String SELECT_PUBLIC_HEALTH_CASE = "" +
			"SELECT Public_Health_Case.public_health_case_uid \"publicHealthCaseUid\","
        +" Public_Health_Case.case_class_cd \"caseClassCd\","
        +" Public_Health_Case.last_chg_time \"lastChgTime\","
         +" Public_Health_Case.txt \"txt\","
         +" Public_Health_Case.mmwr_week \"mmwrWeek\","
        +" Public_Health_Case.mmwr_year \"mmwrYear\","
        +" Public_Health_Case.rpt_cnty_cd \"rptCntyCd\","
        +" Public_Health_Case.cd \"cd\","
        +" Public_Health_Case.count_interval_cd \"countIntervalCd\","
        +" Public_Health_Case.cd_desc_txt \"cdDescTxt\","
        +" Public_Health_Case.rpt_form_cmplt_time \"rptFormCmpltTime\" ,"
        +" Notification.rpt_sent_time  \"rptSentTime\" ,"
        +" Notification.record_status_cd   \"recordStatusCd\""
        +	" from public_health_case public_health_case  "
        +" left outer join act_relationship on public_health_case.public_health_case_uid=act_relationship.target_act_uid "
        +" left outer join notification on notification.notification_uid=act_relationship.source_act_uid ";

	
	private static final LogUtils logger = new LogUtils(AggregateSummaryDataDAOImpl.class.getName());
	@SuppressWarnings("unchecked")
	public Collection<Object>  getAggregateSummaryDataCollection(String WhereClause) throws NEDSSSystemException{
		Collection<Object>  aggregateSummaryDTCollection=new ArrayList<Object> ();
		ArrayList<Object>  publicHealthCaseDTCollection  = new ArrayList<Object> ();
		String query = SELECT_PUBLIC_HEALTH_CASE+ WhereClause;
		 PublicHealthCaseDT publicHealthCaseDT= new  PublicHealthCaseDT();
		try
		{
			publicHealthCaseDTCollection  = (ArrayList<Object> )preparedStmtMethod(publicHealthCaseDT, publicHealthCaseDTCollection, query, NEDSSConstants.SELECT);
			
			if(publicHealthCaseDTCollection!=null && publicHealthCaseDTCollection.size()>0){
				Iterator<Object> it = publicHealthCaseDTCollection.iterator();
				while(it.hasNext()){
					PublicHealthCaseDT newPublichalthCaseDT =(PublicHealthCaseDT)it.next();
					AggregateSummaryResultVO aggregateSummaryResultVO =  new AggregateSummaryResultVO();
					aggregateSummaryResultVO.setPublicHealthCaseDT(newPublichalthCaseDT);
					PamRootDAO PamRootDAO =new PamRootDAO();
					Collection<Object>  nbsCaseAnswerColl = PamRootDAO.getSummaryPamCaseAnswerCollection(newPublichalthCaseDT.getPublicHealthCaseUid());
					aggregateSummaryResultVO.setNbsCaseAnswerDTColl(nbsCaseAnswerColl);
					aggregateSummaryDTCollection.add(aggregateSummaryResultVO);
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getAggregateSummaryDataCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return aggregateSummaryDTCollection;
	}
}
