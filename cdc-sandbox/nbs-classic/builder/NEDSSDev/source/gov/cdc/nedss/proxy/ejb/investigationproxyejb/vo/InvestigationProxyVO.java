//Source file: C:\\Rational_development\\source\\gov\\cdc\\nedss\\helpers\\InvestigationProxyVO.java

package gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo;

import java.util.Collection;
import java.util.ArrayList;
import java.io.*;

import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.entity.entitygroup.vo.*;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;

public class InvestigationProxyVO extends LdfBaseVO
{
	private static final long serialVersionUID = 1L;
   public PublicHealthCaseVO thePublicHealthCaseVO;

   /**
    * DTs
    */
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theRlDTCollection;
   public Collection<Object> theActRelationshipDTCollection;

   /**
    * VOs
    */
   public Collection<Object> thePersonVOCollection;
   public Collection<Object> theOrganizationVOCollection;
   public Collection<Object> theMaterialVOCollection;
   public Collection<ObservationVO> theObservationVOCollection;
   public Collection<Object> theInterventionVOCollection;
   public Collection<Object> theEntityGroupVOCollection;
   public Collection<Object> theNonPersonLivingSubjectVOCollection;
   public Collection<Object> thePlaceVOCollection;
   public Collection<Object> theNotificationVOCollection;
   public Collection<Object> theReferralVOCollection;
   public Collection<Object> thePatientEncounterVOCollection;
   public Collection<Object> theClinicalDocumentVOCollection;
   public Collection<Object> theObservationSummaryVOCollection;//Replaced by theLabReportSummaryVOCollection, theMorbReportSummaryVOCollection
   public Collection<Object> theVaccinationSummaryVOCollection;
   public Collection<Object> theNotificationSummaryVOCollection;
   public Collection<Object> theTreatmentSummaryVOCollection;
   public Collection<Object> theLabReportSummaryVOCollection;
   public Collection<Object> theMorbReportSummaryVOCollection;
   public NotificationVO theNotificationVO;
   private boolean associatedNotificationsInd;
   private String businessObjectName;
   private boolean isOOSystemInd;
   private boolean isOOSystemPendInd;
   private Collection<Object>  theContactVOColl;
   private Collection<Object> theCTContactSummaryDTCollection;
   
   public Collection<Object> getTheCTContactSummaryDTCollection() {
		return theCTContactSummaryDTCollection;
	}

	public void setTheCTContactSummaryDTCollection(
			Collection<Object> theCTContactSummaryDTCollection) {
		this.theCTContactSummaryDTCollection = theCTContactSummaryDTCollection;
	}
   public Collection<Object> getTheContactVOColl() {
	return theContactVOColl;
}

public void setTheContactVOColl(Collection<Object> theContactVOColl) {
	this.theContactVOColl = theContactVOColl;
}

public Collection<Object> theDocumentSummaryVOCollection; 
   
