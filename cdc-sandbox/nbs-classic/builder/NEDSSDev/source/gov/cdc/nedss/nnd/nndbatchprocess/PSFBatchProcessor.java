package gov.cdc.nedss.nnd.nndbatchprocess;


/**
 * Title:        PSFBatchProcessor
 * Description:  PSFBatchProcessor is a Java Class called from PopulatePSFTables.bat batch process and it calls
 * the PopulatePSFTables_sp stored procedure in order to populate the PSF table, like PSF_SESSION.
 * It writes in the NBS_MSGOUTE.Activity_log table the last time the process was run
 *
 * Copyright:    Copyright (c) 2018
 * Company:      GDIT
 * @author       Fatima Lopez Calzado
 */

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.PSFDAOImpl;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.TransactionRolledbackException;


public class PSFBatchProcessor
{
    static final LogUtils logger = new LogUtils(PSFBatchProcessor.class.getName());

    public PSFBatchProcessor()   
    {
    }

    
    public static void main(String[] args) 
    {
    	try{
    	String incrementalOrFull = "Incremental";
    	
    	if (args.length == 0) 
    		
    		System.out.println("Option = Incremental by default");  
    	else
	    	if (args.length == 1) {
	    		incrementalOrFull = args[0];
	    		if(!incrementalOrFull.equalsIgnoreCase("full") && !incrementalOrFull.equalsIgnoreCase("incremental")){
	    			System.out.println("Incorrect parameter. Usage :  PopulatePSFTables.bat [Incremental/Full] or PopulatePSFTables.bat (defaulted to Incremental)");  
	        		System.exit(-1); 
	    		}	
	    	}    	
    		
    	processPopulatePSFTables(incrementalOrFull);

       
    	}catch (Exception e){
    		
    		System.out.println("Exception");
    	}
  
    }
   


    /**
    * processPopulatePSFTables: this method calls the EJB in order to populate the PSF tables.
    * @param incrementalOrFull: incremental or full
    * @throws RemoteException
    * @throws CreateException
    * @throws NEDSSAppException
    */
    
    
    private static void processPopulatePSFTables(String incrementalOrFull)
    {
    	try{
    		NBSSecurityObj nbsSecurityObj = null;
    		NedssUtils nedssUtils = new NedssUtils();
    		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
    		Object objref = nedssUtils.lookupBean(sBeanName);
    	
    		MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow(
    				objref,
    				MainSessionCommandHome.class);
    		MainSessionCommand msCommand = home.create();
    		logger.info("msCommand = " + msCommand.getClass());
    		logger.info(
    				"About to call nbsSecurityLogin with msCommand; VAL IS: " +
    				msCommand);
    		nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin(
    				"nnd_batch_to_cdc", "nnd_batch_to_cdc");
    		logger.debug(
    				"Successfully called nbsSecurityLogin: " +
    				nbsSecurityObj);

    		try
    		{
    			String  sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
    			
                Object[] oParams2 = { incrementalOrFull };

    			String sMethod = "populatePSFTables";
    			
    			ArrayList<Object> arrResult = (ArrayList<Object> )msCommand.processRequest( sBeanJndiName, sMethod, oParams2);
    	
    			if(arrResult!=null && arrResult.get(0)!=null && arrResult.get(0).equals("Failure"))
					System.out.println("PSF Batch Processor Complete with errors");
				else
					System.out.println("PSF Batch Processor Complete !!!");
				 
				 
    			
    			
		    }catch(CreateException e2){
		    	
		    	logger.error("CreateException thrown at processPopulatePSFTables:" + e2.getCause()+e2.getMessage(), e2);
				e2.printStackTrace();
				//throw new EJBException(e2.getMessage(), e2);	
		    	
		    }
		   catch(NEDSSAppException e3){
		    	
		    	logger.error("NEDSSAppException thrown at processPopulatePSFTables:" + e3.getCause()+e3.getMessage(), e3);
		    	e3.printStackTrace();
			//	throw new NEDSSAppException(e3.getMessage(), e3);	
		    	
		    } catch (TransactionRolledbackException e){
				e.printStackTrace();
				logger.error("Found Error in processPopulatePSFTables:" + e.getMessage());

		
    	}
    		 catch (RemoteException  e){
 				e.printStackTrace();
 				logger.error("Found Error in processPopulatePSFTables:" + e.getMessage());

 		
     	}
    		
	    catch (Exception e){
				e.printStackTrace();
				logger.error("Found Error in processPopulatePSFTables:" + e.getMessage());

		} 
    		
    	}catch(Exception e){
    		
    		
    		e.printStackTrace();
			logger.error("Found Error in processPopulatePSFTables:" + e.getMessage());
    		
    	}
    }
    
}