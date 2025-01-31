/**
 * Title: EntityGroupVO helper class.
 * Description: A helper class for EntityGroup value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.entitygroup.vo;

import  gov.cdc.nedss.util.*;
import java.util.Collection;
import java.util.ArrayList;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.entitygroup.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;

public class EntityGroupVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   /**
    * Variable to keep track of changes in the Value Object.
    */
   //private boolean itDirty = false;

   /**
    * Variable to keep track whether Value Object is new or not.
    */
   //private boolean itNew = true;

   /**
    * Data Table of Value Object
    */
   public Collection<Object> theEntityLocatorParticipationDTCollection;
   public Collection<Object> theEntityIdDTCollection;
   private EntityGroupDT theEntityGroupDT = new EntityGroupDT();
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theRoleDTCollection;

   /**
    *
    */
   public EntityGroupVO()
   {
   }

   /**
    * Sets the value of the EntityGroupVO property.
    * @param theEntityGroupDT the new value of the theEntityGroupDT property
    * @param theEntityLocatorParticipationDTCollection  the new value of the theEntityLocatorParticipationDTCollection  property
    * @param theEntityIdDTCollection  the new value of the theEntityIdDTCollection  property
    * @param theParticipationDTCollection  the new value of the theParticipationDTCollection  property
    * @param theRoleDTCollection  the new value of the theRoleDTCollection  property
    */
   public EntityGroupVO(EntityGroupDT theEntityGroupDT,
                    Collection<Object> theEntityLocatorParticipationDTCollection,
                    Collection<Object> theEntityIdDTCollection,
                    Collection<Object> theParticipationDTCollection,
                    Collection<Object> theRoleDTCollection
                    ) {
      this.theEntityGroupDT = theEntityGroupDT;
      this.theEntityLocatorParticipationDTCollection  = theEntityLocatorParticipationDTCollection;
      this.theEntityIdDTCollection  = theEntityIdDTCollection;
   }

   /**
    * Access method for the theEntityLocatorParticipationDTCollection  property.
    * @return   the current value of the theEntityLocatorParticipationDTCollection  property
    */
   public Collection<Object> getTheEntityLocatorParticipationDTCollection()
   {
      return theEntityLocatorParticipationDTCollection;
   }

   /**
    * Sets the value of the theEntityLocatorParticipationDTCollection  property.
    * @param aTheEntityLocatorParticipationDTCollection  the new value of the theEntityLocatorParticipationDTCollection  property
    */
   public void setTheEntityLocatorParticipationDTCollection(Collection<Object> aTheEntityLocatorParticipationDTCollection)
   {
      this.theEntityLocatorParticipationDTCollection  = aTheEntityLocatorParticipationDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theEntityIdDTCollection  property.
    * @return   the current value of the theEntityIdDTCollection  property
    */
   public Collection<Object> getTheEntityIdDTCollection()
   {
      return theEntityIdDTCollection;
   }

   /**
    * Sets the value of the theEntityIdDTCollection  property.
    * @param aTheEntityIdDTCollection  the new value of the theEntityIdDTCollection  property
    */
   public void setTheEntityIdDTCollection(Collection<Object> aTheEntityIdDTCollection)
   {
      this.theEntityIdDTCollection  = aTheEntityIdDTCollection;
      setItDirty(true);
   }

   /**
    * Access method for the theEntityGroupDT property.
    * @return   the current value of the theEntityGroupDTCollection  property
    */
   public EntityGroupDT getTheEntityGroupDT()
   {
      return theEntityGroupDT;
   }

   /**
    * Sets the value of the theEntityGroupDTCollection  property.
    * @param aTheEntityGroupDTCollection  the new value of the theEntityGroupDTCollection  property
    */
   public void setTheEntityGroupDT(EntityGroupDT theEntityGroupDT)
   {
      this.theEntityGroupDT = theEntityGroupDT;
      setItDirty(true);
   }

   /**
    * Access method for the theParticipationDTCollection  property.
    * @return   the current value of the theParticipationDTCollection  property
    */
   public Collection<Object> getTheParticipationDTCollection()
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
   }

   /**
    * Access method for the theRoleDTCollection  property.
    * @return   the current value of the theRoleDTCollection  property
    */
   public Collection<Object> getTheRoleDTCollection()
   {
      return theRoleDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
   public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
   {
      this.theRoleDTCollection  = aTheRoleDTCollection;
   }

   /**
    * This method compares two objects and returns the results
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return   the result of the comparison
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Sets the value of the itDirty property
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
    * Sets the value of the itNew property
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
    * Access method for the itDelete property.
    * @return   the current value of the itDelete property
    */
   public boolean isItDelete() {
     return itDelete;
   }

   /**
    * Sets the value of the itDelete property
    * @param itDelete the new value of the itDelete property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }


   /**
    * Access method for a selected EntityLocatorParticipationDT object
    * @param index the index value of the EntityLocatorParticipationDT object in theEntityLocatorParticipationDTCollection  property
    * @return   the selected EntityLocatorParticipationDT object
    */
   public EntityLocatorParticipationDT getEntityLocatorParticipationDT(int index) {
      // this should really be in the constructor
      if (this.theEntityLocatorParticipationDTCollection  == null)
          this.theEntityLocatorParticipationDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theEntityLocatorParticipationDTCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theEntityLocatorParticipationDTCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT) tempObj;

      //##!! System.out.println("in get DT, retrieved DT: " +tempDT);
          return tempDT;
        }
        catch (Exception e) {
       //##!! System.out.println(e);
       } // do nothing just continue
      }

       EntityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new EntityLocatorParticipationDT();
          this.theEntityLocatorParticipationDTCollection.add(tempDT);
      //##!! System.out.println("in get DT, retrieved DT: " +tempDT);
        }

        return tempDT;
  }


   /**
    * Access method for a selected EntityIdDT object
    * @param index the index value of the EntityIdDT object in theEntityIdDTCollection  property
    * @return   the selected EntityIdDT object
    */
  public EntityIdDT getEntityIdDT(int index) {
      // this should really be in the constructor
      if (this.theEntityIdDTCollection  == null)
          this.theEntityIdDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theEntityIdDTCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theEntityIdDTCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityIdDT tempDT = (EntityIdDT) tempObj;

      //##!! System.out.println("in get DT, retrieved DT: " +tempDT);
          return tempDT;
        }
        catch (Exception e) {
       //##!! System.out.println(e);
       } // do nothing just continue
      }

       EntityIdDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new EntityIdDT();
          this.theEntityIdDTCollection.add(tempDT);
      //##!! System.out.println("in get DT, retrieved DT: " +tempDT);
        }

        return tempDT;
  }

}
