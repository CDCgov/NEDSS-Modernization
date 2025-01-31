/**
 * Title:       TreatmentProxyVO helper class.
 * Description: A helper class for Treatment Proxy VO
 * Copyright:   Copyright (c) 2001
 * Company:     Computer Sciences Corporation
 * @author      NEDSS Development Team
 * @version     1.1
 */

package gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo;

import java.util.*;
import java.util.Collection;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.act.treatment.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;

public class TreatmentProxyVO extends LdfBaseVO
{
	private static final long serialVersionUID = 1L;
   public TreatmentVO theTreatmentVO;
   public Collection<Object>  theParticipationDTCollection;
   public Collection<Object>  theActRelationshipDTCollection;
   public Collection<Object>  thePersonVOCollection;
   public Collection<Object>  theOrganizationVOCollection;
   public Collection<Object>  theStateDefinedDataDTCollection;

   public TreatmentProxyVO()
   {
   }

   /**
    * Access method for the theTreatmentVO property.
    * @return   the current value of the theTreatmentVO property
    */
   public TreatmentVO getTheTreatmentVO()
   {
      return theTreatmentVO;
   }

   /**
    * Access method for the theTreatmentVO property.
    * @return   a new instance of TreatmentVO for the theTreatmentVO property if the current one is null
    */
   public TreatmentVO getTheTreatmentVO_s()
   {
      if (this.theTreatmentVO == null)
        this.theTreatmentVO = new TreatmentVO();

      return theTreatmentVO;
   }

   /**
    * Sets the value of the theTreatmentVO property.
    * @param aTheTreatmentVO the new value of the theTreatmentVO property
    */
   public void setTheTreatmentVO(TreatmentVO aTheTreatmentVO)
   {
      this.theTreatmentVO = aTheTreatmentVO;
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
      this.theParticipationDTCollection  = aTheParticipationDTCollection;
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
      this.theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
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
      this.thePersonVOCollection  = aThePersonVOCollection;
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
      this.theOrganizationVOCollection  = aTheOrganizationVOCollection;
      setItDirty(true);
   }
   /**
    *
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return
    */
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




}
