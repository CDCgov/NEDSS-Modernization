//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessor\\helpers\\NNDObjectMarshaller.java
package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.messageframework.notificationmastermessage.Investigation_Message;
import gov.cdc.nedss.messageframework.notificationmastermessage.NNDEvent;
import gov.cdc.nedss.messageframework.notificationmastermessage.NedssEvent;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.nnd.vo.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;


public class NNDObjectMarshaller
{

    /**
    * @roseuid 3D57A39E0138
    */
    public NNDObjectMarshaller()
    {
    }

    static final LogUtils logger = new LogUtils(NNDObjectMarshaller.class.getName());
    private Investigation_Message investigationMsg = new Investigation_Message();
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

    /**
    * @param messageProcessorVO
    * @param nbsSecurityObj
    * @return String
    * @roseuid 3D579B780251
    */
    public String marshallNNDObjects(MessageProcessorVO messageProcessorVO,
                                     NBSSecurityObj nbsSecurityObj)
                              throws NNDException
    {

        try
        {

            ArrayList<Object>  labresultProxyVOCollection  = null;
            ArrayList<Object> vaccinationProxyVOCollection  = null;
            ArrayList<Object> morbidityProxyVOCollection  = null;
           Iterator<Object>  itr = null;
            labresultProxyVOCollection  = (ArrayList<Object> )messageProcessorVO.getTheLabResultProxyVOCollection();
            vaccinationProxyVOCollection  = (ArrayList<Object> )messageProcessorVO.getTheVaccinationProxyVOCollection();
            morbidityProxyVOCollection  = (ArrayList<Object> )messageProcessorVO.getTheMorbidityProxyVOCollection();

            if (NNDConstantUtil.notNull(messageProcessorVO.getTheSummaryReportProxyVO()))
            {
                System.out.println(" You are in Summary from Object ");

                NNDMarshallerSummaryReport summaryReportMarshaller =
                        new NNDMarshallerSummaryReport();
                addToInvestigationMsgInternal(
                        (NedssEvent)summaryReportMarshaller.marshallSummaryReport(messageProcessorVO.getTheSummaryReportProxyVO()),
                        NNDConstantUtil.NND_EVENT_TYPE_SUMMARY_REPORT);
                System.out.println(
                        " \n\n You are in Summary from Object got the message from Summary report ");
            }

            if (NNDConstantUtil.notNull(messageProcessorVO.getTheInvestigationProxyVO()))
            {

                NNDMarshallerInvestigation investigationMarshaller =
                        new NNDMarshallerInvestigation();
                    // modified signature to include NBSSecurityObj to extract scoping entitiy uids
                addToInvestigationMsgInternal(
                        (NedssEvent)investigationMarshaller.marshallInvestigation(messageProcessorVO.getTheInvestigationProxyVO(), nbsSecurityObj),
                        NNDConstantUtil.NND_EVENT_TYPE_INVESTIGATION);
            }

            {

                if (labresultProxyVOCollection  != null)
                {
                    System.out.println("The lab result is not null");
                    itr = labresultProxyVOCollection.iterator();

                    while (itr.hasNext())
                    {

                        Object obj = itr.next();

                        if (NNDConstantUtil.notNull(obj))
                        {
                            System.out.println(
                                    "Inside the loop the object is =  " +
                                    obj.toString());

                            NNDMarshallerLabReport labReportMarshaller =
                                    new NNDMarshallerLabReport();
                         // modified signature to include NBSSecurityObj to extract scoping entitiy uids
                            addToInvestigationMsgInternal(
                                    (NedssEvent)labReportMarshaller.marshallLabReport(
                                            (LabResultProxyVO)obj, nbsSecurityObj),
                                    NNDConstantUtil.NND_EVENT_TYPE_LABREPORT);
                        }
                    }
                }
            }

            itr = null;
            {

                if (morbidityProxyVOCollection  != null)
                {
                    System.out.println("The morb is not null");
                    itr = morbidityProxyVOCollection.iterator();

                    while (itr.hasNext())
                    {

                        Object obj = itr.next();

                        if (NNDConstantUtil.notNull(obj))
                        {
                            System.out.println(
                                    "Inside the loop the object is =  " +
                                    obj.toString());

                            NNDMarshallerMorbReport morbReportMarshaller =
                                    new NNDMarshallerMorbReport();
                            addToInvestigationMsgInternal(
                                    (NedssEvent)morbReportMarshaller.marshallMorbReport(
                                            (MorbidityProxyVO)obj),"MorbReport");
                        }
                    }
                }
            }

            itr = null;

            if (vaccinationProxyVOCollection  != null)
            {
                itr = vaccinationProxyVOCollection.iterator();

                while (itr.hasNext())
                {

                    Object obj = itr.next();

                    if (NNDConstantUtil.notNull(obj))
                    {

                        NNDMarshallerVaccination vaccinationMarshaller =
                                new NNDMarshallerVaccination();
                      // modified signature to include NBSSecurityObj to extract scoping entitiy uids
                        addToInvestigationMsgInternal(
                                (NedssEvent)vaccinationMarshaller.marshallVaccination(
                                        (VaccinationProxyVO)obj, nbsSecurityObj),
                                NNDConstantUtil.NND_EVENT_TYPE_VACCINATION);
                    }
                }
            }

            //pass the notificationDT to retrieve a nedssEvent object
            NNDMarshallerNotification nndMarshallerNot = new NNDMarshallerNotification();
            NedssEvent nedssEvent = (NedssEvent) nndMarshallerNot.marshallNotification(messageProcessorVO.getTheNotificationDT());
            this.addToInvestigationMsgInternal(nedssEvent,
                                               NNDConstantUtil.NND_EVENT_TYPE_NOTIFICATION);

            /* populate the message header and add it to the message */
            NNDMarshallerHeader headerMarshaller = new NNDMarshallerHeader();
            investigationMsg.setMessageHeader(headerMarshaller.marshallHeader(messageProcessorVO.getTheNotificationDT()));

            /* finished populating the message..                           */
            /* call Castor to actually generate the XML from the structure */
            StringWriter stringWriter = new StringWriter();
            Marshaller.marshal(investigationMsg, stringWriter);

            /* store in a large string */
            String msgString = stringWriter.toString();
            stringWriter.close();

            /* Check to see if we archive or not */
            String s = propertyUtil.getNNDArchiveMessage();

            if (s.equals("Y"))
            {

                try
                {

                    //System.out.println("Archiving the XML = TRUE ");
                    File archiveDirectory = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY);

                    if (!archiveDirectory.exists())
                        archiveDirectory.mkdirs(); /* make the directory if it does not exists */

                    //name the file with the date and time
                    //i.e. C:\Nedss\NND\MessageArchive\2002.09.06-12.06.06.xml
                    SimpleDateFormat formatter = new SimpleDateFormat(
                                                         "yyyy.MM.dd-hh.mm.ss");
                    Date currentTime_1 = new Date();
                    String dateString = formatter.format(currentTime_1);
                    File archiveXML = new File(NNDConstantUtil.NND_ARCHIVE_DIRECTORY,
                                               dateString +
                                               messageProcessorVO.getTheNotificationDT().getLocalId() +
                                               ".xml");
                    FileWriter xmlOut = new FileWriter(archiveXML);

                    if (msgString != null)
                        xmlOut.write(msgString);

                    xmlOut.close(); //close file
                }
                catch (IOException e)
                {
                    logger.error(
                            "NNDObjectMarshaller: Error archiving NND Message " + e);
                }
            } // if archiving

            //return the XML Investigation_Message
            return (msgString);
        }
        catch (MarshalException me)
        {

            String causeException = null;

            if (!(me.getException() == null))
            {

                Exception e = me.getException();
                causeException = e.getMessage();
            }
            else
                causeException = me.getMessage();

            logger.debug(
                    "MarshalException in marshallNNDObjects Info = " +
                    causeException);
            throw new NNDException("MarshalException in marshallNNDObjects Info = " +
                                   causeException);
        }
        catch (ValidationException ve)
        {

            String causeException = null;

            if (!(ve.getException() == null))
            {

                Exception e = ve.getException();
                causeException = e.getMessage();
            }
            else
                causeException = ve.getMessage();

            logger.debug(
                    "ValidationException in marshallNNDObjects Info = " +
                    causeException);
            throw new NNDException("ValidationException in marshallNNDObjects Info = " +
                                   causeException);
        }
        catch (NNDException nnde)
        {
            logger.debug(
                    "NNDException in " + nnde.getModuleName() + " " +
                    nnde.getMessage());
            throw nnde;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.debug(
                    "Unknown Exception in marshallNNDObjects  " +
                    e.getMessage());

            NNDException nndOther = new NNDException(
                                            "Unknown Exception in marshallNNDObjects  " +
                                            e.getMessage());
            nndOther.setModuleName("NNDObjectMarshaller.marshallNNDObjects");
            throw nndOther;
        }
        finally
        {
            investigationMsg = null;
        }
    }

    private void addToInvestigationMsgInternal(NedssEvent nedssEvent, String eventType)
                                throws NNDException
    {

        try
        {

            NNDEvent nndEvent = new NNDEvent();
            nndEvent.setNedssEvent(nedssEvent);
            nndEvent.setNedssEventType(eventType);
            investigationMsg.addNNDEvent(nndEvent);
            nndEvent = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            NNDException nnde = new NNDException(
                                        "Exception while adding Event to Investigation Message" +
                                        e.getMessage());
            nnde.setModuleName("NNDObjectMarshaller.addToInvestigation");
            throw nnde;
        }
    }
}