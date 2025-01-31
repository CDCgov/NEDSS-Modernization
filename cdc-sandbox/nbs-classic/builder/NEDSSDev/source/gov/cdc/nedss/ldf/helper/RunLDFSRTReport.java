package gov.cdc.nedss.ldf.helper;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Raghu Komanduri
 * @version 1.0
 */


import java.io.*;
import java.lang.*;
import java.util.*;

import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.ejb.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.scheduler.ldfsrtreport.ldfsrtreportmanager.LdfSrtReportManager;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.NEDSSAppException;



/*
 import org.apache.xalan.xslt.*;
import org.apache.xalan.*;
 */

public class RunLDFSRTReport {
	static final LogUtils logger = new LogUtils(RunLDFSRTReport.class.getName());
        private NBSSecurityObj securityObj=null;


	/**
	 * Constructor
	 * @throws java.lang.Exception
	 */
	public RunLDFSRTReport(){
	}

	public static void main(String[] args) throws Exception {
		String output = null;
                String fileName=null;
                String reportType = null;
                StringBuffer html = new StringBuffer();
		if (args.length == 0
			|| args.length < 6
                        || !args[0].equalsIgnoreCase("-username")
			|| !args[2].equalsIgnoreCase("-output")
                        || !args[4].equalsIgnoreCase("-reporttype")) {
			System.out.println(
				"Usage: RunLDFSRTReport -username <user> -output <filename> -reporttype <codeset|pageset|validation|datatype|classcode|all>");
			return;
		}

		RunLDFSRTReport ldf = new RunLDFSRTReport();

		boolean securityCheck = ldf.checkPermission(args[1]);
		if(!securityCheck) {
			System.out.println("You do not have permission to run the utility.");
			System.exit(0);
		}
                if(args[2] != null && args[2].equalsIgnoreCase("-output"))
                 fileName = args[3];
               if(args[4] != null && args[4].equalsIgnoreCase("-reporttype"))
                 reportType = args[5];

                html.append("<HTML>");
		if(reportType != null && reportType.equalsIgnoreCase("all"))
                {

                  html.append(ldf.getSRTCodeSets());
                  html.append(ldf.getDataTypes());
                  html.append(ldf.getValidations());
                  html.append(ldf.getClassCodes());
                  html.append(ldf.getPagesets());
                }
                else if(reportType != null && reportType.equalsIgnoreCase("codeset"))
                    html.append(ldf.getSRTCodeSets());
                else if(reportType != null && reportType.equalsIgnoreCase("datatype"))
                    html.append(ldf.getDataTypes());
                else if(reportType != null && reportType.equalsIgnoreCase("validation"))
                    html.append(ldf.getValidations());
                else if(reportType != null && reportType.equalsIgnoreCase("pageset"))
                    html.append(ldf.getPagesets());
                else if(reportType != null && reportType.equalsIgnoreCase("classcode"))
                    html.append(ldf.getClassCodes());

                html.append("</HTML>");
                if(fileName != null)
                  ldf.writeToFile(fileName,html);

	}
        public void writeToFile(String fileName,StringBuffer sb)throws Exception {


           File file = new File(fileName);
           FileWriter fw = new FileWriter(file);
           PrintWriter out = new PrintWriter(new BufferedWriter(fw));
           out.write(sb.toString());
           out.flush();

         }
	private boolean checkPermission(String userName) {
		securityObj = getNBSSecurity(userName, "");
		if(securityObj == null){
			return false;
		}
		return securityObj.getPermission(
						NBSBOLookup.SYSTEM,
						NBSOperationLookup.LDFADMINISTRATION);
	}


        public StringBuffer getSRTCodeSets() throws NEDSSAppException {
          DefinedFieldSRTCodeSets codeSets = new DefinedFieldSRTCodeSets();
          return(codeSets.toHTML());
        }

        public StringBuffer getDataTypes() throws NEDSSAppException {
          DefinedFieldDataTypes dataType = new DefinedFieldDataTypes();
          return(dataType.toHTML());
        }

        public StringBuffer getValidations() throws NEDSSAppException {
          DefinedFieldValidationTypes validationType = new DefinedFieldValidationTypes();
          return(validationType.toHTML());
        }

        public StringBuffer getClassCodes() throws NEDSSAppException {
          DefinedFieldClassCodes classCodes = new DefinedFieldClassCodes();
          return(classCodes.toHTML());
        }

        public StringBuffer getPagesets() throws NEDSSAppException {
          PageSets pageSet = new PageSets();
          return(pageSet.toHTML());
        }

  private NBSSecurityObj getNBSSecurity(String userName, String passWord) {
		NBSSecurityObj securityObj = null;
		try {

			MainSessionCommandHome msCommandHome = null;
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			msCommandHome =
				(MainSessionCommandHome) PortableRemoteObject.narrow(
					nedssUtils.lookupBean(sBeanName),
					MainSessionCommandHome.class);
			MainSessionCommand mainSessionCommand = msCommandHome.create();
			securityObj =
				mainSessionCommand.nbsSecurityLogin(userName, passWord);
		} catch (Exception e) {
			System.out.println(" Error in calling mainsessionCommand  " + e);
		}
		return securityObj;
	}
  public static void ldfSRTReportProcessor(String username, String fileName, String reportType) throws Exception {
		String output = null;
              StringBuffer html = new StringBuffer();
		if ((username==null || (username!=null && username.length()== 0))&&
			(fileName==null || (fileName!=null && fileName.length()== 0))&&
			(reportType==null || (reportType!=null && reportType.length()== 0))) {
			logger.error(
				"Usage: RunLDFSRTReport -username <user> -output <filename> -reporttype <codeset|pageset|validation|datatype|classcode|all>");
			return;
		}

		RunLDFSRTReport ldf = new RunLDFSRTReport();

		boolean securityCheck = ldf.checkPermission(username);
		if(!securityCheck) {
			logger.error("You do not have permission to run the utility.");
			return;
		}
              
              html.append("<HTML>");
		if(reportType != null && reportType.equalsIgnoreCase("all"))
              {

                html.append(ldf.getSRTCodeSets());
                html.append(ldf.getDataTypes());
                html.append(ldf.getValidations());
                html.append(ldf.getClassCodes());
                html.append(ldf.getPagesets());
              }
              else if(reportType != null && reportType.equalsIgnoreCase("codeset"))
                  html.append(ldf.getSRTCodeSets());
              else if(reportType != null && reportType.equalsIgnoreCase("datatype"))
                  html.append(ldf.getDataTypes());
              else if(reportType != null && reportType.equalsIgnoreCase("validation"))
                  html.append(ldf.getValidations());
              else if(reportType != null && reportType.equalsIgnoreCase("pageset"))
                  html.append(ldf.getPagesets());
              else if(reportType != null && reportType.equalsIgnoreCase("classcode"))
                  html.append(ldf.getClassCodes());

              html.append("</HTML>");
              if(fileName != null)
                ldf.writeToFile(fileName,html);

	}
}
