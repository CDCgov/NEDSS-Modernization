package gov.cdc.nedss.pagemanagement.pagebuilderbatchprocess;

/**
 * Description:  PublishAllPages is a Java Class called from PublishAllPages batch process
 *
 * Copyright:    Copyright (c) 2019
 * Company:      GDIT
 * @author       Fatima Lopez Calzado
 */

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.TransactionRolledbackException;


public class PublishAllPages
{
    static final LogUtils logger = new LogUtils(PublishAllPages.class.getName());

    public PublishAllPages()   
    {
    }

    /**
     * Called from the batch process publishAllPages in order to republish all the pages in published mode
     * @param args
     */
    public static void main(String[] args) 
    {
    	try{
    		
    		if (args.length < 1) {
				System.out.println("The user is a required field");
				System.out.println("Usage :  PublishAllPages.bat [USER_ID] to republish all the pages  OR  PublishAllPages.bat [USER_ID] [List of template names separated by # and between \"\"] to republish specific pages");
				System.exit(-1);
			}
    		
    		String userName = args[0];
    		String listOfTemplateNames = "";
    		
    		/*****************************************Validation user name************************************************************/
    		
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
    				userName, userName);
    		logger.debug(
    				"Successfully called nbsSecurityLogin: " +
    				nbsSecurityObj);

    		if(nbsSecurityObj==null){
    			System.out.println("The user doesn't exist");
    			System.exit(-1);
    		}
    		
    		boolean permission = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION);
    		if(permission==false){
       			System.out.println("The user doesn't have page management permissions");
       			System.exit(-1);
       		}
    		
    		/*****************************************************************************************************/
    		
    		
    		
    		if(args.length==2){
    			listOfTemplateNames= args[1];	
    		}
    		else{
    			 String userInput="";
    			 BufferedReader br = null;
    			do{
    				System.out.println("You have not indicated any page to republish, do you want to republish all of them? Y/N");
	    			br = new BufferedReader(new InputStreamReader(System.in)); 
				    userInput = br.readLine(); 
    			}while((userInput==null || userInput=="") || (!userInput.equalsIgnoreCase("n") &&  !userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("no") && !userInput.equalsIgnoreCase("yes")));
    				
    				
			    if(userInput!=null && userInput.trim().toLowerCase().equalsIgnoreCase("n")|| userInput!=null && userInput.trim().toLowerCase().equalsIgnoreCase("no")){
			    	System.out.println("Please, enter the list of template names to be republished separated by # and between \"\".");//They need to be on publish mode
			    	
			    	listOfTemplateNames = br.readLine(); 
			    	 
			    	if(listOfTemplateNames==null || listOfTemplateNames.trim().equalsIgnoreCase("")){
			    		System.out.println("The list of template names is empty. Finishing the batch process.");
			    		System.exit(-1);
			    	}
			    }else{
			     	System.out.println("You have chosen to republish all existing pages.");
					
				}
    		}
    		

    		//String mode = "Pages published from Batch Process: publishAllPages";//This is used to know where we're calling this method from (in case in the future we use some of the methods from a bulk action in the UI) and to put it in the version notes when publishing the page.
	    	 	
