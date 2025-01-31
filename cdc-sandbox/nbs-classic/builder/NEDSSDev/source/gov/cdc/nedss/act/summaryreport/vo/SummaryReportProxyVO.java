/**
 * Title:        SummaryReportProxyVO
 * Description:  SummaryReport Value Object - the objects needed for Summary Report-
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       NEDSS Development Team
 * @version      1.0
 */
package gov.cdc.nedss.act.summaryreport.vo;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.util.AbstractVO;

import java.util.Collection;

public class SummaryReportProxyVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   public PublicHealthCaseVO thePublicHealthCaseVO;
   public Collection<Object> theObservationVOCollection;
   public Collection<Object> theActRelationshipDTCollection;
   public Collection<Object> theNotificationVOCollection;

/**
    * @roseuid 3CE97A6200F3
    */
   public SummaryReportProxyVO()
   {

   }

   /**
    * Access method for the thePublicHealthCaseVO property.
    *
    * @return   the current value of the thePublicHealthCaseVO property
    */
   public PublicHealthCaseVO getThePublicHealthCaseVO()
   {
      return thePublicHealthCaseVO;
   }

   /**
    * Added according to naming convention used in other places so to use reflection
    * @return
    */
   public PublicHealthCaseVO getPublicHealthCaseVO()
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
    * Added according to naming convention used in other places so to use reflection
    * @param aThePublicHealthCaseVO
    */
   public void setPublicHealthCaseVO(PublicHealthCaseVO aThePublicHealthCaseVO)
  {
     thePublicHealthCaseVO = aThePublicHealthCaseVO;
  }


   /**
    * Access method for the theObservationVOCollection  property.
    *
    * @return   the current value of the theObservationVOCollection  property
    */
   public Collection<Object> getTheObservationVOCollection()
   {
      return theObservationVOCollection;
   }

   /**
    * Sets the value of the theObservationVOCollection  property.
    *
    * @param aTheObservationVOCollection  the new value of the theObservationVOCollection  property
    */
   public void setTheObservationVOCollection(Collection<Object> aTheObservationVOCollection)
   {
      theObservationVOCollection  = aTheObservationVOCollection;
   }

   /**
    * Access method for the theActRelationshipDTCollection  property.
    *
    * @return   the current value of the theActRelationshipDTCollection  property
    */
   public Collection<Object> getTheActRelationshipDTCollection()
   {
      return theActRelationshipDTCollection;
   }

   /**
    * Sets the value of the theActRelationshipDTCollection  property.
    *
    * @param aTheActRelationshipDTCollection  the new value of the theActRelationshipDTCollection  property
    */
   public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
   {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
   }

   /**
    * Access method for the theNotificationVOCollection  property.
    *
    * @return   the current value of the theNotificationVOCollection  property
    */
   public Collection<Object> getTheNotificationVOCollection()
   {
      return theNotificationVOCollection;
   }

   /**
    * Sets the value of the theNotificationVOCollection  property.
    *
    * @param aTheNotificationVOCollection  the new value of the theNotificationVOCollection  property
    */
   public void setTheNotificationVOCollection(Collection<Object> aTheNotificationVOCollection)
   {
      theNotificationVOCollection  = aTheNotificationVOCollection;
   }

   /**
    * @param objectname1    the Object
    * @param objectname2     the Object
    * @param voClass         the Class
    * @return boolean
    * @roseuid 3CE97A62011B
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3CE97A6201F7
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3CE97A62025B
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3CE97A620283
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3CE97A6202E7
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3CE97A62030F
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3CE97A620374
    */
   public boolean isItDelete()
   {
    return true;
   }


}

