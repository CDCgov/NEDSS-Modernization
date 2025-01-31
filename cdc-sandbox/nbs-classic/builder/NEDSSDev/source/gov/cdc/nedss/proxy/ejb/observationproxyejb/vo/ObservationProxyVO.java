package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import  gov.cdc.nedss.util.*;
import java.util.Collection;
/**
 * ObservationProxyVO is a class representation of Observation and Related objects
 * This was updated to make Observation Object works in Page builder functionality
 * @version : Release 6.0
 * @author Pradeep Kumar Sharma
 *
 */
public class ObservationProxyVO extends PageActProxyVO
{
	private static final long serialVersionUID = 1L;
   public Collection<Object>  thePersonVOCollection;
   public Collection<Object>  theObservationVOCollection;
   public Collection<Object>  theOrganizationVOCollection;
   public Collection<Object>  theMaterialVOCollection;
   public Collection<Object>  theParticipationDTCollection;
   public Collection<Object>  theActRelationshipDTCollection;
   public Collection<Object>  theInterventionVOCollection;
   public Collection<Object>  theNonPersonLivingSubjectVOCollection;

   /**
    * @roseuid 3C03E4810130
    */
   public ObservationProxyVO()
   {

   }

   /**
    * Access method for the thePersonVOCollection  property.
    *
    * @return   the current value of the thePersonVOCollection  property
    */
   public Collection<Object>  getThePersonVOCollection()
   {
      return thePersonVOCollection;
   }

   /**
    * Sets the value of the thePersonVOCollection  property.
    *
    * @param aThePersonVOCollection  the new value of the thePersonVOCollection  property
    */
   public void setThePersonVOCollection(Collection<Object> aThePersonVOCollection)
   {
      thePersonVOCollection  = aThePersonVOCollection;
   }

   /**
    * Access method for the theObservationVOCollection  property.
    *
    * @return   the current value of the theObservationVOCollection  property
    */
   public Collection<Object>  getTheObservationVOCollection()
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
    * Access method for the theOrganizationVOCollection  property.
    *
    * @return   the current value of the theOrganizationVOCollection  property
    */
   public Collection<Object>  getTheOrganizationVOCollection()
   {
      return theOrganizationVOCollection;
   }

   /**
    * Sets the value of the theOrganizationVOCollection  property.
    *
    * @param aTheOrganizationVOCollection  the new value of the theOrganizationVOCollection  property
    */
   public void setTheOrganizationVOCollection(Collection<Object> aTheOrganizationVOCollection)
   {
      theOrganizationVOCollection  = aTheOrganizationVOCollection;
   }

   /**
    * Access method for the theMaterialVOCollection  property.
    *
    * @return   the current value of the theMaterialVOCollection  property
    */
   public Collection<Object>  getTheMaterialVOCollection()
   {
      return theMaterialVOCollection;
   }

   /**
    * Sets the value of the theMaterialVOCollection  property.
    *
    * @param aTheMaterialVOCollection  the new value of the theMaterialVOCollection  property
    */
   public void setTheMaterialVOCollection(Collection<Object> aTheMaterialVOCollection)
   {
      theMaterialVOCollection  = aTheMaterialVOCollection;
   }

   /**
    * Access method for the theParticipationDTCollection  property.
    *
    * @return   the current value of the theParticipationDTCollection  property
    */
   public Collection<Object>  getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * Sets the value of the theParticipationDTCollection  property.
    *
    * @param aTheParticipationDTCollection  the new value of the theParticipationDTCollection  property
    */
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
      theParticipationDTCollection  = aTheParticipationDTCollection;
   }

   /**
    * Access method for the theActRelationshipDTCollection  property.
    *
    * @return   the current value of the theActRelationshipDTCollection  property
    */
   public Collection<Object>  getTheActRelationshipDTCollection()
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
    * Access method for the theInterventionVOCollection  property.
    *
    * @return   the current value of the theInterventionVOCollection  property
    */
   public Collection<Object>  getTheInterventionVOCollection()
   {
      return theInterventionVOCollection;
   }

   /**
    * Sets the value of the theInterventionVOCollection  property.
    *
    * @param aTheInterventionVOCollection  the new value of the theInterventionVOCollection  property
    */
   public void setTheInterventionVOCollection(Collection<Object> aTheInterventionVOCollection)
   {
      theInterventionVOCollection  = aTheInterventionVOCollection;
   }

   /**
    * Access method for the theNonPersonLivingSubjectVOCollection  property.
    *
    * @return   the current value of the theNonPersonLivingSubjectVOCollection  property
    */
   public Collection<Object>  getTheNonPersonLivingSubjectVOCollection()
   {
      return theNonPersonLivingSubjectVOCollection;
   }

   /**
    * Sets the value of the theNonPersonLivingSubjectVOCollection  property.
    *
    * @param aTheNonPersonLivingSubjectVOCollection  the new value of the theNonPersonLivingSubjectVOCollection  property
    */
   public void setTheNonPersonLivingSubjectVOCollection(Collection<Object> aTheNonPersonLivingSubjectVOCollection)
   {
      theNonPersonLivingSubjectVOCollection  = aTheNonPersonLivingSubjectVOCollection;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C03E481013A
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3C03E481019E
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C03E48101C6
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3C03E48101D0
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C03E481020C
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3C03E4810220
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C03E4810252
    */
   public boolean isItDelete()
   {
    return true;
   }
}
