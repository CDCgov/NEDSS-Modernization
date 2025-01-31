package gov.cdc.nedss.nnd.nndbatchprocess;


/**
 * Title:        NNDBatchProcessor
 * Description:  NNDBatchProcessor is a driver class to init NND message processor. It calles
 *               NNDMessageProcessorEJB through MainSessionCommandEJB to build and send NND message.
 *
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Ning Peng
 */
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DateUtil;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;


public class NNDBatchProcessor
{
    private static String year = "";
    private static String week = "";
    static final LogUtils logger = new LogUtils(NNDBatchProcessor.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

    public NNDBatchProcessor()   
    {
    }

    public Integer getMaxNNDCount()
    {

        //call propertyUtil to get max rows from nedss.properties
        Integer maxRow = propertyUtil.getNNDMaxRow();
        logger.debug(
                "get maxRow from nedss.properties file: " +
                maxRow.intValue());

        return maxRow;
    }

    public static void main(String[] args)
    {
        NNDBatchProcessor batchProcessor = new NNDBatchProcessor();
        String mode =  args.length > 0 ? args[0] : null; 

        // get max rows from NEDSS.properties
        Integer maxRow = batchProcessor.getMaxNNDCount();

        if (maxRow == null)
        {
            logger.fatal("ERROR: can not get maxRow from property file!");
            return;
        }

        logger.debug(" Got maxRow from nedss.properties, maxRow= " + maxRow);

        //return max rows of NotificationUids sequenced by notification_uid
        try
        {
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
            logger.debug( "Successfully called nbsSecurityLogin: " + nbsSecurityObj);

            //call NNDMessageProcessorEJB through MainSessionCommand 
            String sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
            String sMethod = "getApprovedNotification";
            Object[] oParams = { maxRow };
            ArrayList<?>  resultArr = msCommand.processRequest(sBeanJndiName,
                                                           sMethod, oParams);
           
            Map<?, ?> uidMapList = (Map<?, ?>)resultArr.get(0);
            
            /* iterate through Collections of NotificationUids, for each NotificationUid
            it needs to call NNDMessageProcessorEJB through MainSessionCommandEJB to:
            1. updateNotificationToBatch()
            2. buildAndWriteNotificationMessage
            if it is successful, we populate Msg_out table and update notification table to COMPLETE
            if it failed, we only update notification table to Failure
            */
            resultArr = msCommand.processRequest(sBeanJndiName,
	                    sMethod, oParams);
			uidMapList = (Map<?,?>)resultArr.get(0);
			Collection<?> uidTransferList=(Collection<?>)uidMapList.get(NEDSSConstants.APPROVED_TRANFERRED_NOTIFICATION_UID_LIST);
			logger.debug("uidTransferList size is :"+uidTransferList.size());
			
			int uidTransferListSize =0;
			if(uidTransferList!=null && uidTransferList.size()>0)
			uidTransferListSize=uidTransferList.size();
			//process Transfer MEssage third
			logger.debug("There exisits "+uidTransferListSize+" Transfer messages to be processed ");
			processNotificationMessage(uidTransferList, mode);
			logger.debug("Transfer messages processed");

            //persisting MsgOutErrorLogDT values in the NNDActivityLog
            resultArr = msCommand.processRequest(sBeanJndiName,
                                                           sMethod, oParams);
            uidMapList = (Map<?,?>)resultArr.get(0);
            Collection<?>  uidShareList=(Collection<?>)uidMapList.get(NEDSSConstants.APPROVED_SHARED_NOTIFICATION_UID_LIST);
            logger.debug("uidShareList size is :"+uidShareList.size());
           
            //process Share Message first
            int uidShareListSize =0;
            if(uidShareList!=null && uidShareList.size()>0)
            	uidShareListSize=uidShareList.size();
          //process Transfer MEssage third
            logger.debug("There exisits "+uidShareListSize+" share  messages to be processed ");
            processNotificationMessage(uidShareList, mode);
            logger.debug("Share  messages processed");
            //process Share Messages second
            
            Collection<?> uidNNDList=(Collection<?>)uidMapList.get(NEDSSConstants.APPROVED_NND_NOTIFICATION_UID_LIST);
            logger.debug("uidNNDList size is :"+uidNNDList.size());
            int uidNNDListSize =0;
            if(uidNNDList!=null && uidNNDList.size()>0)
            	uidNNDListSize=uidNNDList.size();
          //process Transfer MEssage third
            logger.debug("There exisits "+uidNNDListSize+" Transfer messages to be processed ");
            processNotificationMessage(uidNNDList, mode);
            logger.debug("NND messages processed");
          
            //persisting MsgOutErrorLogDT values in the NNDActivityLog
             try
             {
               sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
               sMethod = "propagateMsgOutError";
               Object[] oParams4 = { };
               ArrayList<?>  resultArr4 = msCommand.processRequest(sBeanJndiName, sMethod, oParams4);
             }
             catch (Exception e4)
             {
               e4.printStackTrace();
               logger.fatal(e4.getMessage() + "FATAL: Can't not insert row in NND_Activity_log table!!! ");
             }
        }
        catch (Exception e3)
        {
            e3.printStackTrace();
            logger.fatal("Error: "+e3.getMessage());
        }

        System.out.println("NNDMessage Batch Processor Complete !!!");
    }
    public static void runNNDBatchProcessor()
    { 

        NNDBatchProcessor batchProcessor = new NNDBatchProcessor();

        // get max rows from NEDSS.properties
        Integer maxRow = batchProcessor.getMaxNNDCount();

        if (maxRow == null)
        {
            logger.fatal("ERROR: can not get maxRow from property file!");

            return;
        }

        logger.debug(" Got maxRow from nedss.properties, maxRow= " + maxRow);

        //return max rows of NotificationUids sequenced by notification_uid
        try
        {

            NBSSecurityObj nbsSecurityObj = null;
            NedssUtils nedssUtils = new NedssUtils();
            String sBeanName = JNDINames.MAIN_CONTROL_EJB;
            Object objref = nedssUtils.lookupBean(sBeanName);
            //##!! System.out.println("objref = " + objref.toString());

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

            //call NNDMessageProcessorEJB through MainSessionCommand
            String sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
            String sMethod = "getApprovedNotification";
            Object[] oParams = { maxRow };
            
            
            ArrayList<?>  resultArr = msCommand.processRequest(sBeanJndiName,
                    sMethod, oParams);
            Map<?,?> uidMapList = (Map<?,?>)resultArr.get(0);
            
            /* iterate through Collections of NotificationUids, for each NotificationUid
            it needs to call NNDMessageProcessorEJB through MainSessionCommandEJB to:
            1. updateNotificationToBatch()
            2. buildAndWriteNotificationMessage
            if it is successful, we populate Msg_out table and update notification table to COMPLETE
            if it failed, we only update notification table to Failure
            */
            resultArr = msCommand.processRequest(sBeanJndiName,
	                    sMethod, oParams);
			uidMapList = (Map<?,?>)resultArr.get(0);
			Collection<?> uidTransferList=(Collection<?>)uidMapList.get(NEDSSConstants.APPROVED_TRANFERRED_NOTIFICATION_UID_LIST);
			logger.debug("uidTransferList size is :"+uidTransferList.size());
			
			int uidTransferListSize =0;
			if(uidTransferList!=null && uidTransferList.size()>0)
			uidTransferListSize=uidTransferList.size();
			//process Transfer MEssage third
			logger.debug("There exisits "+uidTransferListSize+" Transfer messages to be processed ");
			processNotificationMessage(uidTransferList);
			logger.debug("Transfer messages processed");


//persisting MsgOutErrorLogDT values in the NNDActivityLog
            resultArr = msCommand.processRequest(sBeanJndiName,
                                                           sMethod, oParams);
            uidMapList = (Map<?,?>)resultArr.get(0);
            Collection<?> uidShareList=(Collection<?>)uidMapList.get(NEDSSConstants.APPROVED_SHARED_NOTIFICATION_UID_LIST);
            logger.debug("uidShareList size is :"+uidShareList.size());
           
            //process Share Message first
            int uidShareListSize =0;
            if(uidShareList!=null && uidShareList.size()>0)
            	uidShareListSize=uidShareList.size();
          //process Transfer MEssage third
            logger.debug("There exisits "+uidShareListSize+" share  messages to be processed ");
            processNotificationMessage(uidShareList);
            logger.debug("Share  messages processed");
            //process Share Messages second
            
            Collection<?> uidNNDList=(Collection<?>)uidMapList.get(NEDSSConstants.APPROVED_NND_NOTIFICATION_UID_LIST);
            logger.debug("uidNNDList size is :"+uidNNDList.size());
            int uidNNDListSize =0;
            if(uidNNDList!=null && uidNNDList.size()>0)
            	uidNNDListSize=uidNNDList.size();
          //process Transfer MEssage third
            logger.debug("There exisits "+uidNNDListSize+" Transfer messages to be processed ");
            processNotificationMessage(uidNNDList);
            logger.debug("NND messages processed");
            
             //persisting MsgOutErrorLogDT values in the NNDActivityLog
             try
             {
               sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
               sMethod = "propagateMsgOutError";
               Object[] oParams4 = { };
               ArrayList<?>  resultArr4 = msCommand.processRequest(sBeanJndiName, sMethod, oParams4);
             }
             catch (Exception e4)
             {
               e4.printStackTrace();
               logger.fatal(e4.getMessage() +
                            "FATAL: Can't not insert row in NND_Activity_log table!!! ");
             }
        }
        catch (Exception e3)
        {
            e3.printStackTrace();
            logger.fatal("Error: "+e3.getMessage());
        }
        System.out.println("NNDMessage Batch Processor Complete !!!");
    }
    
    private static void processNotificationMessage(Collection<?> uidList ) throws RemoteException, CreateException, NEDSSAppException
    {
        processNotificationMessage(uidList, null);
    }
    
    private static void processNotificationMessage(Collection<?> uidList, String mode) throws RemoteException, CreateException, NEDSSAppException
    {
        boolean foundCsr = false;
    	for (Iterator<?> anIterator = uidList.iterator(); anIterator.hasNext();)
    	{
    	    foundCsr = false;
    		NBSSecurityObj nbsSecurityObj = null;
    		NedssUtils nedssUtils = new NedssUtils();
    		String sBeanName = JNDINames.MAIN_CONTROL_EJB;
    		Object objref = nedssUtils.lookupBean(sBeanName);
    		//##!! System.out.println("objref = " + objref.toString());

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
    		NotificationDT notifDT = null;
//  		for testing, only use one notification_uid
//  		Long notificationUid = new Long("470077903");

    		Long notificationUid = (Long)anIterator.next();
    		//Long notificationUid = new Long(312069240);
    		System.out.println("Got notificationUid = " +  notificationUid);

    		try
    		{
    			//call NNDMessageProcessorEJB through MainSessionCommand
    			String  sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
    			String sMethod = "updateNotificationRecordToBatch";

    			Object[] oParams1 = { notificationUid };
    			ArrayList<?>  resultArr1 = msCommand.processRequest(
    					sBeanJndiName, sMethod,
    					oParams1);
    			logger.debug(
    					"updateNotificationRecordToBatch, notificationUid = " +
    					notificationUid);
    			notifDT = (NotificationDT)resultArr1.get(0);

    			/*  
    			 * if Notification.cd = 'NOTF", call buildAndWriteInvesigationMessage in EJB;
                 * if Notification.cd = 'NSUM', call buildAndWriteSummaryNotificationMessage in EJB
    			 */
    			
    			String notificationCd = notifDT.getCd();
    			if(notifDT == null || notifDT.getCd() ==null)
    			{
    				System.out.println("notification cd is null, can not distiguish notification is a individual or a summary !");
    				return;
    			}
    			 
                System.out.println("notificationDT.cd = " + notificationCd);
                Object[] oParams2 = { notifDT };

    			if(notificationCd.equals(NNDConstantUtil.NOTIFICATION_CD_NOTF) || 
    					notificationCd.equals(NEDSSConstants.CLASS_CD_EXP_NOTF) || 
    					notificationCd.equals(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC) ||
    					notificationCd.equals(NEDSSConstants.CLASS_CD_SHARE_NOTF) ||
    					notificationCd.equals(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC) )
    			{
    				sMethod = "buildAndWriteInvestigationMessage";
    			}
    			else if(notificationCd.equals(NNDConstantUtil.NOTIFICATION_CD_NSUM) ||notificationCd.equals(NEDSSConstants.AGGREGATE_NOTIFICATION_CD) )
    			{
    				sMethod = "buildAndWriteSummaryNotificationMessage";
    			}
    			else
    			{
    				System.out.println("Notification is NOT  investigation/summary/CSR");
    				return;
    			} 
    			ArrayList<?>  resultArr2 = msCommand.processRequest( sBeanJndiName, sMethod, oParams2);
    			System.out.println( "Sent Notification, uid = " + notificationUid); 
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			logger.error(
    					"Found Error in sending notification message. Update notification table as failure condition." + e.getMessage());

    			//update record_status_cd in notification_table as "MSG_FAIL"
    			try
    			{

    				//temporary fix for dataconcurrence issue.
    				/*                        notifDT.setVersionCtrlNbr(new Integer(
                                                 notifDT.getVersionCtrlNbr()
                      .intValue() + 1));
    				 */
    				String  sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
    				String sMethod = "updateNotificationFailure";

    				Object[] oParams3 = { notifDT };
    				ArrayList<?> resultArr3 = msCommand.processRequest(sBeanJndiName, sMethod,oParams3);
    			}
    			catch (Exception e2)
    			{
    				e2.printStackTrace();
    				logger.fatal(
    						e2.getMessage() +
    						"FATAL: Can't not update notification table!!! " +
    						" notificationuid = " + notificationUid);
    			}

    			//persist NND_Activity_log
    			try
    			{

    				String sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
    				String sMethod = "persistNNDActivityLog";

    				Long msgOutMessageUid = null; // dummy uid
    				Object[] oParams3 = { notifDT, NNDConstantUtil.NND_ACTIVITY_RECORD_STATUS_CD_DEV_ERROR, NNDConstantUtil.NND_ACTIVITY_STATUS_CD_DEV_ERROR, msgOutMessageUid, e.getMessage() };
    				ArrayList<?>  resultArr3 = msCommand.processRequest(
    						sBeanJndiName, sMethod,
    						oParams3); 
    			}
    			catch (Exception e3)
    			{
    				e3.printStackTrace();
    				logger.fatal(
    						e3.getMessage() +
    						"FATAL: Can't not update NND_Activity_log table!!! " +
    						" notificationuid = " + notificationUid);
    			} 
    		} 
    		
    	} //end of for loop
    } 
    
    private static boolean allowToWrite(String mode, NotificationDT notf) throws NEDSSAppException
    {
        boolean isAllowed = true; 
        try{
	        if( mode == null || "".equalsIgnoreCase(mode))
	            return isAllowed;
	        RulesEngineUtil util = new RulesEngineUtil();
	        int [] wY = util.CalcMMWR( DateUtil.getToday("MM/dd/yyyy") );
	        if(wY != null && wY.length > 0) 
	        {
	           week = String.valueOf(wY[0]) ;
	           year = String.valueOf(wY[1]) ;
	        }
	        
	        System.out.println( " Current MMWR Week = " + week + ", Year=" + year + "  Notification MMWR Week = " + notf.getMmwrYear() );
	        if( "WEEKLY".equalsIgnoreCase(mode))
	        {
	            if( year.equals(notf.getMmwrYear()) && week.compareTo(notf.getMmwrWeek()) > 0 )
	            {
	                return isAllowed;
	            }
	        }
	        else if( "CURRENT_YEAR".equalsIgnoreCase(mode))
	        {
	            if( year.equals(notf.getMmwrYear()))
	            {
	                return isAllowed;
	            }
	        }
	        else if( "PRIOR_YEAR".equalsIgnoreCase(mode))
	        {
	            if( year.compareTo(notf.getMmwrYear()) == 1)
	            {
	                return isAllowed;
	            }
	        }
        }catch(Exception ex){
			logger.fatal("Exception: " + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
        return false;
    }
    
}