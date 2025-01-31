/**
 * Title: MaterialVO helper class.
 * Description: A helper class for material value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.material.vo;

import gov.cdc.nedss.util.*;
import java.util.Collection;
import java.util.ArrayList;

import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.person.dt.*;

public class MaterialVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   /**
    * Variable to keep track of changes in the Value Object.
    */
   private boolean itDirty = false;

   /**
    * Variable to keep track whether Value Object is new or not.
    */
   private boolean itNew = true;

   /**
    * Data Table of Value Object
    */
   private MaterialDT theMaterialDT = new MaterialDT ();

   /**
    * Related Locators
    */
   private Collection<Object> theEntityLocatorParticipationDTCollection;

   /**
    * Other Related Entities
    */
   private Collection<Object> theEntityIdDTCollection;

   /**
    * collections for role and participation object association added by John Park
    */
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theRlDTCollection;
   private Collection<Object> theManufacturedMaterialDTCollection;

   /**
    * Sets the value of the MaterialVO property.
    * @param theMaterialDT the new value of the theMaterialDT property
    * @param theEntityLocatorParticipationDTCollection  the new value of the theEntityLocatorParticipationDTCollection  property
    * @param theEntityIdDTCollection  the new value of the theEntityIdDTCollection  property
    * @param theParticipationDTCollection  the new value of the theParticipationDTCollection  property
    * @param theRoleDTCollection  the new value of the theRoleDTCollection  property
    */
    public MaterialVO(MaterialDT theMaterialDT,
                    Collection<Object> theEntityLocatorParticipationDTCollection,
                    Collection<Object> theEntityIdDTCollection,
                    Collection<Object> theParticipationDTCollection,
                    Collection<Object> theRoleDTCollection
                    ) {
      this.theMaterialDT = theMaterialDT;
      this.theEntityLocatorParticipationDTCollection  = theEntityLocatorParticipationDTCollection;
      this.theEntityIdDTCollection  = theEntityIdDTCollection;
      this.theParticipationDTCollection  = theParticipationDTCollection;
      this.theRlDTCollection  = theRoleDTCollection;
      setItNew(true);
   }

   /**
    * Sets the itNew property value of the MaterialVO.
    */
   public MaterialVO()
   {
      setItNew(true);
   }

   /**
    * Access method for the theMaterialDT property.
    * @return   the current value of the theMaterialDT property
    */
   public MaterialDT getTheMaterialDT()
   {
      return theMaterialDT;
   }

   /**
    * Sets the value of the theMaterialDT property.
    * @param aTheMaterialDT the new value of the theMaterialDT property
    */
   public void setTheMaterialDT(MaterialDT aTheMaterialDT)
   {
      this.theMaterialDT = aTheMaterialDT;
      setItDirty(true);
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
      theEntityIdDTCollection  = aTheEntityIdDTCollection;
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
        theParticipationDTCollection  = aTheParticipationDTCollection;
   }

   /**
    * Access method for the theRoleDTCollection  property.
    * @return   the current value of the theRoleDTCollection  property
    */
   public Collection<Object> getTheRoleDTCollection()
   {
     return theRlDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
   public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
   {
      theRlDTCollection  = aTheRoleDTCollection;
   }

   /**
    * Access method for the theManufacturedMaterialDTCollection  property.
    * @return   the current value of the theManufacturedMaterialDTCollection  property
    */
   public Collection<Object> getTheManufacturedMaterialDTCollection()
   {
      return theManufacturedMaterialDTCollection;
   }

   /**
    * Sets the value of the theManufacturedMaterialDTCollection  property.
    * @param aTheManufacturedMaterialDTCollection  the new value of the theManufacturedMaterialDTCollection  property
    */
   public void setTheManufacturedMaterialDTCollection(Collection<Object> aTheManufacturedMaterialDTCollection)
   {
      theManufacturedMaterialDTCollection  = aTheManufacturedMaterialDTCollection;
   }

   /**
    * This method compares two objects and returns the results
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return   the result of the comparison
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
      voClass =  ((PersonDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
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
    * Access method for a selected EntityLocatorParticipationDT object
    * @param index the index value of the EntityLocatorParticipationDT object in theEntityLocatorParticipationDTCollection  property
    * @return   the selected EntityLocatorParticipationDT object
    */
   public EntityLocatorParticipationDT getEntityLocatorParticipationDT_s(int index)
   {
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
   public EntityIdDT getEntityIdDT_s(int index)
   {
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

    public ManufacturedMaterialDT getManufacturedMaterialDT_s(int index) {
      // this should really be in the constructor
      if (this.theManufacturedMaterialDTCollection  == null)
          this.theManufacturedMaterialDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theManufacturedMaterialDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theManufacturedMaterialDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ManufacturedMaterialDT tempDT = (ManufacturedMaterialDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ManufacturedMaterialDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ManufacturedMaterialDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theManufacturedMaterialDTCollection.add(tempDT);
        }

        return tempDT;
  }

}
