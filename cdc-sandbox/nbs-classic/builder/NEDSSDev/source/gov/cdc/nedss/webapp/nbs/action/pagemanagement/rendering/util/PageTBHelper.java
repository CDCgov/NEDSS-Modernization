package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import gov.cdc.nedss.act.tb.ejb.dao.TBDAOImpl;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;




/**
 * 
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2022</p>
 * <p>Company: GDIT</p>
 * PageTBHelper.java
 * Nov 2nd, 2022
 * 
 */


public class PageTBHelper {

	static final LogUtils logger = new LogUtils(PageTBHelper.class.getName());

	public static CachedDropDownValues cdv = new CachedDropDownValues();
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance(); //check STD PA

	
	
	/**
	 * isCaseNumberUnique: this method will return true if the state case number is unique across the pages in the system and false if there are more state case number ids with same value
	 * @param caseNumber
	 * @return
	 */
	
	public boolean isCaseNumberUnique(String caseNumber, String publicHealthCaseUid){
		
		boolean isUnique = true;

		String TBConditions = propertyUtil.getTBConditionsCodes();
		
		if(TBConditions!=null && !TBConditions.isEmpty())
			TBConditions += ",10220";
		else
			TBConditions = "10220";
			
			
        TBDAOImpl tbDAOImpl = new TBDAOImpl();
        String selectedCaseNumber = tbDAOImpl.selectCaseNumber(caseNumber, publicHealthCaseUid, TBConditions);
        
        if(selectedCaseNumber!=null && !selectedCaseNumber.isEmpty())
        	isUnique=false;

		return isUnique;
		
		
	}

	
    
}
		
