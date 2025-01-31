package gov.cdc.nedss.page.ejb.pageproxyejb.vo;

import java.util.Collection;

import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.util.AbstractVO;

/**
 *Name: PageProxyVO.java 
 *Description: PageProxyVO Object for Dynamic Pages
 *Copyright(c) 2010
 *Company: CSC
 *@since: NBS4.0
 *@author Pradeep Sharma
 *@updated by: Pradeep Sharma
 *@since: NBS 4.5
 */

public interface PageProxyVO{
	public static final long serialVersionUID = 1L;

	public String getPageProxyTypeCd();
	public void setPageProxyTypeCd(String pageProxyTypeCd);
	public PublicHealthCaseVO getPublicHealthCaseVO();
	public void setPublicHealthCaseVO(PublicHealthCaseVO publicHealthCaseVO);
	public InterviewVO getInterviewVO();
	public void setInterviewVO(InterviewVO interviewVO);	
	public InterventionVO getInterventionVO();
	public void setInterventionVO(InterventionVO interventionVO);
}
