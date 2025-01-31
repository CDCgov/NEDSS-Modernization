package gov.cdc.nedss.act.intervention.vo;

import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.intervention.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.locator.dt.*;

import java.util.Collection;
import java.util.ArrayList;
/**
 * Title:        InterventionVO.java
 * Description: Value object for intervention object.
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author       Pradeep Sharma
 * @version 1.0
 */

public class InterventionVO extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   private InterventionDT theInterventionDT = new InterventionDT();
   private Collection<Object> theProcedure1DTCollection;
   private Collection<Object> theSubstanceAdministrationDTCollection;
   private Collection<Object> theActIdDTCollection;
   private Collection<Object> theActivityLocatorParticipationDTCollection;
   //Collections added for Participation and Activity Relationship object association
   public Collection<Object> theParticipationDTCollection;
   public Collection<Object> theActRelationshipDTCollection;

   /**
    * Default constructor
    */
   public InterventionVO()
   {
   }

   /**
    * Constructor containing all attributes
    */
   public InterventionVO(InterventionDT theInterventionDT,
                          Collection<Object> theProcedure1DTCollection,
                          Collection<Object> theSubstanceAdministrationDTCollection,
                          Collection<Object> theActIdDTCollection,
                          Collection<Object> theActivityLocatorParticipationDTCollection)
   {
    this.theInterventionDT = theInterventionDT;
	this.theProcedure1DTCollection=theProcedure1DTCollection;
	this.theSubstanceAdministrationDTCollection=theSubstanceAdministrationDTCollection;
    this.theActIdDTCollection = theActIdDTCollection;
    this.theActivityLocatorParticipationDTCollection = theActivityLocatorParticipationDTCollection;
    setItNew(true);
   }

    /**
     * compare to find if two objects are equal
     * @param objectname1 : first object
     * @param objectname2 : second object
     * @param voClass : Class
     * @return boolean value
     */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * sets itDirty
    * @param itDirty : boolean value
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;
   }

   /**
    * gets itDirty
    * @return : boolean value
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    * sets itNew
    * @param itNew : boolean value
    */
   public void setItNew(boolean itNew)
   {
      this.itNew = itNew;
   }

   /**
    * gets itNew
    * @return : boolean value
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * gets itDelete
    * @return : boolean value
    */
    public boolean isItDelete()
   {
    return itDelete;
   }

   /**
    * sets itDelete
    * @param itDelete : boolean value
    */
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

   /**
    * Gets InterventionDT
    * @return : InterventionDT
    */
   public InterventionDT getTheInterventionDT()
   {
      return theInterventionDT;
   }

   /**
    * sets theInterventionDT
    * @param theInterventionDT : Collection
    */
   public void setTheInterventionDT(InterventionDT theInterventionDT)
   {
      this.theInterventionDT = theInterventionDT;
      setItDirty(true);
   }

   /**
    * gets Procedure1DTCollection
    * @return : Collection
    */
    public Collection<Object> getTheProcedure1DTCollection()
   {
      return theProcedure1DTCollection;
   }

   /**
    * sets theProcedure1DT
    * @param theProcedure1DT : Collection
    */
   public void setTheProcedure1DTCollection(Collection<Object> theProcedure1DT)
   {
      this.theProcedure1DTCollection = theProcedure1DT;
      setItDirty(true);
   }

    /**
    * gets SubstanceAdministrationDTCollection
    * @return : Collection
    */
   public Collection<Object> getTheSubstanceAdministrationDTCollection()
   {
      return theSubstanceAdministrationDTCollection;
   }

   /**
    * sets theSubstanceAdministrationDT
    * @param theSubstanceAdministrationDT : Collection
    */
   public void setTheSubstanceAdministrationDTCollection(Collection<Object> theSubstanceAdministrationDT)
   {
	  this.theSubstanceAdministrationDTCollection=theSubstanceAdministrationDT;
          setItDirty(true);
   }

    /**
    * gets ActIdDTCollection
    * @return : Collection
    */
   public Collection<Object> getTheActIdDTCollection()
   {
      return theActIdDTCollection;
   }

   /**
    * sets ActDTCollection
    * @param theActIdDTCollection  : Collection
    */
   public void setTheActIdDTCollection(Collection<Object> theActIdDTCollection)
   {
      this.theActIdDTCollection  = theActIdDTCollection;
      setItDirty(true);
   }

  /**
    * gets ActivityLocatorParticipationDTCollection
    * @return : Collection
    */
    public Collection<Object> getTheActivityLocatorParticipationDTCollection() {
    return theActivityLocatorParticipationDTCollection;
  }

   /**
    * sets ActivityLocatorParticipationDTCollection
    * @param theActivityLocatorParticipationDTCollection  : Collection
    */
  public void setTheActivityLocatorParticipationDTCollection(Collection<Object> theActivityLocatorParticipationDTCollection) {
    this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
    setItDirty(true);
  }

  /**
    * gets ParticipationDTCollection
    * @return : Collection
    */
    public Collection<Object> getTheParticipationDTCollection()
   {
      return theParticipationDTCollection;
   }

   /**
    * sets aTheParticipationDTCollection
    * @param aTheParticipationDTCollection  : Collection
    */
   public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
   {
     theParticipationDTCollection  = aTheParticipationDTCollection;
     setItDirty(true);
   }


  /**
    * gets ActRelationshipDTCollection
    * @return : Collection
    */
   public Collection<Object> getTheActRelationshipDTCollection()
   {
     return theActRelationshipDTCollection;
   }

   /**
    * sets ActRelationshipDTCollection
    * @param aTheActRelationshipDTCollection  : Collection
    */
    public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection)
    {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
      setItDirty(true);
    }

   /**
    * gets ActIdDT_s
    * @return : ActIdDT
    */
   public ActIdDT getActIdDT_s(int index) {
      // this should really be in the constructor
      if (this.theActIdDTCollection  == null)
          this.theActIdDTCollection = new ArrayList<Object> ();

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
    * gets ActLocatorParticipationDT_s
    * @return : ActLocatorParticipationDT
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


   /**
    * gets Procedure1DT_s
    * @return : Procedure1DT
    */
  public Procedure1DT getProcedure1DT_s(int index) {
      // this should really be in the constructor
      if (this.theProcedure1DTCollection  == null)
          this.theProcedure1DTCollection  = new ArrayList<Object> ();

      int currentSize = this.theProcedure1DTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theProcedure1DTCollection.toArray();

          Object tempObj  = tempArray[index];

          Procedure1DT tempDT = (Procedure1DT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       Procedure1DT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new Procedure1DT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theProcedure1DTCollection.add(tempDT);
        }

        return tempDT;
  }

   /**
    * gets SubstanceAdministrationDT_s
    * @return : SubstanceAdministrationDT
    */
  public SubstanceAdministrationDT getSubstanceAdministrationDT_s(int index) {
      // this should really be in the constructor
      if (this.theSubstanceAdministrationDTCollection  == null)
          this.theSubstanceAdministrationDTCollection  = new ArrayList<Object> ();

      int currentSize = this.theSubstanceAdministrationDTCollection.size();

      // check if we have a this many DTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.theSubstanceAdministrationDTCollection.toArray();

          Object tempObj  = tempArray[index];

          SubstanceAdministrationDT tempDT = (SubstanceAdministrationDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }

       SubstanceAdministrationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new SubstanceAdministrationDT();
          tempDT.setItNew(true);  // this should be done in the constructor of the DT
          this.theSubstanceAdministrationDTCollection.add(tempDT);
        }

        return tempDT;
  }
}
