//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\proxy\\ejb\\observationproxyejb\\vo\\MorbidityProxyVO.java

package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.RoleDT;

import java.util.Collection;
import java.util.ArrayList;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;

public class MorbidityProxyVO extends LdfBaseVO
{
	
   private static final long serialVersionUID = 1L;
   public boolean associatedNotificationInd;
   public Collection<ObservationVO> theObservationVOCollection;
   public Collection<Object> thePlaceVOCollection;
   public Collection<Object> theNonPersonLivingSubjectVOCollection;
   public Collection<Object> theOrganizationVOCollection;
   public Collection<Object> thePersonVOCollection;
   public Collection<Object> theMaterialVOCollection;
   public Collection<Object> theEntityGroupVOCollection;
   public Collection<Object> theActRelationshipDTCollection;
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theRlDTCollection;
   public Collection<Object> thePatientEncounterVOCollection;
   public Collection<Object> theInterventionVOCollection;
   public Collection<Object> theReferralVOCollection;
   public Collection<Object> theClinicalDocumentVOCollection;
   public Collection<Object> thePublicHealthCaseVOCollection;
   public Collection<Object> theNotificationVOCollection;
   private Collection<Object> theTreatmentVOCollection;
   private Collection<Object> theAttachmentVOCollection;
   
   private String labClia = null;
   private boolean associatedInvInd = false;
   /**
    * @roseuid 3CEA3AAB00CB
    */
   public MorbidityProxyVO()
   {

   }

   /**
    * Determines if the associatedNotificationInd property is true.
    *
    * @return   <code>true<code> if the associatedNotificationInd property is true
    */
   public boolean getAssociatedNotificationInd()
   {
      return associatedNotificationInd;
   }

   /**
    * Sets the value of the associatedNotificationInd property.
    *
    * @param aAssociatedNotificationInd the new value of the associatedNotificationInd property
    */
   public void setAssociatedNotificationInd(boolean aAssociatedNotificationInd)
   {
      associatedNotificationInd = aAssociatedNotificationInd;
   }

   /**
    * Access method for the theObservationVOCollection  property.
    *
    * @return   the current value of the theObservationVOCollection  property
    */
   public Collection<ObservationVO> getTheObservationVOCollection()
   {
      return theObservationVOCollection;
   }

   /**
    * Sets the value of the theObservationVOCollection  property.
    *
    * @param aTheObservationVOCollection  the new value of the theObservationVOCollection  property
    */
   public void setTheObservationVOCollection(Collection<ObservationVO> aTheObservationVOCollection)
   {
      theObservationVOCollection  = aTheObservationVOCollection;
   }

   /**
    * Access method for the thePlaceVOCollection  property.
    *
    * @return   the current value of the thePlaceVOCollection  property
    */
   public Collection<Object> getThePlaceVOCollection()
   {
      return thePlaceVOCollection;
   }

   /**
    * Sets the value of the thePlaceVOCollection  property.
    *
    * @param aThePlaceVOCollection  the new value of the thePlaceVOCollection  property
    */
   public void setThePlaceVOCollection(Collection<Object> aThePlaceVOCollection)
   {
      thePlaceVOCollection  = aThePlaceVOCollection;
   }

   /**
    * Access method for the theNonPersonLivingSubjectVOCollection  property.
    *
    * @return   the current value of the theNonPersonLivingSubjectVOCollection  property
    */
   public Collection<Object> getTheNonPersonLivingSubjectVOCollection()
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
    * Access method for the theOrganizationVOCollection  property.
    *
    * @return   the current value of the theOrganizationVOCollection  property
    */
   public Collection<Object> getTheOrganizationVOCollection()
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
    * Access method for the thePersonVOCollection  property.
    *
    * @return   the current value of the thePersonVOCollection  property
    */
   public Collection<Object> getThePersonVOCollection()
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
    * Access method for the theMaterialVOCollection  property.
    *
    * @return   the current value of the theMaterialVOCollection  property
    */
   public Collection<Object> getTheMaterialVOCollection()
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
    * Access method for the theEntityGroupVOCollection  property.
    *
    * @return   the current value of the theEntityGroupVOCollection  property
    */
   public Collection<Object> getTheEntityGroupVOCollection()
   {
      return theEntityGroupVOCollection;
   }

