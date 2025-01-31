package gov.cdc.nedss.act.treatment.vo;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.treatment.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.locator.dt.*;

import java.util.Collection;
import java.util.ArrayList;

/**
 * Title:        TreatmentVO.java
 * Description:  Value object for the Treatment object.
 * Copyright:    Copyright (c) 2001
 * Company:      Computer Sciences Corporation
 * @author       Aaron Aycock
 * @version      1.0
 */

public class TreatmentVO
    extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private TreatmentDT theTreatmentDT = new TreatmentDT();
  private Collection<Object> theTreatmentProcedureDTCollection;
  private Collection<Object> theTreatmentAdmDTCollection;
  private Collection<Object> theActIdDTCollection;
  private Collection<Object> theActivityLocatorParticipationDTCollection;
  //Collections added for Participation and Activity Relationship object association
  public Collection<Object> theParticipationDTCollection;
  public Collection<Object> theActRelationshipDTCollection;

  /**
   * @roseuid 3EAEC6C100B9
   */
  public TreatmentVO() {
  }

  /**
   * Constructor containing all attributes
   */
  public TreatmentVO(TreatmentDT theTreatmentDT,
                     Collection<Object> theTreatmentProcedureDTCollection,
                     Collection<Object> theTreatmentAdministeredDTCollection,
                     Collection<Object> theActIdDTCollection,
                     Collection<Object> theActivityLocatorParticipationDTCollection) {
    this.theTreatmentDT = theTreatmentDT;
    this.theTreatmentProcedureDTCollection  = theTreatmentProcedureDTCollection;
    this.theTreatmentAdmDTCollection  =
        theTreatmentAdministeredDTCollection;
    this.theActIdDTCollection  = theActIdDTCollection;
    this.theActivityLocatorParticipationDTCollection  =
        theActivityLocatorParticipationDTCollection;
    setItNew(true);
  }

  /**
   * @param objectname1
   * @param objectname2
   * @param voClass
   * @return boolean
   * @roseuid 3EAECE150221
   */
  public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
    return true;
  }

  /**
   * @param itDirty
   * @roseuid 3EAECE1601A4
   */
  public void setItDirty(boolean itDirty) {
    this.itDirty = itDirty;

  }

  /**
   * @return boolean
   * @roseuid 3EAECE1602FB
   */
  public boolean isItDirty() {
    return itDirty;
  }

  /**
   * @param itNew
   * @roseuid 3EAECE1603A7
   */
  public void setItNew(boolean itNew) {
    this.itNew = itNew;
  }

  /**
   * @return boolean
   * @roseuid 3EAECE170146
   */
  public boolean isItNew() {
    return itNew;
  }

  /**
   * @param itDelete
   * @roseuid 3EAECE1701F2
   */
  public void setItDelete(boolean itDelete) {
    this.itDelete = itDelete;

  }

  /**
   * @return boolean
   * @roseuid 3EAECE17030B
   */
  public boolean isItDelete() {
    return itDelete;
  }

  /**
   * Gets TreatmentDT
   * @return : TreatmentDT
   */
  public TreatmentDT getTheTreatmentDT() {
    return theTreatmentDT;
  }

  /**
   * sets theTreatmentDT
   * @param theTreatmentDT
   */
  public void setTheTreatmentDT(TreatmentDT theTreatmentDT) {
    this.theTreatmentDT = theTreatmentDT;
    setItDirty(true);
  }

  /**
   * gets Procedure1DTCollection
   * @return : Collection
   */
  public Collection<Object> getTheTreatmentProcedureDTCollection() {
    return theTreatmentProcedureDTCollection;
  }

  /**
   * sets theProcedure1DT
   * @param theProcedure1DT : Collection
   */
  public void setTheTreatmentProcedureDTCollection(Collection<Object> theProcedure1DT) {
    this.theTreatmentProcedureDTCollection  = theProcedure1DT;
    setItDirty(true);
  }

  /**
   * gets SubstanceAdministrationDTCollection
   * @return : Collection
   */
  public Collection<Object> getTheTreatmentAdministeredDTCollection() {
    return theTreatmentAdmDTCollection;
  }

  /**
   * sets theTreatmentAdministeredDT
   * @param theTreatmentAdministeredDT : Collection
   */
  public void setTheTreatmentAdministeredDTCollection(Collection<Object> theTreatmentAdministeredDT) {
    this.theTreatmentAdmDTCollection  = theTreatmentAdministeredDT;
    setItDirty(true);
  }

  /**
   * gets ActIdDTCollection
   * @return : Collection
   */
  public Collection<Object> getTheActIdDTCollection() {
    return theActIdDTCollection;
  }

  /**
   * sets ActDTCollection
   * @param theActIdDTCollection  : Collection
   */
  public void setTheActIdDTCollection(Collection<Object> theActIdDTCollection) {
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
  public void setTheActivityLocatorParticipationDTCollection(Collection<Object>
      theActivityLocatorParticipationDTCollection) {
    this.theActivityLocatorParticipationDTCollection  =
        theActivityLocatorParticipationDTCollection;
    setItDirty(true);
  }

  /**
   * gets ParticipationDTCollection
   * @return : Collection
   */
  public Collection<Object> getTheParticipationDTCollection() {
    return theParticipationDTCollection;
  }

  /**
   * sets aTheParticipationDTCollection
   * @param aTheParticipationDTCollection  : Collection
   */
  public void setTheParticipationDTCollection(Collection<Object>
                                              aTheParticipationDTCollection) {
    theParticipationDTCollection  = aTheParticipationDTCollection;
    setItDirty(true);
  }

  /**
   * gets ActRelationshipDTCollection
   * @return : Collection
   */
  public Collection<Object> getTheActRelationshipDTCollection() {
    return theActRelationshipDTCollection;
  }

  /**
   * sets ActRelationshipDTCollection
   * @param aTheActRelationshipDTCollection  : Collection
   */
  public void setTheActRelationshipDTCollection(Collection<Object>
                                                aTheActRelationshipDTCollection) {
    theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    setItDirty(true);
  }

  /**
   * gets ActIdDT_s
   * @return : ActIdDT
   */
  public ActIdDT getActIdDT_s(int index) {
    /** @todo move to const */
    if (this.theActIdDTCollection  == null)
      this.theActIdDTCollection  = new ArrayList<Object> ();

    int currentSize = this.theActIdDTCollection.size();

    // check if we have this many DTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.theActIdDTCollection.toArray();

        Object tempObj = tempArray[index];

        ActIdDT tempDT = (ActIdDT) tempObj;

        return tempDT;
      }
      catch (Exception e) {

      } // do nothing just continue
    }

    ActIdDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new ActIdDT();
      tempDT.setItNew(true);
      this.theActIdDTCollection.add(tempDT);
    }

    return tempDT;
  }

  /**
   * gets ActLocatorParticipationDT_s
   * @return : ActLocatorParticipationDT
   */
  public ActivityLocatorParticipationDT getActLocatorParticipationDT_s(int
      index) {
    /** @todo move to const */
    if (this.theActivityLocatorParticipationDTCollection  == null)
      this.theActivityLocatorParticipationDTCollection  = new ArrayList<Object> ();

    int currentSize = this.theActivityLocatorParticipationDTCollection.size();

    // check if we have this many DTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.theActivityLocatorParticipationDTCollection.
            toArray();

        Object tempObj = tempArray[index];

        ActivityLocatorParticipationDT tempDT = (ActivityLocatorParticipationDT)
            tempObj;

        return tempDT;
      }
      catch (Exception e) { /** @todo  Add catch logic*/
      } // do nothing just continue
    }

    ActivityLocatorParticipationDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new ActivityLocatorParticipationDT();
      tempDT.setItNew(true); /** @todo move to const */
      this.theActivityLocatorParticipationDTCollection.add(tempDT);
    }

    return tempDT;
  }

  /**
   * gets Procedure1DT_s
   * @return : Procedure1DT
   */
  public TreatmentProcedureDT getTreatmentProcedureDT_s(int index) {
    /** @todo move to const */
    if (this.theTreatmentProcedureDTCollection  == null)
      this.theTreatmentProcedureDTCollection  = new ArrayList<Object> ();

    int currentSize = this.theTreatmentProcedureDTCollection.size();

    // check if we have this many DTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.theTreatmentProcedureDTCollection.toArray();

        Object tempObj = tempArray[index];

        TreatmentProcedureDT tempDT = (TreatmentProcedureDT) tempObj;

        return tempDT;
      }
      catch (Exception e) {

      } // do nothing just continue
    }

    TreatmentProcedureDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new TreatmentProcedureDT();
      tempDT.setItNew(true); /** @todo move to const */
      this.theTreatmentProcedureDTCollection.add(tempDT);
    }

    return tempDT;
  }

  /**
   * gets SubstanceAdministrationDT_s
   * @return : SubstanceAdministrationDT
   */
  public TreatmentAdministeredDT getTreatmentAdministeredDT_s(int index) {
    /** @todo move to const */
    if (this.theTreatmentAdmDTCollection  == null)
      this.theTreatmentAdmDTCollection  = new ArrayList<Object> ();

    int currentSize = this.theTreatmentAdmDTCollection.size();

    // check if we have a this many DTs
    if (index < currentSize) {
      try {
        Object[] tempArray = this.theTreatmentAdmDTCollection.toArray();

        Object tempObj = tempArray[index];

        TreatmentAdministeredDT tempDT = (TreatmentAdministeredDT) tempObj;

        return tempDT;
      }
      catch (Exception e) {
        /** @todo  Add catch logic*/
      } // do nothing just continue
    }

    TreatmentAdministeredDT tempDT = null;

    for (int i = currentSize; i < index + 1; i++) {
      tempDT = new TreatmentAdministeredDT();
      tempDT.setItNew(true); /** @todo move to const */
      this.theTreatmentAdmDTCollection.add(tempDT);
    }

    return tempDT;
  }


}