   /**
    * @roseuid 3C5073770243
    */
   public InvestigationProxyVO()
   {

   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C507377024D
    */
   public Collection<Object> getTheVaccinationSummaryVOCollection()
   {
      return theVaccinationSummaryVOCollection;
   }

   /**
    * @param aVaccinationSummaryVOCollection
    * @roseuid 3C50737702C5
    */
   public void setTheVaccinationSummaryVOCollection(Collection<Object> aVaccinationSummaryVOCollection)
   {
      theVaccinationSummaryVOCollection  = aVaccinationSummaryVOCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    */
   public Collection<Object> getTheTreatmentSummaryVOCollection()
   {
      return theTreatmentSummaryVOCollection;
   }

   /**
    * @param aTreatmentSummaryVOCollection
    */
   public void setTheTreatmentSummaryVOCollection(Collection<Object> aTreatmentSummaryVOCollection)
   {
      theTreatmentSummaryVOCollection  = aTreatmentSummaryVOCollection;
          setItDirty(true);
   }



   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C507377033D
    */
   public Collection<Object> getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * @param aTheParticipationDTCollection
    * @roseuid 3C50737703C0
    */
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
      theParticipationDTCollection  = aTheParticipationDTCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C5073780050
    */
   public Collection<Object> getTheRoleDTCollection()
   {
      return theRlDTCollection;
   }

   /**
    * @param aTheRoleDTCollection
    * @roseuid 3C50737800C8
    */
   public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
   {
      theRlDTCollection  = aTheRoleDTCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C5073780140
    */
   public Collection<Object> getTheActRelationshipDTCollection()
   {
      return theActRelationshipDTCollection;
   }

   /**
    * @param aTheActRelationshipDTCollection
    * @roseuid 3C50737801B8
    */
   public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
   {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C5073780230
    */
   public Collection<Object> getThePersonVOCollection()
   {
      return thePersonVOCollection;
   }

   /**
    * @param aThePersonVOCollection
    * @roseuid 3C50737802B3
    */
   public void setThePersonVOCollection(Collection<Object> aThePersonVOCollection)
   {
      thePersonVOCollection  = aThePersonVOCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C507378032B
    */
   public Collection<Object> getTheOrganizationVOCollection()
   {
      return theOrganizationVOCollection;
   }

   /**
    * @param aTheOrganizationVOCollection
    * @roseuid 3C5073780399
    */
   public void setTheOrganizationVOCollection(Collection<Object> aTheOrganizationVOCollection)
   {
      theOrganizationVOCollection  = aTheOrganizationVOCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C5073790033
    */
   public Collection<Object> getTheMaterialVOCollection()
   {
      return theMaterialVOCollection;
   }

   /**
    * @param aTheMaterialVOCollection
    * @roseuid 3C50737900A1
    */
   public void setTheMaterialVOCollection(Collection<Object> aTheMaterialVOCollection)
   {
      theMaterialVOCollection  = aTheMaterialVOCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection
    * @roseuid 3C5073790124
    */
   public Collection<ObservationVO> getTheObservationVOCollection()
   {
      return theObservationVOCollection;
   }

   /**
    * @param aTheObservationVOCollection
    * @roseuid 3C50737901A6
    */
   public void setTheObservationVOCollection(Collection<ObservationVO> aTheObservationVOCollection)
   {
      theObservationVOCollection  = aTheObservationVOCollection;
          setItDirty(true);
   }

   /**
    * @return gov.cdc.nedss.wum.helpers.PublicHealthCaseVO
    * @roseuid 3C5073790228
    */
   public PublicHealthCaseVO getPublicHealthCaseVO()
   {
      return thePublicHealthCaseVO;
   }

   /**
    * @return gov.cdc.nedss.wum.helpers.PublicHealthCaseVO
    * @roseuid 3C50737902A0
    */
   public PublicHealthCaseVO getPublicHealthCaseVO_s()
   {
      if (this.thePublicHealthCaseVO == null)
        this.thePublicHealthCaseVO = new PublicHealthCaseVO();

      return this.thePublicHealthCaseVO;
   }

   /**
    * @param aPublicHealthCaseVO
    * @roseuid 3C507379030E
    */
   public void setPublicHealthCaseVO(PublicHealthCaseVO aPublicHealthCaseVO)
   {
        thePublicHealthCaseVO = aPublicHealthCaseVO;
        setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C5073790390
    */
   public Collection<Object> getTheInterventionVOCollection()
   {
      return theInterventionVOCollection;
   }

   /**
    * @param aTheInterventionVOCollection
    * @roseuid 3C50737A0017
    */
   public void setTheInterventionVOCollection(Collection<Object> aTheInterventionVOCollection)
   {
      theInterventionVOCollection  = aTheInterventionVOCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737A00A3
    */
   public Collection<Object> getTheEntityGroupVOCollection()
   {
      return theEntityGroupVOCollection;
   }

   /**
    * @param aTheEntityGroupVOCollection
    * @roseuid 3C50737A0111
    */
   public void setTheEntityGroupVOCollection(Collection<Object> aTheEntityGroupVOCollection)
   {
      theEntityGroupVOCollection  = aTheEntityGroupVOCollection;
      setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737A0193
    */
   public Collection<Object> getTheNonPersonLivingSubjectVOCollection()
   {
      return theNonPersonLivingSubjectVOCollection;
   }

   /**
    * @param aTheNonPersonLivingSubjectVOCollection
    * @roseuid 3C50737A0201
    */
   public void setTheNonPersonLivingSubjectVOCollection(Collection<Object> aTheNonPersonLivingSubjectVOCollection)
   {
      theNonPersonLivingSubjectVOCollection  = aTheNonPersonLivingSubjectVOCollection;
      setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737A0283
    */
   public Collection<Object> getThePlaceVOCollection()
   {
      return thePlaceVOCollection;
   }

   /**
    * @param aThePlaceVOCollection
    * @roseuid 3C50737A02FC
    */
   public void setThePlaceVOCollection(Collection<Object> aThePlaceVOCollection)
   {
      thePlaceVOCollection  = aThePlaceVOCollection;
      setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737A037E
    */
   public Collection<Object> getTheNotificationVOCollection()
   {
      return theNotificationVOCollection;
   }

   public NotificationVO getNotificationVO_s()
   {
      if (this.theNotificationVO == null)
        this.theNotificationVO = new NotificationVO();

      return this.theNotificationVO;
   }
   /**
    * @param aTheNotificationVOCollection
    * @roseuid 3C50737B0004
    */
   public void setTheNotificationVOCollection(Collection<Object> aTheNotificationVOCollection)
   {
      theNotificationVOCollection  = aTheNotificationVOCollection;
      setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737B0086
    */
   public Collection<Object> getTheReferralVOCollection()
   {
      return theReferralVOCollection;
   }

   /**
    * @param aTheReferralVOCollection
    * @roseuid 3C50737B00F4
    */
   public void setTheReferralVOCollection(Collection<Object> aTheReferralVOCollection)
   {
      theReferralVOCollection  = aTheReferralVOCollection;
      setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737B0176
    */
   public Collection<Object> getThePatientEncounterVOCollection()
   {
      return thePatientEncounterVOCollection;
   }

   /**
    * @param aThePatientEncounterVOCollection
    * @roseuid 3C50737B01EF
    */
   public void setThePatientEncounterVOCollection(Collection<Object> aThePatientEncounterVOCollection)
   {
      thePatientEncounterVOCollection  = aThePatientEncounterVOCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737B0271
    */
   public Collection<Object> getTheClinicalDocumentVOCollection()
   {
      return theClinicalDocumentVOCollection;
   }

   /**
    * @param aTheClinicalDocumentVOCollection
    * @roseuid 3C50737B02DF
    */
   public void setTheClinicalDocumentVOCollection(Collection<Object> aTheClinicalDocumentVOCollection)
   {
      theClinicalDocumentVOCollection  = aTheClinicalDocumentVOCollection;
          setItDirty(true);
   }

   /**
    * @return java.util.Collection<Object>
    * @roseuid 3C50737B0361
    */
   public Collection<Object> getTheObservationSummaryVOCollection()
   {
      return theObservationSummaryVOCollection;
   }

   /**
    * Getter for the lab report summary vo Collection<Object>
    * @return Collection<Object>
    */
   public Collection<Object> getTheLabReportSummaryVOCollection()
   {
      return theLabReportSummaryVOCollection;
   }

   /**
    * Getter for the morb report summary vo Collection<Object>
    * @return Collection<Object>
    */
   public Collection<Object> getTheMorbReportSummaryVOCollection()
   {
      return theMorbReportSummaryVOCollection;
   }



   public Collection<Object> getTheNotificationSummaryVOCollection()
   {
      return theNotificationSummaryVOCollection;
   }

   /**
    * @param aTheObservationSummaryVOCollection
    * @roseuid 3C50737B03CF
    */
   public void setTheObservationSummaryVOCollection(Collection<Object> aTheObservationSummaryVOCollection)
   {
      theObservationSummaryVOCollection  = aTheObservationSummaryVOCollection;
          setItDirty(true);
   }

   /**
    * Setter for the lab report summary vo Collection<Object>
    * @param aTheLabReportSummaryVOCollection
    */
   public void setTheLabReportSummaryVOCollection(Collection<Object> aTheLabReportSummaryVOCollection)
   {
      theLabReportSummaryVOCollection  = aTheLabReportSummaryVOCollection;
      setItDirty(true);
   }

   /**
    * Setter for the morb report summary vo Collection<Object>
    * @param aTheMorbReportSummaryVOCollection
    */
   public void setTheMorbReportSummaryVOCollection(Collection<Object> aTheMorbReportSummaryVOCollection)
   {
      theMorbReportSummaryVOCollection  = aTheMorbReportSummaryVOCollection;
      setItDirty(true);
   }

   public void setTheNotificationSummaryVOCollection(Collection<Object> aTheNotificationSummaryVOCollection)
   {
      theNotificationSummaryVOCollection  = aTheNotificationSummaryVOCollection;
          setItDirty(true);
   }
   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C50737C006A
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3C50737C0146
    */
   public void setItDirty(boolean itDirty)
   {
                this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3C50737C0164
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3C50737C0178
    */
   public void setItNew(boolean itNew)
   {
                this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3C50737C018C
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3C50737C01A0
    */
   public void setItDelete(boolean itDelete)
   {
                this.itDelete = itDelete;
   }

   /**
    * @return boolean
    * @roseuid 3C50737C01BE
    */
   public boolean isItDelete()
   {
                return itDelete;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.wum.helpers.ObservationVO
    * @roseuid 3C50737C01C8
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

   /**
    * @param index
    * @return gov.cdc.nedss.cdm.helpers.OrganizationVO
    * @roseuid 3C50737C0254
    */
   public OrganizationVO getOrganizationVO_s(int index)
   {
      // this should really be in the constructor
      if (this.theOrganizationVOCollection  == null)
          this.theOrganizationVOCollection  = new ArrayList<Object> ();

      int currentSize = this.theOrganizationVOCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theOrganizationVOCollection.toArray();

          Object tempObj  = tempArray[index];

          OrganizationVO tempVO = (OrganizationVO) tempObj;

          return tempVO;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       OrganizationVO tempVO = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempVO = new OrganizationVO();
          this.theOrganizationVOCollection.add(tempVO);
        }

        return tempVO;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.cdm.helpers.PersonVO
    * @roseuid 3C50737C02D6
    */
   public PersonVO getPersonVO_s(int index)
   {
      // this should really be in the constructor
      if (this.thePersonVOCollection  == null)
          this.thePersonVOCollection  = new ArrayList<Object> ();

      int currentSize = this.thePersonVOCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.thePersonVOCollection.toArray();

          Object tempObj  = tempArray[index];

          PersonVO tempVO = (PersonVO) tempObj;

          return tempVO;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       PersonVO tempVO = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempVO = new PersonVO();
          this.thePersonVOCollection.add(tempVO);
        }

        return tempVO;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.cdm.helpers.MaterialVO
    * @roseuid 3C50737C0359
    */
   public MaterialVO getMaterialVO_s(int index)
   {
      // this should really be in the constructor
      if (this.theMaterialVOCollection  == null)
          this.theMaterialVOCollection  = new ArrayList<Object> ();

      int currentSize = this.theMaterialVOCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theMaterialVOCollection.toArray();

          Object tempObj  = tempArray[index];

          MaterialVO tempVO = (MaterialVO) tempObj;

          return tempVO;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       MaterialVO tempVO = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempVO = new MaterialVO();
          this.theMaterialVOCollection.add(tempVO);
        }

        return tempVO;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.wum.helpers.InterventionVO
    * @roseuid 3C50737C03D1
    */
   public InterventionVO getInterventionVO_s(int index)
   {
      // this should really be in the constructor
      if (this.theInterventionVOCollection  == null)
          this.theInterventionVOCollection  = new ArrayList<Object> ();

      int currentSize = this.theInterventionVOCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theInterventionVOCollection.toArray();

          Object tempObj  = tempArray[index];

          InterventionVO tempVO = (InterventionVO) tempObj;

          return tempVO;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       InterventionVO tempVO = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempVO = new InterventionVO();
          this.theInterventionVOCollection.add(tempVO);
        }

        return tempVO;
   }

   /**
    * @param index
    * @return gov.cdc.nedss.cdm.helpers.EntityGroupVO
    * @roseuid 3C50737D006B
    */
   public EntityGroupVO getEntityGroupVO_s(int index)
   {
      // this should really be in the constructor
      if (this.theEntityGroupVOCollection  == null)
          this.theEntityGroupVOCollection  = new ArrayList<Object> ();

      int currentSize = this.theEntityGroupVOCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theEntityGroupVOCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityGroupVO tempVO = (EntityGroupVO) tempObj;

          return tempVO;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       EntityGroupVO tempVO = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempVO = new EntityGroupVO();
          this.theEntityGroupVOCollection.add(tempVO);
        }

        return tempVO;
   }

    public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object deepCopy = ois.readObject();

        return  deepCopy;
    }



   public boolean getAssociatedNotificationsInd(){
     return this.associatedNotificationsInd;
   }

   public void setAssociatedNotificationsInd(boolean setAssociatedNotificationsInd){
     this.associatedNotificationsInd = setAssociatedNotificationsInd;
   }

   public String getBusinessObjNm(){
     return this.businessObjectName;
   }
   public void setBusinessObjNm(String aBusinessObjNm){
     this.businessObjectName = aBusinessObjNm;
   }

public Collection<Object> getTheDocumentSummaryVOCollection() {
	return theDocumentSummaryVOCollection;
}

public void setTheDocumentSummaryVOCollection(
		Collection<Object> theDocumentSummaryVOCollection) {
	this.theDocumentSummaryVOCollection  = theDocumentSummaryVOCollection;
}

public boolean isOOSystemInd() {
	return isOOSystemInd;
}

public void setOOSystemInd(boolean isOOSystemInd) {
	this.isOOSystemInd = isOOSystemInd;
}

public boolean isOOSystemPendInd() {
	return isOOSystemPendInd;
}

public void setOOSystemPendInd(boolean isOOSystemPendInd) {
	this.isOOSystemPendInd = isOOSystemPendInd;
}


}
