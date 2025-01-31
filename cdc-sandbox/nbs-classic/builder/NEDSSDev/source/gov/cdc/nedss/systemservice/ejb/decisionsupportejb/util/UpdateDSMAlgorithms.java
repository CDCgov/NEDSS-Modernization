package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import javax.rmi.PortableRemoteObject;

public class UpdateDSMAlgorithms {

	static final LogUtils logger = new LogUtils(UpdateDSMAlgorithms.class.getName());
	
	public static void main(String[] args) {
		System.out.println("Starting UpdateDSMAlgorithms Batch Job.............");
		try{
			if (args.length != 1) {
				System.out.println("The user is a required field");
				System.out.println("Usage :  UpdateDSMAlgorithms.bat [USER_ID] ");
				System.exit(-1);
			}
			String userName = args[0];
			NBSSecurityObj nbsSecurityObj = obtainNBSSecurityObject(userName);
			new DSMAlgorithmUtil().updateDSMAlgorithms(nbsSecurityObj);
			System.out.println("Successfully updated existing Algorithms...Process completed...");
		}catch(Exception ex){
			logger.fatal("Exception UpdateDSMAlgorithms "+ex.getMessage(), ex);
		}
		System.out.println("End UpdateDSMAlgorithms Batch Job.............");
	}

	private static NBSSecurityObj obtainNBSSecurityObject(String userName) throws Exception {
		NBSSecurityObj nbsSecurityObj = null;
		
		try{
			NedssUtils nedssUtils = new NedssUtils();
			
			String sBeanName = JNDINames.MAIN_CONTROL_EJB;
			MainSessionCommandHome  msCommandHome = (MainSessionCommandHome)PortableRemoteObject.narrow(nedssUtils.lookupBean(sBeanName), MainSessionCommandHome.class);
	        MainSessionCommand mainSessionCommand = msCommandHome.create();
	
			nbsSecurityObj = mainSessionCommand.nbsSecurityLogin(userName, userName);
		}catch(Exception ex){
			logger.fatal("Exception while creating nbsSecurityObj "+ex.getMessage(), ex);
			throw ex;
		}
		return nbsSecurityObj;
	}
}
