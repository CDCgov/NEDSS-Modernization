package gov.cdc.nedss.report.javaRepot.util;

import gov.cdc.nedss.util.LogUtils;
/**
 * Utility class to convert arrays, parse out values, etc
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportCommonUtility {
	static final LogUtils logger = new LogUtils(ReportCommonUtility.class.getName());
	
	public static String convertArrayToStringSql(String[] array){
		StringBuffer tempValues =new StringBuffer();
		if(array!=null && array.length>0){
			for(int i =0; i<array.length; i++){
				tempValues.append(array[i]);
				if((i+1)<array.length)
					tempValues.append(",");
			}
		}else{
			logger.error("ReportCommonUtility.convertArrayToStringSql Passsed epiLinkId:", array);
			logger.error("ReportCommonUtility.convertArrayToStringSql Passsed diagnosisArray is null, Please check!!!", array);
			//return null;
		}
		logger.debug("ReportCommonUtility.convertArrayToStringSql the input array: "+array);
		logger.debug("ReportCommonUtility.convertArrayToStringSql the tempValues are :"+tempValues.toString());
		return tempValues.toString();
	}
	
}
