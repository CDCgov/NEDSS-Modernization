package gov.cdc.nedss.act.referral.vo;

/**
 * Title:        RefferalVO.java
 * Description:  A Value Object class which has association with few DTs
 * Copyright:    Copyright (c) 2001
 * Company:      Computer Sciences Corporation
 * @author:      Nedss Development Team
 * @version      1.0
 */

import  gov.cdc.nedss.util.*;
import java.util.Collection;
import java.util.ArrayList;
import gov.cdc.nedss.act.referral.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.locator.dt.*;

public class ReferralVO extends AbstractVO implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
  // private boolean itDirty = false;
  // private boolean itNew = true;
  // private boolean itDelete = false;

   public ReferralDT theReferralDT = new ReferralDT();
   public Collection<Object> theActivityLocatorParticipationDTCollection;
   public Collection<Object> theActIdDTCollection;
   //Collections added for Participation and Activity Relationship object association
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theActRelationshipDTCollection;
   /**
   @roseuid 3BB88E540343
    */
   public ReferralVO()
   {

   }

   /**
    * Constructors containing all attributes.
    */
   public ReferralVO(ReferralDT theReferralDT,
                   Collection<Object> theActivityLocatorParticipationDTCollection,
                   Collection<Object> theActivityIdDTCollection)
   {
         this.theReferralDT = theReferralDT;
         this.theActivityLocatorParticipationDTCollection=theActivityLocatorParticipationDTCollection;
         this.theActIdDTCollection=theActivityIdDTCollection;
         setItNew(true);
   }

  /**
   * Checks whether the objects are equal
   * @param objectname1   the Object
   * @param objectname2   the Object
   * @param voClass       the class
   * @return boolean
   * @roseuid 3BB8D63003C7
   */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

  /**
   * Setting the Dirty Flag
   * @param itDirty   the boolean
   * @roseuid 3BB8D6310039
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
   *  retuning the value of Dirty flag
   * @return boolean
   * @roseuid 3BB8D6310057
   */
   public boolean isItDirty()
   {
    return itDirty;
   }

  /**
   * Setting the value of New flag
   * @param itNew  the boolean
   * @roseuid 3BB8D6310061
   */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

  /**
   * Returns the value of New flag
   * @return boolean
   * @roseuid 3BB8D6310089
   */
   public boolean isItNew()
   {
    return itNew;
   }
   /**
   * Returns the value of Delete flag
   * @return  the boolean
   */
    public boolean isItDelete()
   {
    return itDelete;
   }

  /**
    * Sets the value of theActivityLocatorParticipationDTCollection  property
    * @param itDelete
    */
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

 /**
  * Access method for theActivityLocatorParticipationDTCollection   property
  * @return  the Collection<Object>  of ActivityLocatorParticipationDTs
  */
  public Collection<Object> getTheActivityLocatorParticipationDTCollection() {
    return theActivityLocatorParticipationDTCollection;
  }

/**
 * Sets the value of theActivityLocatorParticipationDTCollection  property
 * @param theActivityLocatorParticipationDTCollection
 */
  public void setTheActivityLocatorParticipationDTCollection(Collection<Object> theActivityLocatorParticipationDTCollection) {
    this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
    setItDirty(true);
  }
 /**
 * Access method for theReferralDT property
 * @return theReferralDT  the  ReferralDT object
 */
  public ReferralDT getTheReferralDT() {
    return theReferralDT;
  }
 /**
 * Sets the value of theReferralDT property
 * @param theReferralDT  the ReferralDT object
 */
  public void setTheReferralDT(ReferralDT theReferralDT) {
    this.theReferralDT = theReferralDT;
    setItDirty(true);
  }

 /**
 * Access method for theActivityIdDTCollection  property
 * @return  theActIdDTCollection   the Collection<Object>  of ActivityIdDTs
 */
  public Collection<Object> getTheActivityIdDTCollection() {
    return theActIdDTCollection;
  }
 /**
  * Sets the value of theActivityIdDTCollection  property
  * @param theActivityIdDTCollection    the collection of ActivityIdDTs
  */
  public void setTheActivityIdDTCollection(Collection<Object> theActivityIdDTCollection) {
    this.theActIdDTCollection  = theActivityIdDTCollection;
    setItDirty(true);
  }
   //Role and participation collection entered by John Park

 /**
  * Sets the value of the theParticipationDTCollection  property.
  *
  * @param aTheParticipationDTCollection  the new value of the theParticipationDTCollection  property
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
    * Access method for the theActRelationshipDT property.
    * @return theActRelationshipDTCollection   the Collection
    */
   public Collection<Object> getTheActRelationshipDTCollection()
   {
     return theActRelationshipDTCollection;
   }

   /**
    * Sets the value of the theRoleDTCollection  property.
    *
    * @param aTheRoleDTCollection  the new value of the theRoleDTCollection  property
    */
    public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
    {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    }

 /**
  * Returns the instance of ActIdDT based on the index which you pass
  * @param index   int - the index of the Collection
  * @return  ActIdDT  the object
  */
 public ActIdDT getActIdDT_s(int index) {
      // this should really be in the constructor
      if (this.theActIdDTCollection  == null)
          this.theActIdDTCollection  = new ArrayList<Object> ();

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
   * Returns the instance of ActivityLocatorParticipationDT based on the index which you pass
   * @param index  the index of the collection
   * @return   the ActivityLocatorParticipationDT object
   */

  public ActivityLocatorParticipationDT getActLocatorParticipationDT_s(int index) {
      // this should really be in the constructor
      if (this.theActivityLocatorParticipationDTCollection  == null)
          this.theActivityLocatorParticipationDTCollection  = new ArrayList<Object> ();

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
