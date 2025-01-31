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
import gov.cdc.nedss.systemservice.scheduler.undocdfimport.undocdfimportscheduler.UndoCdfImportScheduler;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportHome;
import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImport;

/*
 import org.apache.xalan.xslt.*;
import org.apache.xalan.*;
 */

public class LDFUndoImportEJBClient {
	static final LogUtils logger = new LogUtils(LDFUndoImportEJBClient.class.getName());
	private NBSSecurityObj securityObj = null;
	/**
	 * Constructor
	 * @throws java.lang.Exception
	 */
	public LDFUndoImportEJBClient() {
	}

	public static void main(String[] args) throws Exception {
		try {
			String output = null;
			String versionNbr = null;
			boolean success = false;

			if(args.length == 0) {
				System.out.println("Usage: UndoCDFSubformImport <username> or: UndoCDFSubformImport <username> <version number>");
				System.exit(0);
			}

			if (args.length == 2)
				versionNbr = args[1];

			LDFUndoImportEJBClient ldf = new LDFUndoImportEJBClient();

			boolean securityCheck = ldf.checkPermission(args[0]);
			if (!securityCheck) {
				System.out.println(
					"You do not have permission to run the utility.");
				System.exit(0);
			}
			success = ldf.undoImport(versionNbr);
			
			if(success) {			
				System.out.println("Successfully Completed !!!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}

	private boolean checkPermission(String userName) {
		try {
			securityObj = getNBSSecurity(userName, "");
			if (securityObj == null) {
				return false;
			}
			return securityObj.getPermission(
				NBSBOLookup.SYSTEM,
				NBSOperationLookup.LDFADMINISTRATION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	private boolean undoImport(String versionNbr) {
		try {
			CustomDataImportHome cdfHome = null;
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanJndiName = JNDINames.CUSTOM_DATAIMPORT_EJB;
			cdfHome =
				(CustomDataImportHome) PortableRemoteObject.narrow(
					nedssUtils.lookupBean(sBeanJndiName),
					CustomDataImportHome.class);
			CustomDataImport cdfRemote = cdfHome.create();
			if (versionNbr != null)
				cdfRemote.undoImport(Long.valueOf(versionNbr), securityObj);
			else
				cdfRemote.undoLastImport(securityObj);
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			return false;
		}
		return true;

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
			logger.fatal(" Error in calling mainsessionCommand  " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return securityObj;
	}
	
	public static void ldfUndoImportEJBClientProcessor(String username, String versionNbr) throws Exception {
		try {
			String output = null;
			boolean success = false;

			if(username==null || (username!=null && username.length()==0))
				logger.fatal("Usage: UndoCDFSubformImport <username> or: UndoCDFSubformImport <username> <version number>");
			
			LDFUndoImportEJBClient ldf = new LDFUndoImportEJBClient();

			boolean securityCheck = ldf.checkPermission(username);
			if (!securityCheck) {
				System.out.println(
					"You do not have permission to run the utility.");
				System.exit(0);
			}
			success = ldf.undoImport(versionNbr);
			
			if(success) {			
				System.out.println("Successfully Completed !!!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}

}
