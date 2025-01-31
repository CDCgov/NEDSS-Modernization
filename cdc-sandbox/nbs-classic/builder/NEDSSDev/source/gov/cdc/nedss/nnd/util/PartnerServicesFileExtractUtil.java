package gov.cdc.nedss.nnd.util;

/**
 * PartnerServicesFileExtractUtil - Batch program for creating partner services file.
 *  Places output file in the wildfly-10.0.0.Final\nedssdomain\Nedss\report\log directory.
 *  To run:  BatchFiles>NBSPartnerServicesUtil pks March 2017 Anthony Merriweather
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: CSRA for CDC</p>
 * March 25, 2017
 * @version 1.9
 */

import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;

import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.form.hivpartnerservices.HivPartnerServicesForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PartnerServicesFileExtractUtil {
	static final LogUtils logger = new LogUtils(PartnerServicesFileExtractUtil.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	public static void main(String[] args) {
		String userName = null;
		String reportingMonth = null;
		String reportingYear = null;
		String investigationFormCd = null;
		String interviewFormCd = null;
		String contactName = null;
		byte[] theXMLDocument = null;

		String nedssDir = new StringBuffer(System.getProperty("nbs.dir"))
		.append(File.separator)
		.toString()
		.intern();

		String reportLogDir = new StringBuffer(nedssDir)
		.append("report")
		.append(File.separator)
		.append("log")
		.append(File.separator)
		.toString()
		.intern();

		if(args.length>3){
			userName = args[0];
		} else {
			System.out.println("This utility extracts Partner Services data for a 6 month period to a file."); 
			System.out.println("Usage :  NBSPartnerServices.bat <USER_ID> [March or September] [Year YYYY] [Contact Name");
			System.exit(-1); 
		}

		if (args[1].equalsIgnoreCase("March"))
			reportingMonth= "3";
		else if (args[1].equalsIgnoreCase("September"))
			reportingMonth = "9";
		else {
			System.out.println("This utility extracts Partner Services data for a 6 month period to a file."); 
			System.out.println("Usage :  NBSPartnerServices.bat <USER_ID> [March or September] [Year YYYY] [Contact Name");
			System.out.println("Reporting Month is not March or September");
			System.exit(-1);
		}

		if (args[2].equalsIgnoreCase("2015")
				|| args[2].equalsIgnoreCase("2016")
				|| args[2].equalsIgnoreCase("2017")
				|| args[2].equalsIgnoreCase("2018")
				|| args[2].equalsIgnoreCase("2019")
				|| args[2].equalsIgnoreCase("2020")
				|| args[2].equalsIgnoreCase("2021")
				|| args[2].equalsIgnoreCase("2022"))
			reportingYear= args[2];
		else {
			System.out.println("This utility extracts Partner Services data for a 6 month period to a file."); 
			System.out.println("Usage :  NBSPartnerServices.bat <USER_ID> [March or September] [Year YYYY] [Contact Name");
			System.out.println("Reporting Year is not valid");
			System.exit(-1);
		}


		if (args.length > 3) {
			contactName= args[3];
			if (args.length > 4) 
				contactName = contactName + " " + args[4];
			if (args.length > 5) 
				contactName = contactName + " " + args[5]; //could add phone or something

		} else {
			System.out.println("This utility extracts Partner Services data for a 6 month period to a file."); 
			System.out.println("Usage :  NBSPartnerServices.bat <USER_ID> [March or September] [Year YYYY]  [Contact Name");
			System.out.println("Contact Name is not valid");
			System.exit(-1);
		}	    	

		theXMLDocument = requestPartnerServicesFileExtract(userName,reportingMonth,reportingYear,contactName);
		if (theXMLDocument != null) {
			writeXMLDocumentToReportLog(reportingMonth, reportingYear, reportLogDir,theXMLDocument);
		} else {
			System.out.println("Error occurred during request for Partner Services File Extract");
			System.out.println("Check log file.");
			System.exit(1);
		}

		System.exit(0);
	}
	    
	    
	/**
	 *  Write XML document to Nedss\report\log directory  
	 * @param reportingMonth
	 * @param reportingYear
	 * @param reportLogDir
	 * @param theXMLDocument
	 */
	private static void writeXMLDocumentToReportLog(String reportingMonth,
			String reportingYear, String reportLogDir, byte[] theXMLDocument) {
		
		
		FileOutputStream fos = null;
		String fileNameAndPath = reportLogDir+getPartnerServicesFileName(reportingMonth, reportingYear);
		
		
			System.out.println("    writing file to "+fileNameAndPath);
			try {
				fos = new FileOutputStream(new File(fileNameAndPath));
			} catch (FileNotFoundException e1) {
				System.out.println("    error creating/finding file to "+fileNameAndPath);
				e1.printStackTrace();
			}
			
		try {
			fos.write(theXMLDocument);
			fos.close();
			System.out.println("Partner Services file successfully written to "+fileNameAndPath);
;
			//System.out.println("  PS_01__AL_2016QTR34.xml");
		} catch (IOException e) {
			System.out.println("    error i/o exception writing file to "+fileNameAndPath);
			e.printStackTrace();
		}
	}

	/**
	 * requestPartnerServicesFileExtract - call backend to request extract
	 * @param userName
	 * @param reportingMonth
	 * @param reportingYear
	 * @param contactName
	 * @return
	 */
	private static byte[]  requestPartnerServicesFileExtract(String userName,
			String reportingMonth, String reportingYear, String contactName) {
		
		System.out.println("Reporting Month = "+reportingMonth);
		System.out.println("Reporting Year = "+reportingYear);
		System.out.println("Contact Name = "+contactName);
		String investigationFormCode = null;
		String interviewFormCode = null;
		
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;;
		Object objref = nedssUtils.lookupBean(sBeanName);
		byte[] returnByteArray = null;
		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());
			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			System.out.println("Successful Login");
			
			//call to get form code for HIV page
	        WaTemplateDT waTemplateDT = new WaTemplateDT();
	        try
	        {
	            String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
	            String sMethod = "getWaTemplateByCondTypeBusObj";
	            Object[] oParams = new Object[] { "10560", NEDSSConstants.PUBLISHED, NEDSSConstants.INV};
	            MainSessionHolder holder = new MainSessionHolder();
	            ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	            waTemplateDT = (WaTemplateDT) arr.get(0);
	            if (waTemplateDT != null) {
	            	investigationFormCode = waTemplateDT.getFormCd();
	            	System.out.println("InvestigationFormCode = "+investigationFormCode);
	            } else {
	            	System.out.println("Error: Could not find the Published Investigation Page form code for the HIV condition 10560");
	            	System.exit(1);
	            }
	        }
	        catch (Exception ex)
	        {
	        	System.out.println("Error: Exception trying to get the Published Investigation Page form code for the HIV condition 10560");
	        	System.exit(1);
	        }
	        
			//call to get form code for HIV Interview
	        try
	        {
	            String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
	            String sMethod = "getWaTemplateByCondTypeBusObj";
	            Object[] oParams = new Object[] { "10560", NEDSSConstants.PUBLISHED, NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE};
	            MainSessionHolder holder = new MainSessionHolder();
	            ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	            waTemplateDT = (WaTemplateDT) arr.get(0);
	            if (waTemplateDT != null) {
	            	interviewFormCode = waTemplateDT.getFormCd();
	            	System.out.println("InterviewFormCode = "+interviewFormCode);
	            } else {
	            	System.out.println("Error: Could not find the Published Interview Page form code for the HIV condition 10560");
	            	System.exit(1); 
	            }
	        }
	        catch (Exception ex)
	        {
	        	System.out.println("Error: Exception trying to get the Published Interview Page form code for the HIV condition 10560");
	        	System.exit(1);
	        }
	        
	        
			System.out.println("Calling to request file extract for " +reportingMonth +"/" +reportingYear);
			String sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
			String sMethod = "createHivPartnerServicesFile";
			//TBD: get inv and ixs form code
			Object[] oParams = new Object[] {reportingMonth, reportingYear, contactName, investigationFormCode, interviewFormCode};
			ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			returnByteArray = (byte[]) arr.get(0);
			int xmlSize = returnByteArray.length;
			
			System.out.println("Partner Services document returned to batch program - size is " +xmlSize);
	        
	 	} catch (Exception e) {
	 		logger.error("Exception calling createHivPartnerServicesFile is " +e.getMessage());
	 		System.out.println("Exception calling createHivPartnerServicesFile is " +e.getMessage());
			e.printStackTrace();
		}
		
		return returnByteArray;
		
	}

	
	/**
	 * The file name has to be in a particular format. i.e. PS_GA_13_2014QTR34.xml.
	 * @param partnerServicesForm
	 * @return
	 */
	private static String getPartnerServicesFileName(
			String reportingMonth, String reportingYear) {
		//Unusual logic around month and year
		//get the current year
		String rptYearStr = "";
		String quarterStr = "";
		try {
			String currentYearStr = new SimpleDateFormat("yyyy").format(new Date());
			int curYear = Integer.parseInt(currentYearStr);
			int rptYear = Integer.parseInt(reportingYear);
			boolean priorYr = false;
			boolean presentYr = false;
			boolean futureYr = false;
			int reportYearToUse = rptYear;
			if (rptYear == curYear)
				presentYr = true;
			else if (rptYear < curYear)
				priorYr = true;
			else if (rptYear > curYear)
				futureYr = true;

			if (presentYr && reportingMonth.equals("3")) //march
				reportYearToUse = --reportYearToUse;
			else if (futureYr && reportingMonth.equals("3")) //march
				reportYearToUse = --reportYearToUse;
			else if (priorYr && reportingMonth.equals("3")) //march)
				reportYearToUse = --reportYearToUse;
			//System.out.println("Partner Services - Report year to use is: " + reportYearToUse);
			rptYearStr = String.valueOf(reportYearToUse);
			//reporting month/year indicate the date ranges
			quarterStr = "";
			if (reportingMonth.equals("3"))  {//march
				quarterStr = "34";
			}
			if (reportingMonth.equals("9")) { //sept
				quarterStr = "12";
			}
		} catch (Exception ex) {
			logger.error("Error in getPartnerServicesFileName: "+ex.getMessage());
			ex.printStackTrace();
		}
		String classCode = getNBSClassCd();
		String stateCode = getNBSStateCd();
		logger.info("Partner Services - File name is: " + "PS_"+classCode+"_"+stateCode+"_"+rptYearStr+"QTR"+quarterStr+".xml");
		return ("PS_"+stateCode+"_"+classCode+"_"+rptYearStr+"QTR"+quarterStr+".xml");
	}
	/**
	 * getNBSStateCd() get state code from property file
	 * @return state code i.e 13
	 */
    private static String getNBSStateCd()
    {

        //call propertyUtil to get state code i.e 01, 13 from nedss.properties
        String stateCode = propertyUtil.getNBS_STATE_CODE();
        logger.debug(
                "PartnerServices Getting state code from nedss.properties file: " + stateCode);

        return stateCode;
    }
    /**
     * Get NBS Class CD which is the 2 digit State Abbreviation i.e. GA
     * @return NBS Class Cd
     */
    private static String getNBSClassCd()
    {

        //call propertyUtil to get state class code i.e GA, AL from nedss.properties
        String stateClassCode = propertyUtil.getNBS_CLASS_CODE();
        logger.debug(
                "PartnerServices Getting state class code from nedss.properties file: " + stateClassCode);

        return stateClassCode;
    }    
}

