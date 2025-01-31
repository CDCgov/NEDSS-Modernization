/**
 * Title:        NotificationProxyVO
 * Description:  NotificationProxyVO is a value object that represents a notificationproxy. It has a publicHealthVO,
 *               a notificationVO and a collection of actRelationshipDT. It has getting and setting methods, flags for its
 *               properties.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */
package gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo;

import java.util.Collection;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.act.notification.vo.*;

public class NotificationProxyVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   public Collection<Object>  theActRelationshipDTCollection;
   public PublicHealthCaseVO thePublicHealthCaseVO;
   public NotificationVO theNotificationVO;

   /**
    * @roseuid 3C43685F03AC
    */
   public NotificationProxyVO()
   {

   }

   /**
    * Access method for the theActRelationshipDTCollection  property.
    *
    * @return Collection<Object>   the current value of the theActRelationshipDTCollection  property
    */
   public Collection<Object>  getTheActRelationshipDTCollection()
   {
      return theActRelationshipDTCollection;
   }

   /**
    * Sets the value of the theActRelationshipDTCollection  property.
    *
    * @param aTheActRelationshipDTCollection, the new value of the theActRelationshipDTCollection  property
    */
   public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
   {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
   }

   /**
    * Access method for the thePublicHealthCaseVO property.
    *
    * @return PublicHealthCaseVO, the current value of the thePublicHealthCaseVO property
    */
   public PublicHealthCaseVO getThePublicHealthCaseVO()
   {
      return thePublicHealthCaseVO;
   }

   /**
    * Sets the value of the thePublicHealthCaseVO property.
    *
    * @param aThePublicHealthCaseVO the new value of the thePublicHealthCaseVO property
    */
   public void setThePublicHealthCaseVO(PublicHealthCaseVO aThePublicHealthCaseVO)
   {
      thePublicHealthCaseVO = aThePublicHealthCaseVO;
   }

   /**
    * Access method for the theNotificationVO property.
    *
    * @return NotificationVO, the current value of the theNotificationVO property
    */
   public NotificationVO getTheNotificationVO()
   {
      return theNotificationVO;
   }

   /**
    * Sets the value of the theNotificationVO property.
    *
    * @param aTheNotificationVO the new value of the theNotificationVO property
    */
   public void setTheNotificationVO(NotificationVO aTheNotificationVO)
   {
      theNotificationVO = aTheNotificationVO;
   }

   /**
    * Checks if both objects are same or not.
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C43685F03D4
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * set dirty flag
    * @param itDirty
    * @roseuid 3C43686000F0
    */
   public void setItDirty(boolean itDirty)
   {
        this.itDirty = itDirty;
   }

   /**
    * check dirty flag, if the object has been changed, the dirty flag is true
    * @return boolean
    * @roseuid 3C436860015E
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * set new flag
    * @param itNew
    * @roseuid 3C4368600186
    */
   public void setItNew(boolean itNew)
   {
	this.itNew = itNew;
   }

   /**
    * check new flag, if the object is new, returns true
    * @return boolean
    * @roseuid 3C43686001F4
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * set delete flag
    * @param itDelete
    * @roseuid 3C436860021C
    */
   public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

   /**
    * check delete flag
    * @return boolean
    * @roseuid 3C436860028B
    */
   public boolean isItDelete()
   {
    return itDelete;
   }
}