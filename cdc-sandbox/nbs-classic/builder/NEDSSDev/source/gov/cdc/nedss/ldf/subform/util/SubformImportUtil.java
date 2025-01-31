/*
 * Created on Jan 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.cdc.nedss.ldf.subform.util;

import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImport;
import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportHome;
import gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean.SubformMetaData;
import gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean.SubformMetaDataHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.rmi.PortableRemoteObject;
/**
 * @author nmallela
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SubformImportUtil {

	static final LogUtils logger = new LogUtils(SubformImportUtil.class.getName());
	private static boolean readFromFile = true;
	private static boolean valid = true;
	private static Properties properties = new Properties();
	private NBSSecurityObj securityObj = null;
	private final Class<?> c1 = SubformConstants.class;
	private static HashMap<Object,Object> pagesetMap = new HashMap<Object,Object>();
	private long maxImportVersionNbr;

	private FileOutputStream fos;
    private FileInputStream fis;
    private BufferedReader br;
    private PrintStream ps;
    private File file;

    /**
     * Description: No Args Constructor
     */
    public SubformImportUtil()
    {
        FileInputStream myFile = null;
        try
        {
            if(readFromFile)
            {
                // make sure we don't try to read the file again even if we don't read it correctly
                readFromFile = false;
                myFile = new FileInputStream(SubformConstants.PROPERTY_FILE);
                if(myFile != null)
                {
                    properties.load(myFile);
                }
                else
                {
                    valid = false;
                }
            }

        }
        catch(IOException e)
        {
            logger.fatal("", e);
            valid = false;
        }
        finally
        {
            try
            {
                if(myFile != null)
                {
                    //close the stream
                    myFile.close();
                }

            }
            catch(IOException e)
            {
                logger.fatal("Failed to Close Property File", e);
                valid = false;
            }
        }
    }

    /**
     * Description: processSubForms method searches and replaces the ids and objectids with the ldfPageId prepended in all the xhtml files
     * @param list
     * @param ldfPageId
     * @throws IOException
     */
    private void processSubForms(String list, String ldfPageId)throws IOException, Exception {

    	if(list == null) return;

    	StringTokenizer sTkn = new StringTokenizer(list, ",");

    	while(sTkn.hasMoreTokens()) {

    		String xhtml = sTkn.nextToken();

    		Method method = c1.getMethod(getMethodName(xhtml), (Class<?>[])null);

    		Object fileLoc = method.invoke(SubformConstants.class, (Object[])null);

    		file = new File(SubformConstants.RAW_XHTMLS_DIR + fileLoc);

    		if(file.exists()) {

    			fis= new FileInputStream(file);
	    		//build the xml file only if the xhtml exists !!

    		} else {
    			//System.out.println(file.getName() +  " is not available for PageSetId: " + ldfPageId );
    			return;
    		}

    		fos = new FileOutputStream(SubformConstants.PROCESSED_XHTMLS_DIR + ldfPageId + "_" + xhtml + ".xhtml");

    		ps = new PrintStream(fos);

    		br = new BufferedReader(new InputStreamReader(fis));

    		while (br.ready()) {
    			String temp = br.readLine();
    			String replace = xhtml + "_";
    			String temp1 = temp.replaceAll(replace, ldfPageId + "_" + xhtml + "_");
    			ps.println(temp1);
    		}
    		ps.close();

    	}
    	try {

    		generateSubFormImportFiles(list, ldfPageId);

    	} catch (Exception e) {
			System.out.println("Error Occured while generating Import Files");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /**
     * Description: generateSubFormImportFiles reads each of the pagesetid(condition) and gets the properties value of subforms available, and generate import xml file
     * @param xhtmlList
     * @param ldfPageId
     * @throws Exception
     */
    private void generateSubFormImportFiles(String xhtmlList, String ldfPageId) throws Exception {

    	Long importUid = null;
    	File f = new File(SubformConstants.SUBFORMS_IMPORT_XML_DIR + getName(ldfPageId) + ".xml");
    	fos = new FileOutputStream(f);
    	ps = new PrintStream(fos);
    	ps.println(SubformConstants.NEDSSIMPORT);
    	//version should be the max version number from db + 1, temporarily put ldfPageId+000 as importversion number
    	ps.println("<version>" + maxImportVersionNbr + "</version>");
    	ps.println("<comment>Subform Import XML for Ldf Page Id -" + ldfPageId + "</comment>");
    	ps.println("<CustomSubForms>");
    	StringTokenizer sTkn = new StringTokenizer(xhtmlList, ",");
    	int objectId = 1;
    	while(sTkn.hasMoreTokens()) {
    		String xhtml = sTkn.nextToken();

    		String xhtmlName = xhtml.concat("_name");
    		Method method = c1.getMethod(getMethodName(xhtmlName), (Class<?>[])null);
    		Object returnVal = method.invoke(SubformConstants.class, (Object[])null);

    		ps.println("<CustomSubForm>");
    		ps.println("<objectID>" + ldfPageId + ".0.0." + objectId + "</objectID>");
    		ps.println("<comment>" + returnVal + " Subform</comment>");
    		ps.println("<actionType>ADD</actionType>");
    		ps.println("<displayOrder>" + objectId + "</displayOrder>");
    		ps.println("<name>" + returnVal + "</name>");
    		ps.println("<HTMLSource>" + ldfPageId +"_" + xhtml + ".xhtml</HTMLSource>");
    		ps.println("<pageSetID>" + ldfPageId + "</pageSetID>");
    		ps.println("</CustomSubForm>");
    		objectId++;
    	}
    	ps.println("</CustomSubForms>");
    	ps.println("</NEDSSImport>");
    	ps.close();
    	//once import files are generated, they need to be imported into NBS
		/*
    	try {

			//Note: For Production, comment out this as browser based import is performed.
			importUid = performBatchImport(f.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(importUid == null) {
	    		System.out.println(f.getAbsoluteFile() + " could not be Imported !!!");
			}

		}
		*/

    }

    private String getName(String pageSetId) {

    	String desc = pagesetMap.get(pageSetId) == null ? pageSetId :  pagesetMap.get(pageSetId).toString().trim();
    	desc = desc.replaceAll("/", "_").replaceAll(":","_");

    	return desc;

    }

    /**
     *
     * @param importSource
     * @return
     * @throws Exception
     */
    private Long performBatchImport(String importSource) throws Exception {

    	Long importUid = null;
    	CustomDataImportHome cdfHome = null;

		NedssUtils nedssUtils = new NedssUtils();
		String sBeanJndiName = JNDINames.CUSTOM_DATAIMPORT_EJB;
		cdfHome =(CustomDataImportHome) PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanJndiName),CustomDataImportHome.class);
		CustomDataImport cdfRemote = cdfHome.create();
		importUid = cdfRemote.performImport(importSource, securityObj);

		return importUid;
    }


    /**
     * Description: getMethodName returns getter value of subform shortname.
     * @param xhtmlConst
     * @return java.lang.String
     */
    private String getMethodName(String xhtmlConst) {
    	String temp = Character.toUpperCase(xhtmlConst.charAt(0)) + xhtmlConst.substring(1);
    	return "get" + temp;
    }
    /**
     *
     * @param ldfPageId
     * @return java.lang.String
     */
    private String getSubformList(String ldfPageId) {
        if (isValid())
          return (properties.getProperty(ldfPageId));
        return null;
      }

    /**
     *
     * @return
     */
    private static boolean isValid() {
        return valid;
    }

    /**
     * Description: Removes the existing xhtml and import dirs and reCreates them.
     * @return
     */
    private void removeAndCreateDirs(String loc) {

		File dir = new File(loc);
		if(dir.exists()) {
			recursiveDelete(dir);
			dir.delete();
			dir.mkdir();
		} else {
			dir.mkdir();
		}
	}

    /**
     * Description: Recursively deletes the folder contents(files)
     * @param dirPath
     */
    private void recursiveDelete (File dirPath) {
        String [] ls = dirPath.list ();

        for (int idx = 0; idx < ls.length; idx++) {
          File file = new File (dirPath, ls [idx]);
          if (file.isDirectory ())
            recursiveDelete (file);
          file.delete ();
        }
      }



    private void loadPageSetMappings() throws Exception {

    	SubformMetaDataHome sfHome = null;
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanJndiName = JNDINames.SUBFORM_METADATA_EJB;
		sfHome =(SubformMetaDataHome) PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanJndiName),SubformMetaDataHome.class);
		SubformMetaData sfRemote = sfHome.create();
		pagesetMap = sfRemote.getLDFPageSetData(securityObj);

    }

    private long getMaxImportVersion(int nbsRelease) throws Exception {

    	SubformMetaDataHome sfHome = null;
    	//long importVersionNbr = 0;
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanJndiName = JNDINames.SUBFORM_METADATA_EJB;
		sfHome =(SubformMetaDataHome) PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanJndiName),SubformMetaDataHome.class);
		SubformMetaData sfRemote = sfHome.create();
		maxImportVersionNbr = sfRemote.getMaxImportVersion(securityObj).longValue();
		String tempNbr = "";
		if(maxImportVersionNbr > 0) {
			tempNbr = nbsRelease + String.valueOf(maxImportVersionNbr).substring(3);
			try {
				maxImportVersionNbr = Long.parseLong(tempNbr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(maxImportVersionNbr == 0) {
			
			tempNbr = nbsRelease + "100000";
			try {
				maxImportVersionNbr = Long.parseLong(tempNbr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

    	return maxImportVersionNbr;
    }
    /**
     * checkPermission checks whether the user has Admin Import permissions
     * @param userName
     * @return boolean
     */
	private boolean checkPermission(String userName) {
		securityObj = getNBSSecurity(userName, "");
		if (securityObj == null) {
			return false;
		}
		return securityObj.getPermission(
			NBSBOLookup.SYSTEM,
			NBSOperationLookup.LDFADMINISTRATION);
	}
	/**
	 * getNBSSecurity method takes userName and/or password as params and returns NBSSecurityObject
	 * @param userName
	 * @param passWord
	 * @return NBSSecurityObj
	 */
	private NBSSecurityObj getNBSSecurity(String userName, String passWord) {

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

	private boolean importQuestionGroups(SubformImportUtil sfUtil, int nbsRelease) {

		try {
			//remove & create xml/xhtml dir's
			sfUtil.removeAndCreateDirs(SubformConstants.PROCESSED_XHTMLS_DIR);
			sfUtil.removeAndCreateDirs(SubformConstants.SUBFORMS_IMPORT_XML_DIR);
			sfUtil.removeAndCreateDirs(SubformConstants.SUCCESS_DIR);
			sfUtil.removeAndCreateDirs(SubformConstants.FAILURE_DIR);

			//Load all the conditions(pagesetIds) from Properties file and process Subforms for each of them.
			Enumeration<?> enumeration = properties.propertyNames();

			//Hashmap with pagesetIDs as keys and descriptions as values
			loadPageSetMappings();

			//retrieve the max ImportVersionNbr from db, if none set it to 100000
    		getMaxImportVersion(nbsRelease);


			while(enumeration.hasMoreElements()) {
				Object obj = enumeration.nextElement();
				String ldfPageId = obj.toString();
				String subforms = properties.getProperty(ldfPageId);

				System.out.println("Generating Question Groups for --- " + getName(ldfPageId));

				maxImportVersionNbr++;
				sfUtil.processSubForms(subforms, ldfPageId);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * @param args
	 * Description: Standalone utility to generate xml's and process xhtmls(search & replace)
	 */
	public static void main(String[] args) {

		//System.setProperty("nbs.dir", "C:/bea/nedssproject/nedssdomain/Nedss");

		if(args.length == 0) {
			System.out.println("Usage: CDFSubformImport <username> <NBS Release>");
			System.exit(0);
		} else if(args.length == 1) {
			System.out.println("Usage: CDFSubformImport <username> <NBS Release>");
			System.exit(0);			
		}
		String  nbsRelease = args[1].toString();
		if(nbsRelease != null && nbsRelease.length() < 3 && nbsRelease.length() >3) {
			System.out.println("Usage: <NBS Release> should be a 3 digit Release Number");
			System.exit(0);			
		}
		int releaseNbr = 0;
		try {
			releaseNbr = Integer.parseInt(nbsRelease);
		} catch (NumberFormatException e) {
			System.out.println("Usage: <NBS Release> should be a 3 digit Number only !!!");
			System.exit(0);			
		} 
		//if(releaseNbr)
		
		SubformImportUtil sfUtil = new SubformImportUtil();
		boolean success = false;

		boolean securityCheck = sfUtil.checkPermission(args[0]);
		if (!securityCheck) {
			System.out.println(
				"You do not have permission to run the utility.");
			System.exit(0);
		}

		success = sfUtil.importQuestionGroups(sfUtil, releaseNbr);
		if(success) {
			System.out.println("Successfully Completed !!!");
		}
	}
	public static void subFormImportScheduler(String userName, String nbsRelease) {

		//System.setProperty("nbs.dir", "C:/bea/nedssproject/nedssdomain/Nedss");

		if(userName==null || (userName!=null && userName.length() == 0)) {
			logger.fatal("Usage: CDFSubformImport <username> <NBS Release>");
			return;
		} else if(nbsRelease==null || (nbsRelease!=null && nbsRelease.length() == 0)) {
			logger.fatal("Usage: CDFSubformImport <username> <NBS Release>");
			return;
		}
		if(nbsRelease != null && nbsRelease.length() < 3 && nbsRelease.length() >3) {
			logger.fatal("Usage: <NBS Release> should be a 3 digit Release Number");
			return;		
		}
		int releaseNbr = 0;
		try {
			releaseNbr = Integer.parseInt(nbsRelease);
		} catch (NumberFormatException e) {
			logger.fatal("Usage: <NBS Release> should be a 3 digit Number only !!!");
			return;			
		} 
		//if(releaseNbr)
		
		SubformImportUtil sfUtil = new SubformImportUtil();
		boolean success = false;

		boolean securityCheck = sfUtil.checkPermission(userName);
		if (!securityCheck) {
			logger.fatal(
				"You do not have permission to run the utility.");
			return;
		}

		success = sfUtil.importQuestionGroups(sfUtil, releaseNbr);
		if(success) {
			logger.debug("Successfully Completed !!!");
		}
	}

}