	    	publishAllPages(userName, listOfTemplateNames, nbsSecurityObj, msCommand);

    	}catch (Exception e){
    		
    		System.out.println("Exception from PublishAllPages: "+e.getMessage());
    	}
  
    }
   


    /**
    * publishAllPages: this method calls the EJB in order to republish all the already published pages.
    * @param mode: this is used as an indicator that is coming from the batch process and also as the verison notes while publishing the page
    * @throws RemoteException
    * @throws CreateException
    * @throws NEDSSAppException
    */
    
    
    private static void publishAllPages(String userName, String listOfTemplateName, NBSSecurityObj nbsSecurityObj, 	MainSessionCommand msCommand)
    {
    	try{
    		ArrayList<Object> waTemplateList = new ArrayList<Object>();
    		ArrayList<Object> arrResult = null;
    		String  sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;

	    		try
	    		{
	    			
	    			System.out.println("\nStarting publishAllPages Batch Process...\n");
	    	    	
	    			//Get all the pages in publish mode
	    			
	    			if (listOfTemplateName==null || listOfTemplateName.equalsIgnoreCase("")){//republish all the pages
		    			String sMethod2 = "getWaTemplateListToRepublish";
		
		    		
		    	        Object[] oParams = {};
		    	    	
		    			arrResult = (ArrayList<Object> )msCommand.processRequest( sBeanJndiName, sMethod2, oParams);
		    			
		    			if(arrResult!=null && arrResult.get(0)!=null)
		    				 waTemplateList=(ArrayList<Object>)arrResult.get(0);
	    			}else //republish only the pages indicated
	    			{
	    				//listOfTemplateName=listOfTemplateName.trim();
	    				listOfTemplateName="'"+listOfTemplateName+"'";
	    				
	    				
				    	
	    				listOfTemplateName=listOfTemplateName.replaceAll("\\#","','");//changing template1#template2#template3 to 'template1','template2','template3'
	    				
	    				String sMethod2 = "getWaUiTemplateUidsToRepublishByTemplateName";
	    		
		    	        Object[] oParams = {listOfTemplateName};
		    	    	
		    			arrResult = (ArrayList<Object> )msCommand.processRequest( sBeanJndiName, sMethod2, oParams);
		    	
		    			if(arrResult!=null && arrResult.get(0)!=null)
		    				 waTemplateList=(ArrayList<Object>)arrResult.get(0);
		    			
	    				
	    				
	    				
	    			}
	    			//------------------------------------------------------------------------------------
	    			
	    			 
	    			 
	    			 
	    			 
	    			String sMethod = "editAndPublishPage";
	    			int index=0;
	    			 //For each of the pages, edit and publish
	    			if(waTemplateList!=null){
	    				
	    				System.out.println("Number of pages to be republished: "+waTemplateList.size()+"\n");
	    				
	    				for(int i=0 ; i< waTemplateList.size(); i++){
	    				
		    				WaTemplateDT waTemplate = (WaTemplateDT)waTemplateList.get(i);
		        			
		    				String waTemplateUID = waTemplate.getWaTemplateUid()+"";
		    				String templateNm = waTemplate.getTemplateNm();
		    				
		        	        Object[] oParams2 = {waTemplateUID, userName};
		        	        index = i+1;
		        	       
		            		System.out.println(index+". Page to be republished: "+templateNm+", waTemplateUid: "+waTemplateUID);
		        	        arrResult = (ArrayList<Object> )msCommand.processRequest( sBeanJndiName, sMethod, oParams2);
		
			        	    if(arrResult!=null && arrResult.get(0)!=null && arrResult.get(0).equals("Failure"))
			    				System.out.println("\n\n**********************************************************\nThere is an error republishing the page!!\n**********************************************************\n\n");
			        	    else{
			        	    	String waTemplateUidNew = "";
			        	    	if(arrResult!=null && arrResult.get(0)!=null)
			        	    		waTemplateUidNew = (String)arrResult.get(0);
			    				System.out.println("The page has been republished successfully. New waTemplateUid: "+waTemplateUidNew+"\n");
			        	    }
	    				}
	    			
	    			}
	    	
	    			
	    			System.out.println("PublishAllPages Batch Process has been completed.\n");
	    			
			    }catch(CreateException e2){
			    	
			    	logger.error("CreateException thrown at PublishAllPages:" + e2.getCause()+e2.getMessage(), e2);
					e2.printStackTrace();
					//throw new EJBException(e2.getMessage(), e2);	
			    	
			    }
			   catch(NEDSSAppException e3){
			    	
			    	logger.error("NEDSSAppException thrown at PublishAllPages:" + e3.getCause()+e3.getMessage(), e3);
			    	e3.printStackTrace();
				//	throw new NEDSSAppException(e3.getMessage(), e3);	
			    	
			    } catch (TransactionRolledbackException e){
					e.printStackTrace();
					logger.error("Found Error in PublishAllPages:" + e.getMessage());
	
			
	    	}
	    		 catch (RemoteException  e){
	 				e.printStackTrace();
	 				logger.error("Found Error in PublishAllPages:" + e.getMessage());
	
	 		
	     	}
	    		
		    catch (Exception e){
					e.printStackTrace();
					logger.error("Found Error in PublishAllPages:" + e.getMessage());
	
			} 
	    		
    		
	    	}catch(Exception e){
	    		
	    		
	    		e.printStackTrace();
				logger.error("Found Error in PublishAllPages:" + e.getMessage());
	    		
	    	}
	    }
    
    
   
}