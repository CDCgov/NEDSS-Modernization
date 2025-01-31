package gov.cdc.nedss.util;

import gov.cdc.nedss.webapp.nbs.action.pagemanagement.managecondition.ManageConditionAction;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

public class PropertyUtilClient {
	
	private boolean prAreaFlag;

	static final LogUtils logger = new LogUtils(PropertyUtilClient.class.getName());
	
	public boolean getIsStdOrHivProgramArea(String pa) {
		prAreaFlag=PropertyUtil.isStdOrHivProgramArea(pa);
		return prAreaFlag;
	}

	public void setPrAreaFlag(boolean prAreaFlag) {
		this.prAreaFlag = prAreaFlag;
		
	}

	public  boolean getPublished(String conditionCd, HttpSession session) {
		PageManagementActionUtil util = new PageManagementActionUtil();
		Boolean isPublished = Boolean.FALSE;
		try {
			String isPublishedStr = util.getPublishedConditionInd(conditionCd,session);
			logger.debug("getPublished : isPublishedStr is :" + isPublishedStr);
			
			// handling the NullPointerException  by jayasudha on 30-01-2017
			if(isPublishedStr!=null && isPublishedStr.equals("T")) {
				isPublished =Boolean.TRUE;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isPublished.booleanValue();
	} // end of assignJursidiction()
	
	
}
