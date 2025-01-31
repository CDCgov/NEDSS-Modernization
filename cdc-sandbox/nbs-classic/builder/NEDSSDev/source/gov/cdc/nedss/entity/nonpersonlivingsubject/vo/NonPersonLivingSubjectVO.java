/**
 * Title: NonPersonLivingSubjectVO helper class.
 * Description: A helper class for non-person living subject value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.nonpersonlivingsubject.vo;

import  gov.cdc.nedss.util.*;
import java.util.Collection;
import java.util.ArrayList;

import gov.cdc.nedss.entity.nonpersonlivingsubject.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;

public class NonPersonLivingSubjectVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
//   private Boolean itDirty = false; // defined in AbstractVO
//   private Boolean itNew = true; // defined in AbstractVO
   public NonPersonLivingSubjectDT theNonPersonLivingSubjectDT = new NonPersonLivingSubjectDT();
   public Collection<Object> theEntityLocatorParticipationDTCollection;
   public Collection<Object> theEntityIdDTCollection;
   //collections for role and participation object association added by John Park
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theRoleDTCollection;

   /**
    *
    */
   public NonPersonLivingSubjectVO()
   {
   }

   /**
    * Access method for the theNonPersonLivingSubjectDT property.
    * @return   the current value of the theNonPersonLivingSubjectDT property
    */
   public NonPersonLivingSubjectDT getTheNonPersonLivingSubjectDT()
   {
      return theNonPersonLivingSubjectDT;
   }

   /**
    * Sets the value of theNonPersonLivingSubjectDT the property
    * @param theNonPersonLivingSubjectDT the new value of the theNonPersonLivingSubjectDT property
    */
   public void setTheNonPersonLivingSubjectDT(NonPersonLivingSubjectDT theNonPersonLivingSubjectDT)
   {
      this.theNonPersonLivingSubjectDT = theNonPersonLivingSubjectDT;
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
      theEntityLocatorParticipationDTCollection  = aTheEntityLocatorParticipationDTCollection;
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
      theEntityIdDTCollection = aTheEntityIdDTCollection;
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
    return true;
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
    * Sets the value of the itNew property
    * @param itNew the new value of the itNew property
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * Access method for the itDelete property.
    * @return   the current value of the itDelete property
    */
    public boolean isItDelete()
   {
    return itDelete;
   }

   /**
    * Sets the value of the itDelete property
    * @param itDelete the new value of the itDelete property
    */
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
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
     theParticipationDTCollection  = aTheParticipationDTCollection;
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
      theRoleDTCollection  = aTheRoleDTCollection;
    }

   /**
    * Access method for a selected EntityLocatorParticipationDT object
    * @param index the index value of the EntityLocatorParticipationDT object in theEntityLocatorParticipationDTCollection  property
    * @return   the selected EntityLocatorParticipationDT object
    */
   public EntityLocatorParticipationDT getEntityLocatorParticipationDT_s(int index) {
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
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theEntityLocatorParticipationDTCollection.add(tempDT);
        }

        return tempDT;
  }

   /**
    * Access method for a selected EntityIdDT object
    * @param index the index value of the EntityIdDT object in theEntityIdDTCollection  property
    * @return   the selected EntityIdDT object
    */
   public EntityIdDT getEntityIdDT_s(int index) {
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
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theEntityIdDTCollection.add(tempDT);
        }

        return tempDT;
  }
}
