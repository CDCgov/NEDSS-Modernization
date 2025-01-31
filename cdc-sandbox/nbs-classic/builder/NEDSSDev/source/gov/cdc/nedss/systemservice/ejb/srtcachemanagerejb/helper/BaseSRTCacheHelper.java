package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.helper;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTLabTestDT;
import gov.cdc.nedss.systemservice.util.TestResultTestFilterDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 */

public class BaseSRTCacheHelper {
	static final LogUtils logger = new LogUtils(BaseSRTCacheHelper.class.getName());
  public BaseSRTCacheHelper() {
  }

  public Collection<Object>  convertToSRTLabTestDT(Collection<Object> TestResultTestFilterDTList){
   try {
	Iterator<Object>  it =  TestResultTestFilterDTList.iterator();
	    ArrayList<Object> SRTLabTestDTList = new ArrayList<Object> ();
	    while(it.hasNext()){
	      TestResultTestFilterDT oldDT = (TestResultTestFilterDT)it.next();
	      SRTLabTestDT newDT = new SRTLabTestDT();
	
	
	      newDT.setLaboratoryId(oldDT.getLaboratoryId());
	      newDT.setLabTestCd(oldDT.geLabTestCd());
	      newDT.setLabTestDescTxt(oldDT.getLabTestDescTxt());
	      newDT.setIndentLevel(oldDT.getIndentLevel());
	      newDT.setTestTypeCd(oldDT.getTestTypeCd());
	      newDT.setDrugTestInd(oldDT.getDrugTestInd());
	      SRTLabTestDTList.add(newDT);
	
	    }//end while
	    return SRTLabTestDTList;
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	logger.fatal(e.getMessage(), e);
	throw new NEDSSSystemException(e.getMessage(), e);
}
  }

}