package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.AutoLabInvDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.rmi.PortableRemoteObject;


  

public class EdxLabToInvConverter {
	static final LogUtils logger = new LogUtils(EdxLabToInvConverter.class.getName());

	public static void main(String[] args)
	{
		System.out.println("NbsLabToInvConverter batch process  started");
		
		//TODO correct this 
		try
		{
			String processType = null;
			String userName = null;
			String labId = null;
			String conditionCode=null;
			//Check for the user input
			System.out.println("args length is :="+args.length );
			if((args.length == 2) || (args.length == 3)) {
				userName = args[0];
				processType = args[1];
				System.out.println("userName:-"+userName);
				System.out.println("processType:-"+processType);
				System.out.println("args length is :="+args.length );
			}else{
				System.out.println("***************************************************************************************"+
									"\n***Usage :  NbsLabToInvConverter [INV_CONVETER_USER_ID] [populate/labId/Condition] ***"+
									"\n*** where \"populate\" will get map all the labs to conditions for next process  ***"+
									"\n*** \"LabId\" will process one laboratory uid and                                ***" +
									"\n*** \"Condition\" will process all observations tied to a condition.             ***"+
									"\n***************************************************************************************"); 
				System.exit(-1); 
			}
			if(processType != null){
				if(processType.equalsIgnoreCase("populate") ||processType.equalsIgnoreCase("all")|| 	processType.equalsIgnoreCase("Condition") ||processType.equalsIgnoreCase("labId")){
					//ok
				}
			}else {
				System.out.println("Usage :  NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [populate/labId/Condition]"); 
				System.exit(-1); 
			}
			if(processType.equalsIgnoreCase("Condition")){
				try {
					conditionCode= args[2];
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Usage :  NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [populate/labId/Condition] [observationUid/ConditionCode]"); 
				}
				if(conditionCode!=null){
					if(conditionCode.trim().length()==0)
						System.out.println("Usage :  NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [populate/labId/Condition] [observationUid/ConditionCode]"); 
				}else
					System.out.println("Usage :  NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [populate/labId/Condition] [observationUid/ConditionCode]"); 
			}else if(processType.equalsIgnoreCase("labId")){
				try {
					labId= args[2];
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Usage :  NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [populate/labId/Condition] [observationUid/ConditionCode]"); 
				}
				if(labId!=null){
					if(labId.trim().length()==0)
						System.out.println("Usage :  NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [populate/labId/Condition] [observationUid/ConditionCode]"); 
				}else
					System.out.println("Usage :  NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [populate/labId/Condition] [observationUid/ConditionCode]"); 
			}else if(processType.equalsIgnoreCase("all")){
				
			}			
			
			if(processType.equalsIgnoreCase("populate")){
				processLabToInv(userName);
			}
			else if(processType.equalsIgnoreCase("labId") || processType.equalsIgnoreCase("Condition")|| processType.equalsIgnoreCase("all")){
				pamELRConverter(userName,labId, conditionCode, processType);
			}
			else{
				System.out.println("NbsLabToInvConverter failed:Usage: NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [production/prerun] ");
				throw new Exception("Batch file Usage: NbsLabToInvConverter.bat [PAM_IMPORTER_USER_ID] [production/prerun]");
			}
			 
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.fatal("Error: " + e.getMessage());
		}

		System.out.println("NbsLabToInvConverter Complete!!!");
	} 
	
	
	public static void processLabToInv(String userName){
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);
		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			String sBeanJndiName = "NbsPHCRDocumentEJB";
			String sMethod = "labToConditionMapping";
			Object[] oParams = new Object[] {};
			ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			Collection<Object> coll = (ArrayList<Object>)arr.get(0);
			System.out.println("There are "+ coll.size()+" labs that can be converted from lab(s) to Investigation(s).");
			Iterator<Object> it = coll.iterator();
			while(it.hasNext()){
				AutoLabInvDT aLabInvDT = (AutoLabInvDT)it.next();
				System.out.println("The "+aLabInvDT.getOrderObservationLocalId()+" is mapped to " +aLabInvDT.getConditionCode()+ " because of resulted Test uid "+aLabInvDT.getResultObservationUid());
			}
		} catch (Exception e) {
			System.out.println("ERROR raised"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void pamELRConverter(String userName, String labId, String ConditionCode, String processType){
		NBSSecurityObj nbsSecurityObj = null;
		NedssUtils nedssUtils = new NedssUtils();
		//Create a MainSessionCommand bean instance
		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
		Object objref = nedssUtils.lookupBean(sBeanName);

		try {
			MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(  objref, MainSessionCommandHome.class);
			MainSessionCommand msCommand = home.create();

			logger.info("msCommand = " + msCommand.getClass());

			logger.info("About to call nbsSecurityLogin with msCommand; VAL IS: " +msCommand);
			
			
			nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin( userName, userName);

			Map<Object, Object> map=new HashMap<Object, Object>();
			if(processType!=null && processType.equalsIgnoreCase("all"))
				map.put("all", "all");
			else if(labId!=null && labId.trim().length()>0 )
				map.put("uid", labId);
			else if(ConditionCode!=null && ConditionCode.trim().length()>0 )
				map.put("Condition", ConditionCode);
				
			logger.debug("Successfully called nbsSecurityLogin: " +nbsSecurityObj);
			String sBeanJndiName = "NbsPHCRDocumentEJB";
			String sMethod = "ConvertLabToInvestigation";
			Object[] oParams = new Object[] {map};
			ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			Collection<Object> coll = (ArrayList<Object>)arr.get(0);
			System.out.println("There are "+ coll.size()+" labs that can be converted from lab(s) to Investigation(s).");
			Iterator<Object> it = coll.iterator();
			while(it.hasNext()){
				AutoLabInvDT aLabInvDT = (AutoLabInvDT)it.next();
				if(aLabInvDT.getComment().equals("Error")){
					System.out.println("******************************************************************");
					System.out.println("ERROR:-The "+aLabInvDT.getOrderObservationLocalId()+" isalready mapped to " +aLabInvDT.getConditionCode()+ " because of resulted Test uid "+aLabInvDT.getResultObservationUid());
					System.out.println("******************************************************************");
				}else{
					System.out.println("******************************************************************");
					System.out.println("The "+aLabInvDT.getOrderObservationLocalId()+" is mapped to " +aLabInvDT.getConditionCode()+ " because of resulted Test uid "+aLabInvDT.getResultObservationUid());
					System.out.println("Associated comments:-"+aLabInvDT.getComment());
					System.out.println("******************************************************************");
				}
			}
		}catch (Exception e) {
			System.out.println("ERROR raised"+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	


	
	
}
