package gov.cdc.nedss.nnd.nndbatchprocess;
/**
 * Title:        NNDReceiverBatchProcessor
 * Description:  NNDReceiverBatchProcessor is a driver class to init NND message processor. It calles
 *               NNDMessageProcessorEJB through MainSessionCommandEJB to build and send NND message.
 *
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Ning Peng & Rick
 */
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.util.*;

import java.util.*;
import java.io.*;

import javax.rmi.PortableRemoteObject;


class NNDReceiverBatchProcessor
{

    static final LogUtils logger = new LogUtils(NNDReceiverBatchProcessor.class.getName());

    public NNDReceiverBatchProcessor()
    {
    }

    public static void main(String[] args)
    {

        NNDReceiverBatchProcessor receiverBatchProcessor = new NNDReceiverBatchProcessor();

            Boolean result = new Boolean(false);
            File fileDirectory = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY);
            String fileNames[] = fileDirectory.list();

        //return max rows of NotificationUids sequenced by notification_uid
        try
        {

            if(fileNames == null || fileNames.equals(null) || fileNames.length == 0)
            {
                System.out.println("***************** No Files ****************");
            }
            else
            {
                System.out.println("***********************************************");
                System.out.println("fileNames.length = " + fileNames.length);
                for(int i=0; i<fileNames.length; i++)
                {
                    System.out.println("fileNames[" + i + "] = " + fileNames[i]);
                }

                System.out.println("***********************************************");

                NBSSecurityObj nbsSecurityObj = null;
                NedssUtils nedssUtils = new NedssUtils();
                String sBeanName = JNDINames.MAIN_CONTROL_EJB;
                Object objref = nedssUtils.lookupBean(sBeanName);
                logger.debug("objref = " + objref.toString());

                MainSessionCommandHome home = (MainSessionCommandHome)PortableRemoteObject.narrow( objref,
                                                      MainSessionCommandHome.class);
                MainSessionCommand msCommand = home.create();
                logger.debug("msCommand = " + msCommand.getClass());
                logger.debug("About to call nbsSecurityLogin with msCommand; VAL IS: " + msCommand);
                nbsSecurityObj = (NBSSecurityObj)msCommand.nbsSecurityLogin("nnd_batch_to_cdc", "nnd_batch_to_cdc");
                logger.debug("Successfully called nbsSecurityLogin: " + nbsSecurityObj);

                //call NNDReceiverMessageProcessorEJB through MainSessionCommand
                String sBeanJndiName = "NNDReceiverMessageProcessorEJBRef";
                String sMethod = "processNNDMessage";
                Object[] oParams = {fileNames[0]};
                ArrayList<?> ar = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                result = (Boolean)ar.get(0);
		System.out.println("result = " + result);

                if(result.TRUE.booleanValue())
                {
                    System.out.println("***********************************************");
                    File fileComplete = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY + "/" + fileNames[0] );
		    File archiveOkDirectory = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY_COMPLETED);
		     if (!archiveOkDirectory.exists())
                        archiveOkDirectory.mkdirs(); /* make the directory if it does not exists */
                    fileComplete.renameTo(new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY_COMPLETED + "/" + fileNames[0]));
                    System.out.println("\nNNDReceiverMessageProcessor Batch Processor Successful !!!");
                }
                else
                {
                    System.out.println("***********************************************");
                    File fileComplete = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY + fileNames[0] );
		    File archiveFailDirectory = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY_FAILED);
		     if (!archiveFailDirectory.exists())
                        archiveFailDirectory.mkdirs(); /* make the directory if it does not exists */
                    fileComplete.renameTo(new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY_FAILED + fileNames[0]));
                    System.out.println("\nNNDReceiverMessageProcessor Batch Processor FAILED !!!");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("***********************************************");
            File fileComplete = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY + fileNames[0] );
            fileComplete.renameTo(new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY_FAILED + fileNames[0]));
            System.out.println("\nNNDReceiverMessageProcessor Batch Processor FAILED !!!");
            e.printStackTrace();
            logger.fatal("Error: " + e);
        }

    }
}