   /**
    * Sets the value of the theEntityGroupVOCollection  property.
    *
    * @param aTheEntityGroupVOCollection  the new value of the theEntityGroupVOCollection  property
    */
   public void setTheEntityGroupVOCollection(Collection<Object> aTheEntityGroupVOCollection)
   {
      theEntityGroupVOCollection  = aTheEntityGroupVOCollection;
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
    * Access method for the theParticipationDTCollection  property.
    *
    * @return   the current value of the theParticipationDTCollection  property
    */
   public Collection<Object> getTheParticipationDTCollection()
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
    * Access method for the theRoleDTCollection  property.
    *
    * @return   the current value of the theRoleDTCollection  property
    */
   public Collection<Object> getTheRoleDTCollection()
   {
      return theRlDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    *
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
   public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
   {
      theRlDTCollection  = aTheRoleDTCollection;
   }

   /**
    * Access method for the thePatientEncounterVOCollection  property.
    *
    * @return   the current value of the thePatientEncounterVOCollection  property
    */
   public Collection<Object> getThePatientEncounterVOCollection()
   {
      return thePatientEncounterVOCollection;
   }

   /**
    * Sets the value of the thePatientEncounterVOCollection  property.
    *
    * @param aThePatientEncounterVOCollection  the new value of the thePatientEncounterVOCollection  property
    */
   public void setThePatientEncounterVOCollection(Collection<Object> aThePatientEncounterVOCollection)
   {
      thePatientEncounterVOCollection  = aThePatientEncounterVOCollection;
   }

   /**
    * Access method for the theInterventionVOCollection  property.
    *
    * @return   the current value of the theInterventionVOCollection  property
    */
   public Collection<Object> getTheInterventionVOCollection()
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
    * Access method for the theReferralVOCollection  property.
    *
    * @return   the current value of the theReferralVOCollection  property
    */
   public Collection<Object> getTheReferralVOCollection()
   {
      return theReferralVOCollection;
   }

   /**
    * Sets the value of the theReferralVOCollection  property.
    *
    * @param aTheReferralVOCollection  the new value of the theReferralVOCollection  property
    */
   public void setTheReferralVOCollection(Collection<Object> aTheReferralVOCollection)
   {
      theReferralVOCollection  = aTheReferralVOCollection;
   }

   /**
    * Access method for the theClinicalDocumentVOCollection  property.
    *
    * @return   the current value of the theClinicalDocumentVOCollection  property
    */
   public Collection<Object> getTheClinicalDocumentVOCollection()
   {
      return theClinicalDocumentVOCollection;
   }

   /**
    * Sets the value of the theClinicalDocumentVOCollection  property.
    *
    * @param aTheClinicalDocumentVOCollection  the new value of the theClinicalDocumentVOCollection  property
    */
   public void setTheClinicalDocumentVOCollection(Collection<Object> aTheClinicalDocumentVOCollection)
   {
      theClinicalDocumentVOCollection  = aTheClinicalDocumentVOCollection;
   }

   /**
    * Access method for the thePublicHealthCaseVOCollection  property.
    *
    * @return   the current value of the thePublicHealthCaseVOCollection  property
    */
   public Collection<Object> getThePublicHealthCaseVOCollection()
   {
      return thePublicHealthCaseVOCollection;
   }

   /**
    * Sets the value of the thePublicHealthCaseVOCollection  property.
    *
    * @param aThePublicHealthCaseVOCollection  the new value of the thePublicHealthCaseVOCollection  property
    */
   public void setThePublicHealthCaseVOCollection(Collection<Object> aThePublicHealthCaseVOCollection)
   {
      thePublicHealthCaseVOCollection  = aThePublicHealthCaseVOCollection;
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
    * compare the two objects
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3CEA3AAB0128
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * set the itDirty Property
    * @param itDirty
    * @roseuid 3CEA3AAB02DE
    */
   public void setItDirty(boolean itDirty)
   {
     this.itDirty = itDirty;
   }

   /**
    * check for dirty value
    * @return boolean
    * @roseuid 3CEA3AAB037A
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * set the itNew property
    * @param itNew
    * @roseuid 3CEA3AAB03A9
    */
   public void setItNew(boolean itNew)
   {
     this.itNew = itNew;
   }

   /**
    * check if the object is new
    * @return boolean
    * @roseuid 3CEA3AAC006D
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * set the itDirty Property
    * @param itDelete
    * @roseuid 3CEA3AAC009C
    */
   public void setItDelete(boolean itDelete)
   {
     this.itDelete = itDelete;
   }

   /**
    * check for itDirty property
    * @return boolean
    * @roseuid 3CEA3AAC0138
    */
   public boolean isItDelete()
   {
    return itDelete;
   }

   /**
    * Access method for the theTreatmentVOCollection  property.
    * @return   the current value of the theTreatmentVOCollection  property
    * @roseuid 3ECE425F00A1
    */
   public Collection<Object> getTheTreatmentVOCollection()
   {
      return theTreatmentVOCollection;
   }

   /**
    * Sets the value of the theTreatmentVOCollection  property.
    * @param aTheTreatmentVOCollection  the new value of the theTreatmentVOCollection
    * property
    * @roseuid 3ECE425F00E7
    */
   public void setTheTreatmentVOCollection(Collection<Object> aTheTreatmentVOCollection)
   {
      theTreatmentVOCollection  = aTheTreatmentVOCollection;
   }

   /**
    * For struts , need to get observations through index
    * @param index -- the index value for observation
    * @return tempVO -- the observation value Object
    * @roseuid 3ECE425F039A
    */
   public ObservationVO getObservationVO_s(int index)
   {
      // this should really be in the constructor
      if (this.theObservationVOCollection  == null)
	  this.theObservationVOCollection  = new ArrayList<ObservationVO> ();

      int currentSize = this.theObservationVOCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
	try {
	  Object[] tempArray = this.theObservationVOCollection.toArray();

	  Object tempObj  = tempArray[index];

	  ObservationVO tempVO = (ObservationVO) tempObj;

	  return tempVO;
	}
	catch (Exception e) {
	   //##!! System.out.println(e);
        } // do nothing just continue
      }

       ObservationVO tempVO = null;

	for (int i = currentSize; i < index+1; i++)
	{
	  tempVO = new ObservationVO();
	  this.theObservationVOCollection.add(tempVO);
	}

	return tempVO;
   }

public String getLabClia() {
	return labClia;
}

public void setLabClia(String labClia) {
	this.labClia = labClia;
}

/**
 * @return the associatedInvInd
 */
public boolean isAssociatedInvInd() {
	return associatedInvInd;
}

/**
 * @param associatedInvInd the associatedInvInd to set
 */
public void setAssociatedInvInd(boolean associatedInvInd) {
	this.associatedInvInd = associatedInvInd;
}

public Collection<Object> getTheAttachmentVOCollection() {
	return theAttachmentVOCollection;
}

public void setTheAttachmentVOCollection(
		Collection<Object> theAttachmentVOCollection) {
	this.theAttachmentVOCollection = theAttachmentVOCollection;
}
}
