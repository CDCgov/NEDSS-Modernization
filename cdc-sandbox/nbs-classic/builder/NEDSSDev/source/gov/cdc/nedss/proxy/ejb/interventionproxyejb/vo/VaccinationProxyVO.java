/**
 * Title: VaccinationProxyVO helper class.
 * Description: A helper class for Vaccination Proxy VO
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo;

import java.util.*;
import java.util.Collection;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;

public class VaccinationProxyVO extends LdfBaseVO
{
	private static final long serialVersionUID = 1L;
   public InterventionVO theInterventionVO;
   public Collection<Object>  theParticipationDTCollection;
   public Collection<Object>  theActRelationshipDTCollection;
   public Collection<Object>  thePersonVOCollection;
   public MaterialVO theMaterialVO;
   public Collection<Object>  theOrganizationVOCollection;
   public Collection<ObservationVO>  theObservationVOCollection;
   public Collection<Object>  theRoleDTCollection;
   public boolean associatedNotificationInd;
  private String businessObjNm;

   public VaccinationProxyVO()
   {
   }

   /**
    * Access method for the theInterventionVO property.
    * @return   the current value of the theInterventionVO property
    */
   public InterventionVO getTheInterventionVO()
   {
      return theInterventionVO;
   }

   /**
    * Access method for the theInterventionVO property.
    * @return   a new instance of InterventionVO for the theInterventionVO property if the current one is null
    */
   public InterventionVO getTheInterventionVO_s()
   {
      if (this.theInterventionVO == null)
        this.theInterventionVO = new InterventionVO();

      return this.theInterventionVO;
   }

   /**
    * Sets the value of the theInterventionVO property.
    * @param aTheInterventionVO the new value of the theInterventionVO property
    */
   public void setTheInterventionVO(InterventionVO aTheInterventionVO)
   {
      theInterventionVO = aTheInterventionVO;
      setItDirty(true);
   }

   /**
    * Access method for the theParticipationDTCollection  property.
    * @return   the current value of the theParticipationDTCollection  property
    */
   public Collection<Object>  getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * Sets the value of the theParticipationDTCollection  property.
    * @param aTheParticipationDTCollection  the new value of the theParticipationDTCollection  property
    */
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
      theParticipationDTCollection  = aTheParticipationDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theActRelationshipDTCollection  property.
    * @return   the current value of the theActRelationshipDTCollection  property
    */
   public Collection<Object>  getTheActRelationshipDTCollection()
   {
      return theActRelationshipDTCollection;
   }

   /**
    * Sets the value of the theActRelationshipDTCollection  property.
    * @param aTheActRelationshipDTCollection  the new value of the theActRelationshipDTCollection  property
    */
   public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
   {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the thePersonVOCollection  property.
    * @return   the current value of the thePersonVOCollection  property
    */
   public Collection<Object>  getThePersonVOCollection()
   {
      return thePersonVOCollection;
   }

   /**
    * Sets the value of the thePersonVOCollection  property.
    * @param aThePersonVOCollection  the new value of the thePersonVOCollection  property
    */
   public void setThePersonVOCollection(Collection<Object> aThePersonVOCollection)
   {
      thePersonVOCollection  = aThePersonVOCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theMaterialVO property.
    * @return   the current value of the theMaterialVO property
    */
   public MaterialVO getTheMaterialVO()
   {
      return theMaterialVO;
   }

   /**
    * Access method for the theMaterialVO property.
    * @return   a new instance of MaterialVO for the theMaterialVO property if the current one is null
    */
   public MaterialVO getTheMaterialVO_s()
   {
      if (this.theMaterialVO == null)
        this.theMaterialVO = new MaterialVO();

      return this.theMaterialVO;
   }

   /**
    * Sets the value of the theMaterialVO property.
    * @param aTheMaterialVO the new value of the theMaterialVO property
    */
   public void setTheMaterialVO(MaterialVO aTheMaterialVO)
   {
      theMaterialVO = aTheMaterialVO;
      setItDirty(true);
   }

   /**
    * Access method for the theOrganizationVOCollection  property.
    * @return   the current value of the theOrganizationVOCollection  property
    */
   public Collection<Object>  getTheOrganizationVOCollection()
   {
      return theOrganizationVOCollection;
   }

   /**
    * Sets the value of the theOrganizationVOCollection  property.
    * @param aTheOrganizationVOCollection  the new value of the theOrganizationVOCollection  property
    */
   public void setTheOrganizationVOCollection(Collection<Object> aTheOrganizationVOCollection)
   {
      theOrganizationVOCollection  = aTheOrganizationVOCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theObservationVOCollection  property.
    * @return   the current value of the theObservationVOCollection  property
    */
   public Collection<ObservationVO>  getTheObservationVOCollection()
   {
      return theObservationVOCollection;
   }

   /**
    * Sets the value of the theObservationVOCollection  property.
    * @param aTheObservationVOCollection  the new value of the theObservationVOCollection  property
    */
   public void setTheObservationVOCollection(Collection<ObservationVO> aTheObservationVOCollection)
   {
      theObservationVOCollection  = aTheObservationVOCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theRoleDTCollection  property.
    * @return   the current value of the theRoleDTCollection  property
    */
   public Collection<Object>  getTheRoleDTCollection()
   {
      return theRoleDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
   public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
   {
      theRoleDTCollection  = aTheRoleDTCollection;
      setItDirty(true);
   }

   public void setAssociatedNotificationInd(boolean aInd){
     associatedNotificationInd = aInd;
   }

   public boolean getAssociatedNotificationInd(){
     return associatedNotificationInd;

   }
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Sets the value of the itDirty property.
    * @param itDirty the new value of the itDirty property
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
    * Access method for the itDirty property.
    * @return   the current value of the itDirty property
    */
   public boolean isItDirty()
   {
      return itDirty;
   }

   /**
    * Sets the value of the itNew property.
    * @param itNew the new value of the itNew property
    */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

   /**
    * Access method for the isItNew property.
    * @return   the current value of the isItNew property
    */
   public boolean isItNew()
   {
      return itNew;
   }

   /**
    * Sets the value of the itDelete property.
    * @param itDelete the new value of the itDelete property
    */
   public void setItDelete(boolean itDelete)
   {
      this.itDelete = itDelete;
   }

   /**
    * Access method for the isItDelete property.
    * @return   the current value of the isItDelete property
    */
   public boolean isItDelete()
   {
      return itDelete;
   }


   /**
    * Access method for the ObservationVO object retrieved from theObservationVOCollection.
    * @param index is the index value of the ObservationVO in the theObservationVOCollection
    * @return   the selected ObservationVO object
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
  public String getBusinessObjNm() {
    return businessObjNm;
  }
  public void setBusinessObjNm(String businessObjNm) {
    this.businessObjNm = businessObjNm;
  }

}
