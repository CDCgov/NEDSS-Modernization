package gov.cdc.nedss.act.file.vo;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.file.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.locator.dt.*;

import java.util.Collection;
import java.util.ArrayList;

public class WorkupVO extends AbstractVO implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

   public WorkupDT theWorkupDT = new WorkupDT();
   public Collection<Object> theActivityLocatorParticipationDTCollection;
   public Collection<Object>  theActIdDTCollection;
   //Collections added for Participation and Activity Relationship object association
   public Collection<Object>  theParticipationDTCollection;
   public Collection<Object>  theActRelationshipDTCollection;
   /**
   @roseuid 3BB88E540343
    */
   public WorkupVO()
   {
      setItNew(true);
   }

   /**
    * Constructors containing all attributes.
    */
   public WorkupVO(WorkupDT theWorkupDT,
                   Collection<Object>  theActivityLocatorParticipationDTCollection,
                   Collection<Object>  theActivityIdDTCollection)
   {
         this.theWorkupDT = theWorkupDT;
         this.theActivityLocatorParticipationDTCollection=theActivityLocatorParticipationDTCollection;
         this.theActIdDTCollection=theActivityIdDTCollection;
         setItNew(true);
   }

   /**
    * will compare the two java objects passed
    * @param objectname1 -- First object to be compared
    * @param objectname2 -- Second object to be compared
    * @param voClass -- Value Object class name
    * @return boolean -- Boolean value
    * @roseuid 3BB8D63003C7
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * Set the dirty flag for Object
    * @param itDirty -- Boolean value for dirty flag
    * @roseuid 3BB8D6310039
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
    * Check dirty flag of object
    * @return boolean -- return the dirty flag for object
    * @roseuid 3BB8D6310057
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * Set the New flag for Object
    * @param itNew -- Boolean value for new flag
    * @roseuid 3BB8D6310061
    */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

   /**
    * Check the isNew flag for object
    * @return boolean -- return the isNew flag for Object
    * @roseuid 3BB8D6310089
    */
   public boolean isItNew()
   {
    return itNew;
   }
   /**
    * Check the isDelete flag for object
    * @return boolean -- return the isDelete flag for Object
    * @roseuid 3BB8D6310089
    */
    public boolean isItDelete()
   {
    return itDelete;
   }
   /**
    * Set the delete flag for Object
    * @param itDelete -- Boolean value for delete flag
    * @roseuid 3BB8D6310061
    */
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }
  /**
    * Access method for the TheActivityLocatorParticipationDTCollection  property.
    *
    * @return theActivityLocatorParticipationDTCollection  -- the current value of the TheActivityLocatorParticipationDTCollection  property
    */
  public Collection<Object>  getTheActivityLocatorParticipationDTCollection() {
    return theActivityLocatorParticipationDTCollection;
  }
  /**
    * Sets the value of the TheActivityLocatorParticipationDTCollection  property.
    *
    * @param theActivityLocatorParticipationDTCollection  -- The new value of the TheActivityLocatorParticipationDTCollection  property
  */
  public void setTheActivityLocatorParticipationDTCollection(Collection<Object>  theActivityLocatorParticipationDTCollection) {
    this.theActivityLocatorParticipationDTCollection = theActivityLocatorParticipationDTCollection;
    setItDirty(true);
  }
  /**
    * Access method for the TheWorkupDT property.
    *
    * @return theWorkupDT -- the current value of the TheWorkupDT property
    */
  public WorkupDT getTheWorkupDT() {
    return theWorkupDT;
  }
  /**
    * Sets the value of the TheWorkupDT property.
    *
    * @param theWorkupDT -- The new value of the TheWorkupDT property
  */
  public void setTheWorkupDT(WorkupDT theWorkupDT) {
    this.theWorkupDT = theWorkupDT;
    setItDirty(true);
  }
  /**
    * Access method for  theActivityIdDTCollection  property.
    *
    * @return theActIdDTCollection  -- the current value of the theActIdDTCollection  property
    */
  public Collection<Object>  getTheActivityIdDTCollection() {
    return theActIdDTCollection;
  }
  /**
    * Sets the value of the theActIdDTCollection  property.
    *
    * @param theActivityIdDTCollection  -- The new value of the theActIdDTCollection  property
  */
  public void setTheActivityIdDTCollection(Collection<Object>  theActivityIdDTCollection) {
    this.theActIdDTCollection  = theActivityIdDTCollection;
    setItDirty(true);
  }

  /**
    * Access method for  theParticipationDTCollection  property.
    *
    * @return theParticipationDTCollection  -- the current value of the theParticipationDTCollection  property
    */
   public Collection<Object>  getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * Sets the value of the theParticipationDTCollection  property.
    *
    * @param aTheParticipationDTCollection  -- the new value of the theParticipationDTCollection  property
    */
   public void setTheParticipationDTCollection(Collection<Object>  aTheParticipationDTCollection)
   {
     theParticipationDTCollection = aTheParticipationDTCollection;
   }
   /**
    * Access method for the theRoleDTCollection  property.
    *
    * @return  theActRelationshipDTCollection  -- the current value of the theRoleDTCollection  property
    */
   public Collection<Object>  getTheActRelationshipDTCollection()
   {
     return theActRelationshipDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    *
    * @param aTheRoleDTCollection  -- the new value of the theRoleDTCollection  property
    */
    public void setTheActRelationshipDTCollection(Collection<Object>  aTheActRelationshipDTCollection)
    {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    }
/**
 * Access the actIdDT_s property with the passed sequence no.
 * @param index -- the sequence no. to Access actIdDT_s property
 * @return tempDT -- The ActIdDT
 */
 public ActIdDT getActIdDT_s(int index) {
      // this should really be in the constructor
      if (this.theActIdDTCollection  == null)
          this.theActIdDTCollection  = new ArrayList<Object>  ();

      int currentSize = this.theActIdDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theActIdDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ActIdDT tempDT = (ActIdDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ActIdDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ActIdDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theActIdDTCollection.add(tempDT);
        }

        return tempDT;
  }

/**
 * Access the getActLocatorParticipationDT_s property with the passed sequence no.
 * @param index -- the sequence no. to Access getActLocatorParticipationDT_s property
 * @return tempDT -- The ActivityLocatorParticipationDT
 */
  public ActivityLocatorParticipationDT getActLocatorParticipationDT_s(int index) {
      // this should really be in the constructor
      if (this.theActivityLocatorParticipationDTCollection  == null)
          this.theActivityLocatorParticipationDTCollection  = new ArrayList<Object>  ();

      int currentSize = this.theActivityLocatorParticipationDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theActivityLocatorParticipationDTCollection.toArray();

          Object tempObj  = tempArray[index];

          ActivityLocatorParticipationDT tempDT = (ActivityLocatorParticipationDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       ActivityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new ActivityLocatorParticipationDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theActivityLocatorParticipationDTCollection.add(tempDT);
        }

        return tempDT;
  }
}
