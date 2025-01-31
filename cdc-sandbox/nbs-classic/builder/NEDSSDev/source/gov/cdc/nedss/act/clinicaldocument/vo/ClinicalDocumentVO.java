package gov.cdc.nedss.act.clinicaldocument.vo;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.clinicaldocument.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import java.util.Collection;
import java.util.ArrayList;


public class ClinicalDocumentVO extends AbstractVO implements java.io.Serializable
{
  // private boolean itDirty = false;
  // private boolean itNew = true;
  // private boolean itDelete = false;
   private static final long serialVersionUID = 1L;
   public ClinicalDocumentDT theClinicalDocumentDT = new ClinicalDocumentDT();
   public Collection<Object> theActivityLocatorParticipationDTCollection;
   public Collection<Object> theActIdDTCollection;
   //Collections added for Participation and Activity Relationship object association
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theActRelationshipDTCollection;

   /**
   @roseuid 3BB88E540343
    */
   public ClinicalDocumentVO()
   {
   }

   /**
    * Constructors containing all attributes.
    */
   public ClinicalDocumentVO(ClinicalDocumentDT theClinicalDocumentDT,
                   Collection<Object> theActivityLocatorParticipationDTCollection,
                   Collection<Object> theActivityIdDTCollection)
   {
         this.theClinicalDocumentDT = theClinicalDocumentDT;
         this.theActivityLocatorParticipationDTCollection=theActivityLocatorParticipationDTCollection;
         this.theActIdDTCollection=theActivityIdDTCollection;
         setItNew(true);
   }

   /**
   @param objectname1
   @param objectname2
   @param voClass
   @return boolean
   @roseuid 3BB8D63003C7
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
   @param itDirty
   @roseuid 3BB8D6310039
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
   @return boolean
   @roseuid 3BB8D6310057
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
   @param itNew
   @roseuid 3BB8D6310061
    */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

   /**
   @return boolean
   @roseuid 3BB8D6310089
    */
   public boolean isItNew()
   {
    return itNew;
   }
    public boolean isItDelete()
   {
    return itDelete;
   }
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

  public Collection<Object> getTheActivityLocatorParticipationDTCollection() {
    return theActivityLocatorParticipationDTCollection;
  }
  public void setTheActivityLocatorParticipationDTCollection(Collection<Object> theActivityLocatorParticipationDTCollection) {
    this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
    setItDirty(true);
  }
  public ClinicalDocumentDT getTheClinicalDocumentDT() {
    return theClinicalDocumentDT;
  }
  public void setTheClinicalDocumentDT(ClinicalDocumentDT theClinicalDocumentDT) {
    this.theClinicalDocumentDT = theClinicalDocumentDT;
    setItDirty(true);
  }
  public Collection<Object> getTheActivityIdDTCollection() {
    return theActIdDTCollection;
  }
  public void setTheActivityIdDTCollection(Collection<Object> theActivityIdDTCollection) {
    this.theActIdDTCollection  = theActivityIdDTCollection;
    setItDirty(true);
  }
  //ActRelationshiop and participation collection entered by John Park
   public Collection<Object> getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * Sets the value of the theParticipationDTCollection  property.
    */
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
     theParticipationDTCollection  = aTheParticipationDTCollection;
   }
   /**
    * Access method for the theRoleDTCollection  property.
    */
   public Collection<Object> getTheActRelationshipDTCollection()
   {
     return theActRelationshipDTCollection;
   }

   /**
    * Sets the value of the ActRelationship property.
    */
    public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
    {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    }

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


  public ActivityLocatorParticipationDT getActLocatorParticipationDT_s(int index) {
      // this should really be in the constructor
      if (this.theActivityLocatorParticipationDTCollection  == null)
          this.theActivityLocatorParticipationDTCollection = new ArrayList<Object> ();

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
