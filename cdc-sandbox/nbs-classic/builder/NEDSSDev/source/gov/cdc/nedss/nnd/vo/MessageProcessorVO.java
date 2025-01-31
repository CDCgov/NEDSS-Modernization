//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\nnd\\vo\\MessageProcessorVO.java

//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\helpers\\MessageProcessorVO.java

/**
 * Title:        MessageProcessorVO
 * Description:  MessageProcessorVO is a value object that represents a message processor object .
 *               It extends AbstractVO. It contains an InvestigationProxyVO, a collection of LabResultProxyVOs and
 *               a collection of vaccinationProxyVOs, and a notificationLocalID and a notification type attributes.
 *               It has getting and setting methods, and flags for its
 *               properties.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */
package gov.cdc.nedss.nnd.vo;

import gov.cdc.nedss.util.AbstractVO;
import java.util.Collection;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
import gov.cdc.nedss.act.notification.dt.NotificationDT;

public class MessageProcessorVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   public Collection<Object>  theVaccinationProxyVOCollection;
   public Collection<Object>  theLabResultProxyVOCollection;
   public InvestigationProxyVO theInvestigationProxyVO;
   public SummaryReportProxyVO theSummaryReportProxyVO;
   public NotificationDT theNotificationDT;
   public Collection<Object>  theMorbidityProxyVOCollection;

   /**
    * @roseuid 3D579C830222
    */
   public MessageProcessorVO()
   {

   }

   /**
    * Access method for the theVaccinationProxyVOCollection  property.
    *
    * @return   the current value of the theVaccinationProxyVOCollection  property
    */
   public Collection<Object>  getTheVaccinationProxyVOCollection()
   {
      return theVaccinationProxyVOCollection;
   }

   /**
    * Sets the value of the theVaccinationProxyVOCollection  property.
    *
    * @param aTheVaccinationProxyVOCollection  the new value of the theVaccinationProxyVOCollection  property
    */
   public void setTheVaccinationProxyVOCollection(Collection<Object> aTheVaccinationProxyVOCollection)
   {
      theVaccinationProxyVOCollection  = aTheVaccinationProxyVOCollection;
   }

   /**
    * Access method for the theLabResultProxyVOCollection  property.
    *
    * @return   the current value of the theLabResultProxyVOCollection  property
    */
   public Collection<Object>  getTheLabResultProxyVOCollection()
   {
      return theLabResultProxyVOCollection;
   }

   /**
    * Sets the value of the theLabResultProxyVOCollection  property.
    *
    * @param aTheLabResultProxyVOCollection  the new value of the theLabResultProxyVOCollection  property
    */
   public void setTheLabResultProxyVOCollection(Collection<Object> aTheLabResultProxyVOCollection)
   {
      theLabResultProxyVOCollection  = aTheLabResultProxyVOCollection;
   }

   /**
    * Access method for the theInvestigationProxyVO property.
    *
    * @return   the current value of the theInvestigationProxyVO property
    */
   public InvestigationProxyVO getTheInvestigationProxyVO()
   {
      return theInvestigationProxyVO;
   }

   /**
    * Sets the value of the theInvestigationProxyVO property.
    *
    * @param aTheInvestigationProxyVO the new value of the theInvestigationProxyVO property
    */
   public void setTheInvestigationProxyVO(InvestigationProxyVO aTheInvestigationProxyVO)
   {
      theInvestigationProxyVO = aTheInvestigationProxyVO;
   }

   /**
    * Access method for the theSummaryReportProxyVO property.
    *
    * @return   the current value of the theSummaryReportProxyVO property
    */
   public SummaryReportProxyVO getTheSummaryReportProxyVO()
   {
      return theSummaryReportProxyVO;
   }

   /**
    * Sets the value of the theSummaryReportProxyVO property.
    *
    * @param aTheSummaryReportProxyVO the new value of the theSummaryReportProxyVO property
    */
   public void setTheSummaryReportProxyVO(SummaryReportProxyVO aTheSummaryReportProxyVO)
   {
      theSummaryReportProxyVO = aTheSummaryReportProxyVO;
   }

   /**
    * Access method for the theNotificationDT property.
    *
    * @return   the current value of the theNotificationDT property
    */
   public NotificationDT getTheNotificationDT()
   {
      return theNotificationDT;
   }

   /**
    * Sets the value of the theNotificationDT property.
    *
    * @param aTheNotificationDT the new value of the theNotificationDT property
    */
   public void setTheNotificationDT(NotificationDT aTheNotificationDT)
   {
      theNotificationDT = aTheNotificationDT;
   }

   /**
    * Access method for the theMorbidityProxyVOCollection  property.
    *
    * @return   the current value of the theMorbidityProxyVOCollection  property
    */
   public Collection<Object>  getTheMorbidityProxyVOCollection()
   {
      return theMorbidityProxyVOCollection;
   }

   /**
    * Sets the value of the theMorbidityProxyVOCollection  property.
    *
    * @param aTheMorbidityProxyVOCollection  the new value of the theMorbidityProxyVOCollection  property
    */
   public void setTheMorbidityProxyVOCollection(Collection<Object> aTheMorbidityProxyVOCollection)
   {
      theMorbidityProxyVOCollection  = aTheMorbidityProxyVOCollection;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D579C830242
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D579C8302EE
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D579C83033C
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D579C83034B
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D579C830399
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D579C8303A9
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D579C84000F
    */
   public boolean isItDelete()
   {
    return true;
   }

}
/*
String MessageProcessorVO.getNotificationType(){return null;}
 */
/*
String MessageProcessorVO.getNotificationLocalID(){return null;}
 */
/**
 *
 * void MessageProcessorVO.setNotificationLocalID(java.lang.String){
 *       notificationLocalID = aNotificationLocalID;
 *    }
 *
 *
 * void MessageProcessorVO.getNotificationType(){
 *       return notificationType;
 *    }
 *
 *
 * void MessageProcessorVO.setNotificationType(java.lang.String){
 *       notificationType = aNotificationType;
 *    }
 *
 *
 * void MessageProcessorVO.getNotificationLocalID(){
 *       return notificationLocalID;
 *    }
 *
 */
