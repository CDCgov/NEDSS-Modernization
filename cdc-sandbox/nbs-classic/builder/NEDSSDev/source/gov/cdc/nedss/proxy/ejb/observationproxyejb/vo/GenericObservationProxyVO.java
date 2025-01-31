//Source file: C:\\CDC\\Code Frameworks\\gov\\cdc\\nedss\\helpers\\GenericObservationProxyVO.java

package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.association.dt.*;

public class GenericObservationProxyVO extends AbstractVO
{
   public ObservationVO theObservationVO;
   public PersonVO thePersonVO;
   public ParticipationDT theParticipationDT;

   /**
    * @roseuid 3C33A847028D
    */
   public GenericObservationProxyVO()
   {

   }

   /**
    * Access method for the theObservationVO property.
    *
    * @return   the current value of the theObservationVO property
    */
   public ObservationVO getTheObservationVO()
   {
      return theObservationVO;
   }

   public ObservationVO getTheObservationVO_s()
   {
      if (this.theObservationVO == null)
        this.theObservationVO = new ObservationVO();
      return theObservationVO;
   }

   /**
    * Sets the value of the theObservationVO property.
    *
    * @param aTheObservationVO the new value of the theObservationVO property
    */
   public void setTheObservationVO(ObservationVO aTheObservationVO)
   {
      theObservationVO = aTheObservationVO;
   }

   /**
    * Access method for the thePersonVO property.
    *
    * @return   the current value of the thePersonVO property
    */
   public PersonVO getThePersonVO()
   {
      return thePersonVO;
   }

   /**
    * Sets the value of the thePersonVO property.
    *
    * @param aThePersonVO the new value of the thePersonVO property
    */
   public void setThePersonVO(PersonVO aThePersonVO)
   {
      thePersonVO = aThePersonVO;
   }

   /**
    * Access method for the theParticipationDT property.
    *
    * @return   the current value of the theParticipationDT property
    */
   public ParticipationDT getTheParticipationDT()
   {
      return theParticipationDT;
   }

   /**
    * Sets the value of the theParticipationDT property.
    *
    * @param aTheParticipationDT the new value of the theParticipationDT property
    */
   public void setTheParticipationDT(ParticipationDT aTheParticipationDT)
   {
      theParticipationDT = aTheParticipationDT;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C33A84702B5
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3C33A8470387
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C33A84703E1
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3C33A8480021
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C33A8480086
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3C33A84800A4
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C33A8480108
    */
   public boolean isItDelete()
   {
    return true;
   }
}
