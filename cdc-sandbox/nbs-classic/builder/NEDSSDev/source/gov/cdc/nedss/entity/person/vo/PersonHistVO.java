//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\cdm\\helpers\\PersonHistVO.java

package gov.cdc.nedss.entity.person.vo;

import  gov.cdc.nedss.util.*;
import java.util.Collection;
import gov.cdc.nedss.entity.person.dt.*;

public class PersonHistVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   public Collection<Object>  theEntityLocParticipationHistDTCollection;
   public PersonHistDT thePersonHistDT;
   public Collection<Object>  thePersonNameHistDTCollection;
   public Collection<Object>  thePersonRaceHistDTCollection;
   public Collection<Object>  thePersonEthnicGroupHistDTCollection;
   public Collection<Object>  theEntityIdHistDTCollection;

   /**
    * @roseuid 3C56FFEE0152
    */
   public PersonHistVO()
   {

   }

   /**
    * Access method for the theEntityLocParticipationHistDTCollection  property.
    *
    * @return   the current value of the theEntityLocParticipationHistDTCollection  property
    */
   public Collection<Object>  getTheEntityLocParticipationHistDTCollection()
   {
      return theEntityLocParticipationHistDTCollection;
   }

   /**
    * Sets the value of the theEntityLocParticipationHistDTCollection  property.
    *
    * @param aTheEntityLocParticipationHistDTCollection  the new value of the theEntityLocParticipationHistDTCollection  property
    */
   public void setTheEntityLocParticipationHistDTCollection(Collection<Object> aTheEntityLocParticipationHistDTCollection)
   {
      theEntityLocParticipationHistDTCollection  = aTheEntityLocParticipationHistDTCollection;
   }

   /**
    * Access method for the thePersonHistDT property.
    *
    * @return   the current value of the thePersonHistDT property
    */
   public PersonHistDT getThePersonHistDT()
   {
      return thePersonHistDT;
   }

   /**
    * Sets the value of the thePersonHistDT property.
    *
    * @param aThePersonHistDT the new value of the thePersonHistDT property
    */
   public void setThePersonHistDT(PersonHistDT aThePersonHistDT)
   {
      thePersonHistDT = aThePersonHistDT;
   }

   /**
    * Access method for the thePersonNameHistDTCollection  property.
    *
    * @return   the current value of the thePersonNameHistDTCollection  property
    */
   public Collection<Object>  getThePersonNameHistDTCollection()
   {
      return thePersonNameHistDTCollection;
   }

   /**
    * Sets the value of the thePersonNameHistDTCollection  property.
    *
    * @param aThePersonNameHistDTCollection  the new value of the thePersonNameHistDTCollection  property
    */
   public void setThePersonNameHistDTCollection(Collection<Object> aThePersonNameHistDTCollection)
   {
      thePersonNameHistDTCollection  = aThePersonNameHistDTCollection;
   }

   /**
    * Access method for the thePersonRaceHistDTCollection  property.
    *
    * @return   the current value of the thePersonRaceHistDTCollection  property
    */
   public Collection<Object>  getThePersonRaceHistDTCollection()
   {
      return thePersonRaceHistDTCollection;
   }

   /**
    * Sets the value of the thePersonRaceHistDTCollection  property.
    *
    * @param aThePersonRaceHistDTCollection  the new value of the thePersonRaceHistDTCollection  property
    */
   public void setThePersonRaceHistDTCollection(Collection<Object> aThePersonRaceHistDTCollection)
   {
      thePersonRaceHistDTCollection  = aThePersonRaceHistDTCollection;
   }

   /**
    * Access method for the thePersonEthnicGroupHistDTCollection  property.
    *
    * @return   the current value of the thePersonEthnicGroupHistDTCollection  property
    */
   public Collection<Object>  getThePersonEthnicGroupHistDTCollection()
   {
      return thePersonEthnicGroupHistDTCollection;
   }

   /**
    * Sets the value of the thePersonEthnicGroupHistDTCollection  property.
    *
    * @param aThePersonEthnicGroupHistDTCollection  the new value of the thePersonEthnicGroupHistDTCollection  property
    */
   public void setThePersonEthnicGroupHistDTCollection(Collection<Object> aThePersonEthnicGroupHistDTCollection)
   {
      thePersonEthnicGroupHistDTCollection  = aThePersonEthnicGroupHistDTCollection;
   }

   /**
    * Access method for the theEntityIdHistDTCollection  property.
    *
    * @return   the current value of the theEntityIdHistDTCollection  property
    */
   public Collection<Object>  getTheEntityIdHistDTCollection()
   {
      return theEntityIdHistDTCollection;
   }

   /**
    * Sets the value of the theEntityIdHistDTCollection  property.
    *
    * @param aTheEntityIdHistDTCollection  the new value of the theEntityIdHistDTCollection  property
    */
   public void setTheEntityIdHistDTCollection(Collection<Object> aTheEntityIdHistDTCollection)
   {
      theEntityIdHistDTCollection  = aTheEntityIdHistDTCollection;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C56FFEE018E
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3C56FFEE02BB
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C56FFEE033D
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3C56FFEE0365
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C56FFEE03E7
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3C56FFEF0027
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C56FFEF00A9
    */
   public boolean isItDelete()
   {
    return true;
   }
}
